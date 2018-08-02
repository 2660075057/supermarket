package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.OperatorLogEntity;
import com.grape.supermarket.entity.param.OperatorLogParam;

import java.util.List;

public interface OperatorLogDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OperatorLogEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    OperatorLogEntity selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OperatorLogEntity record);

    /**
     * 根据查询实体查询日志记录
     * @param param 查询实体
     */
    List<OperatorLogEntity> selectByParam(OperatorLogParam param);

    /**
     * 根据查询实体统计日志记录
     * @param param 查询实体
     */
    int countByParam(OperatorLogParam param);
}