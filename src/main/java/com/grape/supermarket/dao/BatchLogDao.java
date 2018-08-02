package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.BatchLogEntity;

public interface BatchLogDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(BatchLogEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(BatchLogEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    BatchLogEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(BatchLogEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(BatchLogEntity record);
}