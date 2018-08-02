package com.grape.supermarket.operator.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.dao.PurchaseDao;
import com.grape.supermarket.dao.PurchaseDetailDao;
import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PurchaseDetailEntity;
import com.grape.supermarket.entity.db.PurchaseEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.PurchaseDetailPageEntity;
import com.grape.supermarket.entity.page.PurchasePageEntity;
import com.grape.supermarket.entity.param.PurchaseParam;
import com.grape.supermarket.operator.service.PurchaseService;

/**
 * 
 * author huanghuang 2017年11月18日 下午2:17:50
 */
@Service
public class PurchaseServiceImpl extends BasicService implements PurchaseService {
	
	@Resource
    private PurchaseDao purchaseDao;
	
	@Resource
	private PurchaseDetailDao purchaseDetailDao;
	
    @Resource
    private ShopDao shopDao;
    
    @Resource
    private OperatorDao operatorDao;
    
    @Resource
    private CommodityDao commodityDao;
	
	@Override
	public boolean addPurchase(PurchaseEntity purchase, List<PurchaseDetailEntity> purchaseDetails) {
		if(purchase == null){
    		throw new RuntimeException("the purchase is null");
    	}
		if(purchaseDetails == null){
			throw new RuntimeException("the purchaseDetails is null");
		}
		List<Integer> commIdList = new ArrayList<Integer>();//商品的list
		for (PurchaseDetailEntity purchaseDetail : purchaseDetails) {
			Integer commId = purchaseDetail.getCommId();
			if(commId!=null){
				if(commIdList.contains(commId)){
					getLogger().info("添加采购单时商品存在重复->"+"commId:"+commId);
	                return false;
				}
				commIdList.add(commId);
			}
		}
		purchaseDao.insertSelective(purchase);//添加订购单
        if(purchase.getPurId() == null){
            throw new RuntimeException("insert purchase fail");
        }

        for (PurchaseDetailEntity purchaseDetail : purchaseDetails) {
        	purchaseDetail.setPurId(purchase.getPurId());
        	purchaseDetailDao.insertSelective(purchaseDetail);//添加订购单的详细
        }
		return true;
	}

	@Override
	public List<PurchasePageEntity> purchaseList(PurchaseParam param,
			PageParam page) {
		if(param == null){
			throw new RuntimeException("purchaseList() method throw the param is null");
		}
		try {
            SelectEntity select = new SelectEntity();
            if (page != null) {
                int[] limit = page.getLimit();
                select.setLimit(limit);
            }
            param.setSelect(select);
            param.setDeleteMark((byte)0);
            List<PurchaseEntity> purchases = purchaseDao.selectByParam(param);
            List<PurchasePageEntity> datas = new ArrayList<>(purchases.size());
            for (PurchaseEntity purchase : purchases) {
            	PurchasePageEntity ppe = new PurchasePageEntity();
                BeanUtils.copyProperties(ppe, purchase);
                //得到操作员
                OperatorEntity operator = operatorDao.selectByPrimaryKey(ppe.getOperId());
                ppe.setOperator(operator);
                //得到站点
                ShopEntity shop = shopDao.selectByPrimaryKey(ppe.getShopId());
                ppe.setShop(shop);
                datas.add(ppe);
            }
            return datas;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public int countPurchase(PurchaseParam param) {
		if(param == null){
			return 0;
		}
		param.setDeleteMark((byte)0);
		return purchaseDao.countByParam(param);
	}

	@Override
	public int deleteByPrimaryKey(Integer purId) {
		return purchaseDao.deleteByPrimaryKey(purId);
	}

	@Override
	public PurchasePageEntity selectByPrimaryKey(Integer purId) {
		if(purId == null){
			getLogger().info("the purId is null");
			return new PurchasePageEntity();
		}
		PurchasePageEntity ppe = new PurchasePageEntity();
		PurchaseEntity purchase = purchaseDao.selectByPrimaryKey(purId);
		List<PurchaseDetailPageEntity> purchaseDetailPages = new ArrayList<>();
		if(purchase!=null){
			try {
				BeanUtils.copyProperties(ppe, purchase);
				List<PurchaseDetailEntity> list = purchaseDetailDao.selectListByPurId(purchase.getPurId());
				if(list != null){
					for(PurchaseDetailEntity purchaseDetail : list){
						PurchaseDetailPageEntity pdpe = new PurchaseDetailPageEntity();
						BeanUtils.copyProperties(pdpe, purchaseDetail);
						CommodityEntity commodity = commodityDao.selectByPrimaryKey(purchaseDetail.getCommId());//得到商品
						pdpe.setCommodity(commodity);
						purchaseDetailPages.add(pdpe);
					}
					ppe.setPurchaseDetailPages(purchaseDetailPages);
				}
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		return ppe;
	}

	@Override
	public boolean updPurchase(PurchaseEntity purchase,
			List<PurchaseDetailEntity> purchaseDetails) {
		if(purchase == null){
    		throw new RuntimeException("the purchase is null");
    	}
		if(purchaseDetails == null){
			throw new RuntimeException("the purchaseDetails is null");
		}
		if(purchaseDetails.isEmpty()){
			purchaseDao.updateByPrimaryKeySelective(purchase);//更新采购单
		}else{
			List<Integer> commIdList = new ArrayList<Integer>();//商品的list
			for (PurchaseDetailEntity purchaseDetail : purchaseDetails) {
				Integer commId = purchaseDetail.getCommId();
				if(commId!=null){
					if(commIdList.contains(commId)){
						getLogger().info("添加采购单时商品存在重复->"+"commId:"+commId);
						return false;
					}
					commIdList.add(commId);
				}
			}
			purchaseDao.updateByPrimaryKeySelective(purchase);//更新采购单
			if(purchase.getPurId() == null){
				throw new RuntimeException("update purchase fail");
			}
			purchaseDetailDao.deleteByByPurId(purchase.getPurId());//根据采购单的id删除采购的详情
			for (PurchaseDetailEntity purchaseDetail : purchaseDetails) {
				purchaseDetail.setPurId(purchase.getPurId());
				purchaseDetailDao.insertSelective(purchaseDetail);//添加订购单的详细
			}
		}
		return true;
	}

}
