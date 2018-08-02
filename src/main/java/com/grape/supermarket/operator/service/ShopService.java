package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.ShopPageEntity;
import com.grape.supermarket.entity.param.ShopParam;

import java.util.List;

/**
 * Created by LXP on 2017/9/29.
 */

public interface ShopService {
    List<ShopEntity> shopList(ShopParam param, PageParam page);

    ShopEntity selectShopById(Integer shopId);

    int countShop(ShopParam entity);

    boolean addShop(ShopEntity entity);

    boolean updateShop(ShopEntity entity);

    /**
     * 启用商店
     * @param shopId shopId
     */
    boolean enable(Integer shopId);

    /**
     * 禁用商店
     * @param shopId shopId
     */
    boolean disable(Integer shopId);

    /**
     * 根据经纬度和范围查询商店列表，若不提供经纬度则按照名称进行排序
     * @param param 查询参数
     * @param page 分页参数
     * @param radius 范围，单位km
     * @param lon 用户经度
     * @param lat 用户纬度
     * @return 商店列表
     */
    List<ShopPageEntity> shopList(ShopParam param, PageParam page, int radius, Double lon, Double lat);

    /**
     * 获取微信进店二维码
     * @param shopId 商店id
     * @return 二维码数据，返回null说明暂时无法创建二维码
     */
    String getInAccessBarcode(Integer shopId);
    /**
     * 获取微信出店二维码
     * @param shopId 商店id
     * @return 二维码数据，返回null说明暂时无法创建二维码
     */
    String getOutAccessBarcode(Integer shopId);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param shopId
     */
    ShopEntity selectByPrimaryKey(Integer shopId);
    
    List<ShopEntity> shopListByOperId(Integer operId);
    /**
     * 根据商品ID查询所有销售该商品的站点信息
     * @param commId
     * @return
     */
    List<ShopEntity> selectShopByCommId(Integer commId);

    /**
     * 检查站点是否可删除
     * @param shopId 站点id
     * @return 若可以删除返回true，否则返回false
     */
    boolean canDelete(Integer shopId);

    /**
     * 删除站点
     * @param shopId 站点id
     * @return 若删除成功返回true,否则返回false
     */
    boolean deleteShop(Integer shopId);
}
