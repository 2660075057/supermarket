package com.grape.supermarket.service;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.page.MessagePageEntity;
import com.grape.supermarket.entity.param.MessageParam;
import com.grape.supermarket.operator.service.MessageService;


/**
 * 
 * author huanghuang
 * 2017年10月18日 上午10:53:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class MessageServiceTest extends TestCase {
    @Autowired
    private MessageService messageService;

    @Test
    public void addMessage() throws Exception {
    	MessageEntity msg = new MessageEntity();
    	msg.setUserId(1);
    	msg.setUserType((byte)1);
    	messageService.addMessage(msg);
    }
    
    @Test
    public void deleteMessage() throws Exception {
    	Integer messageId = 1;
    	messageService.deleteMessage(messageId);
    }
    
    @Test
    public void countMessage() throws Exception {
    	 SelectEntity select = new SelectEntity();
         MessageParam param = new MessageParam();
         param.setSelect(select);
//         param.setBoolUser(true);
//         param.setOperAccount("test");
         int count = messageService.countMessage(param);
         System.out.println("count:"+count);
    }
    
    @Test
    public void messageList() throws Exception {
    	 SelectEntity select = new SelectEntity();
         select.setOrderBy("user_type desc");
         MessageParam param = new MessageParam();
         param.setSelect(select);
//         param.setBoolUser(true);
         /*param.setDeleteMark((byte)0);*/
         PageParam page = new PageParam();
         page.setPageCurrent(0);
         page.setPageSize(10);
         List<MessagePageEntity> mpes = messageService.messageList(param, page);
         System.out.println(mpes.toString());
    }



}