package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.ShopCommodityDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.OperatorLogEntity;
import com.grape.supermarket.entity.db.ShopCommodityEntity;
import com.grape.supermarket.entity.db.ShopCommodityKey;
import com.grape.supermarket.entity.page.ShopCommodityPageEntity;
import com.grape.supermarket.entity.param.ShopCommodityParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.OperatorLogService;
import com.grape.supermarket.operator.service.ShopCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by LQW on 2017/10/13.
 */
@Service
public class ShopCommodityServiceImpl extends BasicService implements ShopCommodityService {
    @Resource
    private ShopCommodityDao shopCommodityDao;
    @Autowired
    private OperatorLogService operatorLogService;

    @Override
    public int deleteByPrimaryKey(ShopCommodityKey key) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        if (key == null || key.getShopId() == null || key.getCommId() == null) {
            return 0;
        }
        int i = shopCommodityDao.deleteByPrimaryKey(key);
        //增加日志
        OperatorLogEntity operatorLog
                = operatorLogService.createOperatorLog(session.getOperatorInfo().getOperId(),
                OperatorSessionHelper.getClientIp(), OperatorLogService.COMMODITY_PRICE_DELETE);
        Map<String, Object> data = new TreeMap<>();
        data.put("shopCommodityData", key);
        operatorLog.setData(JsonUtils.toJsonNonNull(data));
        operatorLogService.addLog(operatorLog);
        return i;
    }

    @Override
    public int insertSelective(ShopCommodityEntity record) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        if(record == null){
            return 0;
        }
        ShopCommodityKey key = new ShopCommodityKey();
        key.setCommId(record.getCommId());
        key.setShopId(record.getShopId());
        ShopCommodityEntity sc = shopCommodityDao.selectByPrimaryKey(key);
        int t=0;
        if(sc != null){
            t=-2;
            String message= "shopCommodityDao.insertSelective insert fail primary key existence,data->"+ JsonUtils.toJson(sc);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }else{
            t = shopCommodityDao.insertSelective(record);
            //增加日志
            OperatorLogEntity operatorLog
                    = operatorLogService.createOperatorLog(session.getOperatorInfo().getOperId(),
                    OperatorSessionHelper.getClientIp(), OperatorLogService.COMMODITY_PRICE_ADD);
            Map<String,Object> data = new TreeMap<>();
            data.put("shopCommodityData",record);
            operatorLog.setData(JsonUtils.toJsonNonNull(data));
            operatorLogService.addLog(operatorLog);
        }
        return t;
    }

    @Override
    public ShopCommodityPageEntity selectByPrimaryKey(ShopCommodityKey key) {
        if(key == null){
            return new ShopCommodityPageEntity();
        }
        return shopCommodityDao.selectByPrimaryKey(key);
    }

    @Override
    public int updatePrice(ShopCommodityEntity record) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        if(record == null){
            return 0;
        }
        ShopCommodityKey key = new ShopCommodityKey();
        key.setCommId(record.getCommId());
        key.setShopId(record.getShopId());
        ShopCommodityEntity sc = shopCommodityDao.selectByPrimaryKey(key);
        int t=0;
        if(sc != null){
            t = shopCommodityDao.updateByPrimaryKeySelective(record);
            //增加日志
            OperatorLogEntity operatorLog
                    = operatorLogService.createOperatorLog(session.getOperatorInfo().getOperId(),
                    OperatorSessionHelper.getClientIp(), OperatorLogService.COMMODITY_PRICE_UPDATE);
            Map<String,Object> data = new TreeMap<>();
            data.put("shopCommodityData",record);
            operatorLog.setData(JsonUtils.toJsonNonNull(data));
            operatorLogService.addLog(operatorLog);
        }else{
            String message= "shopCommodityDao.insertSelective insert fail primary key not found,data->"+ JsonUtils.toJson(sc);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }
        return t;
    }

    @Override
    public List<ShopCommodityPageEntity> selectShopCommListParam(ShopCommodityParam shopCommodityParam, PageParam page) {
        if(shopCommodityParam == null){
            shopCommodityParam = new ShopCommodityParam();
        }
        if(page != null){
            SelectEntity select = shopCommodityParam.getSelect();
            if(select == null){
                select = new SelectEntity();
            }
            select.setLimit(page.getLimit());
            shopCommodityParam.setSelect(select);
        }
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        shopCommodityParam.setIdRange(operatorSession.getShopIds());
        List<ShopCommodityPageEntity> list = shopCommodityDao.selectShopCommListParam(shopCommodityParam);
        return list;
    }

    @Override
    public int countShopCommList(ShopCommodityParam shopCommodityParam) {
        if(shopCommodityParam == null){
            shopCommodityParam = new ShopCommodityParam();
        }
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        shopCommodityParam.setIdRange(operatorSession.getShopIds());
        return shopCommodityDao.countShopCommList(shopCommodityParam);
    }
}
