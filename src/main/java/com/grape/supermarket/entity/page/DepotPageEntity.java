package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.DepotEntity;
import com.grape.supermarket.entity.db.PictureEntity;

import java.util.List;

/**
 * Created by LQW on 2017/11/20.
 */

public class DepotPageEntity extends DepotEntity {
    private CommodityEntity commodityEntity;
    private List<PictureEntity> picture;

    public CommodityEntity getCommodityEntity() {
        return commodityEntity;
    }

    public void setCommodityEntity(CommodityEntity commodityEntity) {
        this.commodityEntity = commodityEntity;
    }

    public List<PictureEntity> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureEntity> picture) {
        this.picture = picture;
    }
}
