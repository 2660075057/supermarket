package com.grape.supermarket.wechat.service;

import com.grape.supermarket.event.DepotMessageEvent;
import com.grape.supermarket.event.FireMessageEvent;
import com.grape.supermarket.event.PaySuccessEvent;

/**
 * Created by LXP on 2017/11/18.
 */

public interface WechatMessageSendService {
    /**
     * 库存告警方法接口
     */
    void depotMessageEvent(DepotMessageEvent event);

    /**
     * 火灾告警方法接口
     */
    void fireMessageEvent(FireMessageEvent event);

    void paySuccessEvent(PaySuccessEvent event);
}
