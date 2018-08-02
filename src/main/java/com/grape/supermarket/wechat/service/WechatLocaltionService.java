package com.grape.supermarket.wechat.service;

import com.grape.supermarket.event.WechatLocationEvent;

/**
 * Created by LXP on 2017/10/26.
 * 地理位置服务
 */

public interface WechatLocaltionService {
    /**
     * 获取微信用户的地理位置信息
     * @param openId openId
     * @return 用户的地理位置信息，若获取不到返回null
     */
    WechatLocationEvent.LocationData wechatUserLocation(String openId);

    /**
     * 添加一条微信地理位置信息
     * @param we 地理位置信息
     */
    void addWechatLocation(WechatLocationEvent we);
}
