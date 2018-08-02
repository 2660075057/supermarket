package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class CommodityTypeEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : commodity_type.type_id
     * </pre>
     */
    private Integer typeId;

    /**
     * <pre>
     * 
     * 表字段 : commodity_type.type_name
     * </pre>
     */
    private String typeName;

    /**
     * <pre>
     * 主类id，为null则为主类
     * 表字段 : commodity_type.master_id
     * </pre>
     */
    private Integer masterId;
    /**
     * <pre>
     * 删除标记，0为正常，1为已经删除
     * 表字段 : commodity_type.delete_mark
     * </pre>
     */
    private Byte deleteMark;

    /**
     *
     */
    public CommodityTypeEntity(Integer typeId, String typeName, Integer masterId, Byte deleteMark) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.masterId = masterId;
        this.deleteMark = deleteMark;
    }

    /**
     *
     */
    public CommodityTypeEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_type.type_id
     * </pre>
     *
     * @return commodity_type.type_id：
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_type.type_id
     * </pre>
     *
     * @param typeId
     *            commodity_type.type_id：
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：commodity_type.type_name
     * </pre>
     *
     * @return commodity_type.type_name：
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：commodity_type.type_name
     * </pre>
     *
     * @param typeName
     *            commodity_type.type_name：
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * <pre>
     * 获取：主类id，为null则为主类
     * 表字段：commodity_type.master_id
     * </pre>
     *
     * @return commodity_type.master_id：主类id，为null则为主类
     */
    public Integer getMasterId() {
        return masterId;
    }

    /**
     * <pre>
     * 设置：主类id，为null则为主类
     * 表字段：commodity_type.master_id
     * </pre>
     *
     * @param masterId
     *            commodity_type.master_id：主类id，为null则为主类
     */
    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Byte getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    @Override
    public String toString() {
        return "CommodityTypeEntity{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", masterId=" + masterId +
                ", deleteMark=" + deleteMark +
                '}';
    }
}