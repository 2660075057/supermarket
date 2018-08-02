package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.param.ShopParam;

import java.util.List;

public interface ShopDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param shopId
     */
    int deleteByPrimaryKey(Integer shopId);

    /**
     *
     * @param record
     */
    int insertSelective(ShopEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param shopId
     */
    ShopEntity selectByPrimaryKey(Integer shopId);

    List<ShopEntity> selectByParam(ShopParam param);

    List<ShopEntity> selectByOperId(Integer operId);

    int countByParam(ShopParam param);
    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ShopEntity record);

    /**
     * 根据商品ID查询所有销售该商品的站点信息
     * @param commId
     * @return
     */
    List<ShopEntity> selectShopByCommId(Integer commId);
}