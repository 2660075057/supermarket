package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PayBarcode;
import com.grape.supermarket.entity.PreOrder;
import com.grape.supermarket.entity.page.CommodityPageEntity;

import java.util.Set;

/**
 * 
 * author huanghuang 2017年10月20日 上午8:52:59
 */
public interface CountPriceService {
    /**
     * 通过epc创建预订单
     */
	PreOrder createPreOrderByEpc(Set<String> epcs,int shopId);

	PayBarcode createPayBarcode(Set<String> epcs, Integer shopId, PreOrder po);

    /**
     * 查询商品信息以及真实价格，注意，若shopId为null，返回的商品价格为原始价格
     * @param commId 商品id
     * @param shopId 商店id
     * @return 商品信息和真实价格
     */
    CommodityPageEntity getCommodityAndRealPrice(Integer commId, Integer shopId);
}
