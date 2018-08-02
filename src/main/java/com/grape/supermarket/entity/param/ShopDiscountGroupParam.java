package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;

/**
 * Created by LQW on 2017/11/14.
 */

public class ShopDiscountGroupParam extends ShopDiscountGroupEntity {
    private SelectEntity select;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }
}
