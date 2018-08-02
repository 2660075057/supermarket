package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.InventoryDetailEntity;
import com.grape.supermarket.entity.db.InventoryEntity;

/**
 * 
 * author huanghuang 2017年10月18日 下午4:33:20
 */

public class InventoryDetailPageEntity extends InventoryDetailEntity {
	// private list
	private CommodityPageEntity commodityPageEntity;
	private InventoryPageEntity inventoryPageEntity;

    public CommodityPageEntity getCommodityPageEntity() {
        return commodityPageEntity;
    }

    public InventoryPageEntity getInventoryPageEntity() {
        return inventoryPageEntity;
    }

    public void setCommodityPageEntity(CommodityPageEntity commodityPageEntity) {
        this.commodityPageEntity = commodityPageEntity;
    }

    public void setInventoryPageEntity(InventoryPageEntity inventoryPageEntity) {
        this.inventoryPageEntity = inventoryPageEntity;
    }
}
