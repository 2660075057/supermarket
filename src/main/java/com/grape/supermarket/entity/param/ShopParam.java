package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.ShopEntity;

import java.util.List;

/**
 * Created by LXP on 2017/9/29.
 */

public class ShopParam extends ShopEntity {
    private SelectEntity select;
    private List<Integer> idRange;//id范围，此条件会生成shop_id in(1,2,3,4,5)的条件
    private Double lonMin;
    private Double lonMax;

    private Double latMin;
    private Double latMax;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public Double getLonMin() {
        return lonMin;
    }

    public void setLonMin(Double lonMin) {
        this.lonMin = lonMin;
    }

    public Double getLonMax() {
        return lonMax;
    }

    public void setLonMax(Double lonMax) {
        this.lonMax = lonMax;
    }

    public Double getLatMin() {
        return latMin;
    }

    public void setLatMin(Double latMin) {
        this.latMin = latMin;
    }

    public Double getLatMax() {
        return latMax;
    }

    public void setLatMax(Double latMax) {
        this.latMax = latMax;
    }

    public List<Integer> getIdRange() {
        return idRange;
    }

    public void setIdRange(List<Integer> idRange) {
        this.idRange = idRange;
    }
}
