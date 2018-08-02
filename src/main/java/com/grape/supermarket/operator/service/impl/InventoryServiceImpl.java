package com.grape.supermarket.operator.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.operator.OperatorSession;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.dao.InventoryDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.InventoryEntity;
import com.grape.supermarket.entity.page.InventoryPageEntity;
import com.grape.supermarket.entity.param.InventoryParam;
import com.grape.supermarket.operator.service.InventoryService;


/**
 * 
 * author huanghuang
 * 2017年10月18日 下午4:39:15
 */
@Service
public class InventoryServiceImpl extends BasicService implements InventoryService {
	 @Autowired
	 private InventoryDao inventoryDao;
	 @Autowired
     private ShopDao shopDao;
	 @Autowired
     private OperatorDao operatorDao;

	@Override
	public List<InventoryPageEntity> inventoryList(InventoryParam param,
			PageParam page) {
		if (param == null) {
			param = new InventoryParam();
		}
		if (page != null) {
			SelectEntity select = param.getSelect();
			if (select == null) {
				select = new SelectEntity();
			}
			select.setLimit(page.getLimit());
			param.setSelect(select);
		}
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        param.setIdRange(operatorSession.getShopIds());
		List<InventoryEntity> inventories = inventoryDao.selectByParam(param);
		List<InventoryPageEntity> datas = new ArrayList<>(inventories.size());
		try {
			for (InventoryEntity inventory : inventories) {
				InventoryPageEntity ipe = new InventoryPageEntity();
				BeanUtils.copyProperties(ipe, inventory);
                ShopEntity shopEntity = shopDao.selectByPrimaryKey(inventory.getShopId());
                OperatorEntity operatorEntity = operatorDao.selectByPrimaryKey(inventory.getOperId());
                ipe.setShop(shopEntity);
                ipe.setOperator(operatorEntity);
				datas.add(ipe);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanCopyException(e);
		}
		return datas;
	}

	@Override
	public int countInventory(InventoryParam param) {
		if (param == null) {
			param = new InventoryParam();
		}
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        param.setIdRange(operatorSession.getShopIds());
		return inventoryDao.countByParam(param);
	}
}
