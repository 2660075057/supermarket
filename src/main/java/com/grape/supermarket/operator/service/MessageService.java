package com.grape.supermarket.operator.service;


import java.util.List;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.page.MessagePageEntity;
import com.grape.supermarket.entity.param.MessageParam;

/**
 * 
 * author huanghuang
 * 2017年10月18日 上午9:53:05
 */

public interface MessageService {
	/**
	 * 新增留言
	 * author huanghuang
	 * 2017年10月18日 上午10:40:45
	 * @param entity
	 * @return
	 */
	boolean addMessage(MessageEntity entity);

    /**
     * 回复留言
     * @param entity
     * @return
     */
    MessageEntity replyMessage(MessageEntity entity);

	/**
	 * 删除留言
	 * author huanghuang
	 * 2017年10月18日 上午10:41:01
	 * @param messageId
	 * @return
	 */
	boolean deleteMessage(Integer messageId);
    /**
     * 管理员删除留言，状态改为3
     * @param messageId
     * @return
     */
    boolean deleteMessageAdmin(Integer messageId);
	/**
	 * 分页查询留言
	 * author huanghuang
	 * 2017年10月18日 上午10:37:11
	 * @param param
	 * @param page
	 * @param userType 1管理员  其他为普通用户
	 * @return
	 */
	List<MessagePageEntity> messageList(MessageParam param, PageParam page);

	/**
	 * 留言记录数
	 * author huanghuang
	 * 2017年10月18日 上午10:37:54
	 * @param param
	 * @param userType 1管理员  其他为普通用户
	 * @return
	 */
    int countMessage(MessageParam param);

    /**
     * 修改留言
     * @param param
     * @return
     */
    boolean updateMessage(MessageEntity param);
}
