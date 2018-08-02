package com.grape.supermarket.operator.service;

import java.util.List;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.PurchaseDetailEntity;
import com.grape.supermarket.entity.db.PurchaseEntity;
import com.grape.supermarket.entity.page.PurchasePageEntity;
import com.grape.supermarket.entity.param.PurchaseParam;

/**
 * 
 * author huanghuang
 * 2017年11月18日 下午2:15:17
 */
public interface PurchaseService {
	/**
	 * 添加采购单
	 * author huanghuang
	 * 2017年11月18日 下午2:53:29
	 * @param purchase
	 * @param purchaseDetail
	 * @return
	 */
    boolean addPurchase(PurchaseEntity purchase,List<PurchaseDetailEntity> purchaseDetail);
    
    /**
     * 分页查询采购单
     * author huanghuang
     * 2017年11月18日 下午2:53:44
     * @param entity
     * @param p
     * @return
     */
	List<PurchasePageEntity> purchaseList(PurchaseParam entity, PageParam p);

	/**
	 * 查询采购单的记录数
	 * author huanghuang
	 * 2017年11月18日 下午2:54:04
	 * @param purchaseParam
	 * @return
	 */
	int countPurchase(PurchaseParam purchaseParam);
	
	/**
	 * 删除采购单
	 * author huanghuang
	 * 2017年11月18日 下午4:29:34
	 * @param purId
	 * @return
	 */
	int deleteByPrimaryKey(Integer purId);

	PurchasePageEntity selectByPrimaryKey(Integer purId);
	
	/**
	 * 更新采购单
	 * author huanghuang
	 * 2017年11月20日 上午11:45:28
	 * @param purchaseEntity
	 * @param purchaseDetails
	 * @return
	 */
	boolean updPurchase(PurchaseEntity purchaseEntity,
			List<PurchaseDetailEntity> purchaseDetails);
}
