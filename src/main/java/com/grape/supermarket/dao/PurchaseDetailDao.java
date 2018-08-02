package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.PurchaseDetailEntity;

public interface PurchaseDetailDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @param record
     */
    int insertSelective(PurchaseDetailEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    PurchaseDetailEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PurchaseDetailEntity record);
    
    /**
     * 通过采购单得到对应的采购详情
     * author huanghuang
     * 2017年11月18日 下午4:56:37
     * @param purId
     * @return
     */
    List<PurchaseDetailEntity> selectListByPurId(Integer purId);
    
    /**
     * 通过采购单的id删除具体的采购记录
     * author huanghuang
     * 2017年11月20日 上午11:54:10
     * @param purId
     * @return
     */
    int deleteByByPurId(Integer purId);


}