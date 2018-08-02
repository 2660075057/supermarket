package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.DiscountGroupEntity;

/**
 * 
 * author huanghuang
 * 2017年11月14日 下午4:34:39
 */
public class DiscountGroupParam extends DiscountGroupEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SelectEntity select;

	public SelectEntity getSelect() {
		return select;
	}

	public void setSelect(SelectEntity select) {
		this.select = select;
	}

}
