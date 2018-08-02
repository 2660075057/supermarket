package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.DepotDao;
import com.grape.supermarket.dao.PictureDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.DepotEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PictureEntity;
import com.grape.supermarket.entity.page.DepotPageEntity;
import com.grape.supermarket.entity.param.DepotParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.CountPriceService;
import com.grape.supermarket.operator.service.DepotService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LQW on 2017/10/10.
 */
@Service
public class DepotServiceImpl extends BasicService implements DepotService {
    @Resource
    private DepotDao depotDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private CountPriceService countPriceService;
    @Autowired
    private PictureDao pictureDao;
    
    private Logger log = Logger.getLogger(DepotServiceImpl.class);

    @Override
    public int deleteById(Integer depotId) {
        if(depotId == null){
            return -1;
        }
        return depotDao.deleteByPrimaryKey(depotId);
    }

    @Override
    public int insertDepot(DepotEntity depotEntity) {
        if(depotEntity == null){
           return -1;
        }
        int t =0;
        DepotEntity de = depotDao.selectByPrimaryKey(depotEntity.getDepotId());
        if(de !=null){
            t = -2;
            String message= "depotDao.insertSelective insert fail primary key existence,data->"+ JsonUtils.toJson(de);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }else{
            depotEntity.setCreateTime(new Date());
            OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
            Integer operId = session.getOperatorInfo().getOperId();
            depotEntity.setOperId(operId);
            t = depotDao.insertSelective(depotEntity);
        }
        return t;
    }

    @Override
    public DepotEntity selectById(Integer depotId) {
        if(depotId == null){
            return null;
        }
        return depotDao.selectByPrimaryKey(depotId);
    }

    @Override
    public List<DepotEntity> selectByCondition(DepotParam depotParam,PageParam page) {
        if(depotParam == null){
            depotParam = new DepotParam();
        }
        if(page != null){
            SelectEntity select = depotParam.getSelect();
            if(select == null){
                select = new SelectEntity();
            }
            select.setLimit(page.getLimit());
            depotParam.setSelect(select);
        }
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        depotParam.setIdRange(operatorSession.getShopIds());
        List<DepotEntity> list = depotDao.selectByCondition(depotParam);
        return list;
    }
    
    @Override
    public int costPrice(DepotParam depotParam){
    	List<DepotEntity> list = selectByCondition(depotParam, null);
    	int total = 0;
    	for(DepotEntity de : list){
    		int count = de.getCostPrice() * de.getAmount();
    		total += count;
    	}
    	
    	return total;
    }

    @Override
    public int addAmount(DepotEntity depotEntity) {
        int tem = 0;
        if(depotEntity.getDepotId() != null){
            DepotEntity de = depotDao.selectByPrimaryKey(depotEntity.getDepotId());
            Integer version = de.getVersion();
            Integer amount = de.getAmount();
            DepotEntity entity = new DepotEntity();
            entity.setDepotId(depotEntity.getDepotId());
            entity.setVersion(version);
            entity.setAmount(amount+depotEntity.getAmount());
            entity.setCreateTime(new Date());
            OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
            entity.setOperId(oper.getOperId());
            tem = depotDao.updateByPrimaryKeySelective(entity);
        }
        return tem;
    }

    @Override
    public int turnShopId(DepotEntity depotEntity) {
        int tem = 0;
        DepotEntity depot = new DepotEntity();
        if(depotEntity.getShopId() != null && depotEntity.getCommId() != null){
            depot = depotDao.selectByShopIdAanCommId(depotEntity);
        }
        if(depotEntity.getDepotId() != null){
            DepotEntity de = depotDao.selectByPrimaryKey(depotEntity.getDepotId());
            Integer version = de.getVersion();
            Integer amount = de.getAmount();

            DepotEntity entity = new DepotEntity();
            entity.setDepotId(depotEntity.getDepotId());
            entity.setVersion(version);
            entity.setAmount(amount-depotEntity.getAmount());
            entity.setCreateTime(new Date());
            OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
            entity.setOperId(oper.getOperId());
            tem = depotDao.updateByPrimaryKeySelective(entity);
            if(tem >0){
                int tp = 0;
                DepotEntity depot2 = new DepotEntity();
                depot2.setOperId(oper.getOperId());
                depot2.setCreateTime(new Date());
                if(depot != null){
                    depot2.setDepotId(depot.getDepotId());
                    depot2.setVersion(depot.getVersion());
                    depot2.setAmount(depot.getAmount()+depotEntity.getAmount());
                    tp = depotDao.updateByPrimaryKeySelective(depot2);
                }else{
                    depot2.setShopId(depotEntity.getShopId());
                    depot2.setCommId(depotEntity.getCommId());
                    depot2.setAmount(depotEntity.getAmount());
                    depot2.setState((byte) 0);
                    depot2.setThreshold(-1);
                    tp = depotDao.insertSelective(depot2);
                }
                if(tp > 0){
                    tem = 0;
                   depotDao.updateByPrimaryKeySelective(de);
                }
            }
        }
        return tem;
    }

