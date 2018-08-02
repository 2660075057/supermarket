package com.grape.supermarket.entity.page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LQW on 2017/11/11.
 */

public class FinancePageEntity implements Serializable {
    private Integer shopId;
    private String shopName;
    private Integer payments;
    private Integer grossProfits;
    private Integer storeNum;
    private Integer orderNum;
    private String startTime;
    private String endTime;
    private List<Integer> idRange;//id范围，此条件会生成shop_id in(1,2,3,4,5)的条件

    public String getShopName() {
        return shopName;
    }

    public Integer getGrossProfits() {
        return grossProfits;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public Integer getPayments() {
        return payments;
    }

    public Integer getStoreNum() {
        return storeNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setGrossProfits(Integer grossProfits) {
        this.grossProfits = grossProfits;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public List<Integer> getIdRange() {
        return idRange;
    }

    public void setIdRange(List<Integer> idRange) {
        this.idRange = idRange;
    }
}
