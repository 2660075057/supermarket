package com.grape.supermarket.entity.db;

public class OperatorShopKey {
    /**
     * <pre>
     * 
     * 表字段 : operator_shop.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 
     * 表字段 : operator_shop.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     *
     */
    public OperatorShopKey(Integer operId, Integer shopId) {
        this.operId = operId;
        this.shopId = shopId;
    }

    /**
     *
     */
    public OperatorShopKey() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：operator_shop.oper_id
     * </pre>
     *
     * @return operator_shop.oper_id：
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：operator_shop.oper_id
     * </pre>
     *
     * @param operId
     *            operator_shop.oper_id：
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：operator_shop.shop_id
     * </pre>
     *
     * @return operator_shop.shop_id：
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：operator_shop.shop_id
     * </pre>
     *
     * @param shopId
     *            operator_shop.shop_id：
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}