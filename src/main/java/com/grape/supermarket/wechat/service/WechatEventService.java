package com.grape.supermarket.wechat.service;

/**
 * Created by LXP on 2017/10/23.
 */

public interface WechatEventService {
    /**
     * 微信路由，会将body转发给设置的路由
     * @param body post body
     * @return 路由处理结果
     */
    String messageRoute(String body);
}
