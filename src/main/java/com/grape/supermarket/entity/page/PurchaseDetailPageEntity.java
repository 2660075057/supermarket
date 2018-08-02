package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.PurchaseDetailEntity;

/**
 * 
 * author huanghuang
 * 2017年11月18日 下午2:48:51
 */
public class PurchaseDetailPageEntity extends PurchaseDetailEntity {
	private CommodityEntity commodity;

	public CommodityEntity getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityEntity commodity) {
		this.commodity = commodity;
	}
	
}