package com.grape.supermarket.entity;

/**
 * Created by LXP on 2017/11/7.
 */

public class PayBarcode {
    private int code;//-1错误 0成功 1 与重新预订单不符   2生成的二维码系列不是完整的
    private int preferential;//总计优惠金额
    private int payment;//总金额，不含优惠金额
    private String paymentId;
    private String wechat;

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getPreferential() {
        return preferential;
    }

    public void setPreferential(int preferential) {
        this.preferential = preferential;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "PayBarcode{" +
                "code=" + code +
                ", preferential=" + preferential +
                ", payment=" + payment +
                ", paymentId='" + paymentId + '\'' +
                ", wechat='" + wechat + '\'' +
                '}';
    }
}
