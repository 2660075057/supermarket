package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.OrderDetailEntity;
import com.grape.supermarket.entity.page.FinanceDetailPageEntity;
import com.grape.supermarket.entity.page.OrderDetailPageEntity;
import com.grape.supermarket.entity.param.FinanceDetailParam;

import java.util.List;

public interface OrderDetailDao {

    /**
     *
     * @param record
     */
    int insertSelective(OrderDetailEntity record);
    List<OrderDetailPageEntity> selectOrderDetailByOrderId(Integer orderId);

    /**
     * 财经统计详情
     * @param param
     * @return
     */
    List<FinanceDetailPageEntity> financeStatisticsDetail(FinanceDetailParam param);
    int countStatisticsDetail(FinanceDetailParam param);

    /**
     * 根据订单ID查询订单商品信息，按商品分组，计算商品数量
     * @param orderId
     * @return
     */
    List<OrderDetailPageEntity> selectOrderDetailByOrderIdGroupComm(Integer orderId);
}