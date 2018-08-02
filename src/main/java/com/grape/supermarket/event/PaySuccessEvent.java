package com.grape.supermarket.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * Created by LXP on 2017/11/7.
 */

public class PaySuccessEvent extends ApplicationEvent {
    public PaySuccessEvent(PayData source) {
        super(source);
    }

    public static class PayData{
        private String paymentId;//内部订单id
        private String externalId;//外部订单id
        private byte paymentType;//交易类型
        private Integer coustId;//用户id
        private Integer payment;//总金额，含优惠金额
        private Integer preferential;//优惠金额
        private Integer shopId;//商店id
        private Date payDate;

        public Date getPayDate() {
            return payDate;
        }

        public void setPayDate(Date payDate) {
            this.payDate = payDate;
        }

        public Integer getShopId() {
            return shopId;
        }

        public void setShopId(Integer shopId) {
            this.shopId = shopId;
        }

        public Integer getCoustId() {
            return coustId;
        }

        public void setCoustId(Integer coustId) {
            this.coustId = coustId;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public byte getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(byte paymentType) {
            this.paymentType = paymentType;
        }

        public Integer getPayment() {
            return payment;
        }

        public void setPayment(Integer payment) {
            this.payment = payment;
        }

        public Integer getPreferential() {
            return preferential;
        }

        public void setPreferential(Integer preferential) {
            this.preferential = preferential;
        }

        @Override
        public String toString() {
            return "PayData{" +
                    "paymentId='" + paymentId + '\'' +
                    ", externalId='" + externalId + '\'' +
                    ", paymentType=" + paymentType +
                    ", coustId=" + coustId +
                    ", payment=" + payment +
                    ", preferential=" + preferential +
                    ", shopId=" + shopId +
                    ", payDate=" + payDate +
                    '}';
        }
    }
}
