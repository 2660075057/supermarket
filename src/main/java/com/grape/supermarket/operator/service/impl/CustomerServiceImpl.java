package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.dao.*;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.*;
import com.grape.supermarket.entity.page.CustomerPageEntity;
import com.grape.supermarket.entity.page.OrderDetailPageEntity;
import com.grape.supermarket.entity.page.ShopLogPageEntity;
import com.grape.supermarket.entity.param.CustomerParam;
import com.grape.supermarket.entity.param.OrderParam;
import com.grape.supermarket.entity.param.ShopLogParam;
import com.grape.supermarket.operator.service.CustomerService;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by LXP on 2017/9/28.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ShopLogDao shopLogDao;
    @Resource
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopCommodityDao shopCommodityDao;

    @Override
    public CustomerEntity getCustomerByOpenId(String openId) {
        if (openId == null) {
            return null;
        }
        CustomerEntity customerEntity = customerDao.selectByOpenid(openId);
        if(customerEntity != null && customerEntity.getNickname() != null){
            customerEntity.setNickname(EmojiParser.parseToUnicode(customerEntity.getNickname()));
        }
        return customerEntity;
    }

    @Override
    public List<CustomerPageEntity> customerList(CustomerParam param, PageParam page) {
//        CustomerParam cp = new CustomerParam();
        try {
//            BeanUtils.copyProperties(cp, param);

            SelectEntity select = new SelectEntity();
            if (page != null) {
                int[] limit = page.getLimit();
                select.setLimit(limit);
            }
            param.setSelect(select);
            List<CustomerEntity> customers = customerDao.selectByParam(param);
            List<CustomerPageEntity> datas = new ArrayList<>(customers.size());

            for (CustomerEntity customer : customers) {//计算消费金额最后进店时间等其他参数
                if(customer.getNickname() != null){
                    customer.setNickname(EmojiParser.parseToUnicode(customer.getNickname()));
                }
                CustomerPageEntity cpe = new CustomerPageEntity();
                BeanUtils.copyProperties(cpe, customer);
                //计算消费
                Map<String, Long> countOrder = orderDao.countConsumption(customer.getCoustId(), null);
                Object paymentObj = 0;
                Object preferentialObj = 0;
                if (countOrder != null) {
                    paymentObj = countOrder.get("payment");
                    preferentialObj = countOrder.get("preferential");
                }
                cpe.setPaymentTotal(Long.parseLong(paymentObj.toString()));
                cpe.setPreferentialTotal(Long.parseLong(preferentialObj.toString()));
                //计算最后进店时间
                ShopLogEntity shopLogEntity = shopLogDao.selectLastcome(customer.getCoustId(), null);
                Date date = shopLogEntity != null ? shopLogEntity.getCreateTime() : null;
                cpe.setLastCome(date);
                datas.add(cpe);
            }
            return datas;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int countCustomer(CustomerParam param) {
        /*CustomerParam cp = new CustomerParam();
        try {
            BeanUtils.copyProperties(cp, param);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }*/
        return customerDao.countByParam(param);
    }

    @Override
    public CustomerEntity addCustomer(CustomerEntity param) {
        CustomerEntity customerEntity = customerDao.selectByOpenid(param.getOpenid());
        if(param.getNickname() != null){
            param.setNickname(EmojiParser.parseToAliases(param.getNickname()));
        }
        if (customerEntity == null) {
            customerDao.insertSelective(param);
        } else {
            param.setCoustId(customerEntity.getCoustId());
            customerDao.updateByPrimaryKeySelective(param);
        }

        return customerDao.selectByOpenid(param.getOpenid());
    }

    @Override
    public void updateCustomer(CustomerEntity param){
        if(param == null || param.getCoustId() == null){
            return ;
        }
        if(param.getNickname() != null){
            param.setNickname(EmojiParser.parseToAliases(param.getNickname()));
        }
        customerDao.updateByPrimaryKeySelective(param);
    }

    @Override
    public CustomerPageEntity selectByPrimaryKey(Integer coustId, Date startTime, Date endTime) {
        if (coustId == null) {//检验
            return null;
        }
        CustomerEntity customer = customerDao.selectByPrimaryKey(coustId);//得到顾客
        if (customer.getNickname() != null) {
            customer.setNickname(EmojiParser.parseToUnicode(customer.getNickname()));
        }
        CustomerPageEntity cpe = new CustomerPageEntity();
        try {
            BeanUtils.copyProperties(cpe, customer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        //计算消费
        Map<String, Long> countOrder = orderDao.countConsumption(customer.getCoustId(), null);
        Object paymentObj = 0;
        Object preferentialObj = 0;
        if (countOrder != null) {
            paymentObj = countOrder.get("payment");
            preferentialObj = countOrder.get("preferential");
        }
        cpe.setPaymentTotal(Long.parseLong(paymentObj.toString()));
        cpe.setPreferentialTotal(Long.parseLong(preferentialObj.toString()));
        //计算最后进店时间
        ShopLogEntity shopLogEntity = shopLogDao.selectLastcome(customer.getCoustId(), null);
        Date date = shopLogEntity != null ? shopLogEntity.getCreateTime() : null;
        cpe.setLastCome(date);
        //得到购买分析的数据
        OrderParam orderParam = new OrderParam();
        orderParam.setSelect(null);//不分页
        orderParam.setCoustId(coustId);
        orderParam.setStartTime(startTime);
        orderParam.setEndTime(endTime);
        List<OrderDetailPageEntity> list = new ArrayList<>();
        List<OrderEntity> orderList = orderDao.selectOrderByCoustId(orderParam);
        if (orderList != null && orderList.size() > 0) {
            for (OrderEntity order : orderList) {
                Integer orderId = order.getOrderId();
                if (orderId != null) {
                    List<OrderDetailPageEntity> odpList = orderDetailDao.selectOrderDetailByOrderId(orderId);//得到订单对应的商品
                    Map<String, OrderDetailPageEntity> maps = new HashMap<String, OrderDetailPageEntity>();
                    if (odpList != null && !odpList.isEmpty()) {
                        for (OrderDetailPageEntity odp : odpList) {
                            ShopCommodityEntity sc = shopCommodityDao.selectByCommId(odp.getCommId());
                            if (sc != null) {
                                odp.setShopId(sc.getShopId());//得到商店的ID
                                ShopEntity shop = shopDao.selectByPrimaryKey(sc.getShopId());
                                if (shop != null) {
                                    odp.setShopName(shop.getShopName());//得到商店名称
                                }
                            }
                            odp.setCreateTime(order.getCreateTime());
                            String key = odp.getOrderId() + "_" + odp.getCommId();
                            odp.setCommCount(1);
                            if (maps.containsKey(key)) {
                                odp = maps.get(key);
                                Integer count = odp.getCommCount() + 1;
                                odp.setPayment((odp.getPayment() / odp.getCommCount()) * count);//付款金额
                                odp.setPreferential((odp.getPreferential() / odp.getCommCount()) * count);//优惠金额
                                odp.setCommCount(count);//商品数量加1
                            }
                            maps.put(key, odp);
                        }
                    }
                    list.addAll(new ArrayList<>(maps.values()));
                }
            }
        }
        cpe.setOrders(list);
        //得到进店分析的数据
        ShopLogParam param = new ShopLogParam();
        param.setCoustId(coustId);
        param.setStartTime(startTime);
        param.setEndTime(endTime);
        List<ShopLogPageEntity> slpList = shopLogDao.selectAllIntoShop(param);
        cpe.setShopLogs(slpList);
        //得到所有商店
        List<ShopEntity> shops = new ArrayList<>();
        for (ShopLogPageEntity slp : slpList) {
            ShopEntity s = new ShopEntity();
            s.setShopId(slp.getShopId());
            s.setShopName(slp.getShopName());
            shops.add(s);
        }
        cpe.setShops(shops);
        return cpe;
    }

    @Override
    public void addShopLog(Integer coustId, Integer shopId, int flag, byte type) {
        ShopLogEntity record = new ShopLogEntity();
        record.setCoustId(coustId);
        record.setShopId(shopId);
        record.setType(type);
        if (flag == 0) {
            shopLogDao.insert(record);
        } else if (flag == 1) {
            //出店记录桩
        }
    }
}
