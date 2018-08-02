package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.dao.DiscountGroupDao;
import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.dao.ShopDiscountGroupDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.ShopDiscountGroupPageEntity;
import com.grape.supermarket.entity.param.ShopDiscountGroupParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.ShopDiscountGroupService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LQW on 2017/11/14.
 */
@Service
public class ShopDiscountGroupServiceImpl implements ShopDiscountGroupService {
    @Autowired
    private ShopDiscountGroupDao shopDiscountGroupDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private DiscountGroupDao discountGroupDao;

    @Override
    public int insertSelective(ShopDiscountGroupEntity param) {
        ShopDiscountGroupEntity sdge = shopDiscountGroupDao.selectByGroupIdAndShopIdAndTime(param);
        if(sdge != null){
            return -1;
        }
        OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
        param.setOperId(oper.getOperId());
        int t = shopDiscountGroupDao.insertSelective(param);
        return t;
    }

    @Override
    public List<ShopDiscountGroupPageEntity> selectParam(ShopDiscountGroupParam param, PageParam page) {
        if (page != null) {
            SelectEntity select = param.getSelect();
            if (select == null) {
                select = new SelectEntity();
                param.setSelect(select);
            }
            select.setLimit(page.getLimit());
        }
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        param.setIdRange(operatorSession.getShopIds());
        List<ShopDiscountGroupEntity> list = shopDiscountGroupDao.selectParam(param);
        List<ShopDiscountGroupPageEntity> sdgList = new ArrayList<>();
        try {
            for (ShopDiscountGroupEntity sdg : list) {
                ShopDiscountGroupPageEntity sdgp = new ShopDiscountGroupPageEntity();
                BeanUtils.copyProperties(sdgp,sdg);
                ShopEntity shopEntity = shopDao.selectByPrimaryKey(sdg.getShopId());
                sdgp.setShopEntity(shopEntity);
                DiscountGroupEntity discountGroupEntity = discountGroupDao.selectByPrimaryKey(sdg.getGroupId());
                sdgp.setDiscountGroupEntity(discountGroupEntity);
                sdgList.add(sdgp);
            }
        } catch (Exception e) {
            throw new BeanCopyException(e);
        }

        return sdgList;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        if(id ==null){
            return -1;
        }
        return shopDiscountGroupDao.deleteByPrimaryKey(id);
    }

    @Override
    public int countShopDisGroup(ShopDiscountGroupParam param) {
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        param.setIdRange(operatorSession.getShopIds());
        return shopDiscountGroupDao.countShopDisGroup(param);
    }

    @Override
    public ShopDiscountGroupPageEntity selectByPrimaryKey(Integer id) {
        if(id == null){
            return null;
        }
        ShopDiscountGroupEntity sdge = shopDiscountGroupDao.selectByPrimaryKey(id);
        ShopDiscountGroupPageEntity sdgp = new ShopDiscountGroupPageEntity();
        try {
            BeanUtils.copyProperties(sdgp,sdge);
            ShopEntity shopEntity = shopDao.selectByPrimaryKey(sdge.getShopId());
            sdgp.setShopEntity(shopEntity);
            DiscountGroupEntity discountGroupEntity = discountGroupDao.selectByPrimaryKey(sdge.getGroupId());
            sdgp.setDiscountGroupEntity(discountGroupEntity);
        } catch (IllegalAccessException e) {
            throw new BeanCopyException(e);
        } catch (InvocationTargetException e) {
            throw new BeanCopyException(e);
        }
        return sdgp;
    }

    @Override
    public int updateByPrimaryKeySelective(ShopDiscountGroupEntity param) {
        if(param == null || param.getId() == null){
            return -1;
        }
        ShopDiscountGroupEntity sdge = shopDiscountGroupDao.selectByGroupIdAndShopIdAndTime(param);
        if(sdge != null){
            return -1;
        }
        OperatorEntity oper = OperatorSessionHelper.getSessionOrThrow().getOperatorInfo();
        param.setOperId(oper.getOperId());
        param.setCreateTime(new Date());
        int t = shopDiscountGroupDao.updateByPrimaryKeySelective(param);
        return t;
    }
}
