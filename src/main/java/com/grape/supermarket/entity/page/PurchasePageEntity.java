package com.grape.supermarket.entity.page;

import java.util.List;

import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PurchaseEntity;
import com.grape.supermarket.entity.db.ShopEntity;

/**
 * 
 * author huanghuang
 * 2017年11月18日 下午2:48:51
 */
public class PurchasePageEntity extends PurchaseEntity {
	private ShopEntity shop;
	private OperatorEntity operator;
	private List<PurchaseDetailPageEntity> purchaseDetailPages;

	public ShopEntity getShop() {
		return shop;
	}

	public void setShop(ShopEntity shop) {
		this.shop = shop;
	}

	public OperatorEntity getOperator() {
		return operator;
	}

	public void setOperator(OperatorEntity operator) {
		this.operator = operator;
	}

	public List<PurchaseDetailPageEntity> getPurchaseDetailPages() {
		return purchaseDetailPages;
	}

	public void setPurchaseDetailPages(
			List<PurchaseDetailPageEntity> purchaseDetailPages) {
		this.purchaseDetailPages = purchaseDetailPages;
	}
	
}