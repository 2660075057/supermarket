package com.grape.supermarket.entity;

import java.util.List;

/**
 * Created by LXP on 2017/10/27.
 * 预订单
 */

public class PreOrder {
    private List<CommodityPriceInfo> commodityPriceInfos;
    private int preferential;//总计优惠金额
    private int payment;//总金额，不含优惠金额
    private int code;//订单代码 0成功 1不可销售 2与重新生成的预订单不符

    public List<CommodityPriceInfo> getCommodityPriceInfos() {
        return commodityPriceInfos;
    }

    public void setCommodityPriceInfos(List<CommodityPriceInfo> commodityPriceInfos) {
        this.commodityPriceInfos = commodityPriceInfos;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
