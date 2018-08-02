package com.grape.supermarket.common.util;

/**
 * Created by LXP on 2017/3/6.
 * HTTP响应信息和响应码
 */

public class HttpResponce {
    private int state=-1;//响应码,若为-1表示请求数据失败
    private String body;//响应体，也就是响应信息

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpResponce{" +
                "state=" + state +
                ", body='" + body + '\'' +
                '}';
    }
}
