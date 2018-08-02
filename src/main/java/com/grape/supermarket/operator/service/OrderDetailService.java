package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.page.OrderDetailPageEntity;

import java.util.List;

/**
 * 
 * author huanghuang
 * 2017年11月17日 下午2:31:20
 */
public interface OrderDetailService {
	/**
	 * 通过orderId查询订单详情
	 * author huanghuang
	 * 2017年11月17日 下午2:31:33
	 * @param orderId
	 * @return
	 */
	List<OrderDetailPageEntity> selectOrderDetailByOrderId(Integer orderId);

    /**
     * 通过内部订单id查询订单详情，与List<OrderDetailPageEntity> selectOrderDetailByOrderId(Integer orderId)数据返回一致
     * @param paymentId 内部订单id
     */
	List<OrderDetailPageEntity> selectOrderDetailByOrderId(String paymentId);
}
