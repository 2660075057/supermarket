package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.OperatorShopKey;

public interface OperatorShopDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param key
     */
    int deleteByPrimaryKey(OperatorShopKey key);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OperatorShopKey record);

    /**
     *
     * @param record
     */
    int insertSelective(OperatorShopKey record);
    
    /**
     * 通过operId查询站点
     * author huanghuang
     * 2017年11月6日 下午6:21:26
     * @param operId
     * @return
     */
    List<OperatorShopKey> operatorShopKeyList(Integer operId);

    /**
     * 根据商店id获取管理的管理员id
     * @param shopId 商店id
     */
    List<OperatorShopKey> getOperatorIdList(Integer shopId);
}