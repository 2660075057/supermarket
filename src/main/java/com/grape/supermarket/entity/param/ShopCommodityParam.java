package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.ShopCommodityEntity;

/**
 * Created by LQW on 2017/10/13.
 */

public class ShopCommodityParam extends ShopCommodityEntity {
    private SelectEntity select;
    private boolean commodityDeleteMark = true;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public boolean isCommodityDeleteMark() {
        return commodityDeleteMark;
    }

    public void setCommodityDeleteMark(boolean commodityDeleteMark) {
        this.commodityDeleteMark = commodityDeleteMark;
    }
}
