package com.grape.supermarket.wechat;

import com.grape.supermarket.entity.db.CustomerEntity;

/**
 * Created by LXP on 2017/9/28.
 */

public class WechatSession extends CustomerEntity{
    public static final String SESSION_ID="GRAPE_WECHAT_SESSION_INFO_ID";


    public WechatSession(CustomerEntity entity) {
        super(entity.getCoustId(), entity.getOpenid(), entity.getNickname(), entity.getCoustName(),
                entity.getSex(), entity.getPhone(), entity.getPassword(), entity.getRegionCode(),
                entity.getCreateTime(), entity.getCard());
    }

    public WechatSession() {
    }
}
