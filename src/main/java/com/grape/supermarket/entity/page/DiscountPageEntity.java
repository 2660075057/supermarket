package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.DiscountEntity;

/**
 * 
 * author huanghuang
 * 2017年10月20日 上午8:48:14
 */
public class DiscountPageEntity extends DiscountEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//    private list
	private CommodityTypeEntity commodityTypeEntity;
	
	private CommodityEntity commodityEntity;
	
	public CommodityTypeEntity getCommodityTypeEntity() {
		return commodityTypeEntity;
	}
	public void setCommodityTypeEntity(CommodityTypeEntity commodityTypeEntity) {
		this.commodityTypeEntity = commodityTypeEntity;
	}
	public CommodityEntity getCommodityEntity() {
		return commodityEntity;
	}
	public void setCommodityEntity(CommodityEntity commodityEntity) {
		this.commodityEntity = commodityEntity;
	}
	

}
