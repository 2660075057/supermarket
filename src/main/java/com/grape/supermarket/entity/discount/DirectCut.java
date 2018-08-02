package com.grape.supermarket.entity.discount;

/**
 * Created by LXP on 2017/10/27.
 * 直接折扣
 */

public class DirectCut {
    public final static int typeId = 3;
    private int percent;//折扣百分比 1-99之间

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
