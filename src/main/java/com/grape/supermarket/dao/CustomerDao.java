package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.param.CustomerParam;

import java.util.List;

public interface CustomerDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param coustId
     */
    int deleteByPrimaryKey(Integer coustId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(CustomerEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(CustomerEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param coustId
     */
    CustomerEntity selectByPrimaryKey(Integer coustId);

    /**
     * 根据openid查询顾客信息
     * @param openid openid
     */
    CustomerEntity selectByOpenid(String openid);


    List<CustomerEntity> selectByParam(CustomerParam param);

    int countByParam(CustomerParam param);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(CustomerEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(CustomerEntity record);
}