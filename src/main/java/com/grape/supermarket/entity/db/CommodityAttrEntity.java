package com.grape.supermarket.entity.db;

public class CommodityAttrEntity extends CommodityAttrKey {
    /**
     * <pre>
     * 
     * 表字段 : commodity_attr.value
     * </pre>
     */
    private String value;
    /**
     * 属性名称
     *attribute  : attr_name
     */
    private String attrName;

    /**
     *
     */
    public CommodityAttrEntity(Integer commId, Integer attrId, String value) {
        super(commId, attrId);
        this.value = value;
    }

    /**
     *
     */
    public CommodityAttrEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_attr.value
     * </pre>
     *
     * @return commodity_attr.value：
     */
    public String getValue() {
        return value;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_attr.value
     * </pre>
     *
     * @param value
     *            commodity_attr.value：
     */
    public void setValue(String value) {
        this.value = value;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
}