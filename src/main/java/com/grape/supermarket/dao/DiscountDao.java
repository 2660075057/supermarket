package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.DiscountEntity;
import com.grape.supermarket.entity.param.DiscountParam;

import java.util.List;

public interface DiscountDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param discountId
     */
    int deleteByPrimaryKey(Integer discountId);

    /**
     *
     * @param record
     */
    int insertSelective(DiscountEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param discountId
     */
    DiscountEntity selectByPrimaryKey(Integer discountId);

    List<DiscountEntity> selectByParam(DiscountParam param);
    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DiscountEntity record);

}