package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.param.CommodityParam;

import java.util.List;

public interface CommodityDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param commId
     */
    int deleteByPrimaryKey(Integer commId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(CommodityEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(CommodityEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param commId
     */
    CommodityEntity selectByPrimaryKey(Integer commId);

    List<CommodityEntity> selectByParam(CommodityParam param);

    int countByParam(CommodityParam param);
    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(CommodityEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(CommodityEntity record);
    
    /**
     * 查询商品，用于查询最多仅有一条记录
     * author huanghuang
     * 2017年10月20日 上午9:19:54
     * @param record
     * @return
     */
    CommodityEntity selectByEntity(CommodityEntity record);

    CommodityEntity selectByBarcode(CommodityEntity commodityEntity);

    /**
     * 微信端商品搜索，查询未删除、库存表存在且是在销售的商品
     * @param param
     * @return
     */
    List<CommodityEntity> selectCommByWeChat(CommodityParam param);
    
    /**
     * 查询商品类别
     * @return
     */
    List<CommodityTypeEntity>selectType();
    
    /**
     * 查询商品子类
     * @return
     */
    List<CommodityTypeEntity>ziselectType(String typeid);

}