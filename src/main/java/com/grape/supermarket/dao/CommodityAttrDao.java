package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.entity.db.CommodityAttrKey;

import java.util.List;

public interface CommodityAttrDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param key
     */
    int deleteByPrimaryKey(CommodityAttrKey key);

    int deleteByCommId(Integer commId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(CommodityAttrEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(CommodityAttrEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param key
     */
    CommodityAttrEntity selectByPrimaryKey(CommodityAttrKey key);

    /**
     * 根据商品ID查询记录
     * @param commId
     * @return
     */
    List<CommodityAttrEntity> selectByCommId(Integer commId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(CommodityAttrEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(CommodityAttrEntity record);

    /**
     * 根据商品ID 获取所有属性值
     * @param commId
     * @return
     */
    List<CommodityAttrEntity> selectCommAttrByCommId(Integer commId);
}