package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class InventoryDetailEntity implements Serializable{
    /**
     * <pre>
     * id，无实际意义
     * 表字段 : inventory_detail.detail_id
     * </pre>
     */
    private Integer detailId;

    /**
     * <pre>
     * 盘点id
     * 表字段 : inventory_detail.inventory_id
     * </pre>
     */
    private Integer inventoryId;

    /**
     * <pre>
     * 商品id
     * 表字段 : inventory_detail.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 盘前数量
     * 表字段 : inventory_detail.befor
     * </pre>
     */
    private Integer befor;

    /**
     * <pre>
     * 盘后数量
     * 表字段 : inventory_detail.after
     * </pre>
     */
    private Integer after;

    /**
     * <pre>
     * 错误数量
     * 表字段 : inventory_detail.error
     * </pre>
     */
    private Integer error;

    /**
     * <pre>
     * 售出数量
     * 表字段 : inventory_detail.sold
     * </pre>
     */
    private Integer sold;

    /**
     *
     */
    public InventoryDetailEntity(Integer detailId, Integer inventoryId, Integer commId, Integer befor, Integer after, Integer error, Integer sold) {
        this.detailId = detailId;
        this.inventoryId = inventoryId;
        this.commId = commId;
        this.befor = befor;
        this.after = after;
        this.error = error;
        this.sold = sold;
    }

    /**
     *
     */
    public InventoryDetailEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：id，无实际意义
     * 表字段：inventory_detail.detail_id
     * </pre>
     *
     * @return inventory_detail.detail_id：id，无实际意义
     */
    public Integer getDetailId() {
        return detailId;
    }

    /**
     * <pre>
     * 设置：id，无实际意义
     * 表字段：inventory_detail.detail_id
     * </pre>
     *
     * @param detailId
     *            inventory_detail.detail_id：id，无实际意义
     */
    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    /**
     * <pre>
     * 获取：盘点id
     * 表字段：inventory_detail.inventory_id
     * </pre>
     *
     * @return inventory_detail.inventory_id：盘点id
     */
    public Integer getInventoryId() {
        return inventoryId;
    }

    /**
     * <pre>
     * 设置：盘点id
     * 表字段：inventory_detail.inventory_id
     * </pre>
     *
     * @param inventoryId
     *            inventory_detail.inventory_id：盘点id
     */
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * <pre>
     * 获取：商品id
     * 表字段：inventory_detail.comm_id
     * </pre>
     *
     * @return inventory_detail.comm_id：商品id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id
     * 表字段：inventory_detail.comm_id
     * </pre>
     *
     * @param commId
     *            inventory_detail.comm_id：商品id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：盘前数量
     * 表字段：inventory_detail.befor
     * </pre>
     *
     * @return inventory_detail.befor：盘前数量
     */
    public Integer getBefor() {
        return befor;
    }

    /**
     * <pre>
     * 设置：盘前数量
     * 表字段：inventory_detail.befor
     * </pre>
     *
     * @param befor
     *            inventory_detail.befor：盘前数量
     */
    public void setBefor(Integer befor) {
        this.befor = befor;
    }

    /**
     * <pre>
     * 获取：盘后数量
     * 表字段：inventory_detail.after
     * </pre>
     *
     * @return inventory_detail.after：盘后数量
     */
    public Integer getAfter() {
        return after;
    }

    /**
     * <pre>
     * 设置：盘后数量
     * 表字段：inventory_detail.after
     * </pre>
     *
     * @param after
     *            inventory_detail.after：盘后数量
     */
    public void setAfter(Integer after) {
        this.after = after;
    }

    /**
     * <pre>
     * 获取：错误数量
     * 表字段：inventory_detail.error
     * </pre>
     *
     * @return inventory_detail.error：错误数量
     */
    public Integer getError() {
        return error;
    }

    /**
     * <pre>
     * 设置：错误数量
     * 表字段：inventory_detail.error
     * </pre>
     *
     * @param error
     *            inventory_detail.error：错误数量
     */
    public void setError(Integer error) {
        this.error = error;
    }

    /**
     * <pre>
     * 获取：售出数量
     * 表字段：inventory_detail.sold
     * </pre>
     *
     * @return inventory_detail.sold：售出数量
     */
    public Integer getSold() {
        return sold;
    }

    /**
     * <pre>
     * 设置：售出数量
     * 表字段：inventory_detail.sold
     * </pre>
     *
     * @param sold
     *            inventory_detail.sold：售出数量
     */
    public void setSold(Integer sold) {
        this.sold = sold;
    }
}