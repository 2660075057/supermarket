package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.param.DiscountGroupParam;

public interface DiscountGroupDao {
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
    int insertSelective(DiscountGroupEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param groupId
     */
    DiscountGroupEntity selectByPrimaryKey(Integer groupId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DiscountGroupEntity record);
    
    List<DiscountGroupEntity> selectByParam(DiscountGroupParam param);

    int countByParam(DiscountGroupParam param);
}