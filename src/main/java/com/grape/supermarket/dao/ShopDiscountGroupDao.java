
package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;
import com.grape.supermarket.entity.param.ShopDiscountGroupParam;

import java.util.List;

public interface ShopDiscountGroupDao {
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
    int insertSelective(ShopDiscountGroupEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ShopDiscountGroupEntity selectByPrimaryKey(Integer id);

    ShopDiscountGroupEntity selectNowDiscountGroup(Integer shopId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ShopDiscountGroupEntity record);

    List<ShopDiscountGroupEntity> selectParam(ShopDiscountGroupParam param);
    int countShopDisGroup(ShopDiscountGroupParam param);

    /**
     * 根据站点ID、折扣组ID和有效时间段查询一条记录
     * @param param
     * @return
     */
    ShopDiscountGroupEntity selectByGroupIdAndShopIdAndTime(ShopDiscountGroupEntity param);
    
    List<ShopDiscountGroupEntity> selectByGroupId(Integer groupId);
}