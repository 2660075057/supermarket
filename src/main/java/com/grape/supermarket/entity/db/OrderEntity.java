package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.Date;

public class OrderEntity implements Serializable {
    /**
     * <pre>
     * 订单id
     * 表字段 : order.order_id
     * </pre>
     */
    private Integer orderId;

    /**
     * <pre>
     * 顾客id
     * 表字段 : order.coust_id
     * </pre>
     */
    private Integer coustId;

    /**
     * <pre>
     * 商店id
     * 表字段 : order.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 付款总金额，不含优惠金额
     * 表字段 : order.payment
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
     * 优惠金额，默认值0
     * 表字段 : order.preferential
     * </pre>
     */
    private Integer preferential;

    /**
     * <pre>
     * 订单创建时间
     * 表字段 : order.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 内部订单id
     * 表字段 : order.payment_id
     * </pre>
     */
    private String paymentId;

    /**
     * <pre>
     * 支付类型,1微信，2支付宝，3会员卡
     * 表字段 : order.payment_type
     * </pre>
     */
    private Byte paymentType;

    /**
     * <pre>
     * 外部订单id，使用会员卡消费的可为null
     * 表字段 : order.external_id
     * </pre>
     */
    private String externalId;

    /**
     * <pre>
     * 订单状态值，默认值0，支付成功1，交易关闭2
     * 表字段 : order.state
     * </pre>
     */
    private Byte state;

    /**
     * <pre>
     * 删除标记，1为删除
     * 表字段 : order.delete_mark
     * </pre>
     */
    private Byte deleteMark;

    private Integer version;

    /**
     *
     */
    public OrderEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：订单id
     * 表字段：order.order_id
     * </pre>
     *
     * @return order.order_id：订单id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * <pre>
     * 设置：订单id
     * 表字段：order.order_id
     * </pre>
     *
     * @param orderId
     *            order.order_id：订单id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * <pre>
     * 获取：顾客id
     * 表字段：order.coust_id
     * </pre>
     *
     * @return order.coust_id：顾客id
     */
    public Integer getCoustId() {
        return coustId;
    }

    /**
     * <pre>
     * 设置：顾客id
     * 表字段：order.coust_id
     * </pre>
     *
     * @param coustId
     *            order.coust_id：顾客id
     */
    public void setCoustId(Integer coustId) {
        this.coustId = coustId;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：order.shop_id
     * </pre>
     *
     * @return order.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：order.shop_id
     * </pre>
     *
     * @param shopId
     *            order.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：付款金额
     * 表字段：order.payment
     * </pre>
     *
     * @return order.payment：付款金额
     */
    public Integer getPayment() {
        return payment;
    }

    /**
     * <pre>
     * 设置：付款金额
     * 表字段：order.payment
     * </pre>
     *
     * @param payment
     *            order.payment：付款金额
     */
    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    /**
     * <pre>
     * 获取：优惠金额，默认值0
     * 表字段：order.preferential
     * </pre>
     *
     * @return order.preferential：优惠金额，默认值0
     */
    public Integer getPreferential() {
        return preferential;
    }

    /**
     * <pre>
     * 设置：优惠金额，默认值0
     * 表字段：order.preferential
     * </pre>
     *
     * @param preferential
     *            order.preferential：优惠金额，默认值0
     */
    public void setPreferential(Integer preferential) {
        this.preferential = preferential;
    }

    /**
     * <pre>
     * 获取：订单创建时间
     * 表字段：order.create_time
     * </pre>
     *
     * @return order.create_time：订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：订单创建时间
     * 表字段：order.create_time
     * </pre>
     *
     * @param createTime
     *            order.create_time：订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <pre>
     * 获取：内部订单id
     * 表字段：order.payment_id
     * </pre>
     *
     * @return order.payment_id：内部订单id
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * <pre>
     * 设置：内部订单id
     * 表字段：order.payment_id
     * </pre>
     *
     * @param paymentId
     *            order.payment_id：内部订单id
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * <pre>
     * 获取：支付类型,1微信，2支付宝，3会员卡
     * 表字段：order.payment_type
     * </pre>
     *
     * @return order.payment_type：支付类型,1微信，2支付宝，3会员卡
     */
    public Byte getPaymentType() {
        return paymentType;
    }

    /**
     * <pre>
     * 设置：支付类型,1微信，2支付宝，3会员卡
     * 表字段：order.payment_type
     * </pre>
     *
     * @param paymentType
     *            order.payment_type：支付类型,1微信，2支付宝，3会员卡
     */
    public void setPaymentType(Byte paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * <pre>
     * 获取：外部订单id，使用会员卡消费的可为null
     * 表字段：order.external_id
     * </pre>
     *
     * @return order.external_id：外部订单id，使用会员卡消费的可为null
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * <pre>
     * 设置：外部订单id，使用会员卡消费的可为null
     * 表字段：order.external_id
     * </pre>
     *
     * @param externalId
     *            order.external_id：外部订单id，使用会员卡消费的可为null
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * <pre>
     * 获取：订单状态值，默认值0，支付成功1，交易关闭2
     * 表字段：order.state
     * </pre>
     *
     * @return order.state：订单状态值，默认值0，支付成功1，交易关闭2
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：订单状态值，默认值0，支付成功1，交易关闭2
     * 表字段：order.state
     * </pre>
     *
     * @param state
     *            order.state：订单状态值，默认值0，支付成功1，交易关闭2
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * <pre>
     * 获取：删除标记，1为删除
     * 表字段：order.delete_mark
     * </pre>
     *
     * @return order.delete_mark：删除标记，1为删除
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * <pre>
     * 设置：删除标记，1为删除
     * 表字段：order.delete_mark
     * </pre>
     *
     * @param deleteMark
     *            order.delete_mark：删除标记，1为删除
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Integer getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Integer grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderId=" + orderId +
                ", coustId=" + coustId +
                ", shopId=" + shopId +
                ", payment=" + payment +
                ", grossProfit=" + grossProfit +
                ", preferential=" + preferential +
                ", createTime=" + createTime +
                ", paymentId='" + paymentId + '\'' +
                ", paymentType=" + paymentType +
                ", externalId='" + externalId + '\'' +
                ", state=" + state +
                ", deleteMark=" + deleteMark +
                ", version=" + version +
                '}';
    }
}