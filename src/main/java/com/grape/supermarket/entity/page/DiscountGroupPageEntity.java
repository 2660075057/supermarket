package com.grape.supermarket.entity.page;

import java.util.List;

import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;

/**
 * 
 * author huanghuang
 * 2017年11月14日 下午4:40:19
 */
public class DiscountGroupPageEntity extends DiscountGroupEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OperatorEntity operator;
	private List<DiscountPageEntity> discountPages;

	public OperatorEntity getOperator() {
		return operator;
	}

	public void setOperator(OperatorEntity operator) {
		this.operator = operator;
	}

	public List<DiscountPageEntity> getDiscountPages() {
		return discountPages;
	}

	public void setDiscountPages(List<DiscountPageEntity> discountPages) {
		this.discountPages = discountPages;
	}
	
	
	

}