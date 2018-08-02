package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class CommodityTypeAttrKey implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : commodity_type_attr.type_id
     * </pre>
     */
    private Integer typeId;

    /**
     * <pre>
     * 
     * 表字段 : commodity_type_attr.attr_id
     * </pre>
     */
    private Integer attrId;

    /**
     *
     */
    public CommodityTypeAttrKey(Integer typeId, Integer attrId) {
        this.typeId = typeId;
        this.attrId = attrId;
    }

    /**
     *
     */
    public CommodityTypeAttrKey() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_type_attr.type_id
     * </pre>
     *
     * @return commodity_type_attr.type_id：
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_type_attr.type_id
     * </pre>
     *
     * @param typeId
     *            commodity_type_attr.type_id：
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_type_attr.attr_id
     * </pre>
     *
     * @return commodity_type_attr.attr_id：
     */
    public Integer getAttrId() {
        return attrId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_type_attr.attr_id
     * </pre>
     *
     * @param attrId
     *            commodity_type_attr.attr_id：
     */
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }
}