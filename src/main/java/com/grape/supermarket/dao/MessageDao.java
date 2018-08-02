package com.grape.supermarket.dao;

import java.util.List;

import com.grape.supermarket.entity.db.MessageEntity;
import com.grape.supermarket.entity.param.MessageParam;

public interface MessageDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param messageId
     */
    int deleteByPrimaryKey(Integer messageId);


    /**
     *
     * @param record
     */
    int insertSelective(MessageEntity record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param messageId
     */
    MessageEntity selectByPrimaryKey(Integer messageId);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(MessageEntity record);

	/**
	 * 
	 * author huanghuang
	 * 2017年10月18日 上午10:20:20
	 * @param param
	 * @return
	 */
	int countByParam(MessageParam param);

	/**
	 * 
	 * author huanghuang
	 * 2017年10月18日 上午10:42:02
	 * @param param
	 * @return
	 */
	List<MessageEntity> selectByParam(MessageParam param);
    
}