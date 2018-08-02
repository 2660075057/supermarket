package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;
import com.grape.supermarket.entity.page.ShopDiscountGroupPageEntity;
import com.grape.supermarket.entity.param.ShopDiscountGroupParam;

import java.util.List;

/**
 * Created by LQW on 2017/11/14.
 */

public interface ShopDiscountGroupService {

    /**
     * 新增站点促销记录
     * @param param
     * @return
     */
    int insertSelective(ShopDiscountGroupEntity param);

    /**
     * 分页查询站点促销记录
     * @param param
     * @param page
     * @return
     */
    List<ShopDiscountGroupPageEntity> selectParam(ShopDiscountGroupParam param, PageParam page);

    /**
     * 根据ID删除一条记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 统计站点促销记录
     * @param param
     * @return
     */
    int countShopDisGroup(ShopDiscountGroupParam param);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ShopDiscountGroupPageEntity selectByPrimaryKey(Integer id);

    /**
     * 修改站点促销记录
     * @param param
     * @return
     */
    int updateByPrimaryKeySelective(ShopDiscountGroupEntity param);
}
