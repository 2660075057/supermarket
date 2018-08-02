package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class GroupPermissionEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : group_permission.group_id
     * </pre>
     */
    private Integer groupId;

    /**
     * <pre>
     * 
     * 表字段 : group_permission.p_id
     * </pre>
     */
    private Integer pId;

    /**
     *
     */
    public GroupPermissionEntity(Integer groupId, Integer pId) {
        this.groupId = groupId;
        this.pId = pId;
    }

    /**
     *
     */
    public GroupPermissionEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：group_permission.group_id
     * </pre>
     *
     * @return group_permission.group_id：
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：group_permission.group_id
     * </pre>
     *
     * @param groupId
     *            group_permission.group_id：
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：group_permission.p_id
     * </pre>
     *
     * @return group_permission.p_id：
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：group_permission.p_id
     * </pre>
     *
     * @param pId
     *            group_permission.p_id：
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }
}