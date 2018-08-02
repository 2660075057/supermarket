package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.ShopLogEntity;
import com.grape.supermarket.entity.page.ShopLogPageEntity;
import com.grape.supermarket.entity.param.ShopLogParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopLogDao {
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
    int insert(ShopLogEntity record);

    /**
     *
     * @param record
     */
    int insertSelective(ShopLogEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ShopLogEntity selectByPrimaryKey(Integer id);

    /**
     * 查询最后进店数据
     * @param coustId 顾客id
     * @param shopId 商店id
     * @return 最后进店数据
     */
    ShopLogEntity selectLastcome(@Param("coustId") Integer coustId, @Param("shopId") Integer shopId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ShopLogEntity record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ShopLogEntity record);
    
    List<ShopLogPageEntity> selectAllIntoShop(ShopLogParam param);

    /**
     * 根据站点id删除站点
     * @param shopId 站点id
     */
    int deleteByShopId(Integer shopId);
}