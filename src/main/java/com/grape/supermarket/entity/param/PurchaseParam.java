package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.PurchaseEntity;

/**
 * 
 * author huanghuang
 * 2017年11月18日 下午2:50:17
 */
public class PurchaseParam extends PurchaseEntity {
	/**
	 * 
	 */
	private SelectEntity select;

	public SelectEntity getSelect() {
		return select;
	}

	public void setSelect(SelectEntity select) {
		this.select = select;
	}

}
