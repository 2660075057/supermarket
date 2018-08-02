package com.grape.supermarket.entity.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InventoryEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : inventory.inventory_id
     * </pre>
     */
    private Integer inventoryId;

    /**
     * <pre>
     * 盘前数量
     * 表字段 : inventory.befor
     * </pre>
     */
    private Integer befor;

    /**
     * <pre>
     * 盘后数量
     * 表字段 : inventory.after
     * </pre>
     */
    private Integer after;

    /**
     * <pre>
     * 异常数
     * 表字段 : inventory.error
     * </pre>
     */
    private Integer error;

    /**
     * <pre>
     * 售出数
     * 表字段 : inventory.sold
     * </pre>
     */
    private Integer sold;

    /**
     * <pre>
     * 操作员id
     * 表字段 : inventory.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 商店id
     * 表字段 : inventory.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 盘点时间
     * 表字段 : inventory.inventory_time
     * </pre>
     */
    private Date inventoryTime;

    /**
     * <pre>
     * 创建时间
     * 表字段 : inventory.create_time
     * </pre>
     */
    private Date createTime;
    private List<Integer> idRange;//id范围，此条件会生成shop_id in(1,2,3,4,5)的条件

    /**
     *
     */
    public InventoryEntity(Integer inventoryId, Integer befor, Integer after, Integer error, Integer sold, Integer operId, Integer shopId, Date inventoryTime, Date createTime) {
        this.inventoryId = inventoryId;
        this.befor = befor;
        this.after = after;
        this.error = error;
        this.sold = sold;
        this.operId = operId;
        this.shopId = shopId;
        this.inventoryTime = inventoryTime;
        this.createTime = createTime;
    }

    /**
     *
     */
    public InventoryEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：inventory.inventory_id
     * </pre>
     *
     * @return inventory.inventory_id：
     */
    public Integer getInventoryId() {
        return inventoryId;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：inventory.inventory_id
     * </pre>
     *
     * @param inventoryId
     *            inventory.inventory_id：
     */
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * <pre>
     * 获取：盘前数量
     * 表字段：inventory.befor
     * </pre>
     *
     * @return inventory.befor：盘前数量
     */
    public Integer getBefor() {
        return befor;
    }

    /**
     * <pre>
     * 设置：盘前数量
     * 表字段：inventory.befor
     * </pre>
     *
     * @param befor
     *            inventory.befor：盘前数量
     */
    public void setBefor(Integer befor) {
        this.befor = befor;
    }

    /**
     * <pre>
     * 获取：盘后数量
     * 表字段：inventory.after
     * </pre>
     *
     * @return inventory.after：盘后数量
     */
    public Integer getAfter() {
        return after;
    }

    /**
     * <pre>
     * 设置：盘后数量
     * 表字段：inventory.after
     * </pre>
     *
     * @param after
     *            inventory.after：盘后数量
     */
    public void setAfter(Integer after) {
        this.after = after;
    }

    /**
     * <pre>
     * 获取：异常数
     * 表字段：inventory.error
     * </pre>
     *
     * @return inventory.error：异常数
     */
    public Integer getError() {
        return error;
    }

    /**
     * <pre>
     * 设置：异常数
     * 表字段：inventory.error
     * </pre>
     *
     * @param error
     *            inventory.error：异常数
     */
    public void setError(Integer error) {
        this.error = error;
    }

    /**
     * <pre>
     * 获取：售出数
     * 表字段：inventory.sold
     * </pre>
     *
     * @return inventory.sold：售出数
     */
    public Integer getSold() {
        return sold;
    }

    /**
     * <pre>
     * 设置：售出数
     * 表字段：inventory.sold
     * </pre>
     *
     * @param sold
     *            inventory.sold：售出数
     */
    public void setSold(Integer sold) {
        this.sold = sold;
    }

    /**
     * <pre>
     * 获取：操作员id
     * 表字段：inventory.oper_id
     * </pre>
     *
     * @return inventory.oper_id：操作员id
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：操作员id
     * 表字段：inventory.oper_id
     * </pre>
     *
     * @param operId
     *            inventory.oper_id：操作员id
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：inventory.shop_id
     * </pre>
     *
     * @return inventory.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：inventory.shop_id
     * </pre>
     *
     * @param shopId
     *            inventory.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：盘点时间
     * 表字段：inventory.inventory_time
     * </pre>
     *
     * @return inventory.inventory_time：盘点时间
     */
    public Date getInventoryTime() {
        return inventoryTime;
    }

    /**
     * <pre>
     * 设置：盘点时间
     * 表字段：inventory.inventory_time
     * </pre>
     *
     * @param inventoryTime
     *            inventory.inventory_time：盘点时间
     */
    public void setInventoryTime(Date inventoryTime) {
        this.inventoryTime = inventoryTime;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：inventory.create_time
     * </pre>
     *
     * @return inventory.create_time：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：inventory.create_time
     * </pre>
     *
     * @param createTime
     *            inventory.create_time：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Integer> getIdRange() {
        return idRange;
    }

    public void setIdRange(List<Integer> idRange) {
        this.idRange = idRange;
    }
}