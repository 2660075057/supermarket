package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.List;

public class GroupEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : group.group_id
     * </pre>
     */
    private Integer groupId;

    /**
     * <pre>
     * 
     * 表字段 : group.group_name
     * </pre>
     */
    private String groupName;

    private List<PermissionEntity> permissions;

    /**
     *
     */
    public GroupEntity(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    /**
     *
     */
    public GroupEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：group.group_id
     * </pre>
     *
     * @return group.group_id：
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：group.group_id
     * </pre>
     *
     * @param groupId
     *            group.group_id：
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：group.group_name
     * </pre>
     *
     * @return group.group_name：
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：group.group_name
     * </pre>
     *
     * @param groupName
     *            group.group_name：
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}