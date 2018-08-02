package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.DBException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.*;
import com.grape.supermarket.device.service.BarcodeService;
import com.grape.supermarket.entity.*;
import com.grape.supermarket.entity.db.*;
import com.grape.supermarket.entity.discount.DirectCut;
import com.grape.supermarket.entity.discount.FullCut;
import com.grape.supermarket.entity.discount.NumCut;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.entity.param.DiscountParam;
import com.grape.supermarket.operator.service.CountPriceService;
import com.grape.supermarket.wechat.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Service
public class CountPriceServiceImpl extends BasicService implements CountPriceService {
    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private ShopCommodityDao shopCommodityDao;

    @Autowired
    private DiscountDao discountDao;

    @Autowired
    private DepotDao depotDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private BarcodeService barcodeService;

    @Autowired
    private ShopDiscountGroupDao shopDiscountGroupDao;

    @Autowired
    private PayService payService;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public PreOrder createPreOrderByEpc(Set<String> epcs, int shopId) {
        List<CommodityPriceInfo> commodity = convertCommodity(new ArrayList<>(epcs));
        List<CommodityPriceInfo> commodityPriceInfo = new ArrayList<>();
        DepotEntity depotSelect = new DepotEntity();
        depotSelect.setShopId(shopId);

        boolean canShop = true;
        int countPrice = 0;
        //初始化商品信息，装载商品数据
        for (CommodityPriceInfo cpi : commodity) {
            if(cpi.getState() == 0) {
                //计算是否可销售
                depotSelect.setCommId(cpi.getCommId());
                DepotEntity depot = depotDao.selectByShopIdAanCommId(depotSelect);
                if (depot != null && depot.getState() == 0) {//可以销售，继续计算价格
                    //计算商品真实价格
                    CommodityEntity priceInfo = getCommodityAndRealPrice(cpi.getCommId(), shopId);
                    cpi.setPrice(priceInfo.getCommPrice());
                } else {
                    canShop = false;
                    cpi.setState(-1);
                }
                countPrice += cpi.getPrice();//计算总价
            }
            commodityPriceInfo.add(cpi);
        }

        Collections.sort(commodityPriceInfo, new Comparator<CommodityPriceInfo>() {
            @Override
            public int compare(CommodityPriceInfo o1, CommodityPriceInfo o2) {
                return o1.getBarcode().compareTo(o2.getBarcode());
            }
        });

        List<CommodityPriceInfo> cpi = commodityPriceInfo;
        //计算折扣
        int preferential = 0;
        if (canShop) {//可销售状态，计算折扣
            ShopDiscountGroupEntity sdge = shopDiscountGroupDao.selectNowDiscountGroup(shopId);
            if(sdge != null){
                Integer groupId = sdge.getGroupId();
                DiscountParam param = new DiscountParam();
                param.setGroupId(groupId);
                List<DiscountEntity> des = discountDao.selectByParam(param);
                PreferentialCalculator pc = new PreferentialCalculator(des);
                PreferentialCalculator.PreferentialInfo preferentialInfo = pc.getPreferential(commodityPriceInfo);
                cpi = preferentialInfo.getCommodityPriceInfo();
                preferential = preferentialInfo.getPreferential();
            }
        }

        PreOrder preOrder = new PreOrder();
        preOrder.setPayment(countPrice);
        preOrder.setPreferential(preferential);
        preOrder.setCommodityPriceInfos(cpi);
        preOrder.setCode(canShop ? 0 : 1);
        return preOrder;
    }

