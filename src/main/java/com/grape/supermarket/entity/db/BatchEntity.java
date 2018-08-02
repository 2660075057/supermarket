package com.grape.supermarket.entity.db;

import java.util.Date;

public class BatchEntity {
    /**
     * <pre>
     * id
     * 表字段 : batch.batch_id
     * </pre>
     */
    private Integer batchId;

    /**
     * <pre>
     * 订单号
     * 表字段 : batch.order_num
     * </pre>
     */
    private String orderNum;

    /**
     * <pre>
     * 商品id
     * 表字段 : batch.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 订单商品单价
     * 表字段 : batch.batch_price
     * </pre>
     */
    private Integer batchPrice;

    /**
     * <pre>
     * 商品数量
     * 表字段 : batch.amount
     * </pre>
     */
    private Integer amount;

    /**
     * <pre>
     * 剩余数量，默认为amount
     * 表字段 : batch.remain
     * </pre>
     */
    private Integer remain;

    /**
     * <pre>
     * 创建时间
     * 表字段 : batch.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 版本，用于乐观锁使用
     * 表字段 : batch.version
     * </pre>
     */
    private Short version;

    /**
     *
     */
    public BatchEntity(Integer batchId, String orderNum, Integer commId, Integer batchPrice, Integer amount, Integer remain, Date createTime, Short version) {
        this.batchId = batchId;
        this.orderNum = orderNum;
        this.commId = commId;
        this.batchPrice = batchPrice;
        this.amount = amount;
        this.remain = remain;
        this.createTime = createTime;
        this.version = version;
    }

    /**
     *
     */
    public BatchEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：id
     * 表字段：batch.batch_id
     * </pre>
     *
     * @return batch.batch_id：id
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：batch.batch_id
     * </pre>
     *
     * @param batchId
     *            batch.batch_id：id
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    /**
     * <pre>
     * 获取：订单号
     * 表字段：batch.order_num
     * </pre>
     *
     * @return batch.order_num：订单号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * <pre>
     * 设置：订单号
     * 表字段：batch.order_num
     * </pre>
     *
     * @param orderNum
     *            batch.order_num：订单号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * <pre>
     * 获取：商品id
     * 表字段：batch.comm_id
     * </pre>
     *
     * @return batch.comm_id：商品id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id
     * 表字段：batch.comm_id
     * </pre>
     *
     * @param commId
     *            batch.comm_id：商品id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：订单商品单价
     * 表字段：batch.batch_price
     * </pre>
     *
     * @return batch.batch_price：订单商品单价
     */
    public Integer getBatchPrice() {
        return batchPrice;
    }

    /**
     * <pre>
     * 设置：订单商品单价
     * 表字段：batch.batch_price
     * </pre>
     *
     * @param batchPrice
     *            batch.batch_price：订单商品单价
     */
    public void setBatchPrice(Integer batchPrice) {
        this.batchPrice = batchPrice;
    }

    /**
     * <pre>
     * 获取：商品数量
     * 表字段：batch.amount
     * </pre>
     *
     * @return batch.amount：商品数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * <pre>
     * 设置：商品数量
     * 表字段：batch.amount
     * </pre>
     *
     * @param amount
     *            batch.amount：商品数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * <pre>
     * 获取：剩余数量，默认为amount
     * 表字段：batch.remain
     * </pre>
     *
     * @return batch.remain：剩余数量，默认为amount
     */
    public Integer getRemain() {
        return remain;
    }

    /**
     * <pre>
     * 设置：剩余数量，默认为amount
     * 表字段：batch.remain
     * </pre>
     *
     * @param remain
     *            batch.remain：剩余数量，默认为amount
     */
    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：batch.create_time
     * </pre>
     *
     * @return batch.create_time：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：batch.create_time
     * </pre>
     *
     * @param createTime
     *            batch.create_time：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <pre>
     * 获取：版本，用于乐观锁使用
     * 表字段：batch.version
     * </pre>
     *
     * @return batch.version：版本，用于乐观锁使用
     */
    public Short getVersion() {
        return version;
    }

    /**
     * <pre>
     * 设置：版本，用于乐观锁使用
     * 表字段：batch.version
     * </pre>
     *
     * @param version
     *            batch.version：版本，用于乐观锁使用
     */
    public void setVersion(Short version) {
        this.version = version;
    }
}