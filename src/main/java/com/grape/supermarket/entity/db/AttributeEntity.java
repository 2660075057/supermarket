package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class AttributeEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : attribute.attr_id
     * </pre>
     */
    private Integer attrId;

    /**
     * <pre>
     * 
     * 表字段 : attribute.attr_name
     * </pre>
     */
    private String attrName;

    /**
     *
     */
    public AttributeEntity(Integer attrId, String attrName) {
        this.attrId = attrId;
        this.attrName = attrName;
    }

    /**
     *
     */
    public AttributeEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：attribute.attr_id
     * </pre>
     *
     * @return attribute.attr_id：
     */
    public Integer getAttrId() {
        return attrId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：attribute.attr_id
     * </pre>
     *
     * @param attrId
     *            attribute.attr_id：
     */
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：attribute.attr_name
     * </pre>
     *
     * @return attribute.attr_name：
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：attribute.attr_name
     * </pre>
     *
     * @param attrName
     *            attribute.attr_name：
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
}