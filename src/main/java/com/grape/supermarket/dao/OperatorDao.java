package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.param.OperatorParam;

import java.util.List;

public interface OperatorDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param operId
     */
    int deleteByPrimaryKey(Integer operId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OperatorEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param operId
     */
    OperatorEntity selectByPrimaryKey(Integer operId);

    /**
     * 根据参数查询
     * @param param 查询参数
     * @return 查询结果
     */
    List<OperatorEntity> selectByParam(OperatorParam param);

    int countByParam(OperatorParam param);

    /**
     * 根据主键来更新数据库记录
     * @param record
     */
    int updateByPrimaryKeySelective(OperatorEntity record);
}