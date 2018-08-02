package com.grape.supermarket.entity;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.ShopEntity;

import java.util.List;

/**
 * Created by LXP on 2017/10/12.
 * 与PayService搭配使用的实体
 * @see com.grape.supermarket.wechat.service.PayService
 */

public class PayEntity {
    private ShopEntity shop;//商店信息
    private String paymentId;//内部订单id
    private List<CommodityEntity> commoditys;//商品信息
    private int payment;//支付总金额,未计算优惠金额,单位分
    private int preferential;//优惠金额,单位分

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public List<CommodityEntity> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<CommodityEntity> commoditys) {
        this.commoditys = commoditys;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getPreferential() {
        return preferential;
    }

    public void setPreferential(int preferential) {
        this.preferential = preferential;
    }

    @Override
    public String toString() {
        return "PayEntity{" +
                "shop=" + shop +
                ", paymentId='" + paymentId + '\'' +
                ", commoditys=" + commoditys +
                ", payment=" + payment +
                ", preferential=" + preferential +
                '}';
    }
}
