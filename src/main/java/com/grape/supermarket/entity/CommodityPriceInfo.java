package com.grape.supermarket.entity;

/**
 * author huanghuang
 * 2017年10月23日 上午9:33:20
 */
public class CommodityPriceInfo {
    private Integer commId;
    private Integer typeId;
    private Integer elecId;//标签id
    private String tagData;//商品标签数据，可能为null
    private String barcode;
    private String name;
    private int price;//价格，单位分
    private int preferential;//优惠金额，单位分
    private int state;//状态 -1仓库禁用,0可销售，1已售出，其他值不可销售

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTagData() {
        return tagData;
    }

    public void setTagData(String tagData) {
        this.tagData = tagData;
    }

    public Integer getElecId() {
        return elecId;
    }

    public void setElecId(Integer elecId) {
        this.elecId = elecId;
    }

    public int getPreferential() {
        return preferential;
    }

    public void setPreferential(int preferential) {
        this.preferential = preferential;
    }

    @Override
    public String toString() {
        return "CommodityPriceInfo{" +
                "commId=" + commId +
                ", typeId=" + typeId +
                ", elecId=" + elecId +
                ", tagData='" + tagData + '\'' +
                ", barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", preferential=" + preferential +
                ", state=" + state +
                '}';
    }
}
