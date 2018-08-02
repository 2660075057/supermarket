package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.InventoryDetailEntity;

/**
 * 
 * author huanghuang 2017年10月18日 下午4:36:03
 */
public class InventoryDetailParam extends InventoryDetailEntity {

	private SelectEntity select;
	private Integer typeId;//商品类型
    private String typeIds;//多个商品类型,逗号隔开
	private String commName;//商品名称

	public SelectEntity getSelect() {
		return select;
	}

	public void setSelect(SelectEntity select) {
		this.select = select;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

}
