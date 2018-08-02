package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.db.CommodityAttrEntity;

import java.util.List;

/**
 * Created by LQW on 2017/11/8.
 */

public interface CommodityAttrService {
    /**
     * 根据商品ID查询记录
     * @param commId
     * @return
     */
    List<CommodityAttrEntity> selectByCommId(Integer commId);

    /**
     * 根据商品ID 获取所有属性值
     * @param commId
     * @return
     */
    List<CommodityAttrEntity> selectCommAttrByCommId(Integer commId);
}
