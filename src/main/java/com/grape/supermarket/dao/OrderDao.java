package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.OrderEntity;
import com.grape.supermarket.entity.page.FinancePageEntity;
import com.grape.supermarket.entity.param.OrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param orderId
     */
    int deleteByPrimaryKey(Integer orderId);

    /**
     * @param record
     */
    int insertSelective(OrderEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderId
     */
    OrderEntity selectByPrimaryKey(Integer orderId);

    /**
     * 根据payment_id获取一条数据库记录
     *
     * @param paymentId 内部订单号
     */
    OrderEntity selectByPaymentId(String paymentId);

    /**
     * 消费统计
     *
     * @param coustId 顾客id
     * @param shopId  商店id
     * @return payment:消费总金额 preferential:优惠总金额
     */
    Map<String, Long> countConsumption(@Param("coustId") Integer coustId, @Param("shopId") Integer shopId);

    /**
     * @param record
     */
    int updateByPrimaryKeySelective(OrderEntity record);

    /**
     * 根据顾客ID分页查询订单记录
     *
     * @param orderParam
     * @return
     */
    List<OrderEntity> selectOrderByCoustId(OrderParam orderParam);

    /**
     * 根据顾客ID统计订单总记录数
     *
     * @param coustId
     * @return
     */
    int countOrderByCoustId(Integer coustId);

    /**
     * 财经统计
     *
     * @param param
     * @return
     */
    List<FinancePageEntity> financeStatistics(FinancePageEntity param);
    
    /**
     * 查询订单
     * author huanghuang
     * 2017年11月17日 上午10:37:51
     * @param orderParam
     * @return
     */
    List<OrderEntity> selectOrderByParam(OrderParam orderParam);
    
    /**
     *  查询订单数量
     * author huanghuang
     * 2017年11月17日 上午11:41:31
     * @param orderParam
     * @return
     */
    int countByParam(OrderParam orderParam);

    int atomicUpdate(@Param("param") OrderEntity record, @Param("version") int version);
}