package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.page.MessagePageEntity;
import com.grape.supermarket.entity.param.MessageParam;
import com.grape.supermarket.operator.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by LQW on 2017/12/8.
 */
@Controller
@RequestMapping("/operator/messageadmin")
public class MessageAdminController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/messageList")
    @ResponseBody
    public ResultBean<List<MessagePageEntity>> messageList(String page, String req) {
        List<MessagePageEntity> list = null;
        try {
            MessageParam entity = JsonUtils.fromJson(req, MessageParam.class);
            entity.setBoolUser(1);
            entity.setUserType((byte)2);
            PageParam p = JsonUtils.fromJson(page, PageParam.class);
            list = messageService.messageList(entity, p);
        } catch (Exception e) {
            throw new FailMessageException("参数错误");
        }
        ResultBean<List<MessagePageEntity>> rb = new ResultBean<>();
        rb.setData(list);
        return rb;
    }
    @RequestMapping("/countMessage")
    @ResponseBody
    public ResultBean<Integer> countMessage(HttpServletRequest request, HttpServletResponse response, MessageParam param) {
        param.setBoolUser(1);
        param.setUserType((byte)2);
        int count =  messageService.countMessage(param);
        ResultBean<Integer> rb = new ResultBean<>();
        rb.setData(count);
        return rb;
    }

    @RequestMapping("/deleteMessageAdmin")
    @ResponseBody
    public ResultBean<Boolean> deleteMessageAdmin(HttpServletRequest request, HttpServletResponse response, Integer messageId) {
        boolean bl = messageService.deleteMessageAdmin(messageId);
        ResultBean<Boolean> rb = new ResultBean<>();
        rb.setData(bl);
        return rb;
    }
    @RequestMapping("detailsMessage")
    public ModelAndView showDetails(Integer messageId){
        ModelAndView mav = new ModelAndView("/operator/details_message");
        List<MessagePageEntity> list = null;
        if(messageId != null){
            MessageParam entity = new MessageParam();
            entity.setMessageId(messageId);
            PageParam page = new PageParam();
            list = messageService.messageList(entity, page);
        }
        MessagePageEntity message = new MessagePageEntity();
        if(list != null){
            message = list.get(0);
        }
        mav.addObject("message",message);
        return mav;
    }
    @RequestMapping("/replyMessage")
    @ResponseBody
    public ResultBean<Boolean> replyMessage(MessageEntity param){
        Integer mid = param.getReplyId();
        param.setReplyId(null);
        MessageEntity entity = messageService.replyMessage(param);
        boolean bl = false;
        if(entity != null && entity.getMessageId() != null){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessageId(mid);
            messageEntity.setState((byte)1);
            messageEntity.setReplyId(entity.getMessageId());
            bl = messageService.updateMessage(messageEntity);
        }
        ResultBean<Boolean> rb = new ResultBean<>();
        rb.setData(bl);
        return rb;
    }
}
