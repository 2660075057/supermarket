package com.grape.supermarket.entity;

/**
 * Created by LXP on 2018/1/5.
 */

public class WechatUserInfo {
    private String openid;
    private String nickname;
    private boolean subscribe;
    private String sex;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "WechatUserInfo{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", subscribe=" + subscribe +
                ", sex='" + sex + '\'' +
                '}';
    }
}
