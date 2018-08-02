package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.DiscountEntity;

/**
 * Created by LXP on 2017/9/29.
 */

public class DiscountParam extends DiscountEntity {
    private SelectEntity select;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }
}
