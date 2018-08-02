package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.ShopCommodityEntity;
import com.grape.supermarket.entity.db.ShopCommodityKey;
import com.grape.supermarket.entity.page.ShopCommodityPageEntity;
import com.grape.supermarket.entity.param.ShopCommodityParam;

import java.util.List;

public interface ShopCommodityDao {
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
    int updateByPrimaryKeySelective(ShopCommodityEntity record);

    /**
     * 分页查询站点销售信息
     * @param shopCommodityParam
     * @return
     */
    List<ShopCommodityPageEntity> selectShopCommListParam(ShopCommodityParam shopCommodityParam);

    /**
     * 统计分页查询总记录数
     * @param shopCommodityParam
     * @return
     */
    int countShopCommList(ShopCommodityParam shopCommodityParam);
    
    /**
     * 通过商品id查询商品
     * author huanghuang
     * 2017年11月11日 上午11:04:54
     * @param commId
     * @return
     */
    ShopCommodityEntity selectByCommId(Integer commId);

}