    @Override
    public PayBarcode createPayBarcode(Set<String> epcs, Integer shopId, PreOrder po) {
        PayBarcode payBarcode = new PayBarcode();
        //计算订单详情
        PreOrder orderInfo = createPreOrderByEpc(epcs, shopId);
        List<CommodityPriceInfo> commodityPriceInfos = orderInfo.getCommodityPriceInfos();

        //插入订单详情和计算商品进价
        List<OrderDetailEntity> orderDetailList = new ArrayList<>(commodityPriceInfos.size());
        int countCostPrice = 0;
        for (CommodityPriceInfo commodityPriceInfo : commodityPriceInfos) {
            if (commodityPriceInfo.getState() == 0) {
                CommodityEntity commodityInfo = commodityDao.selectByPrimaryKey(commodityPriceInfo.getCommId());

                OrderDetailEntity ode = new OrderDetailEntity();
                ode.setCommId(commodityPriceInfo.getCommId());
                ode.setGrossProfit(commodityPriceInfo.getPrice() - commodityPriceInfo.getPreferential() - commodityInfo.getCostPrice());//毛利
                ode.setPreferential(commodityPriceInfo.getPreferential());//优惠金额
                ode.setPayment(commodityPriceInfo.getPrice());//付款金额
                ode.setElecId(commodityPriceInfo.getElecId());
                orderDetailList.add(ode);

                countCostPrice += commodityInfo.getCostPrice();
            }
        }
        //生成内部订单id
        String paymentId = UUID.randomUUID().toString().replace("-","");

        //插入总订单
        OrderEntity oe = new OrderEntity();
        oe.setShopId(shopId);
        oe.setPayment(po.getPayment());
        oe.setPreferential(po.getPreferential());
        oe.setPaymentId(paymentId);
        //订单支付金额-订单优惠金额-总商品进价 得到毛利
        oe.setGrossProfit(po.getPayment() - po.getPreferential() - countCostPrice);
        orderDao.insertSelective(oe);
        Integer orderId = oe.getOrderId();
        if(orderId == null){
            throw new DBException("插入order数据失败,data->"+oe);
        }

        //插入订单详情
        for (OrderDetailEntity orderDetail : orderDetailList) {
            orderDetail.setOrderId(orderId);
            orderDetailDao.insertSelective(orderDetail);
        }

        //订单总金额和优惠金额
        payBarcode.setPreferential(po.getPreferential());
        payBarcode.setPayment(po.getPayment());

        //生成支付二维码
        int barcodeCount = 0;
        PayEntity payEntity = new PayEntity();
        payEntity.setPayment(po.getPayment());
        payEntity.setPreferential(po.getPreferential());
        payEntity.setPaymentId(paymentId);
        PayOrderInfoEntity wechatBarcodeInfo = null;
        try {
            wechatBarcodeInfo = payService.payBarcode(payEntity);
            barcodeCount++;
        } catch (Exception e) {
            getLogger().error("生成微信二维码发生异常",e);
        }

        payBarcode.setPaymentId(paymentId);
        if(wechatBarcodeInfo != null) {
            payBarcode.setWechat(wechatBarcodeInfo.getBarcode());
        }
        if(barcodeCount < 1){
            throw new RuntimeException("生成支付订单失败");
        }
        payBarcode.setCode(0);
        return payBarcode;
    }

    @Override
    public CommodityPageEntity getCommodityAndRealPrice(Integer commId, Integer shopId) {
        CommodityEntity commodity = commodityDao.selectByPrimaryKey(commId);
        if(commodity != null){
            CommodityPageEntity ce = new CommodityPageEntity();
            BeanUtils.copyProperties(commodity, ce);
            if (shopId != null) {
                ShopCommodityKey shopCommoditySelect = new ShopCommodityKey();
                shopCommoditySelect.setCommId(commId);
                shopCommoditySelect.setShopId(shopId);
                ShopCommodityEntity sc = shopCommodityDao.selectByPrimaryKey(shopCommoditySelect);
                if(sc != null){
                    ce.setCommPrice(sc.getPrice());
                }
            }
            PictureEntity param = new PictureEntity();
            param.setType((byte) 1);
            param.setRecno(ce.getCommId());
            List<PictureEntity> picture = pictureDao.selectByParam(param);
            ce.setPicture(picture);

            return ce;
        }
        return null;
    }

    private static class PreferentialCalculator {
        private List<FullCut> fullCuts;
        private Map<String, Object> commCut;//商品优惠以id字符串为key，类型优惠以type_+类型id字符串为key

        public PreferentialCalculator(List<DiscountEntity> discounts) {
            fullCuts = new ArrayList<>();
            commCut = new HashMap<>();
            for (DiscountEntity discount : discounts) {
                Object disCountObj = null;
                if (discount.getDiscountType() == FullCut.typeId) {
                    FullCut fc = JsonUtils.fromJson(discount.getData(), FullCut.class);
                    disCountObj = fc;
                } else if (discount.getDiscountType() == NumCut.typeId) {
                    NumCut nc = JsonUtils.fromJson(discount.getData(), NumCut.class);
                    disCountObj = nc;
                } else if (discount.getDiscountType() == DirectCut.typeId) {
                    DirectCut dc = JsonUtils.fromJson(discount.getData(), DirectCut.class);
                    disCountObj = dc;
                }
                //装填折扣数据
                if (disCountObj != null) {
                    if (discount.getCommId() != null) {
                        commCut.put(""+discount.getCommId(), disCountObj);
                    } else if (discount.getTypeId() != null) {
                        commCut.put("type_"+discount.getTypeId(), disCountObj);
                    }else if(disCountObj instanceof FullCut){
                        fullCuts.add(((FullCut) disCountObj));
                    }
                }
            }
            Collections.sort(fullCuts, new Comparator<FullCut>() {
                @Override
                public int compare(FullCut o1, FullCut o2) {
                    return o1.getMaxPrice() - o2.getMaxPrice();
                }
            });
        }

