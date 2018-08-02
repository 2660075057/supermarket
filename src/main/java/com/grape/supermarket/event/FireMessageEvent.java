package com.grape.supermarket.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2018/1/16.
 */

public class FireMessageEvent extends ApplicationEvent {
    private Integer shopId;
    private List<Integer> noticeOperatorId;
    private Date time;

    public FireMessageEvent() {
        super("FireMessageEvent");
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public List<Integer> getNoticeOperatorId() {
        return noticeOperatorId;
    }

    public void setNoticeOperatorId(List<Integer> noticeOperatorId) {
        this.noticeOperatorId = noticeOperatorId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
