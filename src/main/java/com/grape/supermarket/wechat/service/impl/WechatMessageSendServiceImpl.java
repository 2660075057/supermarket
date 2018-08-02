package com.grape.supermarket.wechat.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.util.HttpClientUtil;
import com.grape.supermarket.common.util.HttpResponce;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.event.DepotMessageEvent;
import com.grape.supermarket.event.FireMessageEvent;
import com.grape.supermarket.event.PaySuccessEvent;
import com.grape.supermarket.wechat.entity.TemplateMessageEntity;
import com.grape.supermarket.wechat.service.TemplateMessageHandleService;
import com.grape.supermarket.wechat.service.WechatApiUrl;
import com.grape.supermarket.wechat.service.WechatMessageSendService;
import com.grape.supermarket.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2018/4/24.
 */
@Service
public class WechatMessageSendServiceImpl extends BasicService implements WechatMessageSendService {

    @Autowired
    private TemplateMessageHandleService messageHandleService;
    @Autowired
    private WechatService wechatService;

    @EventListener
    @Override
    public void depotMessageEvent(DepotMessageEvent event) {
        List<TemplateMessageEntity> msg = messageHandleService.depotMessageMessage(event);
        for (TemplateMessageEntity templateMessage : msg) {
            sendMessage(templateMessage.getData(),templateMessage.getOpenId(),
                    templateMessage.getTemplateId(),templateMessage.getUrl());
        }
    }

    @EventListener
    @Override
    public void fireMessageEvent(FireMessageEvent event) {
        List<TemplateMessageEntity> msg = messageHandleService.fireMessageMessage(event);
        for (TemplateMessageEntity templateMessage : msg) {
            sendMessage(templateMessage.getData(),templateMessage.getOpenId(),
                    templateMessage.getTemplateId(),templateMessage.getUrl());
        }
    }

    @EventListener
    @Override
    public void paySuccessEvent(PaySuccessEvent event) {
        TemplateMessageEntity msg = messageHandleService.paySuccessMessage(event);
        if(msg != null){
            sendMessage(msg.getData(),msg.getOpenId(), msg.getTemplateId(),msg.getUrl());
        }
    }

    private void sendMessage(Map<String, Object> data,String userOpenId,String templateId,String jumpUrl) {
        try {
            Map<String, Object> postData = new HashMap<>();
            postData.put("touser", userOpenId);
            postData.put("template_id", templateId);
            postData.put("data", data);
            if(jumpUrl != null){
                postData.put("url",jumpUrl);
            }
            String accessToken = wechatService.getAccessToken();
            String url = WechatApiUrl.templateMessage + "?access_token=" + accessToken;
            HttpResponce hr = HttpClientUtil.doPost(url, JsonUtils.toJson(postData), "utf-8");
            getLogger().debug("发送模板消息返回->" + hr);
            boolean result = false;
            if (hr.getState() == 200) {
                Map<String, Object> map = JsonUtils.fromJson(hr.getBody(), Map.class);
                result = "0".equals(String.valueOf(map.get("errcode")));
            }
            if (!result) {
                getLogger().error("发送微信消息失败,request->" + hr);
            } else {
                getLogger().info("发送微信消息成功,postData->" + JsonUtils.toJson(postData));
            }
        } catch (IOException e) {
            getLogger().error("获取微信AccessToken失败", e);
        }
    }
}
