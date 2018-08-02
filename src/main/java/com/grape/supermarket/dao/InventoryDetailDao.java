package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.InventoryDetailEntity;
import com.grape.supermarket.entity.param.InventoryDetailParam;

public interface InventoryDetailDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param detailId
     */
    int deleteByPrimaryKey(Integer detailId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(InventoryDetailEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(InventoryDetailEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param detailId
     */
    InventoryDetailEntity selectByPrimaryKey(Integer detailId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InventoryDetailEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(InventoryDetailEntity record);

    /**
     * 查询盘点详情
     * author huanghuang
     * 2017年10月18日 下午5:28:34
     * @param param
     * @return
     */
	List<InventoryDetailEntity> selectByParam(InventoryDetailParam param);
}