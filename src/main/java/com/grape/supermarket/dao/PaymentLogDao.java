package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.PaymentLogEntity;

public interface PaymentLogDao {
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
    int insertSelective(PaymentLogEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    PaymentLogEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PaymentLogEntity record);
}