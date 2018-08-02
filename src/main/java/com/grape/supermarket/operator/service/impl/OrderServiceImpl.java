package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.WechatSessionHelper;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.ExcelUtil;
import com.grape.supermarket.dao.*;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.*;
import com.grape.supermarket.entity.page.*;
import com.grape.supermarket.entity.param.DepotParam;
import com.grape.supermarket.entity.param.FinanceDetailParam;
import com.grape.supermarket.entity.param.OrderParam;
import com.grape.supermarket.event.ChangeElecTagFailEvent;
import com.grape.supermarket.event.PayCancelEvent;
import com.grape.supermarket.event.PaySuccessEvent;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.CountPriceService;
import com.grape.supermarket.operator.service.DepotService;
import com.grape.supermarket.operator.service.OrderService;
import com.grape.supermarket.wechat.WechatSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LQW on 2017/10/18.
 */
@Service
public class OrderServiceImpl extends BasicService implements OrderService {
    private Logger logger = Logger.getLogger(getClass());

    @Resource
    private OrderDao orderDao;
    @Resource
    private ShopDao shopDao;
    @Resource
    private OrderDetailDao orderDetailDao;
    @Resource
    private DepotDao depotDao;
    @Resource
    private CustomerDao customerDao;
    @Autowired
    private ElecTagDao elecTagDao;
    @Autowired
    private CommodityTypeDao commodityTypeDao;
    @Autowired
    private DepotService depotService;
    @Autowired
    private CountPriceService countPriceService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public List<OrderPageEntity> wechatSelectOrderByCoustId(PageParam pageParam) {
        OrderParam orderParam = new OrderParam();
        if (pageParam != null) {
            SelectEntity select = orderParam.getSelect();
            if (select == null) {
                select = new SelectEntity();
            }
            select.setLimit(pageParam.getLimit());
            orderParam.setSelect(select);
        }
        WechatSession session = WechatSessionHelper.getSessionOrThrow();
        Integer coustId = session.getCoustId();
        orderParam.setCoustId(coustId);
        List<OrderEntity> orderList = orderDao.selectOrderByCoustId(orderParam);
        List<OrderPageEntity> list = new ArrayList<>();
        if (orderList != null && orderList.size() > 0) {
            try {
                for (OrderEntity order : orderList) {
                    OrderPageEntity orderPageEntity = new OrderPageEntity();
                    BeanUtils.copyProperties(orderPageEntity, order);
                    Integer orderId = order.getOrderId();
                    Integer shopId = order.getShopId();
                    if (shopId != null) {
                        ShopEntity shopEntity = shopDao.selectByPrimaryKey(shopId);
                        orderPageEntity.setShopEntity(shopEntity);
                    }
                    if (orderId != null) {
                        List<OrderDetailPageEntity> odpList = orderDetailDao.selectOrderDetailByOrderIdGroupComm(orderId);
                        orderPageEntity.setOrderDetail(odpList);
                    }
                    list.add(orderPageEntity);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BeanCopyException(e);
            }
        }


        return list;
    }

    @Override
    public int wechatcountOrderByCoustId() {
        int t = 0;
        WechatSession session = WechatSessionHelper.getSessionOrThrow();
        Integer coustId = session.getCoustId();
        if (coustId != null) {
            t = orderDao.countOrderByCoustId(coustId);
        }
        return t;
    }

    @Override
    public boolean wechatDeleteById(Integer orderId) {
        int t = 0;
        if (orderId != null) {
            OrderEntity order = new OrderEntity();
            order.setOrderId(orderId);
            order.setDeleteMark((byte) 1);
            t = orderDao.updateByPrimaryKeySelective(order);
        }
        return t > 0;
    }

    @EventListener
    @Override
    public void paySuccessEvent(PaySuccessEvent event) {
        PaySuccessEvent.PayData payData = (PaySuccessEvent.PayData) event.getSource();
        OrderEntity orderEntity = orderDao.selectByPaymentId(payData.getPaymentId());
        if (orderEntity == null) {
            getLogger().error("查找目标订单失败,支付数据->" + payData);
            return;
        }

        List<OrderDetailPageEntity> commoditys = orderDetailDao.selectOrderDetailByOrderId(orderEntity.getOrderId());
        OrderDetailPageEntity commodity = null;

        ElecTagEntity record = new ElecTagEntity();
        record.setState((byte) 1);

        for (OrderDetailPageEntity orderDetailPageEntity : commoditys) {
            commodity = orderDetailPageEntity;
            //捕获修改库存异常
            try {
                depotDao.reduceCommodity(orderEntity.getShopId(), orderDetailPageEntity.getCommId());
            } catch (Exception e) {
                logger.error("减少库存发生异常,data->" + commodity, e);
            }
            //捕获修改电子标签状态异常，防止出现修改失败无法出门情况
            try {
                record.setSellTime(new Date());
                record.setElecId(commodity.getElecId());
                elecTagDao.updateByPrimaryKeySelective(record);
            } catch (Exception e) {
                ChangeElecTagFailEvent cetfe = new ChangeElecTagFailEvent(commodity.getElecId());
                publisher.publishEvent(cetfe);
                logger.error("修改电子标签为出售状态失败,data->" + commodity, e);
            }
        }

        //最后修改订单表数据，防止数据库连接异常导致问题
        OrderEntity upOe = new OrderEntity();
        upOe.setOrderId(orderEntity.getOrderId());
        upOe.setExternalId(payData.getExternalId());
        upOe.setPaymentType(payData.getPaymentType());
        upOe.setState((byte) 1);
        if (payData.getCoustId() != null) {
            upOe.setCoustId(payData.getCoustId());
        }
        try {
            orderDao.updateByPrimaryKeySelective(upOe);
        } catch (Exception e) {
            logger.error("修改订单状态失败,pay data->" + payData, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FinancePageEntity> financeStatistics(FinancePageEntity param) {
        if (param == null) {
            param = new FinancePageEntity();
        }
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        param.setIdRange(operatorSession.getShopIds());
        List<FinancePageEntity> list = orderDao.financeStatistics(param);
        return list;
    }

    @Override
    public List<FinanceDetailPageEntity> financeStatisticsDetail(FinanceDetailParam param, PageParam page) {
        if (page != null) {
            SelectEntity select = param.getSelect();
            if (select == null) {
                select = new SelectEntity();
                param.setSelect(select);
            }
            select.setLimit(page.getLimit());
        }
        return orderDetailDao.financeStatisticsDetail(param);
    }

    @Override
    public int countStatisticsDetail(FinanceDetailParam param) {
        return orderDetailDao.countStatisticsDetail(param);
    }

    @Override
    public void exportFinanceStatisticsDetail(Integer shopId, Date startDate, Date endDate, HttpServletResponse response){
        ShopEntity shop = shopDao.selectByPrimaryKey(shopId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        FinanceDetailParam param = new FinanceDetailParam();
        param.setShopId(shopId);
        param.setStartTime(sdf.format(startDate));
        param.setEndTime(sdf.format(endDate)+" 23:59:59");
        List<FinanceDetailPageEntity> finance = financeStatisticsDetail(param, null);
        Map<String, FinanceDetailPageEntity> mergeMap = new TreeMap<>();
        for (FinanceDetailPageEntity financeDetail : finance) {
            //计算进价
            int cost = (financeDetail.getPayments() - financeDetail.getPreferentials() - financeDetail.getGrossProfits())
                    / financeDetail.getSellNum();
            //计算销售单价
            int sell = (financeDetail.getPayments()) / financeDetail.getSellNum();

            String mergeKey = financeDetail.getBarcode() + cost + sell;
            FinanceDetailPageEntity temp = mergeMap.get(mergeKey);
            if (temp == null) {
                mergeMap.put(mergeKey, financeDetail);
            } else {
                //合并数量
                temp.setSellNum(temp.getSellNum() + financeDetail.getSellNum());
                //合并优惠金额
                temp.setPreferentials(temp.getPreferentials() + financeDetail.getPreferentials());
                //合并售出金额
                temp.setPayments(temp.getPayments() + financeDetail.getPayments());
                //合并毛利
                temp.setGrossProfits(temp.getGrossProfits() + financeDetail.getGrossProfits());
            }
        }
        List<FinanceDetailPageEntity> mergeFinance = new ArrayList<>(mergeMap.values());
        String title = shop != null ? "站点：" + shop.getShopName()+"  " : "";
        title += "时间：" + sdf.format(startDate) + "-" + sdf.format(endDate);
        List<String> header = Arrays.asList("序号", "名称", "商品类型", "条码", "进价", "销售单价", "数量", "销售金额", "销售毛利", "毛利率");

        List<String[]> data = new ArrayList<>(mergeFinance.size());
        Set<Integer> sellCommId = new TreeSet<>();
        for (int i = 0,z = mergeFinance.size(); i < z; i++) {
            FinanceDetailPageEntity entity = mergeFinance.get(i);
            int cost = (entity.getPayments() - entity.getPreferentials() - entity.getGrossProfits())
                    / entity.getSellNum();
            //计算销售单价
            int sell = (entity.getPayments()) / entity.getSellNum();
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数 
            String[] row = new String[10];
            row[0] = "" + (i + 1);
            row[1] = "" + entity.getCommName();
            row[2] = "" + entity.getTypeName();//商品类型
            row[3] = "" + entity.getBarcode();
            row[4] = "" + (cost / 100.0);//进价
            row[5] = "" + (sell / 100.0);//销售单价
            row[6] = "" + entity.getSellNum();//销售数量
            row[7] = "" + ((entity.getPayments() - entity.getPreferentials()) / 100.0);//销售金额
            row[8] = "" + (entity.getGrossProfits() / 100.0);
            if(((entity.getPayments() - entity.getPreferentials()) / 100.0) != 0){
            	row[9] = "" + df.format((float)(((entity.getGrossProfits() / 100.0) / ((entity.getPayments() - entity.getPreferentials()) / 100.0)) * 100)) + "%";//毛利率
            }else{
            	row[9] = "" + df.format((float)((entity.getGrossProfits() / 100.0) * 100)) + "%";//毛利率
            }
            

            data.add(row);
            sellCommId.add(entity.getCommId());
        }

        int index = data.size() + 1;//0售出商品起始序号
        //查询销售记录为0的商品
        List<CommodityEntity> zeroSellCommodity = getZeroSellCommodity(shopId, sellCommId);
        for (CommodityEntity commodityEntity : zeroSellCommodity) {
        	CommodityTypeEntity commType = commodityTypeDao.selectByPrimaryKey(commodityEntity.getTypeId());
            String[] strings = new String[10];
            strings[0] = "" + index;
            strings[1] = "" + commodityEntity.getCommName();
            strings[2] = "" + commType.getTypeName();//商品类型
            strings[3] = "" + commodityEntity.getBarcode();
            strings[4] = "" + (commodityEntity.getCostPrice() / 100.0);//进价
            strings[5] = "" + (commodityEntity.getCommPrice() / 100.0);//销售单价
            strings[6] = "0";//销售数量
            strings[7] = "0";
            strings[8] = "0";
            strings[9] = "0.00%";//毛利率
            index++;
            data.add(strings);
        }

        response.setHeader("Content-disposition", "attachment;filename=" +
                "finance"+sdf.format(startDate) + "-" + sdf.format(endDate)+".xls");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try(OutputStream out = response.getOutputStream()) {
            ExcelUtil.createExcel(title, header, data, out);//输出excel
        } catch (IOException e) {
            logger.warn("导出财经excel失败", e);
        }
    }

    private List<CommodityEntity> getZeroSellCommodity(Integer shopId, Set<Integer> existsCommid) {
        DepotParam param = new DepotParam();
        param.setShopId(shopId);
        param.setState((byte) 0);//只统计在售商品
        List<DepotEntity> depotEntities = depotService.selectByCondition(param, null);
        List<CommodityEntity> data = new ArrayList<>();
        for (DepotEntity depotEntity : depotEntities) {
            if (!existsCommid.contains(depotEntity.getCommId())) {
                CommodityPageEntity entity = countPriceService.getCommodityAndRealPrice(depotEntity.getCommId(), shopId);
                data.add(entity);
            }
        }
        return data;
    }

    @Override
    public List<OrderPageEntity> selectOrderByParam(OrderParam param, PageParam page) {
        try {
            SelectEntity select = new SelectEntity();
            if (page != null) {
                int[] limit = page.getLimit();
                select.setLimit(limit);
            }
            param.setSelect(select);
            List<OrderEntity> orders = orderDao.selectOrderByParam(param);
            List<OrderPageEntity> datas = new ArrayList<>(orders.size());
            for (OrderEntity order : orders) {
                OrderPageEntity ope = new OrderPageEntity();
                BeanUtils.copyProperties(ope, order);
                //得到顾客
                CustomerEntity customer = customerDao.selectByPrimaryKey(ope.getCoustId());
                ope.setCustomer(customer);
                //得到站点
                ShopEntity shop = shopDao.selectByPrimaryKey(ope.getShopId());
                ope.setShopEntity(shop);
                datas.add(ope);
            }
            return datas;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByParam(OrderParam param) {
        return orderDao.countByParam(param);
    }

    @Override
    public OrderPageEntity selectByPrimaryKey(Integer orderId) {
        if (orderId == null) {
            logger.info("orderId为空");
            return null;
        }
        OrderEntity order = orderDao.selectByPrimaryKey(orderId);
        OrderPageEntity ope = new OrderPageEntity();
        try {
            BeanUtils.copyProperties(ope, order);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanCopyException(e);
        }
        return ope;
    }

    @Override
    public OrderEntity selectByPaymentId(String paymentId) {
        return orderDao.selectByPaymentId(paymentId);
    }

    @Override
    public boolean cancelOrder(String paymentId) {
        OrderEntity orderEntity = orderDao.selectByPaymentId(paymentId);
        if (orderEntity != null && orderEntity.getState() == 0) {
            OrderEntity upe = new OrderEntity();
            upe.setState((byte) 2);
            upe.setOrderId(orderEntity.getOrderId());

            int r = orderDao.atomicUpdate(upe, orderEntity.getVersion());
            int times = 0;
            while (r == 0 && orderEntity.getState() == 0) {
                if (times == 5) {
                    times = -1;
                    break;
                }
                orderEntity = orderDao.selectByPaymentId(paymentId);
                r = orderDao.atomicUpdate(upe, orderEntity.getVersion());
                times++;
            }
            if (times == -1) {
                logger.error("关闭订单[" + paymentId + "]修改数据库状态失败，最后查询结果" + orderEntity);
                return false;
            }
            PayCancelEvent.PayCancelData pcd = new PayCancelEvent.PayCancelData();
            pcd.setPaymentId(paymentId);
            publisher.publishEvent(new PayCancelEvent(pcd));
            return true;
        }else if(orderEntity != null && (orderEntity.getState() == 2 || orderEntity.getState() == 1)){
            getLogger().info("关闭订单操作，订单[" + paymentId + "]已经支付成功");
            return true;
        } else {
            return false;
        }
    }
    

    @Override
     public void exportSaleStatisticsDetail(OrderParam param, HttpServletResponse response) {
    	List<OrderPageEntity> datas = selectOrderByParam(param, null);
    	String name = encodingFileName("销售详情");
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("content-disposition", "attachment; filename=" + name
        		+ ".xls");
        OutputStream out =null;
        DecimalFormat df = new DecimalFormat("0.00");
        try {
			out = new BufferedOutputStream(response.getOutputStream());
			List<String> header = new ArrayList<String>();
	        header.add("序号");
	        header.add("站点名称");
	        header.add("付款总额");
	        header.add("毛利");
	        header.add("优惠金额");
	        List<String[]> data = new ArrayList<>(datas.size());
	        for(int i = 0;i<datas.size();i++){
	        	OrderPageEntity ope = datas.get(i);
	        	String[] s = {""+(i+1),ope.getShopEntity().getShopName(),""+df.format(((float)ope.getPayment()/100))
	        			,""+df.format(((float)ope.getGrossProfit()/100)),""+df.format(((float)ope.getPreferential()/100))};
	        	data.add(s);
	        }
	        ExcelUtil.createExcel(header,data,out);
		} catch (IOException e) {
			logger.info("下载销售详情文件异常", e);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
     }
    //解决中文乱码问题 
     private String encodingFileName(String name) {
        String returnFileName = "";
        try {
            returnFileName = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnFileName;
    }
}
