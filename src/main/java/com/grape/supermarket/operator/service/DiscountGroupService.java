package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.DiscountEntity;
import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.page.DiscountGroupPageEntity;
import com.grape.supermarket.entity.param.DiscountGroupParam;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/9/29.
 */

public interface DiscountGroupService {
    boolean addDiscountGroup(DiscountGroupEntity discountGroup,List<DiscountEntity> discounts);

    boolean addDiscountGroup(DiscountGroupEntity discountGroup,Map<Integer,Object> commDiscount,Map<Integer,Object> typeDiscount,List<Object> allDiscount);
    
    List<DiscountGroupPageEntity> discountGroupList(DiscountGroupParam param, PageParam page);

	int countDiscountGroup(DiscountGroupParam param);

	DiscountGroupPageEntity selectByPrimaryKey(Integer groupId);
	
	int deleteByPrimaryKey(Integer groupId);
}
