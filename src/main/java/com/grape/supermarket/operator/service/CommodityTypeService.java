package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;

import java.util.List;

/**
 * Created by LXP on 2017/9/30.
 */

public interface CommodityTypeService {

    boolean addCommodityType(CommodityTypeEntity entity, List<Integer> attrIds);

    /**
     * 删除商品类型
     * @param typeId 商品类型id
     * @return 0为删除成功，1为存在此商品的商品类型，-1失败
     */
    int deleteCommodityType(Integer typeId);

    boolean updateCommodityType(CommodityTypeEntity entity, List<Integer> attrIds);

    List<CommodityTypePageEntity> commodityTypeList(CommodityTypeParam param, PageParam page);
    
    List<CommodityTypePageEntity> commodityTree(CommodityTypeParam param);
    /**
     * 统计分类记录数
     * lqw 2017-11-2
     * @param param
     * @return
     */
    int countByParam(CommodityTypeParam param);
    /**
     * 根据主类型ID查询子分类
     * @param masterId
     * @return
     */
    List<CommodityTypeEntity> selectByMasterId(Integer masterId);
}
