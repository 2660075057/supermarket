package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.common.WechatSessionHelper;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.MessagePageEntity;
import com.grape.supermarket.entity.page.OrderPageEntity;
import com.grape.supermarket.entity.param.MessageParam;
import com.grape.supermarket.entity.param.ShopParam;
import com.grape.supermarket.operator.service.MessageService;
import com.grape.supermarket.operator.service.ShopService;
import com.grape.supermarket.wechat.WechatSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by LXP on 2017/11/16.
 */
@Controller
@RequestMapping("/wechat/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ShopService shopService;

    @RequestMapping
    public ModelAndView index(){
        MessageParam param = new MessageParam();
        PageParam page = new PageParam();
        param.setUserType((byte) 2);
        page.setPageSize(10);
        page.setPageCurrent(1);
        WechatSession session = WechatSessionHelper.getSessionOrThrow();
        Integer coustId = session.getCoustId();
        param.setUserId(coustId);
        param.setDeleteMark((byte)0);
        List<MessagePageEntity> list = messageService.messageList(param,page);
        ShopParam shopParam = new ShopParam();
        List<ShopEntity> shoplist = shopService.shopList(shopParam,null);
        ModelAndView mav = new ModelAndView("/wechatPage/message");
        mav.addObject("messList",list);
        mav.addObject("shopList",shoplist);
        return mav;
    }

    @RequestMapping("/addMessage")
    @ResponseBody
    public boolean addMessage(MessageEntity param){
        WechatSession session = WechatSessionHelper.getSessionOrThrow();
        Integer coustId = session.getCoustId();
        param.setUserId(coustId);
        return messageService.addMessage(param);
    }
    @RequestMapping("/messageParam")
    @ResponseBody
    public List<MessagePageEntity> messageParam(PageParam page){
        MessageParam param = new MessageParam();
        param.setUserType((byte) 2);
        List<MessagePageEntity> list = messageService.messageList(param,page);
        return list;
    }
}
