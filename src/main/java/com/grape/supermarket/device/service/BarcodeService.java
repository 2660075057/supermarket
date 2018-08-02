package com.grape.supermarket.device.service;

import com.grape.supermarket.entity.CommodityPriceInfo;

import java.util.List;

/**
 * Created by LXP on 2017/10/19.
 */

public interface BarcodeService {

    /**
     * 标签获取商品数据
     * @param data 标识数据
     * @return 商品信息，若不存在返回null，
     */
    CommodityPriceInfo epcToCommodity(String data);

    List<CommodityPriceInfo> epcToCommodity(List<String> data);
}
