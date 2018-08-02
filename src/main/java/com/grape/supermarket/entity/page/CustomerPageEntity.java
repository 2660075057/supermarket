package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.ShopEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2017/9/26.
 */

public class CustomerPageEntity extends CustomerEntity {
    private long paymentTotal;//累计消费
    private long preferentialTotal;//累计优优惠
    private Date lastCome;//最后进店时间
    
    private List<OrderDetailPageEntity> orders;
    private List<ShopLogPageEntity> shopLogs;
    
    private List<ShopEntity> shops;

    public long getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(long paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public long getPreferentialTotal() {
        return preferentialTotal;
    }

    public void setPreferentialTotal(long preferentialTotal) {
        this.preferentialTotal = preferentialTotal;
    }

    public Date getLastCome() {
        return lastCome;
    }

    public void setLastCome(Date lastCome) {
        this.lastCome = lastCome;
    }

	public List<OrderDetailPageEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetailPageEntity> orders) {
		this.orders = orders;
	}

	public List<ShopLogPageEntity> getShopLogs() {
		return shopLogs;
	}

	public void setShopLogs(List<ShopLogPageEntity> shopLogs) {
		this.shopLogs = shopLogs;
	}

	public List<ShopEntity> getShops() {
		return shops;
	}

	public void setShops(List<ShopEntity> shops) {
		this.shops = shops;
	}
	
}
