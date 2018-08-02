package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.WechatBanner;

import java.util.List;

/**
 * Created by LXP on 2018/1/6.
 * 微信配置服务
 */

public interface WechatConfigService {
    /**
     * 获取微信横幅设置
     * @return 若存在返回，有可能返回空的list
     */
    List<WechatBanner> getWechatBanner();

    /**
     * 设置微信横幅
     * @param banners 横幅设置
     */
    boolean saveWechatBanner(List<WechatBanner> banners);

    /**
     * 获取微信菜单设置
     * @return 若存在则返回设置json
     */
    String getWechatMenu();

    /**
     * 获取微信菜单设置
     * @param menuJsob 微信菜单json，必须要符合微信菜单规则
     * @see 'https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013'
     */
    boolean setWechatMenu(String menuJsob);
}
