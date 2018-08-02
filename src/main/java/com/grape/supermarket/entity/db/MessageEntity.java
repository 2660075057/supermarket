package com.grape.supermarket.entity.db;

import java.util.Date;

public class MessageEntity {
    /**
     * <pre>
     * 自增id
     * 表字段 : message.message_id
     * </pre>
     */
    private Integer messageId;
    /**
     * <pre>
     * 消息类型 1、留言  2、催货
     * 表字段 : message.message_type
     * </pre>
     */
    private String messageType;

    /**
     * <pre>
     * 回复id，与message_id对应
     * 表字段 : message.reply_id
     * </pre>
     */
    private Integer replyId;

    /**
     * <pre>
     * 商店id
     * 表字段 : message.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 用户id
     * 表字段 : message.user_id
     * </pre>
     */
    private Integer userId;

    /**
     * <pre>
     * 用户类型，1管理员，2普通用户
     * 表字段 : message.user_type
     * </pre>
     */
    private Byte userType;

    /**
     * <pre>
     * 反馈内容
     * 表字段 : message.content
     * </pre>
     */
    private String content;

    /**
     * <pre>
     * 状态，0为未回复，1为已回复
     * 表字段 : message.state
     * </pre>
     */
    private Byte state;

    /**
     * <pre>
     * 删除标记，默认0,1为已经删除
     * 表字段 : message.delete_mark
     * </pre>
     */
    private Byte deleteMark;

    /**
     * <pre>
     * 留言时间/回复时间
     * 表字段 : message.create_time
     * </pre>
     */
    private Date createTime;

    /**
     *
     */
    public MessageEntity(Integer messageId, String messageType, Integer replyId, Integer shopId, Integer userId, Byte userType, String content, Byte state, Byte deleteMark, Date createTime) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.replyId = replyId;
        this.shopId = shopId;
        this.userId = userId;
        this.userType = userType;
        this.content = content;
        this.state = state;
        this.deleteMark = deleteMark;
        this.createTime = createTime;
    }

    /**
     *
     */
    public MessageEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：自增id
     * 表字段：message.message_id
     * </pre>
     *
     * @return message.message_id：自增id
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * <pre>
     * 设置：自增id
     * 表字段：message.message_id
     * </pre>
     *
     * @param messageId
     *            message.message_id：自增id
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * <pre>
     * 获取：回复id，与message_id对应
     * 表字段：message.reply_id
     * </pre>
     *
     * @return message.reply_id：回复id，与message_id对应
     */
    public Integer getReplyId() {
        return replyId;
    }

    /**
     * <pre>
     * 设置：回复id，与message_id对应
     * 表字段：message.reply_id
     * </pre>
     *
     * @param replyId
     *            message.reply_id：回复id，与message_id对应
     */
    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：message.shop_id
     * </pre>
     *
     * @return message.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：message.shop_id
     * </pre>
     *
     * @param shopId
     *            message.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：用户id
     * 表字段：message.user_id
     * </pre>
     *
     * @return message.user_id：用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * <pre>
     * 设置：用户id
     * 表字段：message.user_id
     * </pre>
     *
     * @param userId
     *            message.user_id：用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * <pre>
     * 获取：用户类型，1管理员，2普通用户
     * 表字段：message.user_type
     * </pre>
     *
     * @return message.user_type：用户类型，1管理员，2普通用户
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * <pre>
     * 设置：用户类型，1管理员，2普通用户
     * 表字段：message.user_type
     * </pre>
     *
     * @param userType
     *            message.user_type：用户类型，1管理员，2普通用户
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    /**
     * <pre>
     * 获取：反馈内容
     * 表字段：message.content
     * </pre>
     *
     * @return message.content：反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * <pre>
     * 设置：反馈内容
     * 表字段：message.content
     * </pre>
     *
     * @param content
     *            message.content：反馈内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <pre>
     * 获取：状态，0为未回复，1为已回复
     * 表字段：message.state
     * </pre>
     *
     * @return message.state：状态，0为未回复，1为已回复
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：状态，0为未回复，1为已回复
     * 表字段：message.state
     * </pre>
     *
     * @param state
     *            message.state：状态，0为未回复，1为已回复
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * <pre>
     * 获取：删除标记，默认0,1为已经删除
     * 表字段：message.delete_mark
     * </pre>
     *
     * @return message.delete_mark：删除标记，默认0,1为已经删除
     */
    public Byte getDeleteMark() {
        return deleteMark;
    }

    /**
     * <pre>
     * 设置：删除标记，默认0,1为已经删除
     * 表字段：message.delete_mark
     * </pre>
     *
     * @param deleteMark
     *            message.delete_mark：删除标记，默认0,1为已经删除
     */
    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    /**
     * <pre>
     * 获取：留言时间/回复时间
     * 表字段：message.create_time
     * </pre>
     *
     * @return message.create_time：留言时间/回复时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：留言时间/回复时间
     * 表字段：message.create_time
     * </pre>
     *
     * @param createTime
     *            message.create_time：留言时间/回复时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}