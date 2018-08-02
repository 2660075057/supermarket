package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.ShopLogEntity;


/**
 * 
 * author huanghuang
 * 2017年11月9日 下午5:00:09
 */
public class ShopLogPageEntity extends ShopLogEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shopName;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
