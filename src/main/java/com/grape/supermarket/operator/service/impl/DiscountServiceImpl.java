package com.grape.supermarket.operator.service.impl;


import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.CommodityTypeDao;
import com.grape.supermarket.dao.DiscountDao;
import com.grape.supermarket.dao.DiscountGroupDao;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.dao.ShopDiscountGroupDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.DiscountEntity;
import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;
import com.grape.supermarket.entity.discount.DirectCut;
import com.grape.supermarket.entity.discount.FullCut;
import com.grape.supermarket.entity.discount.NumCut;
import com.grape.supermarket.entity.page.DiscountGroupPageEntity;
import com.grape.supermarket.entity.page.DiscountPageEntity;
import com.grape.supermarket.entity.param.DiscountGroupParam;
import com.grape.supermarket.entity.param.DiscountParam;
import com.grape.supermarket.operator.service.DiscountGroupService;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * author huanghuang
 * 2017年10月18日 下午4:39:15
 */
@Service
public class DiscountServiceImpl extends BasicService implements DiscountGroupService {
    @Autowired
    private DiscountGroupDao discountGroupDao;

    @Autowired
    private DiscountDao discountDao;
    
    @Autowired
    private OperatorDao operatorDao;
    
    @Autowired
    private CommodityTypeDao commodityTypeDao;
    
    @Autowired
    private CommodityDao commodityDao;
    
    @Autowired
    private ShopDiscountGroupDao shopDiscountGroupDao;

    @Override
    public boolean addDiscountGroup(DiscountGroupEntity discountGroup, List<DiscountEntity> discounts) {
    	if(discounts == null){
    		throw new RuntimeException("the discounts is null");
    	}
    	if(discounts.isEmpty()){
    		throw new RuntimeException("the discounts is empty");
    	}
    	try{
    		List<Integer> typeIdList = new ArrayList<Integer>();//商品类型的list
    		List<Integer> commIdList = new ArrayList<Integer>();//商品的list
    		List<Integer> maxPriceList = new ArrayList<Integer>();//消费金额的list
    		for (int d=0,len=discounts.size(); d<len; d++) {//验证折扣类型的可靠性
    			DiscountEntity discountEntity = discounts.get(d);
    			Integer  discountType = discountEntity.getDiscountType();
    			JSONObject data = JSONObject.fromObject(discountEntity.getData());
    			if(DirectCut.typeId == discountType){//直接折扣
    				Integer typeId = discountEntity.getTypeId();
    				Integer commId = discountEntity.getCommId();
    				if(typeId==null&&commId==null){
    					getLogger().info("添加直接折扣时typeId和commId不能同时为空->"+"typeId:"+typeId+"\t commId:"+commId);
    	                return false;
    				}
    				if(typeId!=null){
    					if(typeIdList.contains(typeId)){
    						getLogger().info("添加直接折扣时商品类型存在重复->"+"typeId:"+typeId);
        	                return false;
    					}
    					typeIdList.add(typeId);
    				}
    				if(commId!=null){
    					if(commIdList.contains(commId)){
    						getLogger().info("添加直接折扣时商品存在重复->"+"commId:"+commId);
        	                return false;
    					}
    					commIdList.add(commId);
    				}
    				String percent = data.get("percent")==null?String.valueOf(0):data.getString("percent");
    				if(!strIsNum(percent)){
    					getLogger().info("添加直接折扣时折扣必须为正整数->"+"percent:"+percent);
    	                return false;
    				}
    				if(Integer.parseInt(percent)>99){
    					getLogger().info("添加直接折扣时折扣必须为1-99的正整数->"+"percent:"+percent);
    	                return false;
    				}
    				
    			}else if(FullCut.typeId == discountType){//满减折扣
    				FullCut  fullCut = new FullCut();
    				String maxPrice = data.get("maxPrice")==null?String.valueOf(0):data.getString("maxPrice");
    				String reducePrice = data.get("reducePrice")==null?String.valueOf(0):data.getString("reducePrice");
    				Integer maxPriceInt = new BigDecimal(maxPrice).multiply(new BigDecimal(100)).intValue();
    				Integer reducePriceInt = new BigDecimal(reducePrice).multiply(new BigDecimal(100)).intValue();
    				if(maxPriceInt<reducePriceInt){
    					getLogger().info("优惠金额大于消费金额->"+"maxPrice:"+maxPriceInt+"\t reducePrice:"+reducePriceInt);
    	                return false;
    				}
    				if(maxPriceList.contains(maxPriceInt)){
						getLogger().info("添加满减折扣时消费金额有重复->"+"maxPrice:"+maxPriceInt);
    	                return false;
					}
    				maxPriceList.add(maxPriceInt);
    				fullCut.setMaxPrice(maxPriceInt);//消费金额*100
    				fullCut.setReducePrice(reducePriceInt);//优惠金额*100
    				discounts.get(d).setData(JsonUtils.toJson(fullCut));
    			}
    		}
    	}catch(NumberFormatException nfe){
    		throw new RuntimeException("maxPrice or reducePrice is not number");
    	}
        discountGroupDao.insertSelective(discountGroup);//添加折扣组
        if(discountGroup.getGroupId() == null){
            throw new RuntimeException("insert discountGroup fail");
        }

        for (DiscountEntity discount : discounts) {
            discount.setGroupId(discountGroup.getGroupId());
            discountDao.insertSelective(discount);//添加折扣
        }

        return true;
    }
    
	private static boolean strIsNum(String target){
		if(StringUtils.isBlank(target)){
			return false;
		}
		for (int i = 0; i < target.length(); i++) {
		    if (!Character.isDigit(target.charAt(i))) {
		        return false;
		    }
		}
		return true;
	}

