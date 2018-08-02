package com.grape.supermarket.operator.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.grape.supermarket.common.WechatSessionHelper;
import com.grape.supermarket.dao.CustomerDao;
import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.wechat.WechatSession;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.MessageDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.page.MessagePageEntity;
import com.grape.supermarket.entity.param.MessageParam;
import com.grape.supermarket.operator.service.MessageService;


/**
 * 
 * author huanghuang
 * 2017年10月18日 上午9:54:09
 */
@Service
public class MessageServiceImpl extends BasicService implements MessageService {
	 @Autowired
	 private MessageDao messageDao;
	 @Autowired
     private ShopDao shopDao;
	 @Autowired
     private CustomerDao customerDao;

	@Override
	public boolean addMessage(MessageEntity entity) {
		if(entity==null){
	        return false;
	    }
        entity.setMessageId(null);
        entity.setState((byte) 0);
        entity.setDeleteMark((byte) 0);
        if(entity.getUserId()==null){
            String message= "insert fail userId or userType is null,data->"+ JsonUtils.toJson(entity);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }
        int t=messageDao.insertSelective(entity);
        if(t >0){
            return true;
        }else{
            return false;
        }
	}
    @Override
    public MessageEntity replyMessage(MessageEntity entity) {
        if(entity==null){
            return null;
        }
        entity.setMessageId(null);
        entity.setState((byte) 0);
        entity.setDeleteMark((byte) 0);
        if(entity.getUserId()==null){
            String message= "insert fail userId or userType is null,data->"+ JsonUtils.toJson(entity);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }
        int t=messageDao.insertSelective(entity);
       return entity;
    }

	@Override
	public boolean deleteMessage(Integer messageId) {
		if(messageId == null){
            return false;
        }
		MessageEntity msg = new MessageEntity();
		msg.setMessageId(messageId);
		msg.setDeleteMark((byte) 1);
        messageDao.updateByPrimaryKeySelective(msg);
        return true;
	}

    @Override
    public boolean deleteMessageAdmin(Integer messageId) {
        if(messageId == null){
            return false;
        }
        MessageEntity msg = new MessageEntity();
        msg.setMessageId(messageId);
        msg.setDeleteMark((byte) 3);
        messageDao.updateByPrimaryKeySelective(msg);
        return true;
    }

    @Override
	public List<MessagePageEntity> messageList(MessageParam param,
			PageParam page) {
		if (param == null) {
			param = new MessageParam();
		}
		if (page != null) {
			SelectEntity select = param.getSelect();
			if (select == null) {
				select = new SelectEntity();
			}
			select.setLimit(page.getLimit());
			param.setSelect(select);
		}
		List<MessageEntity> messages = messageDao.selectByParam(param);
		List<MessagePageEntity> datas = new ArrayList<>(messages.size());
		try {
			for (MessageEntity message : messages) {
				MessagePageEntity mpe = new MessagePageEntity();
				BeanUtils.copyProperties(mpe, message);
				if(message.getReplyId() != null){
                    MessageEntity me = messageDao.selectByPrimaryKey(message.getReplyId());
                    mpe.setMessage(me);
                }
                ShopEntity shop = shopDao.selectByPrimaryKey(message.getShopId());
                mpe.setShop(shop);
                CustomerEntity customer = customerDao.selectByPrimaryKey(message.getUserId());
                mpe.setCustomer(customer);
				datas.add(mpe);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanCopyException(e);
		}
		return datas;
	}

	@Override
	public int countMessage(MessageParam param) {
		if (param == null) {
			param = new MessageParam();
		}
		return messageDao.countByParam(param);
	}

    @Override
    public boolean updateMessage(MessageEntity param) {
	    if(param == null){
	        return false;
        }
        int t = messageDao.updateByPrimaryKeySelective(param);
        return t>0;
    }

}
