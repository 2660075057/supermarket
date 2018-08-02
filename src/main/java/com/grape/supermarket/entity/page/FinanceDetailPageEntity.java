package com.grape.supermarket.entity.page;

import java.io.Serializable;

/**
 * Created by LQW on 2017/11/13.
 */

public class FinanceDetailPageEntity implements Serializable {

    private Integer orderId;
    private Integer commId;
    private String barcode;
    private Integer typeId;
    private String typeName;
    private String commName;
    private Integer payments;
    private Integer grossProfits;
    private Integer preferentials;
    private Integer sellNum;
    private Integer shopId;
    private String startTime;
    private String endTime;

    public Integer getPayments() {
        return payments;
    }

    public Integer getGrossProfits() {
        return grossProfits;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getCommName() {
        return commName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Integer getCommId() {
        return commId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getPreferentials() {
        return preferentials;
    }

    public Integer getSellNum() {
        return sellNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setGrossProfits(Integer grossProfits) {
        this.grossProfits = grossProfits;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setPreferentials(Integer preferentials) {
        this.preferentials = preferentials;
    }

    public void setSellNum(Integer sellNum) {
        this.sellNum = sellNum;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
