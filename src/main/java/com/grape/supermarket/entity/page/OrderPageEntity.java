package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.OrderEntity;
import com.grape.supermarket.entity.db.ShopEntity;

import java.util.List;

/**
 * Created by LQW on 2017/10/18.
 */

public class OrderPageEntity extends OrderEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OrderDetailPageEntity> orderDetail;
    private ShopEntity shopEntity;
    private CustomerEntity customer;

    public ShopEntity getShopEntity() {
        return shopEntity;
    }

    public List<OrderDetailPageEntity> getOrderDetail() {
        return orderDetail;
    }

    public void setShopEntity(ShopEntity shopEntity) {
        this.shopEntity = shopEntity;
    }

    public void setOrderDetail(List<OrderDetailPageEntity> orderDetail) {
        this.orderDetail = orderDetail;
    }

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
    
}
