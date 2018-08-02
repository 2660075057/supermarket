package com.grape.supermarket.wechat.entity;

/**
 * Created by LXP on 2017/10/23.
 */

public class UserAuthData {
    private String openId;
    private String accessToken;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