        public PreferentialInfo getPreferential(List<CommodityPriceInfo> data) {
            List<CommodityPriceInfo> commodityPriceInfos = getClone(data);
            int countPreferential = 0;
            int count = 0;
            BigDecimal b100 = new BigDecimal(100);
            //计算普通折扣
            Map<Integer, List<CommodityPriceInfo>> times = new HashMap<>();
            Map<Integer, Integer> prices = new HashMap<>();
//            Map<Integer,Integer> fullCutId = new HashMap<>();
            for (CommodityPriceInfo commodity : commodityPriceInfos) {
                if(commodity.getState() != 0){//检查是否为可售商品
                    continue;
                }
                count += commodity.getPrice();//计算总价

                //计算商品出现次数
                List<CommodityPriceInfo> timeData = times.get(commodity.getCommId());
                if (timeData == null) {
                    timeData = new LinkedList<>();
                }
                timeData.add(commodity);
                times.put(commodity.getCommId(), timeData);


                //计算单个商品总价
                Integer commPrice = prices.get(commodity.getCommId());
                if(commPrice == null){
                    commPrice = commodity.getPrice();
                    prices.put(commodity.getCommId(),commPrice);
                } else if (commPrice != -1) {
                    prices.put(commodity.getCommId(), commPrice + commodity.getPrice());
                }

                //查找商品折扣
                Object o = commCut.get("" + commodity.getCommId());
                //查找品类折扣,在查找不到商品折扣时
                if (o == null) {
                    o = commCut.get("type_" + commodity.getTypeId());
                    if (o == null) {
                        continue;
                    }
                }
                //解析和计算折扣
                if (o instanceof NumCut) {//满送折扣
                    NumCut nc = ((NumCut) o);
                    if (timeData.size() == nc.getMaxNum()) {
                        int preferential = nc.getNum() * commodity.getPrice();
                        countPreferential += preferential;
                        //插入优惠金额到商品价格信息中
                        int avgP = preferential / timeData.size();
                        for (CommodityPriceInfo cpi : timeData) {
                            cpi.setPreferential(cpi.getPreferential() + avgP);
                        }

                        timeData.clear();//重置次数为0
                    }
                } else if (o instanceof DirectCut) {//直接折扣，打折
                    DirectCut dc = ((DirectCut) o);
                    BigDecimal bd = BigDecimal.ONE.subtract(new BigDecimal(dc.getPercent()).divide(b100));
                    BigDecimal p = new BigDecimal(commodity.getPrice());
                    //优惠的金额
                    int preferential = p.multiply(bd).setScale(0, RoundingMode.HALF_EVEN).intValue();
                    countPreferential += preferential;
                    //插入优惠金额到商品价格信息中
                    commodity.setPreferential(commodity.getPreferential() + preferential);
                }
                /*else if(o instanceof FullCut){//商品或商品类型满减折扣
                    FullCut fc = ((FullCut) o);
                    Integer temp = prices.get(commodity.getCommId());
                    if(temp != null && temp != -1 && temp >= fc.getMaxPrice()){//存在总价，没有被计算过满减，且达到优惠条件
                        allPreferential += fc.getReducePrice();
                        prices.put(commodity.getCommId(),-1);
                        fullCutId.put(commodity.getCommId(), fc.getReducePrice());
                    }
                }*/
            }


            Integer fullCutPreferential = null;
            //计算总计满减折扣
            for (FullCut fullCut : fullCuts) {
                if (count - countPreferential >= fullCut.getMaxPrice()) {
                    fullCutPreferential = fullCut.getReducePrice();
                    countPreferential += fullCut.getReducePrice();
                    break;
                }
            }

            //满减优惠平均分配到每个商品的优惠价格中
            if (fullCutPreferential != null) {
                int avg = fullCutPreferential / commodityPriceInfos.size();
                for (CommodityPriceInfo commodityPriceInfo : commodityPriceInfos) {
                    commodityPriceInfo.setPreferential(commodityPriceInfo.getPreferential() + avg);
                }
            }

            PreferentialInfo preferentialInfo = new PreferentialInfo();
            preferentialInfo.setCommodityPriceInfo(commodityPriceInfos);
            preferentialInfo.setPreferential(countPreferential);
            return preferentialInfo;
        }

        private List<CommodityPriceInfo> getClone(List<CommodityPriceInfo> commodityPriceInfos){
            List<CommodityPriceInfo> clone = new ArrayList<>(commodityPriceInfos.size());
            for (CommodityPriceInfo commodityPriceInfo : commodityPriceInfos) {
                CommodityPriceInfo cpi = new CommodityPriceInfo();
                BeanUtils.copyProperties(commodityPriceInfo,cpi);

                clone.add(cpi);
            }

            return clone;
        }

        /**
         * 计算结果包装
         */
        public static class PreferentialInfo {
            private List<CommodityPriceInfo> commodityPriceInfo;
            private int preferential;

            public List<CommodityPriceInfo> getCommodityPriceInfo() {
                return commodityPriceInfo;
            }

            public void setCommodityPriceInfo(List<CommodityPriceInfo> commodityPriceInfo) {
                this.commodityPriceInfo = commodityPriceInfo;
            }

            public int getPreferential() {
                return preferential;
            }

            public void setPreferential(int preferential) {
                this.preferential = preferential;
            }
        }
    }

    /**
     * 电子标签转换为商品价格信息
     * @param epcs 电子标签
     * @return 商品价格信息
     *
     */
    private List<CommodityPriceInfo> convertCommodity(List<String> epcs) {
        List<CommodityPriceInfo> data = new ArrayList<>(epcs.size());
        for (String epc : epcs) {
            CommodityPriceInfo cpi = barcodeService.epcToCommodity(epc);
            if(cpi != null) {
                data.add(cpi);
            }
        }
        return data;
    }
}
