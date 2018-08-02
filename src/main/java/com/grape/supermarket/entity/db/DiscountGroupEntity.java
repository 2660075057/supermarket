package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.Date;

public class DiscountGroupEntity implements Serializable {
    /**
     * <pre>
     *
     * 表字段 : discount_group.group_id
     * </pre>
     */
    private Integer groupId;

    /**
     * 折扣标题
     */
    private String title;

    /**
     * <pre>
     * 删除标记，默认0，1为已删除
     * 表字段 : discount_group.delete_mark
     * </pre>
     */
    private Byte deleteMark;

    /**
     * <pre>
     * 创建操作员
     * 表字段 : discount_group.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 创建时间
     * 表字段 : discount_group.create_time
     * </pre>
     */
    private Date createTime;

    /**
     *
     */
    public DiscountGroupEntity(Integer groupId,String title, Byte deleteMark, Integer operId, Date createTime) {
        this.groupId = groupId;
        this.title = title;
        this.deleteMark = deleteMark;
        this.operId = operId;
        this.createTime = createTime;
    }

    /**
     *
     */
    public DiscountGroupEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：discount_group.group_id
     * </pre>
     *
     * @return discount_group.group_id：
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：discount_group.group_id
     * </pre>
     *
     * @param groupId discount_group.group_id：
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * <pre>
     * 获取：删除标记，默认0，1为已删除
     * 表字段：discount_group.delete_mark
     * </pre>
     *
     * @return discount_group.delete_mark：删除标记，默认0，1为已删除
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * <pre>
     * 设置：删除标记，默认0，1为已删除
     * 表字段：discount_group.delete_mark
     * </pre>
     *
     * @param deleteMark discount_group.delete_mark：删除标记，默认0，1为已删除
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    /**
     * <pre>
     * 获取：创建操作员
     * 表字段：discount_group.oper_id
     * </pre>
     *
     * @return discount_group.oper_id：创建操作员
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：创建操作员
     * 表字段：discount_group.oper_id
     * </pre>
     *
     * @param operId discount_group.oper_id：创建操作员
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：discount_group.create_time
     * </pre>
     *
     * @return discount_group.create_time：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：discount_group.create_time
     * </pre>
     *
     * @param createTime discount_group.create_time：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}