    @Override
    public boolean addDiscountGroup(DiscountGroupEntity discountGroup, Map<Integer, Object> commDiscount, Map<Integer, Object> typeDiscount, List<Object> allDiscount) {
        List<DiscountEntity> discounts = new ArrayList<>();
        Set<Map.Entry<Integer, Object>> entries = commDiscount.entrySet();
        for (Map.Entry<Integer, Object> commDiscountEntry : entries) {
            Object value = commDiscountEntry.getValue();
            DiscountEntity de = convertObj(value);
            if(de == null){
                getLogger().info("不支持的商品折扣->"+JsonUtils.toJson(value));
                return false;
            }
            de.setCommId(commDiscountEntry.getKey());
            discounts.add(de);
        }

        Set<Map.Entry<Integer, Object>> entries1 = typeDiscount.entrySet();
        for (Map.Entry<Integer, Object> typeDiscountEntry : entries1) {
            Object value = typeDiscountEntry.getValue();
            DiscountEntity de = convertObj(value);
            if(de == null){
                getLogger().info("不支持的类型折扣->"+JsonUtils.toJson(value));
                return false;
            }
            de.setTypeId(typeDiscountEntry.getKey());
            discounts.add(de);
        }

        for (Object o : allDiscount) {
            if(o instanceof FullCut){
                DiscountEntity de = convertObj(o);
                discounts.add(de);
            }else{
                getLogger().info("不支持的类型折扣->"+JsonUtils.toJson(o));
                return false;
            }
        }

        return addDiscountGroup(discountGroup,discounts);
    }

    private DiscountEntity convertObj(Object o){
        DiscountEntity de = null;
        if(o instanceof DirectCut){
            de = new DiscountEntity();
            de.setDiscountType(DirectCut.typeId);
            de.setData(JsonUtils.toJson(o));
        }else if(o instanceof FullCut){
            de = new DiscountEntity();
            de.setDiscountType(FullCut.typeId);
            de.setData(JsonUtils.toJson(o));
        }else if(o instanceof NumCut){
            de = new DiscountEntity();
            de.setDiscountType(NumCut.typeId);
            de.setData(JsonUtils.toJson(o));
        }

        return de;
    }

	@Override
	public List<DiscountGroupPageEntity> discountGroupList(
			DiscountGroupParam param, PageParam page) {
		if (param == null) {
			param = new DiscountGroupParam();
		}
		if (page != null) {
			SelectEntity select = param.getSelect();
			if (select == null) {
				select = new SelectEntity();
			}
			select.setLimit(page.getLimit());
			param.setSelect(select);
		}
		param.setDeleteMark((byte)0);
		List<DiscountGroupEntity> discountGroups = discountGroupDao.selectByParam(param);
		List<DiscountGroupPageEntity> datas = new ArrayList<>(discountGroups.size());
		try {
			for (DiscountGroupEntity discountGroup : discountGroups) {
				DiscountGroupPageEntity ipe = new DiscountGroupPageEntity();
				BeanUtils.copyProperties(ipe, discountGroup);
                OperatorEntity operatorEntity = operatorDao.selectByPrimaryKey(discountGroup.getOperId());
                ipe.setOperator(operatorEntity);
				datas.add(ipe);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanCopyException(e);
		}
		return datas;
	}

	@Override
	public int countDiscountGroup(DiscountGroupParam param) {
		if (param == null) {
			param = new DiscountGroupParam();
		}
		param.setDeleteMark((byte)0);
		return discountGroupDao.countByParam(param);
	}

	@Override
	public DiscountGroupPageEntity selectByPrimaryKey(Integer groupId) {
		if(groupId == null){
			return null;
		}
		DiscountGroupPageEntity discountGroupPage = new DiscountGroupPageEntity();
		DiscountGroupEntity discountGroup = discountGroupDao.selectByPrimaryKey(groupId);
		DiscountParam param = new DiscountParam();
		param.setGroupId(groupId);
		List<DiscountEntity> discounts = discountDao.selectByParam(param);
		if(discounts!=null){
			List<DiscountPageEntity> discountPages = new ArrayList<DiscountPageEntity>();
			try {
				BeanUtils.copyProperties(discountGroupPage, discountGroup);
				for(DiscountEntity discount : discounts){
					Integer typeId = discount.getDiscountType();
					if(FullCut.typeId==typeId||DirectCut.typeId==typeId){
						DiscountPageEntity dpe = new DiscountPageEntity();
						BeanUtils.copyProperties(dpe, discount);
						if(discount.getCommId()!=null){
							CommodityEntity commodity = commodityDao.selectByPrimaryKey(discount.getCommId());
							dpe.setCommodityEntity(commodity);
						}else if(discount.getTypeId()!=null){
							CommodityTypeEntity commodityType = commodityTypeDao.selectByPrimaryKey(discount.getTypeId());
							dpe.setCommodityTypeEntity(commodityType);
						}
						discountPages.add(dpe);
					}
				}
				discountGroupPage.setDiscountPages(discountPages);
			}catch (IllegalAccessException | InvocationTargetException e) {
				throw new BeanCopyException(e);
			}
		}
		
		return discountGroupPage;
	}

	@Override
	public int deleteByPrimaryKey(Integer groupId) {
		if(groupId==null){
			return -1;
		}
		List<ShopDiscountGroupEntity> sdge = shopDiscountGroupDao.selectByGroupId(groupId);
		if(sdge!=null&&!sdge.isEmpty()){
            return 1;
        }
		discountGroupDao.deleteByPrimaryKey(groupId);
		return 0;
	}
}
