package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.InventoryEntity;
import com.grape.supermarket.entity.param.InventoryParam;

public interface InventoryDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param inventoryId
     */
    int deleteByPrimaryKey(Integer inventoryId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(InventoryEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(InventoryEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param inventoryId
     */
    InventoryEntity selectByPrimaryKey(Integer inventoryId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(InventoryEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(InventoryEntity record);

    /**
     * 分页查询盘点记录
     * author huanghuang
     * 2017年10月18日 下午4:47:28
     * @param param
     * @return
     */
	List<InventoryEntity> selectByParam(InventoryParam param);

	/**
	 * 查询盘点总记录数
	 * author huanghuang
	 * 2017年10月18日 下午4:47:48
	 * @param param
	 * @return
	 */
	int countByParam(InventoryParam param);
}