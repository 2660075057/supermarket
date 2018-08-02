package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.db.ShopEntity;

/**
 * 
 * author huanghuang
 * 2017年10月18日 上午10:29:32
 */

public class MessagePageEntity extends MessageEntity {
//    private list
    private MessageEntity message;
    private ShopEntity shop;
    private CustomerEntity customer;

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "MessagePageEntity{" +
                "message=" + message +
                ", shop=" + shop +
                ", customer=" + customer +
                '}';
    }
}
