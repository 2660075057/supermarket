package com.grape.supermarket.entity;

/**
 * Created by LXP on 2017/10/13.
 * 与PayService搭配使用的实体
 * @see com.grape.supermarket.wechat.service.PayService
 */

public class PayOrderInfoEntity {
    public enum PAY_ORDER_TYPE{WECHAT_SCAN}

    private PAY_ORDER_TYPE type;//订单类型
    private String prepayId;//微信预订单id，有效期约2小时
    private String paymentId;//内部订单id
    private String barcode;//二维码数据

    public PAY_ORDER_TYPE getType() {
        return type;
    }

    public void setType(PAY_ORDER_TYPE type) {
        this.type = type;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return "PayOrderInfoEntity{" +
                "type=" + type +
                ", prepayId='" + prepayId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
