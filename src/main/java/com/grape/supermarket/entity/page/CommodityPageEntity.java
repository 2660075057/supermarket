package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.PictureEntity;

import java.util.List;

/**
 * Created by LXP on 2017/9/30.
 */

public class CommodityPageEntity extends CommodityEntity {
    private CommodityTypeEntity commodityType;
    private List<PictureEntity> picture;
//    private list

    public CommodityTypeEntity getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(CommodityTypeEntity commodityType) {
        this.commodityType = commodityType;
    }

    public List<PictureEntity> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureEntity> picture) {
        this.picture = picture;
    }
}
