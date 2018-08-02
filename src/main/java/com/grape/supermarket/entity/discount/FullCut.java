package com.grape.supermarket.entity.discount;

/**
 * Created by LXP on 2017/10/27.
 * 满减打折实体
 */

public class FullCut {
    public final static int typeId = 1;
    private int maxPrice;//金额条件
    private int reducePrice;//减少价格

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(int reducePrice) {
        this.reducePrice = reducePrice;
    }
}
