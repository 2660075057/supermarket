package com.grape.supermarket.common.util;

/**
 * Created by LXP on 2017/4/10.
 * 二进制HTTP响应信息和响应码
 */

public class HttpByteResponce {
    private int state=-1;//响应码,若为-1表示请求数据失败
    private byte[] body;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
