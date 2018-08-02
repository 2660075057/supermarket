package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.BatchEntity;

public interface BatchDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param batchId
     */
    int deleteByPrimaryKey(Integer batchId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(BatchEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(BatchEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param batchId
     */
    BatchEntity selectByPrimaryKey(Integer batchId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(BatchEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(BatchEntity record);
}