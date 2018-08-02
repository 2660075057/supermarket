package com.grape.supermarket.entity;

/**
 * Created by LXP on 2018/1/6.
 */

public class WechatBanner {
    private String imgUrl;
    private String url;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WechatBanner{" +
                "imgUrl='" + imgUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
