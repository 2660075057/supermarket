package com.grape.supermarket.entity.db;

import java.util.Date;

public class PurchaseEntity {
    /**
     * <pre>
     * 自增id
     * 表字段 : purchase.pur_id
     * </pre>
     */
    private Integer purId;

    /**
     * <pre>
     * 商店id
     * 表字段 : purchase.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 创建管理员id
     * 表字段 : purchase.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 订单信息，存放创建订单理由等
     * 表字段 : purchase.message
     * </pre>
     */
    private String message;

    /**
     * <pre>
     * 采购单类型，1采购，2入库，3转仓
     * 表字段 : purchase.type
     * </pre>
     */
    private Byte type;

    /**
     * <pre>
     * 订单状态，0已创建，1审核通过，-1审核未通过
     * 表字段 : purchase.state
     * </pre>
     */
    private Byte state;

    /**
     * <pre>
     * 未审核理由
     * 表字段 : purchase.remark
     * </pre>
     */
    private String remark;

    /**
     * <pre>
     * 
     * 表字段 : purchase.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 删除标记，默认0,1为已删除
     * 表字段 : purchase.delete_mark
     * </pre>
     */
    private Byte deleteMark;

    /**
     * <pre>
     * 存放自定义数据
     * 表字段 : purchase.data
     * </pre>
     */
    private String data;

    /**
     * <pre>
     * 获取：自增id
     * 表字段：purchase.pur_id
     * </pre>
     *
     * @return purchase.pur_id：自增id
     */
    public Integer getPurId() {
        return purId;
    }

    /**
     * <pre>
     * 设置：自增id
     * 表字段：purchase.pur_id
     * </pre>
     *
     * @param purId
     *            purchase.pur_id：自增id
     */
    public void setPurId(Integer purId) {
        this.purId = purId;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：purchase.shop_id
     * </pre>
     *
     * @return purchase.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：purchase.shop_id
     * </pre>
     *
     * @param shopId
     *            purchase.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：创建管理员id
     * 表字段：purchase.oper_id
     * </pre>
     *
     * @return purchase.oper_id：创建管理员id
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：创建管理员id
     * 表字段：purchase.oper_id
     * </pre>
     *
     * @param operId
     *            purchase.oper_id：创建管理员id
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：订单信息，存放创建订单理由等
     * 表字段：purchase.message
     * </pre>
     *
     * @return purchase.message：订单信息，存放创建订单理由等
     */
    public String getMessage() {
        return message;
    }

    /**
     * <pre>
     * 设置：订单信息，存放创建订单理由等
     * 表字段：purchase.message
     * </pre>
     *
     * @param message
     *            purchase.message：订单信息，存放创建订单理由等
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * <pre>
     * 获取：采购单类型，1采购，2入库，3转仓
     * 表字段：purchase.type
     * </pre>
     *
     * @return purchase.type：采购单类型，1采购，2入库，3转仓
     */
    public Byte getType() {
        return type;
    }

    /**
     * <pre>
     * 设置：采购单类型，1采购，2入库，3转仓
     * 表字段：purchase.type
     * </pre>
     *
     * @param type
     *            purchase.type：采购单类型，1采购，2入库，3转仓
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * <pre>
     * 获取：订单状态，0已创建，1审核通过，-1审核未通过
     * 表字段：purchase.state
     * </pre>
     *
     * @return purchase.state：订单状态，0已创建，1审核通过，-1审核未通过
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：订单状态，0已创建，1审核通过，-1审核未通过
     * 表字段：purchase.state
     * </pre>
     *
     * @param state
     *            purchase.state：订单状态，0已创建，1审核通过，-1审核未通过
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * <pre>
     * 获取：未审核理由
     * 表字段：purchase.remark
     * </pre>
     *
     * @return purchase.remark：未审核理由
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <pre>
     * 设置：未审核理由
     * 表字段：purchase.remark
     * </pre>
     *
     * @param remark
     *            purchase.remark：未审核理由
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：purchase.create_time
     * </pre>
     *
     * @return purchase.create_time：
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：purchase.create_time
     * </pre>
     *
     * @param createTime
     *            purchase.create_time：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <pre>
     * 获取：删除标记，默认0,1为已删除
     * 表字段：purchase.delete_mark
     * </pre>
     *
     * @return purchase.delete_mark：删除标记，默认0,1为已删除
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * <pre>
     * 设置：删除标记，默认0,1为已删除
     * 表字段：purchase.delete_mark
     * </pre>
     *
     * @param deleteMark
     *            purchase.delete_mark：删除标记，默认0,1为已删除
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    /**
     * <pre>
     * 获取：存放自定义数据
     * 表字段：purchase.data
     * </pre>
     *
     * @return purchase.data：存放自定义数据
     */
    public String getData() {
        return data;
    }

    /**
     * <pre>
     * 设置：存放自定义数据
     * 表字段：purchase.data
     * </pre>
     *
     * @param data
     *            purchase.data：存放自定义数据
     */
    public void setData(String data) {
        this.data = data;
    }
}