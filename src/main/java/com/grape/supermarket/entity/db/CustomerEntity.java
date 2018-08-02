package com.grape.supermarket.entity.db;

import java.util.Date;

public class CustomerEntity {
    /**
     * <pre>
     * 顾客id
     * 表字段 : customer.coust_id
     * </pre>
     */
    private Integer coustId;

    /**
     * <pre>
     * 微信openid
     * 表字段 : customer.openid
     * </pre>
     */
    private String openid;

    /**
     * <pre>
     * 昵称
     * 表字段 : customer.nickname
     * </pre>
     */
    private String nickname;

    /**
     * <pre>
     * 用户姓名
     * 表字段 : customer.coust_name
     * </pre>
     */
    private String coustName;

    /**
     * <pre>
     * 性别
     * 表字段 : customer.sex
     * </pre>
     */
    private String sex;

    /**
     * <pre>
     * 顾客手机号
     * 表字段 : customer.phone
     * </pre>
     */
    private String phone;

    /**
     * <pre>
     * 顾客密码
     * 表字段 : customer.password
     * </pre>
     */
    private String password;

    /**
     * <pre>
     * 顾客所属地区码
     * 表字段 : customer.region_code
     * </pre>
     */
    private String regionCode;

    /**
     * <pre>
     * 注册时间
     * 表字段 : customer.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 顾客会员卡
     * 表字段 : customer.card
     * </pre>
     */
    private String card;

    /**
     *
     */
    public CustomerEntity(Integer coustId, String openid, String nickname, String coustName, String sex, String phone, String password, String regionCode, Date createTime, String card) {
        this.coustId = coustId;
        this.openid = openid;
        this.nickname = nickname;
        this.coustName = coustName;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.regionCode = regionCode;
        this.createTime = createTime;
        this.card = card;
    }

    /**
     *
     */
    public CustomerEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：顾客id
     * 表字段：customer.coust_id
     * </pre>
     *
     * @return customer.coust_id：顾客id
     */
    public Integer getCoustId() {
        return coustId;
    }

    /**
     * <pre>
     * 设置：顾客id
     * 表字段：customer.coust_id
     * </pre>
     *
     * @param coustId
     *            customer.coust_id：顾客id
     */
    public void setCoustId(Integer coustId) {
        this.coustId = coustId;
    }

    /**
     * <pre>
     * 获取：微信openid
     * 表字段：customer.openid
     * </pre>
     *
     * @return customer.openid：微信openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * <pre>
     * 设置：微信openid
     * 表字段：customer.openid
     * </pre>
     *
     * @param openid
     *            customer.openid：微信openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * <pre>
     * 获取：昵称
     * 表字段：customer.nickname
     * </pre>
     *
     * @return customer.nickname：昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * <pre>
     * 设置：昵称
     * 表字段：customer.nickname
     * </pre>
     *
     * @param nickname
     *            customer.nickname：昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * <pre>
     * 获取：用户姓名
     * 表字段：customer.coust_name
     * </pre>
     *
     * @return customer.coust_name：用户姓名
     */
    public String getCoustName() {
        return coustName;
    }

    /**
     * <pre>
     * 设置：用户姓名
     * 表字段：customer.coust_name
     * </pre>
     *
     * @param coustName
     *            customer.coust_name：用户姓名
     */
    public void setCoustName(String coustName) {
        this.coustName = coustName;
    }

    /**
     * <pre>
     * 获取：性别
     * 表字段：customer.sex
     * </pre>
     *
     * @return customer.sex：性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * <pre>
     * 设置：性别
     * 表字段：customer.sex
     * </pre>
     *
     * @param sex
     *            customer.sex：性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * <pre>
     * 获取：顾客手机号
     * 表字段：customer.phone
     * </pre>
     *
     * @return customer.phone：顾客手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <pre>
     * 设置：顾客手机号
     * 表字段：customer.phone
     * </pre>
     *
     * @param phone
     *            customer.phone：顾客手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <pre>
     * 获取：顾客密码
     * 表字段：customer.password
     * </pre>
     *
     * @return customer.password：顾客密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * <pre>
     * 设置：顾客密码
     * 表字段：customer.password
     * </pre>
     *
     * @param password
     *            customer.password：顾客密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <pre>
     * 获取：顾客所属地区码
     * 表字段：customer.region_code
     * </pre>
     *
     * @return customer.region_code：顾客所属地区码
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * <pre>
     * 设置：顾客所属地区码
     * 表字段：customer.region_code
     * </pre>
     *
     * @param regionCode
     *            customer.region_code：顾客所属地区码
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * <pre>
     * 获取：注册时间
     * 表字段：customer.create_time
     * </pre>
     *
     * @return customer.create_time：注册时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：注册时间
     * 表字段：customer.create_time
     * </pre>
     *
     * @param createTime
     *            customer.create_time：注册时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <pre>
     * 获取：顾客会员卡
     * 表字段：customer.card
     * </pre>
     *
     * @return customer.card：顾客会员卡
     */
    public String getCard() {
        return card;
    }

    /**
     * <pre>
     * 设置：顾客会员卡
     * 表字段：customer.card
     * </pre>
     *
     * @param card
     *            customer.card：顾客会员卡
     */
    public void setCard(String card) {
        this.card = card;
    }
}