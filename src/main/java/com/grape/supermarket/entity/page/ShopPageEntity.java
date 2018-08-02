package com.grape.supermarket.entity.page;

import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.entity.db.ShopEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by LXP on 2017/10/10.
 */

public class ShopPageEntity extends ShopEntity {
    private Double distance;//到用户距离,单位米

    public ShopPageEntity(ShopEntity shopEntity) {
        try {
            BeanUtils.copyProperties(this,shopEntity);
        } catch (IllegalAccessException|InvocationTargetException e) {
            throw new BeanCopyException(e);
        }
    }

    /**
     * 商店到用户距离，单位米
     * @return distance 商店到用户距离，单位米
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * 商店到用户距离，单位米
     * @param distance 商店到用户距离，单位米
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
