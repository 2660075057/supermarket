package com.grape.supermarket.entity;

/**
 * Created by LXP on 2017/9/25.
 */

public class SelectEntity {
    private Integer limitS;
    private Integer limitE;
    private String groupBy;
    private String orderBy;

    public Integer getLimitS() {
        return limitS;
    }

    public void setLimitS(Integer limitS) {
        this.limitS = limitS;
    }

    public Integer getLimitE() {
        return limitE;
    }

    public void setLimitE(Integer limitE) {
        this.limitE = limitE;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setLimit(int[] limit){
        if(limit != null){
            limitS = limit[0];
            limitE = limit[1];
        }
    }
}
