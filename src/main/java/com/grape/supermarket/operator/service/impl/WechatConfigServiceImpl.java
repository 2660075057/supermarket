package com.grape.supermarket.operator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.WechatConfDao;
import com.grape.supermarket.entity.WechatBanner;
import com.grape.supermarket.entity.db.WechatConfEntity;
import com.grape.supermarket.operator.service.WechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2018/1/6.
 */
@Service
public class WechatConfigServiceImpl implements WechatConfigService {
    @Autowired
    private WechatConfDao wechatConfDao;

    @Override
    public List<WechatBanner> getWechatBanner() {
        List<WechatBanner> banners;
        List<WechatConfEntity> wechatConfs = wechatConfDao.selectByType(1);
        if (!wechatConfs.isEmpty()) {
            WechatConfEntity wechatConfEntity = wechatConfs.get(0);
            banners = JsonUtils.fromJson(wechatConfEntity.getData(), new TypeReference<List<WechatBanner>>() {
            });
        } else {
            banners = new ArrayList<>(1);
        }

        return banners;
    }

    @Override
    public boolean saveWechatBanner(List<WechatBanner> banners) {
        assert banners != null && !banners.isEmpty();
        List<WechatConfEntity> wechatConfs = wechatConfDao.selectByType(1);
        if (wechatConfs.isEmpty()) {//第一次添加
            WechatConfEntity wce = new WechatConfEntity();
            wce.setType(1);
            wce.setData(JsonUtils.toJson(banners));
            wechatConfDao.insertSelective(wce);
        } else {
            WechatConfEntity wce = wechatConfs.get(0);
            wce.setData(JsonUtils.toJson(banners));
            wechatConfDao.updateByPrimaryKeySelective(wce);
        }
        return true;
    }

    @Override
    public String getWechatMenu() {
        List<WechatConfEntity> wechatConfs = wechatConfDao.selectByType(2);
        if (wechatConfs.isEmpty()) {
            return null;
        } else {
            return wechatConfs.get(0).getData();
        }
    }

    @Override
    public boolean setWechatMenu(String menuJsob) {
        List<WechatConfEntity> wechatConfs = wechatConfDao.selectByType(2);
        if (wechatConfs.isEmpty()) {
            WechatConfEntity wce = new WechatConfEntity();
            wce.setType(2);
            wce.setData(menuJsob);
            wechatConfDao.insertSelective(wce);
        } else {
            WechatConfEntity wechatConfEntity = wechatConfs.get(0);
            wechatConfEntity.setData(menuJsob);
            wechatConfDao.updateByPrimaryKeySelective(wechatConfEntity);
        }
        return true;
    }
}
