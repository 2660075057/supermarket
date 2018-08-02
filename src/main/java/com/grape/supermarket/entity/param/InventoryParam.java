package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.InventoryEntity;

/**
 * 
 * author huanghuang 2017年10月18日 下午4:36:03
 */
public class InventoryParam extends InventoryEntity {
	private SelectEntity select;

	public SelectEntity getSelect() {
		return select;
	}

	public void setSelect(SelectEntity select) {
		this.select = select;
	}

}
