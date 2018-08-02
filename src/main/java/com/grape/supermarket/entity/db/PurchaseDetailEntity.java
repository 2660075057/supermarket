package com.grape.supermarket.entity.db;

public class PurchaseDetailEntity {
    /**
     * <pre>
     * id 无实际意义
     * 表字段 : purchase_detail.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 购买id
     * 表字段 : purchase_detail.pur_id
     * </pre>
     */
    private Integer purId;

    /**
     * <pre>
     * 商品id
     * 表字段 : purchase_detail.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 数量
     * 表字段 : purchase_detail.amount
     * </pre>
     */
    private Integer amount;

    /**
     * <pre>
     * 获取：id 无实际意义
     * 表字段：purchase_detail.id
     * </pre>
     *
     * @return purchase_detail.id：id 无实际意义
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：id 无实际意义
     * 表字段：purchase_detail.id
     * </pre>
     *
     * @param id
     *            purchase_detail.id：id 无实际意义
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：购买id
     * 表字段：purchase_detail.pur_id
     * </pre>
     *
     * @return purchase_detail.pur_id：购买id
     */
    public Integer getPurId() {
        return purId;
    }

    /**
     * <pre>
     * 设置：购买id
     * 表字段：purchase_detail.pur_id
     * </pre>
     *
     * @param purId
     *            purchase_detail.pur_id：购买id
     */
    public void setPurId(Integer purId) {
        this.purId = purId;
    }

    /**
     * <pre>
     * 获取：商品id
     * 表字段：purchase_detail.comm_id
     * </pre>
     *
     * @return purchase_detail.comm_id：商品id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id
     * 表字段：purchase_detail.comm_id
     * </pre>
     *
     * @param commId
     *            purchase_detail.comm_id：商品id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：数量
     * 表字段：purchase_detail.amount
     * </pre>
     *
     * @return purchase_detail.amount：数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * <pre>
     * 设置：数量
     * 表字段：purchase_detail.amount
     * </pre>
     *
     * @param amount
     *            purchase_detail.amount：数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}