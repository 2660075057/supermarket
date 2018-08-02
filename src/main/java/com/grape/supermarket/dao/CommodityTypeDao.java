package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;

import java.util.List;

public interface CommodityTypeDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param typeId
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(CommodityTypeEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(CommodityTypeEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param typeId
     */
    CommodityTypeEntity selectByPrimaryKey(Integer typeId);

    /**
     * 根据主类型ID查询子分类
     * @param masterId
     * @return
     */
    List<CommodityTypeEntity> selectByMasterId(Integer masterId);

    List<CommodityTypeEntity> selectByParam(CommodityTypeParam param);
    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(CommodityTypeEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(CommodityTypeEntity record);

    /**
     * 统计分类记录数
     * lqw 2017-11-2
     * @param param
     * @return
     */
    int countByParam(CommodityTypeParam param);
}