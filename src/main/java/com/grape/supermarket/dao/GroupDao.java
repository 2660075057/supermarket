package com.grape.supermarket.dao;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.GroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param groupId
     */
    int deleteByPrimaryKey(Integer groupId);


    /**
     *
     * @param record
     */
    int insertSelective(GroupEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param groupId
     */
    GroupEntity selectByPrimaryKey(Integer groupId);

    List<GroupEntity> selectByParam(@Param("param")GroupEntity param, @Param("select")SelectEntity select);
    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(GroupEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(GroupEntity record);
    
    /**
     * 查询权限的总记录数
     * author huanghuang
     * 2017年11月1日 下午5:21:11
     * @return
     */
    int countGroup();
}