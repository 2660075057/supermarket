package com.grape.supermarket.entity.db;

public class ShopCommodityKey {
    /**
     * <pre>
     * 商品id
     * 表字段 : shop_commodity.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 商店id
     * 表字段 : shop_commodity.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     *
     */
    public ShopCommodityKey(Integer commId, Integer shopId) {
        this.commId = commId;
        this.shopId = shopId;
    }

    /**
     *
     */
    public ShopCommodityKey() {
        super();
    }

    /**
     * <pre>
     * 获取：商品id
     * 表字段：shop_commodity.comm_id
     * </pre>
     *
     * @return shop_commodity.comm_id：商品id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id
     * 表字段：shop_commodity.comm_id
     * </pre>
     *
     * @param commId
     *            shop_commodity.comm_id：商品id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：shop_commodity.shop_id
     * </pre>
     *
     * @return shop_commodity.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：shop_commodity.shop_id
     * </pre>
     *
     * @param shopId
     *            shop_commodity.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}