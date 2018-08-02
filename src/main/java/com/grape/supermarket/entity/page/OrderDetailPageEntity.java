package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.OrderDetailEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by LQW on 2017/10/18.
 */

public class OrderDetailPageEntity extends OrderDetailEntity {


    /**
     * <pre>
     * 条码号
     * 表字段 : commodity.barcode
     * </pre>
     */
    private String barcode;

    /**
     * <pre>
     *
     * 表字段 : commodity.type_id
     * </pre>
     */
    private Integer typeId;

    /**
     * <pre>
     * 商品名
     * 表字段 : commodity.comm_name
     * </pre>
     */
    private String commName;

    /**
     * <pre>
     * 商品单价
     * 表字段 : commodity.comm_price
     * </pre>
     */
    private Integer commPrice;
    
    private Integer shopId;

    /**
     * <pre>
     * 删除标记，0为正常，1为已经删除
     * 表字段 : commodity.delete_mark
     * </pre>
     */
    private Byte deleteMark;
    
    //数量
    private Integer commCount;
    //创建时间
    private Date createTime;
    //商品名
    private String shopName;

    public String getBarcode() {
        return barcode;
    }

    public String getCommName() {
        return commName;
    }

    public Byte getDeleteMark() {
        return deleteMark;
    }

    public Integer getCommPrice() {
        return commPrice;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public void setCommPrice(Integer commPrice) {
        this.commPrice = commPrice;
    }

    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

	public Integer getCommCount() {
		return commCount;
	}

	public void setCommCount(Integer commCount) {
		this.commCount = commCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

    public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "OrderDetailPageEntity [barcode=" + barcode + ", typeId="
				+ typeId + ", commName=" + commName + ", commPrice="
				+ commPrice + ", shopId=" + shopId + ", deleteMark="
				+ deleteMark + ", commCount=" + commCount + ", createTime="
				+ createTime + ", shopName=" + shopName + "]";
	}

}
