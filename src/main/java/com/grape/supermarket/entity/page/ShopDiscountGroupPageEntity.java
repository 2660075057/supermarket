package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;
import com.grape.supermarket.entity.db.ShopEntity;

/**
 * Created by LQW on 2017/11/14.
 */

public class ShopDiscountGroupPageEntity extends ShopDiscountGroupEntity {
    private DiscountGroupEntity discountGroupEntity;
    private ShopEntity shopEntity;

    public ShopEntity getShopEntity() {
        return shopEntity;
    }

    public DiscountGroupEntity getDiscountGroupEntity() {
        return discountGroupEntity;
    }

    public void setShopEntity(ShopEntity shopEntity) {
        this.shopEntity = shopEntity;
    }

    public void setDiscountGroupEntity(DiscountGroupEntity discountGroupEntity) {
        this.discountGroupEntity = discountGroupEntity;
    }
}
