package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.DepotEntity;

/**
 * Created by LQW on 2017/10/10.
 */

public class DepotParam extends DepotEntity {
    private SelectEntity select;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }
}
