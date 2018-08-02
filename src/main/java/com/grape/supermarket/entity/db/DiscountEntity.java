package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class DiscountEntity implements Serializable {
    /**
     * <pre>
     *
     * 表字段 : discount.discount_id
     * </pre>
     */
    private Integer discountId;

    /**
     * <pre>
     * 折扣组id
     * 表字段 : discount.gropu_id
     * </pre>
     */
    private Integer groupId;

    /**
     * <pre>
     * 折扣类型
     * 表字段 : discount.discount_type
     * </pre>
     */
    private Integer discountType;

    /**
     * <pre>
     * 商品id，与type_id至少有一个不为null
     * 表字段 : discount.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 商品类型id，与type_id至少有一个不为null
     * 表字段 : discount.type_id
     * </pre>
     */
    private Integer typeId;

    /**
     * <pre>
     * 折扣计算数据
     * 表字段 : discount.data
     * </pre>
     */
    private String data;

    /**
     *
     */
    public DiscountEntity(Integer discountId, Integer gropuId, Integer discountType, Integer commId, Integer typeId, String data) {
        this.discountId = discountId;
        this.groupId = gropuId;
        this.discountType = discountType;
        this.commId = commId;
        this.typeId = typeId;
        this.data = data;
    }

    /**
     *
     */
    public DiscountEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：discount.discount_id
     * </pre>
     *
     * @return discount.discount_id：
     */
    public Integer getDiscountId() {
        return discountId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：discount.discount_id
     * </pre>
     *
     * @param discountId discount.discount_id：
     */
    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    /**
     * <pre>
     * 获取：折扣组id
     * 表字段：discount.gropu_id
     * </pre>
     *
     * @return discount.gropu_id：折扣组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * <pre>
     * 设置：折扣组id
     * 表字段：discount.gropu_id
     * </pre>
     *
     * @param groupId discount.gropu_id：折扣组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * <pre>
     * 获取：折扣类型
     * 表字段：discount.discount_type
     * </pre>
     *
     * @return discount.discount_type：折扣类型
     */
    public Integer getDiscountType() {
        return discountType;
    }

    /**
     * <pre>
     * 设置：折扣类型
     * 表字段：discount.discount_type
     * </pre>
     *
     * @param discountType discount.discount_type：折扣类型
     */
    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    /**
     * <pre>
     * 获取：商品id，与type_id至少有一个不为null
     * 表字段：discount.comm_id
     * </pre>
     *
     * @return discount.comm_id：商品id，与type_id至少有一个不为null
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id，与type_id至少有一个不为null
     * 表字段：discount.comm_id
     * </pre>
     *
     * @param commId discount.comm_id：商品id，与type_id至少有一个不为null
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：商品类型id，与type_id至少有一个不为null
     * 表字段：discount.type_id
     * </pre>
     *
     * @return discount.type_id：商品类型id，与type_id至少有一个不为null
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * <pre>
     * 设置：商品类型id，与type_id至少有一个不为null
     * 表字段：discount.type_id
     * </pre>
     *
     * @param typeId discount.type_id：商品类型id，与type_id至少有一个不为null
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * <pre>
     * 获取：折扣计算数据
     * 表字段：discount.data
     * </pre>
     *
     * @return discount.data：折扣计算数据
     */
    public String getData() {
        return data;
    }

    /**
     * <pre>
     * 设置：折扣计算数据
     * 表字段：discount.data
     * </pre>
     *
     * @param data discount.data：折扣计算数据
     */
    public void setData(String data) {
        this.data = data;
    }
}