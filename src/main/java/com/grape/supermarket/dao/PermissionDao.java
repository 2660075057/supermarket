package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.PermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @param record
     */
    int insertSelective(PermissionEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    PermissionEntity selectByPrimaryKey(Integer id);

    List<PermissionEntity> selectByParam(@Param("param")PermissionEntity param);

    List<PermissionEntity> selectByGroupId(Integer groupId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PermissionEntity record);

}