    @Override
    public int setThreshold(DepotEntity depotEntity) {
        int tem = 0;
        if(depotEntity.getDepotId() != null){
            DepotEntity de = depotDao.selectByPrimaryKey(depotEntity.getDepotId());
            Integer version = de.getVersion();
            DepotEntity entity = new DepotEntity();
            entity.setDepotId(depotEntity.getDepotId());
            entity.setVersion(version);
            entity.setThreshold(depotEntity.getThreshold());
            entity.setCreateTime(new Date());
            OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
            entity.setOperId(oper.getOperId());
            tem = depotDao.updateByPrimaryKeySelective(entity);
        }
        return tem;
    }

    @Override
    public int openDepot(Integer depotId, Byte state) {
        int tem = 0;
        if(depotId != null && state != null){
            DepotEntity de = depotDao.selectByPrimaryKey(depotId);
            DepotEntity entity = new DepotEntity();
            entity.setDepotId(depotId);
            entity.setState(state);
            entity.setCreateTime(new Date());
            entity.setVersion(de.getVersion());
            OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
            entity.setOperId(oper.getOperId());
            tem = depotDao.updateByPrimaryKeySelective(entity);
        }
        return tem;
    }

    @Override
    public int closeDepot(Integer depotId, Byte state) {
        int tem = 0;
        if(depotId != null && state != null){
            DepotEntity de = depotDao.selectByPrimaryKey(depotId);
            DepotEntity entity = new DepotEntity();
            entity.setDepotId(depotId);
            entity.setState(state);
            entity.setCreateTime(new Date());
            entity.setVersion(de.getVersion());
            OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
            entity.setOperId(oper.getOperId());
            tem = depotDao.updateByPrimaryKeySelective(entity);
        }
        return tem;
    }

    @Override
    public DepotEntity selectByShopIdAanCommId(DepotEntity depotEntity) {
        DepotEntity de = new DepotEntity();
        if(depotEntity.getShopId() != null && depotEntity.getCommId() != null){
           de = depotDao.selectByShopIdAanCommId(depotEntity);
        }
        return de;
    }

    @Override
    public int countDepotByCondition(DepotParam depotParam) {
        if(depotParam == null){
            depotParam = new DepotParam();
        }
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        depotParam.setIdRange(operatorSession.getShopIds());
        return depotDao.countDepotByCondition(depotParam);
    }

    @Override
    public List<DepotPageEntity> selectDepotByWeChat(DepotParam param, PageParam page) {
        if(page != null){
            SelectEntity select = param.getSelect();
            if(select == null){
                select = new SelectEntity();
            }
            select.setLimit(page.getLimit());
            param.setSelect(select);
        }
        List<DepotEntity> list = depotDao.selectDepotByWeChat(param);
        List<DepotPageEntity> dpel = new ArrayList<>();
        for(DepotEntity de:list){
            DepotPageEntity dpe = new DepotPageEntity();
            try {
                BeanUtils.copyProperties(dpe,de);
                CommodityEntity ce = countPriceService.getCommodityAndRealPrice(de.getCommId(),de.getShopId());
                //图片
                PictureEntity picParam = new PictureEntity();
                picParam.setRecno(ce.getCommId());
                picParam.setType((byte) 1);
                List<PictureEntity> picture = pictureDao.selectByParam(picParam);

                dpe.setPicture(picture);
                dpe.setCommodityEntity(ce);
            } catch (IllegalAccessException|InvocationTargetException e) {
                throw new BeanCopyException(e);
            }
           dpel.add(dpe);
        }
        return dpel;
    }
    
