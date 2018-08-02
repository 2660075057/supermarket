package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.page.FinanceDetailPageEntity;

/**
 * Created by LQW on 2017/11/13.
 */

public class FinanceDetailParam extends FinanceDetailPageEntity {
    private SelectEntity select;
    private String typeIds;//多个商品类型,逗号隔开

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }
}
