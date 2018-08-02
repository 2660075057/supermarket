package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class CommodityAttrKey implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : commodity_attr.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 
     * 表字段 : commodity_attr.attr_id
     * </pre>
     */
    private Integer attrId;

    /**
     *
     */
    public CommodityAttrKey(Integer commId, Integer attrId) {
        this.commId = commId;
        this.attrId = attrId;
    }

    /**
     *
     */
    public CommodityAttrKey() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_attr.comm_id
     * </pre>
     *
     * @return commodity_attr.comm_id：
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_attr.comm_id
     * </pre>
     *
     * @param commId
     *            commodity_attr.comm_id：
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_attr.attr_id
     * </pre>
     *
     * @return commodity_attr.attr_id：
     */
    public Integer getAttrId() {
        return attrId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_attr.attr_id
     * </pre>
     *
     * @param attrId
     *            commodity_attr.attr_id：
     */
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }
}