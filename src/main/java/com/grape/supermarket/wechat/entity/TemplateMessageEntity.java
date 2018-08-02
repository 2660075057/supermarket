package com.grape.supermarket.wechat.entity;

import java.util.Map;

/**
 * Created by LXP on 2018/4/24.
 */

public class TemplateMessageEntity {
    private String openId;//用户openid
    private String templateId;//模板消息id
    private Map<String,Object> data;//消息内容
    private String url;//模板消息跳转链接

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
