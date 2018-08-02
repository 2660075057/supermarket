package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.PurchaseEntity;
import com.grape.supermarket.entity.param.PurchaseParam;

public interface PurchaseDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param purId
     */
    int deleteByPrimaryKey(Integer purId);

    /**
     *
     * @param record
     */
    int insertSelective(PurchaseEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param purId
     */
    PurchaseEntity selectByPrimaryKey(Integer purId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PurchaseEntity record);
    
    /**
     * 分页查询采购单
     * author huanghuang
     * 2017年11月18日 下午3:06:55
     * @param param
     * @return
     */
    List<PurchaseEntity> selectByParam(PurchaseParam param);

    /**
     * 查询采购单的记录数
     * author huanghuang
     * 2017年11月18日 下午3:07:18
     * @param param
     * @return
     */
    int countByParam(PurchaseParam param);


}