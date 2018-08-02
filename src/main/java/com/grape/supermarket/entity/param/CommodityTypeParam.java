package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;

/**
 * Created by LXP on 2017/9/30.
 */

public class CommodityTypeParam extends CommodityTypeEntity {
    private SelectEntity select;
    private int model = 1; //是否查询所有分类，1为只查询未删除的分类

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
