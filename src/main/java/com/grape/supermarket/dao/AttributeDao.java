package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.AttributeEntity;

import java.util.List;

public interface AttributeDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param attrId
     */
    int deleteByPrimaryKey(Integer attrId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(AttributeEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(AttributeEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param attrId
     */
    AttributeEntity selectByPrimaryKey(Integer attrId);

    /**
     * 根据商品类型id查询属性名
     * @param typeId 商品类型id
     * @return 属性列表
     */
    List<AttributeEntity> selectByCommTypeId(Integer typeId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(AttributeEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(AttributeEntity record);

    List<AttributeEntity> selectAll();
}