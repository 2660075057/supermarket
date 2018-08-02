package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.MessageRecordEntity;

public interface MessageRecordDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param phone
     */
    int deleteByPrimaryKey(String phone);

    /**
     *
     * @param record
     */
    int insertSelective(MessageRecordEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param phone
     */
    MessageRecordEntity selectByPrimaryKey(String phone);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(MessageRecordEntity record);

}