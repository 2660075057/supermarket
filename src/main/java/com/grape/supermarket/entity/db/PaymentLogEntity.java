package com.grape.supermarket.entity.db;

import java.util.Date;

public class PaymentLogEntity {
    /**
     * <pre>
     * 日志id
     * 表字段 : payment_log.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 交易类型1微信，2支付宝，3会员卡
     * 表字段 : payment_log.transaction_type
     * </pre>
     */
    private Byte transactionType;

    /**
     * <pre>
     * 日志代码，具体值有
     * 100000001：生成支付订单
     * 100001001：支付成功——收到支付通知
     * 100001002：支付失败
     * 100001003：付款金额与数据库订单金额不符
     * 100001004：查询不到数据库订单信息
     * 100003001：取消订单成功
     * 100003002：取消订单失败
     * 100003002：取消订单时，订单已经支付
     * 100003002：订单已经关闭
     * 100003005：系统错误（微信方）
     *
     *
     * 表字段 : payment_log.log_code
     * </pre>
     */
    private String logCode;

    /**
     * <pre>
     * 内部订单id
     * 表字段 : payment_log.payment_id
     * </pre>
     */
    private String paymentId;

    /**
     * <pre>
     * 
     * 表字段 : payment_log.create_time
     * </pre>
     */
    private Date createTime;
    /**
     * <pre>
     * 支付相关数据，可存自定义数据
     * 表字段 : payment_log.payment_data
     * </pre>
     */
    private String paymentData;

    /**
     * <pre>
     * 备注、笔记
     * 表字段 : payment_log.note
     * </pre>
     */
    private String note;
    /**
     *
     */
    public PaymentLogEntity(Integer id, Byte transactionType, String logCode, String paymentId, Date createTime,String paymentData, String note) {
        this.id = id;
        this.transactionType = transactionType;
        this.logCode = logCode;
        this.paymentId = paymentId;
        this.createTime = createTime;
        this.paymentData = paymentData;
        this.note = note;
    }

    /**
     *
     */
    public PaymentLogEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：日志id
     * 表字段：payment_log.id
     * </pre>
     *
     * @return payment_log.id：日志id
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：日志id
     * 表字段：payment_log.id
     * </pre>
     *
     * @param id
     *            payment_log.id：日志id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：交易类型1微信，2支付宝，3会员卡
     * 表字段：payment_log.transaction_type
     * </pre>
     *
     * @return payment_log.transaction_type：交易类型1微信，2支付宝，3会员卡
     */
    public Byte getTransactionType() {
        return transactionType;
    }

    /**
     * <pre>
     * 设置：交易类型1微信，2支付宝，3会员卡
     * 表字段：payment_log.transaction_type
     * </pre>
     *
     * @param transactionType
     *            payment_log.transaction_type：交易类型1微信，2支付宝，3会员卡
     */
    public void setTransactionType(Byte transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * <pre>
     * 获取：日志代码，具体值需要定义，例如有以下几种情况1生成支付二维码，2取消交易，3支付成功，4支付失败
     * 表字段：payment_log.log_code
     * </pre>
     *
     * @return payment_log.log_code：日志代码，具体值需要定义，例如有以下几种情况1生成支付二维码，2取消交易，3支付成功，4支付失败
     */
    public String getLogCode() {
        return logCode;
    }

    /**
     * <pre>
     * 设置：日志代码，具体值需要定义，例如有以下几种情况1生成支付二维码，2取消交易，3支付成功，4支付失败
     * 表字段：payment_log.log_code
     * </pre>
     *
     * @param logCode
     *            payment_log.log_code：日志代码，具体值需要定义，例如有以下几种情况1生成支付二维码，2取消交易，3支付成功，4支付失败
     */
    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    /**
     * <pre>
     * 获取：内部订单id
     * 表字段：payment_log.payment_id
     * </pre>
     *
     * @return payment_log.payment_id：内部订单id
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * <pre>
     * 设置：内部订单id
     * 表字段：payment_log.payment_id
     * </pre>
     *
     * @param paymentId
     *            payment_log.payment_id：内部订单id
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：payment_log.create_time
     * </pre>
     *
     * @return payment_log.create_time：
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：payment_log.create_time
     * </pre>
     *
     * @param createTime
     *            payment_log.create_time：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}