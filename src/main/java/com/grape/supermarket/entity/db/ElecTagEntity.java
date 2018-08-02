package com.grape.supermarket.entity.db;

import java.util.Date;

public class ElecTagEntity {
    /**
     * <pre>
     * 
     * 表字段 : elec_tag.elec_id
     * </pre>
     */
    private Integer elecId;

    /**
     * <pre>
     * 商品id
     * 表字段 : elec_tag.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 标签数据
     * 表字段 : elec_tag.data
     * </pre>
     */
    private String data;

    /**
     * <pre>
     * 标签状态，默认0。1为已售出
     * 表字段 : elec_tag.state
     * </pre>
     */
    private Byte state;

    /**
     * 出售时间
     */
    private Date sellTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 标签数据版本号
     */
    private Byte version;

    /**
     * <pre>
     * 获取：
     * 表字段：elec_tag.elec_id
     * </pre>
     *
     * @return elec_tag.elec_id：
     */
    public Integer getElecId() {
        return elecId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：elec_tag.elec_id
     * </pre>
     *
     * @param elecId
     *            elec_tag.elec_id：
     */
    public void setElecId(Integer elecId) {
        this.elecId = elecId;
    }

    /**
     * <pre>
     * 获取：商品id
     * 表字段：elec_tag.comm_id
     * </pre>
     *
     * @return elec_tag.comm_id：商品id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id
     * 表字段：elec_tag.comm_id
     * </pre>
     *
     * @param commId
     *            elec_tag.comm_id：商品id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：标签数据
     * 表字段：elec_tag.data
     * </pre>
     *
     * @return elec_tag.data：标签数据
     */
    public String getData() {
        return data;
    }

    /**
     * <pre>
     * 设置：标签数据
     * 表字段：elec_tag.data
     * </pre>
     *
     * @param data
     *            elec_tag.data：标签数据
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * <pre>
     * 获取：标签状态，默认0。1为已售出
     * 表字段：elec_tag.state
     * </pre>
     *
     * @return elec_tag.state：标签状态，默认0。1为已售出
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：标签状态，默认0。1为已售出
     * 表字段：elec_tag.state
     * </pre>
     *
     * @param state
     *            elec_tag.state：标签状态，默认0。1为已售出
     */
    public void setState(Byte state) {
        this.state = state;
    }


    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ElecTagEntity{" +
                "elecId=" + elecId +
                ", commId=" + commId +
                ", data='" + data + '\'' +
                ", state=" + state +
                ", sellTime=" + sellTime +
                ", createTime=" + createTime +
                ", version=" + version +
                '}';
    }
}