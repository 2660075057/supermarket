package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.ElecTagEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ElecTagDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param elecId
     */
    int deleteByPrimaryKey(Integer elecId);


    /**
     *
     * @param record
     */
    int insertSelective(ElecTagEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param elecId
     */
    ElecTagEntity selectByPrimaryKey(Integer elecId);

    /**
     * 根据标签数据获取一条记录
     * @param data
     * @return
     */
    ElecTagEntity selectByData(String data);

    /**
     * 查询电子标签最大序号
     * @param commId 商品id
     * @param data 电子标签规则
     */
    ElecTagEntity selectMaxSeq(@Param("commId") Integer commId,@Param("data") String data);

    /**
     * 查询过期的电子标签
     * @param date 过期时间
     */
    int deleteTimeout(Date date);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ElecTagEntity record);

}