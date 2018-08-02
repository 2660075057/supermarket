package com.grape.supermarket.entity.db;

import java.util.Date;

public class OperatorLogEntity {
    /**
     * <pre>
     * id
     * 表字段 : operator_log.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 操作员id
     * 表字段 : operator_log.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 客户端ip
     * 表字段 : operator_log.cilent_ip
     * </pre>
     */
    private String cilentIp;

    /**
     * <pre>
     * 操作码
     * 表字段 : operator_log.cmd
     * </pre>
     */
    private String cmd;

    /**
     * <pre>
     * 操作数据id
     * 表字段 : operator_log.data_id
     * </pre>
     */
    private String dataId;

    /**
     * <pre>
     * 可以存放日志自定义数据
     * 表字段 : operator_log.data
     * </pre>
     */
    private String data;

    /**
     * <pre>
     * 操作时间
     * 表字段 : operator_log.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 获取：id
     * 表字段：operator_log.id
     * </pre>
     *
     * @return operator_log.id：id
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：operator_log.id
     * </pre>
     *
     * @param id
     *            operator_log.id：id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：操作员id
     * 表字段：operator_log.oper_id
     * </pre>
     *
     * @return operator_log.oper_id：操作员id
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：操作员id
     * 表字段：operator_log.oper_id
     * </pre>
     *
     * @param operId
     *            operator_log.oper_id：操作员id
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：客户端ip
     * 表字段：operator_log.cilent_ip
     * </pre>
     *
     * @return operator_log.cilent_ip：客户端ip
     */
    public String getCilentIp() {
        return cilentIp;
    }

    /**
     * <pre>
     * 设置：客户端ip
     * 表字段：operator_log.cilent_ip
     * </pre>
     *
     * @param cilentIp
     *            operator_log.cilent_ip：客户端ip
     */
    public void setCilentIp(String cilentIp) {
        this.cilentIp = cilentIp;
    }

    /**
     * <pre>
     * 获取：操作码
     * 表字段：operator_log.cmd
     * </pre>
     *
     * @return operator_log.cmd：操作码
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * <pre>
     * 设置：操作码
     * 表字段：operator_log.cmd
     * </pre>
     *
     * @param cmd
     *            operator_log.cmd：操作码
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * <pre>
     * 获取：操作数据id
     * 表字段：operator_log.data_id
     * </pre>
     *
     * @return operator_log.data_id：操作数据id
     */
    public String getDataId() {
        return dataId;
    }

    /**
     * <pre>
     * 设置：操作数据id
     * 表字段：operator_log.data_id
     * </pre>
     *
     * @param dataId
     *            operator_log.data_id：操作数据id
     */
    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    /**
     * <pre>
     * 获取：操作时间
     * 表字段：operator_log.create_time
     * </pre>
     *
     * @return operator_log.create_time：操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：操作时间
     * 表字段：operator_log.create_time
     * </pre>
     *
     * @param createTime
     *            operator_log.create_time：操作时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <pre>
     * 获取：可以存放日志自定义数据
     * 表字段：operator_log.data
     * </pre>
     *
     * @return operator_log.data：可以存放日志自定义数据
     */
    public String getData() {
        return data;
    }

    /**
     * <pre>
     * 设置：可以存放日志自定义数据
     * 表字段：operator_log.data
     * </pre>
     *
     * @param data
     *            operator_log.data：可以存放日志自定义数据
     */
    public void setData(String data) {
        this.data = data;
    }
}