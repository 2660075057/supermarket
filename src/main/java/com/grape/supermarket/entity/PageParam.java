package com.grape.supermarket.entity;

/**
 * Created by LXP on 2017/9/25.
 */

public class PageParam {
    private Integer pageCurrent;
    private Integer pageSize;

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取limit值
     * @return int[0]为limit开始值，int[1]等于limit结束值
     */
    public int[] getLimit(){
        if(pageCurrent != null && pageSize != null){
            int e = pageSize;
            int s = (pageCurrent-1)*e;
            if(s >=0 && e>0){
                int[] p = new int[2];
                p[0] = s;
                p[1] = e;
                return p;
            }
        }
        return null;
    }
}
