package com.grape.supermarket.wechat.service.impl;

import com.grape.supermarket.event.WechatLocationEvent;
import com.grape.supermarket.wechat.service.WechatLocaltionService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by LXP on 2017/10/26.
 */
@Service
public class WechatLocaltionServiceImpl implements WechatLocaltionService {

    private ConcurrentMap<String, WechatLocationEvent.LocationData> wechatUserLocation
            = new ConcurrentHashMap<>(256);

    @Override
    public WechatLocationEvent.LocationData wechatUserLocation(String openId) {
        if (openId == null) {
            return null;
        }
        WechatLocationEvent.LocationData locationData = wechatUserLocation.get(openId);
        if (locationData.getCreateTime() + 600_000 < System.currentTimeMillis()) {
            wechatUserLocation.remove(openId, locationData);
            return null;
        }
        return locationData;
    }

    @EventListener
    @Override
    public void addWechatLocation(WechatLocationEvent we) {
        WechatLocationEvent.LocationData source = (WechatLocationEvent.LocationData) we.getSource();
        wechatUserLocation.put(source.getOpenId(), source);
    }
}
