package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class OrderDetailEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : order_detail.order_id
     * </pre>
     */
    private Integer orderId;

    /**
     * <pre>
     * 
     * 表字段 : order_detail.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 付款金额
     * 表字段 : order_detail.payment
     * </pre>
     */
    private Integer payment;
    /**
     * <pre>
     * 毛利
     * 表字段 : order.gross_profit
     * </pre>
     */
    private Integer grossProfit;

    /**
     * <pre>
     * 优惠金额
     * 表字段 : order_detail.preferential
     * </pre>
     */
    private Integer preferential;

    /**
     * 电子标签id
     */
    private Integer elecId;


    /**
     * <pre>
     * 获取：
     * 表字段：order_detail.order_id
     * </pre>
     *
     * @return order_detail.order_id：
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：order_detail.order_id
     * </pre>
     *
     * @param orderId
     *            order_detail.order_id：
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：order_detail.comm_id
     * </pre>
     *
     * @return order_detail.comm_id：
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：order_detail.comm_id
     * </pre>
     *
     * @param commId
     *            order_detail.comm_id：
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：付款金额
     * 表字段：order_detail.payment
     * </pre>
     *
     * @return order_detail.payment：付款金额
     */
    public Integer getPayment() {
        return payment;
    }

    /**
     * <pre>
     * 设置：付款金额
     * 表字段：order_detail.payment
     * </pre>
     *
     * @param payment
     *            order_detail.payment：付款金额
     */
    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    /**
     * <pre>
     * 获取：优惠金额
     * 表字段：order_detail.preferential
     * </pre>
     *
     * @return order_detail.preferential：优惠金额
     */
    public Integer getPreferential() {
        return preferential;
    }

    /**
     * <pre>
     * 设置：优惠金额
     * 表字段：order_detail.preferential
     * </pre>
     *
     * @param preferential
     *            order_detail.preferential：优惠金额
     */
    public void setPreferential(Integer preferential) {
        this.preferential = preferential;
    }

    public Integer getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Integer grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Integer getElecId() {
        return elecId;
    }

    public void setElecId(Integer elecId) {
        this.elecId = elecId;
    }
}