package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.dao.OrderDao;
import com.grape.supermarket.dao.OrderDetailDao;
import com.grape.supermarket.dao.ShopCommodityDao;
import com.grape.supermarket.entity.db.OrderEntity;
import com.grape.supermarket.entity.db.ShopCommodityEntity;
import com.grape.supermarket.entity.page.OrderDetailPageEntity;
import com.grape.supermarket.operator.service.OrderDetailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * author huanghuang
 * 2017年11月17日 下午2:32:39
 */
@Service
public class OrderDetailServiceImpl extends BasicService implements OrderDetailService {
    private Logger logger = Logger.getLogger(getClass());
    
    @Resource
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private ShopCommodityDao shopCommodityDao;
    
    @Resource
    private OrderDao orderDao;

	@Override
	public List<OrderDetailPageEntity> selectOrderDetailByOrderId(
			Integer orderId) {
		if(orderId == null){
			logger.info("orderId为空->orderId:\t"+orderId);
			return null;
		}
		OrderEntity order = orderDao.selectByPrimaryKey(orderId);
		 List<OrderDetailPageEntity> list = new ArrayList<>();
		 List<OrderDetailPageEntity> odpList = orderDetailDao.selectOrderDetailByOrderId(orderId);//得到订单对应的商品
         Map<String,OrderDetailPageEntity> maps = new HashMap<String, OrderDetailPageEntity>();
         if(odpList!=null&&!odpList.isEmpty()){
			for (OrderDetailPageEntity odp : odpList) {
				ShopCommodityEntity sc = shopCommodityDao.selectByCommId(odp
						.getCommId());
				if (sc != null) {
					odp.setShopId(sc.getShopId());// 得到商店的ID
				}
				odp.setCreateTime(order.getCreateTime());
				odp.setCommPrice(odp.getPayment());
				String key = odp.getOrderId() + "_" + odp.getCommId();
				odp.setCommCount(1);
				if (maps.containsKey(key)) {
					odp = maps.get(key);
					Integer count = odp.getCommCount() + 1;
					odp.setPayment((odp.getPayment() / odp.getCommCount())
							* count);// 付款金额
					odp.setPreferential((odp.getPreferential() / odp
							.getCommCount()) * count);// 优惠金额
					odp.setCommCount(count);// 商品数量加1
				}
				maps.put(key, odp);
			}
         }
        list.addAll(new ArrayList<OrderDetailPageEntity>(maps.values()));
		return list;
	}

    @Override
    public List<OrderDetailPageEntity> selectOrderDetailByOrderId(String paymentId) {
        OrderEntity order = orderDao.selectByPaymentId(paymentId);
        if(order == null){
            return null;
        }
        return selectOrderDetailByOrderId(order.getOrderId());
    }
}