    @Override
    public HashMap<Integer,Object> selectDepotCheck(String str, Integer shopid){
    	List<DepotEntity> list = new ArrayList<>();
    	List<Object> errorList = new ArrayList<>();
    	HashMap<Integer,Object> map = new HashMap<Integer,Object>();
        JSONArray arr=new JSONArray(str);
        for(int i = 0; i<arr.length(); i++){
        	DepotEntity de = new DepotEntity();
        	DepotEntity de2 = new DepotEntity();
        	de.setShopId(shopid);
        	String arrA = arr.getString(i);
        	String[] arrB = arrA.split(":");
        	if(arrB[0].equals("")){  // 一行没传数据或者为空
        		errorList.add("没有数据");
        		continue;
        	}else if(arrB.length == 1 ){  //一行只传一个字段
        		errorList.add(arrB[0] + "  数据格式有误");
        		continue;
        	}else if(arrB.length == 2 ){  //一行传两个字段
        		de.setBarcode(arrB[0]);
        		de.setThreshold(-1);
        	}else if(arrB.length >= 3){  //一行传三个或以上字段
        		de.setBarcode(arrB[0]);
        		if(isNumeric(arrB[2])){
        			de.setThreshold((int)Double.parseDouble(arrB[2]));
        		}else{
        			errorList.add("条形码为" + arrB[0] +",输入数字格式有误   " +arrB[2] );
        			continue;
        		}
        		
        	}
        	//判断字符串是不是数字
        	if(isNumeric(arrB[1])){
        		de.setAmount((int)Double.parseDouble(arrB[1]));
        	}else{
        		errorList.add("条形码为" + arrB[0] +",输入数量格式有误    " +arrB[1] );
        		continue;
        	}
        	//判断输入的阀值是否大于添加数量
        	if(de.getThreshold() > de.getAmount()){
        		de.setThreshold(de.getAmount());
        	}
    		if(de.getAmount() < 1){
    			de2.setAmount(de.getAmount());
    			errorList.add("条形码为" + arrB[0]+",数量" + arrB[1] + "  小于1");
    			continue;
    		}
    		  //搜索商品类型
    		CommodityEntity ce = new CommodityEntity();
            ce.setBarcode(arrB[0]);
            CommodityEntity dityEntity = commodityDao.selectByBarcode(ce);
            if(dityEntity != null){
            	de.setCommId(dityEntity.getCommId());
            	de.setCommName(dityEntity.getCommName());
            }else{
            	errorList.add(arrB[0] + "  该条形码数据不存在");
            	continue;
            }
            
           if(de.getShopId() != null && de.getCommId() != null){
            	DepotEntity Depot = new DepotEntity();
            	Depot = depotDao.selectByShopIdAanCommId(de);
                if(Depot != null){
                	errorList.add(arrB[0] + " 条形码，商店已存在该商品库存(做库存添加) ");
                }
             }
            list.add(de);
        }
	        map.put(0, list);
	        map.put(1, errorList);
		return map;
    	
    }
    
    @Override
    public void batchInsertDepot(List<DepotEntity> Delist){
    	
    	for(DepotEntity de : Delist){
    		if(de.getShopId() != null && de.getCommId() != null){
            	DepotEntity Depot = new DepotEntity();
            	Depot = depotDao.selectByShopIdAanCommId(de);
                if(Depot != null){  //商店是否已存在该商品库存
                	Depot.setAmount(de.getAmount());
                	Depot.setThreshold(de.getThreshold());
                	int tem = 0;
                	int count = 0;
	                while(tem == 0){
	                	tem = addAmount(Depot);
	                	count ++;
	                	if(count >= 10){  //添加库存，循环10次
	                		log.error("商品库存添加失败！");
	                		break;
	                	}
	                }
                }else{
                	de.setState((byte) 0);
                	insertDepot(de);
                }
             }
    		
    	}
    	
    }
    
    /**
     * 采用正则表达式的方式来判断一个字符串是否为数字
	 * 可以判断正负、整数小数 
     * @param str
     * @return
     */
    private boolean isNumeric(String str){
	   	 String reg = "^-?[0-9]+(.[0-9]+)?$";  
	     return str.matches(reg); 
    }
}
