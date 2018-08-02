package com.grape.supermarket.entity.discount;

/**
 * Created by LXP on 2017/10/27.
 */

public class NumCut {
    public final static int typeId = 2;
    private int maxNum;//满X
    private int num;//送X

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
