package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.Date;

public class ShopEntity  implements Serializable {
    /**
     * <pre>
     * id
     * 表字段 : shop.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 商店名
     * 表字段 : shop.shop_name
     * </pre>
     */
    private String shopName;

    /**
     * <pre>
     * 商店描述
     * 表字段 : shop.info
     * </pre>
     */
    private String info;

    /**
     * <pre>
     * 经度
     * 表字段 : shop.longitude
     * </pre>
     */
    private Double longitude;

    /**
     * <pre>
     * 维度
     * 表字段 : shop.latitude
     * </pre>
     */
    private Double latitude;

    /**
     * <pre>
     * 商店地址
     * 表字段 : shop.address
     * </pre>
     */
    private String address;

    /**
     * <pre>
     * 地区码
     * 表字段 : shop.region_code
     * </pre>
     */
    private String regionCode;

    /**
     * <pre>
     * 商店状态，0服务中，1维护中
     * 表字段 : shop.state
     * </pre>
     */
    private Byte state;

    /**
     * <pre>
     * 创建时间
     * 表字段 : shop.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 获取：id
     * 表字段：shop.shop_id
     * </pre>
     *
     * @return shop.shop_id：id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：shop.shop_id
     * </pre>
     *
     * @param shopId
     *            shop.shop_id：id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：商店名
     * 表字段：shop.shop_name
     * </pre>
     *
     * @return shop.shop_name：商店名
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * <pre>
     * 设置：商店名
     * 表字段：shop.shop_name
     * </pre>
     *
     * @param shopName
     *            shop.shop_name：商店名
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * <pre>
     * 获取：商店描述
     * 表字段：shop.info
     * </pre>
     *
     * @return shop.info：商店描述
     */
    public String getInfo() {
        return info;
    }

    /**
     * <pre>
     * 设置：商店描述
     * 表字段：shop.info
     * </pre>
     *
     * @param info
     *            shop.info：商店描述
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * <pre>
     * 获取：经度
     * 表字段：shop.longitude
     * </pre>
     *
     * @return shop.longitude：经度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * <pre>
     * 设置：经度
     * 表字段：shop.longitude
     * </pre>
     *
     * @param longitude
     *            shop.longitude：经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * <pre>
     * 获取：维度
     * 表字段：shop.latitude
     * </pre>
     *
     * @return shop.latitude：维度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * <pre>
     * 设置：维度
     * 表字段：shop.latitude
     * </pre>
     *
     * @param latitude
     *            shop.latitude：维度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * <pre>
     * 获取：商店地址
     * 表字段：shop.address
     * </pre>
     *
     * @return shop.address：商店地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * <pre>
     * 设置：商店地址
     * 表字段：shop.address
     * </pre>
     *
     * @param address
     *            shop.address：商店地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <pre>
     * 获取：地区码
     * 表字段：shop.region_code
     * </pre>
     *
     * @return shop.region_code：地区码
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * <pre>
     * 设置：地区码
     * 表字段：shop.region_code
     * </pre>
     *
     * @param regionCode
     *            shop.region_code：地区码
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * <pre>
     * 获取：商店状态，0服务中，1维护中
     * 表字段：shop.state
     * </pre>
     *
     * @return shop.state：商店状态，0服务中，1维护中
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：商店状态，0服务中，1维护中
     * 表字段：shop.state
     * </pre>
     *
     * @param state
     *            shop.state：商店状态，0服务中，1维护中
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：shop.create_time
     * </pre>
     *
     * @return shop.create_time：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：shop.create_time
     * </pre>
     *
     * @param createTime
     *            shop.create_time：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}