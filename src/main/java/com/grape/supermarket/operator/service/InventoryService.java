package com.grape.supermarket.operator.service;

import java.util.List;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.page.InventoryPageEntity;
import com.grape.supermarket.entity.param.InventoryParam;

/**
 * 
 * author huanghuang 2017年10月18日 下午4:31:59
 */
public interface InventoryService {

	/**
	 * 分页查询盘点记录
	 * author huanghuang
	 * 2017年10月18日 下午5:27:12
	 * @param param
	 * @param page
	 * @return
	 */
	List<InventoryPageEntity> inventoryList(InventoryParam param, PageParam page);

	/**
	 * 查询盘点总记录数
	 * author huanghuang
	 * 2017年10月18日 下午5:27:35
	 * @param param
	 * @return
	 */
	int countInventory(InventoryParam param);
}
