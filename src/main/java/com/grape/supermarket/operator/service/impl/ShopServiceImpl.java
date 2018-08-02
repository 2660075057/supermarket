package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.Pager;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.dao.*;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.OperatorShopKey;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.ShopPageEntity;
import com.grape.supermarket.entity.param.*;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.ShopService;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Point;
import com.spatial4j.core.shape.Rectangle;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LXP on 2017/9/29.
 */
@Service
public class ShopServiceImpl extends BasicService implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private OperatorShopDao operatorShopDao;
    @Autowired
    @Qualifier("wechatProperties")
    private PropertiesLoader wechatProperties;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ShopCommodityDao shopCommodityDao;
    @Autowired
    private DepotDao depotDao;
    @Autowired
    private ShopDiscountGroupDao shopDiscountGroupDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private ShopLogDao shopLogDao;

    @Override
    public List<ShopEntity> shopList(ShopParam entity, PageParam page) {
        if (page != null) {
            SelectEntity select = entity.getSelect();
            if (select == null) {
                select = new SelectEntity();
                entity.setSelect(select);
            }
            select.setLimit(page.getLimit());
        }
        return shopDao.selectByParam(entity);
    }

    @Override
    public ShopEntity selectShopById(Integer shopId) {
        if(shopId == null){
            throw new IllegalArgumentException("shopId is null");
        }
        return shopDao.selectByPrimaryKey(shopId);
    }

    @Override
    public int countShop(ShopParam entity) {
        return shopDao.countByParam(entity);
    }

    @Override
    public boolean addShop(ShopEntity entity) {
        entity.setShopId(null);
        int i = shopDao.insertSelective(entity);
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        if(operatorSession.getOperatorInfo().getOperId() != 0){
            OperatorShopKey record = new OperatorShopKey();
            record.setOperId(operatorSession.getOperatorInfo().getOperId());
            record.setShopId(entity.getShopId());
            operatorShopDao.insert(record);
        }
        return i == 1;
    }

    @Override
    public boolean updateShop(ShopEntity entity) {
        if (entity.getShopId() == null) {
            return false;
        }
        return shopDao.updateByPrimaryKeySelective(entity) > 0;
    }

    @Override
    public boolean enable(Integer shopId) {
        ShopEntity entity = new ShopEntity();
        entity.setShopId(shopId);
        entity.setState((byte) 0);
        return updateShop(entity);
    }

    @Override
    public boolean disable(Integer shopId) {
        ShopEntity entity = new ShopEntity();
        entity.setShopId(shopId);
        entity.setState((byte) 1);
        return updateShop(entity);
    }

    @Override
    public String getInAccessBarcode(Integer shopId) {
        String domain = wechatProperties.getProperty("domain",null);
        if(domain == null){
            return null;
        }
        return domain+"/wechat/index/authJump/inShopVITIS"+shopId;
    }

    @Override
    public String getOutAccessBarcode(Integer shopId) {
        String domain = wechatProperties.getProperty("domain",null);
        if(domain == null){
            return null;
        }
        return domain+"/wechat/index/authJump/outShopVITIS"+shopId;
    }

    @Override
    public ShopEntity selectByPrimaryKey(Integer shopId) {
        return shopDao.selectByPrimaryKey(shopId);
    }

    @Override
    public List<ShopPageEntity> shopList(ShopParam param, PageParam page, int radius, Double lon, Double lat) {
        if (lon == null || lat == null) {//使用名称排序
            SelectEntity select = new SelectEntity();
            select.setOrderBy("shop_name asc");
            param.setSelect(select);
            List<ShopEntity> shops = shopList(param, page);
            List<ShopPageEntity> datas = new ArrayList<>(shops.size());
            for (ShopEntity shop : shops) {
                ShopPageEntity spe = new ShopPageEntity(shop);
                datas.add(spe);
            }
            return datas;
        } else {
            SpatialContext geo = SpatialContext.GEO;
            Point userPoint = geo.makePoint(lon, lat);
            Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(
                    userPoint, radius * DistanceUtils.KM_TO_DEG, geo, null);
            ShopParam sp = new ShopParam();
            try {
                BeanUtils.copyProperties(sp,param);
            } catch (IllegalAccessException|InvocationTargetException e) {
                throw new BeanCopyException(e);
            }
            sp.setLonMin(rectangle.getMinX());
            sp.setLonMax(rectangle.getMaxX());
            sp.setLatMin(rectangle.getMinY());
            sp.setLatMax(rectangle.getMaxY());
            List<ShopEntity> shops = shopDao.selectByParam(sp);
            List<ShopPageEntity> datas = new ArrayList<>(shops.size());
            for (ShopEntity shop : shops) {
                ShopPageEntity spe = new ShopPageEntity(shop);
                double distance = geo.getDistCalc().distance(userPoint, shop.getLongitude(), shop.getLatitude());
                distance *= 111319.49079327358D;
                spe.setDistance(distance);
                datas.add(spe);
            }
            Collections.sort(datas, new Comparator<ShopPageEntity>() {
                @Override
                public int compare(ShopPageEntity o1, ShopPageEntity o2) {
                    return o1.getDistance().compareTo(o2.getDistance());
                }
            });
            //分页
            if (page != null && page.getPageCurrent() != null && page.getPageSize() != null) {
                Pager<ShopPageEntity> pager = Pager.create(datas, page.getPageSize());
                return pager.getPagedList(page.getPageCurrent());
            } else {
                return datas;
            }
        }
    }

	@Override
	public List<ShopEntity> shopListByOperId(Integer operId) {
		if(operId==null){
			return null;
		}
		List<OperatorShopKey> operatorShopList = operatorShopDao.operatorShopKeyList(operId);
		List<ShopEntity> shops = new ArrayList<>();
		if(operatorShopList!=null){
			for(OperatorShopKey key : operatorShopList){
				ShopEntity shopEntity = selectByPrimaryKey(key.getShopId());
				shops.add(shopEntity);
			}
		}
		return shops;
	}

    @Override
    public List<ShopEntity> selectShopByCommId(Integer commId) {
        if(commId == null){
            return null;
        }
        return shopDao.selectShopByCommId(commId);
    }

    @Override
    public boolean canDelete(Integer shopId) {
        OrderParam orderParam = new OrderParam();
        orderParam.setShopId(shopId);
        int order = orderDao.countByParam(orderParam);
        if (order != 0) {
            return false;
        }
        InventoryParam inventoryParam = new InventoryParam();
        inventoryParam.setShopId(shopId);
        int inventory = inventoryDao.countByParam(inventoryParam);
        if (inventory != 0) {
            return false;
        }
        ShopCommodityParam shopCommodityParam = new ShopCommodityParam();
        shopCommodityParam.setShopId(shopId);
        shopCommodityParam.setCommodityDeleteMark(false);
        int shopCommodity = shopCommodityDao.countShopCommList(shopCommodityParam);
        if (shopCommodity != 0) {
            return false;
        }
        DepotParam depotParam = new DepotParam();
        depotParam.setShopId(shopId);
        int depot = depotDao.countDepotByCondition(depotParam);
        if (depot != 0) {
            return false;
        }
        ShopDiscountGroupParam shopDiscountParam = new ShopDiscountGroupParam();
        shopDiscountParam.setShopId(shopId);
        int shopDisGroup = shopDiscountGroupDao.countShopDisGroup(shopDiscountParam);
        if (shopDisGroup != 0) {
            return false;
        }
        PurchaseParam purchaseParam = new PurchaseParam();
        purchaseParam.setShopId(shopId);
        int purchase = purchaseDao.countByParam(purchaseParam);
        if (purchase != 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteShop(Integer shopId) {
        boolean b = canDelete(shopId);
        if (!b) {
            return false;
        }
        //删除操作员关联数据
        List<OperatorShopKey> operatorIdList = operatorShopDao.getOperatorIdList(shopId);
        for (OperatorShopKey operatorShopKey : operatorIdList) {
            operatorShopKey.setShopId(shopId);
            operatorShopDao.deleteByPrimaryKey(operatorShopKey);
        }
        //删除进店记录
        shopLogDao.deleteByShopId(shopId);
        //删除站点
        shopDao.deleteByPrimaryKey(shopId);
        return true;
    }
}
