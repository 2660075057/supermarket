package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.Date;

public class BatchLogEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : batch_log.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 操作员id
     * 表字段 : batch_log.operator_id
     * </pre>
     */
    private Integer operatorId;

    /**
     * <pre>
     * 来源id，batch#开头的位来自订单，depot#开头的来自仓库
     * 表字段 : batch_log.form_id
     * </pre>
     */
    private String formId;

    /**
     * <pre>
     * 目标id，batch#开头的位来自订单，depot#开头的来自仓库
     * 表字段 : batch_log.to_id
     * </pre>
     */
    private String toId;

    /**
     * <pre>
     * 流通数量
     * 表字段 : batch_log.amount
     * </pre>
     */
    private Integer amount;

    /**
     * <pre>
     * 类型，值暂未定义
     * 表字段 : batch_log.type
     * </pre>
     */
    private Byte type;

    /**
     * <pre>
     * 
     * 表字段 : batch_log.create_time
     * </pre>
     */
    private Date createTime;

    /**
     *
     */
    public BatchLogEntity(Integer id, Integer operatorId, String formId, String toId, Integer amount, Byte type, Date createTime) {
        this.id = id;
        this.operatorId = operatorId;
        this.formId = formId;
        this.toId = toId;
        this.amount = amount;
        this.type = type;
        this.createTime = createTime;
    }

    /**
     *
     */
    public BatchLogEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：batch_log.id
     * </pre>
     *
     * @return batch_log.id：
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：batch_log.id
     * </pre>
     *
     * @param id
     *            batch_log.id：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：操作员id
     * 表字段：batch_log.operator_id
     * </pre>
     *
     * @return batch_log.operator_id：操作员id
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * <pre>
     * 设置：操作员id
     * 表字段：batch_log.operator_id
     * </pre>
     *
     * @param operatorId
     *            batch_log.operator_id：操作员id
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * <pre>
     * 获取：来源id，batch#开头的位来自订单，depot#开头的来自仓库
     * 表字段：batch_log.form_id
     * </pre>
     *
     * @return batch_log.form_id：来源id，batch#开头的位来自订单，depot#开头的来自仓库
     */
    public String getFormId() {
        return formId;
    }

    /**
     * <pre>
     * 设置：来源id，batch#开头的位来自订单，depot#开头的来自仓库
     * 表字段：batch_log.form_id
     * </pre>
     *
     * @param formId
     *            batch_log.form_id：来源id，batch#开头的位来自订单，depot#开头的来自仓库
     */
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * <pre>
     * 获取：目标id，batch#开头的位来自订单，depot#开头的来自仓库
     * 表字段：batch_log.to_id
     * </pre>
     *
     * @return batch_log.to_id：目标id，batch#开头的位来自订单，depot#开头的来自仓库
     */
    public String getToId() {
        return toId;
    }

    /**
     * <pre>
     * 设置：目标id，batch#开头的位来自订单，depot#开头的来自仓库
     * 表字段：batch_log.to_id
     * </pre>
     *
     * @param toId
     *            batch_log.to_id：目标id，batch#开头的位来自订单，depot#开头的来自仓库
     */
    public void setToId(String toId) {
        this.toId = toId;
    }

    /**
     * <pre>
     * 获取：流通数量
     * 表字段：batch_log.amount
     * </pre>
     *
     * @return batch_log.amount：流通数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * <pre>
     * 设置：流通数量
     * 表字段：batch_log.amount
     * </pre>
     *
     * @param amount
     *            batch_log.amount：流通数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * <pre>
     * 获取：类型，值暂未定义
     * 表字段：batch_log.type
     * </pre>
     *
     * @return batch_log.type：类型，值暂未定义
     */
    public Byte getType() {
        return type;
    }

    /**
     * <pre>
     * 设置：类型，值暂未定义
     * 表字段：batch_log.type
     * </pre>
     *
     * @param type
     *            batch_log.type：类型，值暂未定义
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：batch_log.create_time
     * </pre>
     *
     * @return batch_log.create_time：
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：batch_log.create_time
     * </pre>
     *
     * @param createTime
     *            batch_log.create_time：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}