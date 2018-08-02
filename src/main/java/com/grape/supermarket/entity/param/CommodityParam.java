package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CommodityEntity;

/**
 * Created by LXP on 2017/9/29.
 */

public class CommodityParam extends CommodityEntity {
    private SelectEntity select;
    private Integer startPrice;
    private Integer endPrice;
    private Integer shopId;
    private Integer typeId;
    private String priceSort;
    private String masterId;
    


	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public Integer getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Integer endPrice) {
        this.endPrice = endPrice;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public Integer getTypeId() {
        return typeId;
    }

    @Override
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(String priceSort) {
        this.priceSort = priceSort;
    }
}
