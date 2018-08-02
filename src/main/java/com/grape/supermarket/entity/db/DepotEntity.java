package com.grape.supermarket.entity.db;

import java.util.Date;
import java.util.List;

public class DepotEntity {
    /**
     * <pre>
     * 库存id
     * 表字段 : depot.depot_id
     * </pre>
     */
    private Integer depotId;

    /**
     * <pre>
     * 商店id
     * 表字段 : depot.shop_id
     * </pre>
     */
    private Integer shopId;

    /**
     * <pre>
     * 商品id
     * 表字段 : depot.comm_id
     * </pre>
     */
    private Integer commId;

    /**
     * <pre>
     * 数量
     * 表字段 : depot.amount
     * </pre>
     */
    private Integer amount;
    
    /**
     * 进货价
     */
    private  Integer costPrice;

    /**
     * <pre>
     * 最后操作员id
     * 表字段 : depot.oper_id
     * </pre>
     */
    private Integer operId;

    /**
     * <pre>
     * 创建时间/最后更新时间
     * 表字段 : depot.create_time
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * 乐观锁字段
     * 表字段 : depot.version
     * </pre>
     */
    private Integer version;

    /**
     * <pre>
     * 告警阀值，amount达到此值将告警，-1为不告警
     * 表字段 : depot.threshold
     * </pre>
     */
    private Integer threshold;

    /**
     * <pre>
     * 库存状态，0为可销售，1为暂停销售
     * 表字段 : depot.state
     * </pre>
     */
    private Byte state;
    /**
     * <pre>
     * 条码号
     * 表字段 : commodity.barcode
     * </pre>
     */
    private String barcode;
    /**
     * <pre>
     * 商品名
     * 表字段 : commodity.comm_name
     * </pre>
     */
    private String commName;
    /**
     * <pre>
     *
     * 表字段 : commodity_type.type_name
     * </pre>
     */
    private String typeName;
    /**
     * <pre>
     * 商店名
     * 表字段 : shop.shop_name
     * </pre>
     */
    private String shopName;
    /**
     *
     * 商店id多个 ","分割
     */
    private String shop_id_str;
    private List<Integer> idRange;//id范围，此条件会生成shop_id in(1,2,3,4,5)的条件

    /**
     * 商品类型ID
     */
    private Integer typeId;
    /**
     * 多个typeid，查询除了这些类型以外的记录
     */
    private String notTypeIds;

    /**
     *
     */
    public DepotEntity(Integer depotId, Integer shopId, Integer commId, Integer amount, Integer operId, Date createTime, Integer version, Integer threshold, Byte state, String barcode, String commName, String typeName, String shopName, String shop_id_str, List<Integer> idRange, Integer typeId, String notTypeIds) {
        this.depotId = depotId;
        this.shopId = shopId;
        this.commId = commId;
        this.amount = amount;
        this.operId = operId;
        this.createTime = createTime;
        this.version = version;
        this.threshold = threshold;
        this.state = state;
        this.barcode = barcode;
        this.commName = commName;
        this.typeName = typeName;
        this.shopName = shopName;
        this.shop_id_str = shop_id_str;
        this.idRange = idRange;
        this.typeId = typeId;
        this.notTypeIds = notTypeIds;
    }

    /**
     *
     */
    public DepotEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：库存id
     * 表字段：depot.depot_id
     * </pre>
     *
     * @return depot.depot_id：库存id
     */
    public Integer getDepotId() {
        return depotId;
    }

    /**
     * <pre>
     * 设置：库存id
     * 表字段：depot.depot_id
     * </pre>
     *
     * @param depotId
     *            depot.depot_id：库存id
     */
    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    /**
     * <pre>
     * 获取：商店id
     * 表字段：depot.shop_id
     * </pre>
     *
     * @return depot.shop_id：商店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * <pre>
     * 设置：商店id
     * 表字段：depot.shop_id
     * </pre>
     *
     * @param shopId
     *            depot.shop_id：商店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * <pre>
     * 获取：商品id
     * 表字段：depot.comm_id
     * </pre>
     *
     * @return depot.comm_id：商品id
     */
    public Integer getCommId() {
        return commId;
    }

    /**
     * <pre>
     * 设置：商品id
     * 表字段：depot.comm_id
     * </pre>
     *
     * @param commId
     *            depot.comm_id：商品id
     */
    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    /**
     * <pre>
     * 获取：数量
     * 表字段：depot.amount
     * </pre>
     *
     * @return depot.amount：数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * <pre>
     * 设置：数量
     * 表字段：depot.amount
     * </pre>
     *
     * @param amount
     *            depot.amount：数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * <pre>
     * 获取：最后操作员id
     * 表字段：depot.oper_id
     * </pre>
     *
     * @return depot.oper_id：最后操作员id
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * <pre>
     * 设置：最后操作员id
     * 表字段：depot.oper_id
     * </pre>
     *
     * @param operId
     *            depot.oper_id：最后操作员id
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * <pre>
     * 获取：创建时间/最后更新时间
     * 表字段：depot.create_time
     * </pre>
     *
     * @return depot.create_time：创建时间/最后更新时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <pre>
     * 设置：创建时间/最后更新时间
     * 表字段：depot.create_time
     * </pre>
     *
     * @param createTime
     *            depot.create_time：创建时间/最后更新时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <pre>
     * 获取：乐观锁字段
     * 表字段：depot.version
     * </pre>
     *
     * @return depot.version：乐观锁字段
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * <pre>
     * 设置：乐观锁字段
     * 表字段：depot.version
     * </pre>
     *
     * @param version
     *            depot.version：乐观锁字段
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * <pre>
     * 获取：告警阀值，amount达到此值将告警，-1为不告警
     * 表字段：depot.threshold
     * </pre>
     *
     * @return depot.threshold：告警阀值，amount达到此值将告警，-1为不告警
     */
    public Integer getThreshold() {
        return threshold;
    }

    /**
     * <pre>
     * 设置：告警阀值，amount达到此值将告警，-1为不告警
     * 表字段：depot.threshold
     * </pre>
     *
     * @param threshold
     *            depot.threshold：告警阀值，amount达到此值将告警，-1为不告警
     */
    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    /**
     * <pre>
     * 获取：库存状态，0为可销售，1为暂停销售
     * 表字段：depot.state
     * </pre>
     *
     * @return depot.state：库存状态，0为可销售，1为暂停销售
     */
    public Byte getState() {
        return state;
    }

    /**
     * <pre>
     * 设置：库存状态，0为可销售，1为暂停销售
     * 表字段：depot.state
     * </pre>
     *
     * @param state
     *            depot.state：库存状态，0为可销售，1为暂停销售
     */
    public void setState(Byte state) {
        this.state = state;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getShop_id_str() {
        return shop_id_str;
    }

    public void setShop_id_str(String shop_id_str) {
        this.shop_id_str = shop_id_str;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Integer> getIdRange() {
        return idRange;
    }

    public void setIdRange(List<Integer> idRange) {
        this.idRange = idRange;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getNotTypeIds() {
        return notTypeIds;
    }

    public void setNotTypeIds(String notTypeIds) {
        this.notTypeIds = notTypeIds;
    }

	public Integer getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Integer costPrice) {
		this.costPrice = costPrice;
	}
}