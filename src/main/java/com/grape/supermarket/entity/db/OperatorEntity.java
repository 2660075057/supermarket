package com.grape.supermarket.entity.db;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OperatorEntity implements Serializable{

    /**
     * <pre>
     * 操作员id
     * 表字段 : operator.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 操作员账号
     * 表字段 : operator.oper_account
     * </pre>
     */
    @NotNull
    @Length(min = 3,max = 16,message = "用户名长度应该在3-16之间")
    private String operAccount;

    /**
     * <pre>
     * 
     * 表字段 : operator.oper_pwd
     * </pre>
     */
    private String operPwd;

    /**
     * <pre>
     * 操作员姓名
     * 表字段 : operator.oper_name
     * </pre>
     */
    private String operName;

    /**
     * <pre>
     * 
     * 表字段 : operator.sex
     * </pre>
     */
    private String sex;

    /**
     * <pre>
     * 
     * 表字段 : operator.phone
     * </pre>
     */
    private String phone;

    /**
     * <pre>
     * 权限组id
     * 表字段 : operator.group_id
     * </pre>
     */
    private Integer groupId;

    /**
     * <pre>
     * 操作员状态，0正常，1锁定，2删除
     * 表字段 : operator.state
     * </pre>
     */
    private Byte state;

    private String openId;//管理员微信openid
    private String maintenanceCard;//管理员维护卡号

    private Date createTime;//创建时间

    private List<ShopEntity> shops;//管理站点
    private GroupEntity userGroup;//用户组

    public OperatorEntity(Integer operId, String operAccount, String operPwd, String operName, String sex, String phone, Integer groupId, Byte state, String openId, String maintenanceCard,Date createTime) {
        this.operId = operId;
        this.operAccount = operAccount;
        this.operPwd = operPwd;
        this.operName = operName;
        this.sex = sex;
        this.phone = phone;
        this.groupId = groupId;
        this.state = state;
        this.openId = openId;
        this.maintenanceCard = maintenanceCard;
        this.createTime = createTime;
    }

    /**
     *
     */
    public OperatorEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：操作员id
     * 表字段：operator.oper_id
     * </pre>
     *
     * @return operator.oper_id：操作员id
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：操作员id
     * 表字段：operator.oper_id
     * </pre>
     *
     * @param operId
     *            operator.oper_id：操作员id
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：操作员账号
     * 表字段：operator.oper_account
     * </pre>
     *
     * @return operator.oper_account：操作员账号
     */
    public String getOperAccount() {
        return operAccount;
    }

    /**
     * <pre>
     * 设置：操作员账号
     * 表字段：operator.oper_account
     * </pre>
     *
     * @param operAccount
     *            operator.oper_account：操作员账号
     */
    public void setOperAccount(String operAccount) {
        this.operAccount = operAccount;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：operator.oper_pwd
     * </pre>
     *
     * @return operator.oper_pwd：
     */
    public String getOperPwd() {
        return operPwd;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：operator.oper_pwd
     * </pre>
     *
     * @param operPwd
     *            operator.oper_pwd：
     */
    public void setOperPwd(String operPwd) {
        this.operPwd = operPwd;
    }

    /**
     * <pre>
     * 获取：操作员姓名
     * 表字段：operator.oper_name
     * </pre>
     *
     * @return operator.oper_name：操作员姓名
     */
    public String getOperName() {
        return operName;
    }

    /**
     * <pre>
     * 设置：操作员姓名
     * 表字段：operator.oper_name
     * </pre>
     *
     * @param operName
     *            operator.oper_name：操作员姓名
     */
    public void setOperName(String operName) {
        this.operName = operName;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：operator.sex
     * </pre>
     *
     * @return operator.sex：
     */
    public String getSex() {
        return sex;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：operator.sex
     * </pre>
     *
     * @param sex
     *            operator.sex：
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * <pre>
     * 获取：
     * 表字段：operator.phone
     * </pre>
     *
     * @return operator.phone：
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：operator.phone
     * </pre>
     *
     * @param phone
     *            operator.phone：
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <pre>
     * 获取：权限组id
     * 表字段：operator.group_id
     * </pre>
     *
     * @return operator.group_id：权限组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * <pre>
     * 设置：权限组id
     * 表字段：operator.group_id
     * </pre>
     *
     * @param groupId
     *            operator.group_id：权限组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * <pre>
     * 获取：操作员状态，0正常，1锁定，2删除
     * 表字段：operator.state
     * </pre>
     *
     * @return operator.state：操作员状态，0正常，1锁定，2删除
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：操作员状态，0正常，1锁定，2删除
     * 表字段：operator.state
     * </pre>
     *
     * @param state
     *            operator.state：操作员状态，0正常，1锁定，2删除
     */
    public void setState(Byte state) {
        this.state = state;
    }

    public List<ShopEntity> getShops() {
        return shops;
    }

    public void setShops(List<ShopEntity> shops) {
        this.shops = shops;
    }

    public GroupEntity getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(GroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMaintenanceCard() {
        return maintenanceCard;
    }

    public void setMaintenanceCard(String maintenanceCard) {
        this.maintenanceCard = maintenanceCard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}