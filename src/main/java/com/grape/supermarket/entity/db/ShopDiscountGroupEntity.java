package com.grape.supermarket.entity.db;

import java.util.Date;
import java.util.List;

public class ShopDiscountGroupEntity {
    /**
     * <pre>
     * 主键id
     * 表字段 : shop_discount_group.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 商店id
     * 表字段 : shop_discount_group.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 折扣组id
     * 表字段 : shop_discount_group.group_id
     * </pre>
     */
    private Integer groupId;

    /**
     * <pre>
     * 折扣开始时间
     * 表字段 : shop_discount_group.start_time
     * </pre>
     */
    private Date startTime;

    /**
     * <pre>
     * 折扣结束时间
     * 表字段 : shop_discount_group.end_time
     * </pre>
     */
    private Date endTime;

    /**
     * <pre>
     * 操作员id
     * 表字段 : shop_discount_group.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 创建时候
     * 表字段 : shop_discount_group.create_time
     * </pre>
     */
    private Date createTime;
    private List<Integer> idRange;//id范围，此条件会生成shop_id in(1,2,3,4,5)的条件

    /**
     *
     */
    public ShopDiscountGroupEntity(Integer id, Integer shopId, Integer groupId, Date startTime, Date endTime, Integer operId, Date createTime) {
        this.id = id;
        this.shopId = shopId;
        this.groupId = groupId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.operId = operId;
        this.createTime = createTime;
    }

    /**
     *
     */
    public ShopDiscountGroupEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：主键id
     * 表字段：shop_discount_group.id
     * </pre>
     *
     * @return shop_discount_group.id：主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键id
     * 表字段：shop_discount_group.id
     * </pre>
     *
     * @param id
     *            shop_discount_group.id：主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：shop_discount_group.shop_id
     * </pre>
     *
     * @return shop_discount_group.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：shop_discount_group.shop_id
     * </pre>
     *
     * @param shopId
     *            shop_discount_group.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：折扣组id
     * 表字段：shop_discount_group.group_id
     * </pre>
     *
     * @return shop_discount_group.group_id：折扣组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * <pre>
     * 设置：折扣组id
     * 表字段：shop_discount_group.group_id
     * </pre>
     *
     * @param groupId
     *            shop_discount_group.group_id：折扣组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * <pre>
     * 获取：折扣开始时间
     * 表字段：shop_discount_group.start_time
     * </pre>
     *
     * @return shop_discount_group.start_time：折扣开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * <pre>
     * 设置：折扣开始时间
     * 表字段：shop_discount_group.start_time
     * </pre>
     *
     * @param startTime
     *            shop_discount_group.start_time：折扣开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * <pre>
     * 获取：折扣结束时间
     * 表字段：shop_discount_group.end_time
     * </pre>
     *
     * @return shop_discount_group.end_time：折扣结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * <pre>
     * 设置：折扣结束时间
     * 表字段：shop_discount_group.end_time
     * </pre>
     *
     * @param endTime
     *            shop_discount_group.end_time：折扣结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * <pre>
     * 获取：操作员id
     * 表字段：shop_discount_group.oper_id
     * </pre>
     *
     * @return shop_discount_group.oper_id：操作员id
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：操作员id
     * 表字段：shop_discount_group.oper_id
     * </pre>
     *
     * @param operId
     *            shop_discount_group.oper_id：操作员id
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：创建时候
     * 表字段：shop_discount_group.create_time
     * </pre>
     *
     * @return shop_discount_group.create_time：创建时候
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：创建时候
     * 表字段：shop_discount_group.create_time
     * </pre>
     *
     * @param createTime
     *            shop_discount_group.create_time：创建时候
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Integer> getIdRange() {
        return idRange;
    }

    public void setIdRange(List<Integer> idRange) {
        this.idRange = idRange;
    }
}