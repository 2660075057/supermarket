package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.GroupPermissionEntity;

public interface GroupPermissionDao {
    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(GroupPermissionEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(GroupPermissionEntity record);

    int deleteByGroupId(Integer groupId);
    
    List<GroupPermissionEntity> selectList(Integer groupId);
}