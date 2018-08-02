package com.grape.supermarket.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by LXP on 2017/11/28.
 */

public class PayCancelEvent extends ApplicationEvent {
    public PayCancelEvent(PayCancelData source) {
        super(source);
    }

    public static class PayCancelData{
        private String paymentId;

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }
    }
}
