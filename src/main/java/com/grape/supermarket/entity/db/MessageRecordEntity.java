package com.grape.supermarket.entity.db;

import java.util.Date;

public class MessageRecordEntity {
    /**
     * <pre>
     * 手机号码
     * 表字段 : message_record.phone
     * </pre>
     */
    private String phone;

    /**
     * <pre>
     * 今日发送次数
     * 表字段 : message_record.frequency
     * </pre>
     */
    private Integer frequency;

    /**
     * <pre>
     * 最后发送代码
     * 表字段 : message_record.code
     * </pre>
     */
    private String code;

    /**
     * <pre>
     * 最后发送时间
     * 表字段 : message_record.last_send
     * </pre>
     */
    private Date lastSend;

    /**
     *
     */
    public MessageRecordEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：手机号码
     * 表字段：message_record.phone
     * </pre>
     *
     * @return message_record.phone：手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <pre>
     * 设置：手机号码
     * 表字段：message_record.phone
     * </pre>
     *
     * @param phone
     *            message_record.phone：手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <pre>
     * 获取：今日发送次数
     * 表字段：message_record.frequency
     * </pre>
     *
     * @return message_record.frequency：今日发送次数
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * <pre>
     * 设置：今日发送次数
     * 表字段：message_record.frequency
     * </pre>
     *
     * @param frequency
     *            message_record.frequency：今日发送次数
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    /**
     * <pre>
     * 获取：最后发送代码
     * 表字段：message_record.code
     * </pre>
     *
     * @return message_record.code：最后发送代码
     */
    public String getCode() {
        return code;
    }

    /**
     * <pre>
     * 设置：最后发送代码
     * 表字段：message_record.code
     * </pre>
     *
     * @param code
     *            message_record.code：最后发送代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * <pre>
     * 获取：最后发送时间
     * 表字段：message_record.last_send
     * </pre>
     *
     * @return message_record.last_send：最后发送时间
     */
    public Date getLastSend() {
        return lastSend;
    }

    /**
     * <pre>
     * 设置：最后发送时间
     * 表字段：message_record.last_send
     * </pre>
     *
     * @param lastSend
     *            message_record.last_send：最后发送时间
     */
    public void setLastSend(Date lastSend) {
        this.lastSend = lastSend;
    }

    @Override
    public String toString() {
        return "MessageRecordEntity{" +
                "phone='" + phone + '\'' +
                ", frequency=" + frequency +
                ", code='" + code + '\'' +
                ", lastSend=" + lastSend +
                '}';
    }
}