package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.ShopCommodityEntity;
import com.grape.supermarket.entity.db.ShopEntity;

/**
 * Created by LQW on 2017/10/13.
 */

public class ShopCommodityPageEntity extends ShopCommodityEntity {
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

    public String getTypeName() {
        return typeName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getCommName() {
        return commName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
