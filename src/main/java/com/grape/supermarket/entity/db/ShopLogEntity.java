package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.Date;

public class ShopLogEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : shop_log.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 商店id
     * 表字段 : shop_log.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 顾客id
     * 表字段 : shop_log.coust_id
     * </pre>
     */
    private Integer coustId;

    /**
     * <pre>
     * 进店类型，1微信扫码进店
     * 表字段 : shop_log.type
     * </pre>
     */
    private Byte type;

    /**
     * <pre>
     * 入店时间
     * 表字段 : shop_log.create_time
     * </pre>
     */
    private Date createTime;

    /**
     *
     */
    public ShopLogEntity(Integer id, Integer shopId, Integer coustId, Byte type, Date createTime) {
        this.id = id;
        this.shopId = shopId;
        this.coustId = coustId;
        this.type = type;
        this.createTime = createTime;
    }

    /**
     *
     */
    public ShopLogEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：shop_log.id
     * </pre>
     *
     * @return shop_log.id：
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：shop_log.id
     * </pre>
     *
     * @param id
     *            shop_log.id：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：shop_log.shop_id
     * </pre>
     *
     * @return shop_log.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：shop_log.shop_id
     * </pre>
     *
     * @param shopId
     *            shop_log.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：顾客id
     * 表字段：shop_log.coust_id
     * </pre>
     *
     * @return shop_log.coust_id：顾客id
     */
    public Integer getCoustId() {
        return coustId;
    }

    /**
     * <pre>
     * 设置：顾客id
     * 表字段：shop_log.coust_id
     * </pre>
     *
     * @param coustId
     *            shop_log.coust_id：顾客id
     */
    public void setCoustId(Integer coustId) {
        this.coustId = coustId;
    }

    /**
     * <pre>
     * 获取：进店类型，1微信扫码进店
     * 表字段：shop_log.type
     * </pre>
     *
     * @return shop_log.type：进店类型，1微信扫码进店
     */
    public Byte getType() {
        return type;
    }

    /**
     * <pre>
     * 设置：进店类型，1微信扫码进店
     * 表字段：shop_log.type
     * </pre>
     *
     * @param type
     *            shop_log.type：进店类型，1微信扫码进店
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * <pre>
     * 获取：入店时间
     * 表字段：shop_log.create_time
     * </pre>
     *
     * @return shop_log.create_time：入店时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：入店时间
     * 表字段：shop_log.create_time
     * </pre>
     *
     * @param createTime
     *            shop_log.create_time：入店时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}