package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.CommodityTypeAttrKey;

public interface CommodityTypeAttrDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param key
     */
    int deleteByPrimaryKey(CommodityTypeAttrKey key);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(CommodityTypeAttrKey record);

    /**
     *
     * @param record
     */
    int insertSelective(CommodityTypeAttrKey record);
}