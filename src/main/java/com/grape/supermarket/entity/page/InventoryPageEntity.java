package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.InventoryEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopEntity;

/**
 * 
 * author huanghuang 2017年10月18日 下午4:33:20
 */

public class InventoryPageEntity extends InventoryEntity {
	private ShopEntity shop;
	private OperatorEntity operator;

    public OperatorEntity getOperator() {
        return operator;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setOperator(OperatorEntity operator) {
        this.operator = operator;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }
}
