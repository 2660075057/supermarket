package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.WechatConfEntity;

import java.util.List;

public interface WechatConfDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @param record
     */
    int insertSelective(WechatConfEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    WechatConfEntity selectByPrimaryKey(Integer id);

    /**
     * 根据配置类型获取书籍
     */
    List<WechatConfEntity> selectByType(Integer type);

    /**
     * @param record
     */
    int updateByPrimaryKeySelective(WechatConfEntity record);
}