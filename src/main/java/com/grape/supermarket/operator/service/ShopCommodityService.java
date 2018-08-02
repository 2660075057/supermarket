package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopCommodityEntity;
import com.grape.supermarket.entity.db.ShopCommodityKey;
import com.grape.supermarket.entity.page.ShopCommodityPageEntity;
import com.grape.supermarket.entity.param.ShopCommodityParam;

import java.util.List;

/**
 * Created by LQW on 2017/10/13.
 */

public interface ShopCommodityService {
    /**
     * 根据主键删除数据库的记录
     *
     * @param key
     */
    int deleteByPrimaryKey(ShopCommodityKey key);


    /**
     *插入数据
     * @param record
     */
    int insertSelective(ShopCommodityEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param key
     */
    ShopCommodityPageEntity selectByPrimaryKey(ShopCommodityKey key);

    /**
     *修改站点价格
     * @param record
     */
    int updatePrice(ShopCommodityEntity record);

    /**
     * 分页查询站点销售信息
     * @return
     */
    List<ShopCommodityPageEntity> selectShopCommListParam(ShopCommodityParam shopCommodityParam, PageParam page);
    /**
     * 统计分页查询总记录数
     * @param shopCommodityParam
     * @return
     */
    int countShopCommList(ShopCommodityParam shopCommodityParam);
}
