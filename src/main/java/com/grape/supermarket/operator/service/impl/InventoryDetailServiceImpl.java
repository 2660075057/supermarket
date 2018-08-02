package com.grape.supermarket.operator.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.grape.supermarket.dao.*;
import com.grape.supermarket.entity.db.*;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.entity.page.InventoryPageEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.entity.page.InventoryDetailPageEntity;
import com.grape.supermarket.entity.param.InventoryDetailParam;
import com.grape.supermarket.operator.service.InventoryDetailService;

/**
 * 
 * author huanghuang 2017年10月18日 下午4:39:15
 */
@Service
public class InventoryDetailServiceImpl extends BasicService implements
		InventoryDetailService {
	@Autowired
	private InventoryDetailDao inventoryDetailDao;
	@Autowired
	private CommodityDao commodityDao;
	@Autowired
    private CommodityTypeDao commodityTypeDao;
	@Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private OperatorDao operatorDao;

	@Override
	public List<InventoryDetailPageEntity> inventoryDetailList(
			InventoryDetailParam param) {
		if (param == null) {
			param = new InventoryDetailParam();
		}
		List<InventoryDetailEntity> inventoryDetails = inventoryDetailDao.selectByParam(param);
		List<InventoryDetailPageEntity> datas = new ArrayList<>(inventoryDetails.size());
		try {
			for (InventoryDetailEntity inventoryDetail : inventoryDetails) {
				InventoryDetailPageEntity idpe = new InventoryDetailPageEntity();
				BeanUtils.copyProperties(idpe, inventoryDetail);
				CommodityEntity commodity = commodityDao.selectByPrimaryKey(inventoryDetail.getCommId());
                CommodityTypeEntity commodityTypeEntity = commodityTypeDao.selectByPrimaryKey(commodity.getTypeId());
                CommodityPageEntity cp = new CommodityPageEntity();
                BeanUtils.copyProperties(cp, commodity);
                cp.setCommodityType(commodityTypeEntity);
				idpe.setCommodityPageEntity(cp);

                InventoryEntity inventoryEntity = inventoryDao.selectByPrimaryKey(inventoryDetail.getInventoryId());
                ShopEntity shopEntity = shopDao.selectByPrimaryKey(inventoryEntity.getShopId());
                OperatorEntity operatorEntity = operatorDao.selectByPrimaryKey(inventoryEntity.getOperId());
                InventoryPageEntity inventoryPageEntity = new InventoryPageEntity();
                BeanUtils.copyProperties(inventoryPageEntity, inventoryEntity);
                inventoryPageEntity.setOperator(operatorEntity);
                inventoryPageEntity.setShop(shopEntity);
				idpe.setInventoryPageEntity(inventoryPageEntity);
				datas.add(idpe);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanCopyException(e);
		}
		return datas;
	}

    @Override
    public List<InventoryDetailEntity> inventoryDetailListByTypeId(InventoryDetailParam param) {
        if (param == null) {
            param = new InventoryDetailParam();
        }
        List<InventoryDetailEntity> inventoryDetails = inventoryDetailDao.selectByParam(param);
        return inventoryDetails;
    }

}
