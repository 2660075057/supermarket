package com.grape.supermarket.entity.db;

import java.util.List;

public class ShopCommodityEntity extends ShopCommodityKey {
    /**
     * <pre>
     * 商品价格，这个优先于comm_price
     * 表字段 : shop_commodity.price
     * </pre>
     */
    private Integer price;
    /**
     * <pre>
     * 条码号
     * 表字段 : commodity.barcode
     * </pre>
     */
    private String barcode;

    /**
     * <pre>
     * 商品名
     * 表字段 : commodity.comm_name
     * </pre>
     */
    private String commName;
    /**
     * <pre>
     *
     * 表字段 : commodity_type.type_name
     * </pre>
     */
    private String typeName;
    /**
     * <pre>
     * 商店名
     * 表字段 : shop.shop_name
     * </pre>
     */
    private String shopName;
    private List<Integer> idRange;//id范围，此条件会生成shop_id in(1,2,3,4,5)的条件

    /**
     *
     */
    public ShopCommodityEntity(Integer commId, Integer shopId, Integer price) {
        super(commId, shopId);
        this.price = price;
    }

    /**
     *
     */
    public ShopCommodityEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：商品价格，这个优先于comm_price
     * 表字段：shop_commodity.price
     * </pre>
     *
     * @return shop_commodity.price：商品价格，这个优先于comm_price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * <pre>
     * 设置：商品价格，这个优先于comm_price
     * 表字段：shop_commodity.price
     * </pre>
     *
     * @param price
     *            shop_commodity.price：商品价格，这个优先于comm_price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCommName() {
        return commName;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getShopName() {
        return shopName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Integer> getIdRange() {
        return idRange;
    }

    public void setIdRange(List<Integer> idRange) {
        this.idRange = idRange;
    }
}