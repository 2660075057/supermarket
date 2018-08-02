package com.grape.supermarket.entity;

/**
 * Created by LXP on 2018/1/8.
 * 门禁实体
 */

public class AccessEntity {
    public transient static String SESSION_ID="SESSION_ID_ACCESS_ENTITY";
    private volatile int state = 0;//0正在处理，1已开门,2门禁被禁止
    private Integer shopId;//商店门禁
    private int timeout;//门禁超时时间
    private int type;//访问类型，1微信
    private Object info;//信息

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
