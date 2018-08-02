package com.grape.supermarket.wechat.service;

import com.grape.supermarket.event.DepotMessageEvent;
import com.grape.supermarket.event.FireMessageEvent;
import com.grape.supermarket.event.PaySuccessEvent;
import com.grape.supermarket.wechat.entity.TemplateMessageEntity;

import java.util.List;

/**
 * Created by LXP on 2018/4/24.
 * 模板消息转换服务，用于将消息通知转换为模板消息，此接口负责实现下列内容<br/>
 * 1.将消息通知转换为模板消息实体
 * 2.决定消息发送用户
 */

public interface TemplateMessageHandleService {
    /**
     * 库存告警消息
     * @param event 库存不足提醒数据，此对象应当是事实不可变的
     * @return 库存模板消息，返回空的数组不发送任何库存模板消息
     */
    List<TemplateMessageEntity> depotMessageMessage(DepotMessageEvent event);

    /**
     * 火灾告警消息
     * @param event 火警提醒数据，此对象应当是事实不可变的
     * @return 火警模板消息，返回空的数组不发送任何火警模板消息
     */
    List<TemplateMessageEntity> fireMessageMessage(FireMessageEvent event);

    /**
     * 支付成功消息
     * @param event 支付数据，此对象应当是事实不可变的
     * @return 支付成功模板消息，返回null不发送支付成功消息
     */
    TemplateMessageEntity paySuccessMessage(PaySuccessEvent event);
}
