package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.AttributeEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;

import java.util.List;

/**
 * Created by LXP on 2017/9/30.
 */

public class CommodityTypePageEntity extends CommodityTypeEntity {
    List<AttributeEntity> attrs;

    public List<AttributeEntity> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<AttributeEntity> attrs) {
        this.attrs = attrs;
    }
}
