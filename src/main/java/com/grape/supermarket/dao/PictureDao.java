package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.PictureEntity;

import java.util.List;

public interface PictureDao {
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
    int insert(PictureEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    PictureEntity selectByPrimaryKey(Integer id);

    List<PictureEntity> selectByParam(PictureEntity param);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(PictureEntity record);

    int deleteByParam(PictureEntity param);
}