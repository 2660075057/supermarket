package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class CommodityEntity implements Serializable{
    /**
     * <pre>
     * id
     * 表字段 : commodity.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 条码号
     * 表字段 : commodity.barcode
     * </pre>
     */
    private String barcode;

    /**
     * <pre>
     * 
     * 表字段 : commodity.type_id
     * </pre>
     */
    private Integer typeId;

    /**
     * <pre>
     * 商品名
     * 表字段 : commodity.comm_name
     * </pre>
     */
    private String commName;

    /**
     * 进货价
     */
    private  Integer costPrice;

    /**
     * <pre>
     * 商品单价
     * 表字段 : commodity.comm_price
     * </pre>
     */
    private Integer commPrice;

    /**
     * <pre>
     * 删除标记，0为正常，1为已经删除
     * 表字段 : commodity.delete_mark
     * </pre>
     */
    private Byte deleteMark;

    /**
     *
     */
    public CommodityEntity(Integer commId, String barcode, Integer typeId, String commName, Integer commPrice, Byte deleteMark) {
        this.commId = commId;
        this.barcode = barcode;
        this.typeId = typeId;
        this.commName = commName;
        this.commPrice = commPrice;
        this.deleteMark = deleteMark;
    }

    /**
     *
     */
    public CommodityEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：id
     * 表字段：commodity.comm_id
     * </pre>
     *
     * @return commodity.comm_id：id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：commodity.comm_id
     * </pre>
     *
     * @param commId
     *            commodity.comm_id：id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：条码号
     * 表字段：commodity.barcode
     * </pre>
     *
     * @return commodity.barcode：条码号
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * <pre>
     * 设置：条码号
     * 表字段：commodity.barcode
     * </pre>
     *
     * @param barcode
     *            commodity.barcode：条码号
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity.type_id
     * </pre>
     *
     * @return commodity.type_id：
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity.type_id
     * </pre>
     *
     * @param typeId
     *            commodity.type_id：
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * <pre>
     * 获取：商品名
     * 表字段：commodity.comm_name
     * </pre>
     *
     * @return commodity.comm_name：商品名
     */
    public String getCommName() {
        return commName;
    }

    /**
     * <pre>
     * 设置：商品名
     * 表字段：commodity.comm_name
     * </pre>
     *
     * @param commName
     *            commodity.comm_name：商品名
     */
    public void setCommName(String commName) {
        this.commName = commName;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * <pre>
     * 获取：商品单价
     * 表字段：commodity.comm_price
     * </pre>
     *
     * @return commodity.comm_price：商品单价
     */
    public Integer getCommPrice() {
        return commPrice;
    }

    /**
     * <pre>
     * 设置：商品单价
     * 表字段：commodity.comm_price
     * </pre>
     *
     * @param commPrice
     *            commodity.comm_price：商品单价
     */
    public void setCommPrice(Integer commPrice) {
        this.commPrice = commPrice;
    }

    /**
     * <pre>
     * 获取：删除标记，0为正常，1为已经删除
     * 表字段：commodity.delete_mark
     * </pre>
     *
     * @return commodity.delete_mark：删除标记，0为正常，1为已经删除
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * <pre>
     * 设置：删除标记，0为正常，1为已经删除
     * 表字段：commodity.delete_mark
     * </pre>
     *
     * @param deleteMark
     *            commodity.delete_mark：删除标记，0为正常，1为已经删除
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    @Override
    public String toString() {
        return "CommodityEntity{" +
                "commId=" + commId +
                ", barcode='" + barcode + '\'' +
                ", typeId=" + typeId +
                ", commName='" + commName + '\'' +
                ", costPrice=" + costPrice +
                ", commPrice=" + commPrice +
                ", deleteMark=" + deleteMark +
                '}';
    }
}