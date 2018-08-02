package com.grape.supermarket.operator.service;

import java.util.List;

import com.grape.supermarket.entity.db.InventoryDetailEntity;
import com.grape.supermarket.entity.page.InventoryDetailPageEntity;
import com.grape.supermarket.entity.param.InventoryDetailParam;

/**
 * 
 * author huanghuang
 * 2017年10月18日 下午5:00:20
 */
public interface InventoryDetailService {
	/**
	 * 查询盘点详情
	 * author huanghuang
	 * 2017年10月18日 下午5:25:29
	 * @param param
	 * @return
	 */
	List<InventoryDetailPageEntity> inventoryDetailList(InventoryDetailParam param);

    List<InventoryDetailEntity> inventoryDetailListByTypeId(InventoryDetailParam param);
}
