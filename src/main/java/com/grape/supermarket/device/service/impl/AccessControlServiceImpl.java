package com.grape.supermarket.device.service.impl;

import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.device.service.AccessControlService;
import com.grape.supermarket.entity.AccessEntity;
import com.grape.supermarket.entity.AccessResult;
import com.grape.supermarket.entity.db.ShopEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LXP on 2017/10/13.
 */
@Service
public final class AccessControlServiceImpl implements AccessControlService {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ShopDao shopDao;

    private final ConcurrentMap<Integer, AccessInfo> inshopContainer = new ConcurrentHashMap<>(64);
    private final ConcurrentMap<Integer, AccessInfo> outshopContainer = new ConcurrentHashMap<>(64);

    @Override
    public Access_Code addInAccess(AccessEntity accessEntity) {
        assert accessEntity != null : "accessEntity is null";
        ShopEntity shopEntity = shopDao.selectByPrimaryKey(accessEntity.getShopId());
        if (shopEntity == null || shopEntity.getState() == 1) {
            return Access_Code.Disable;
        }
        AccessInfo ae = new AccessInfo();
        ae.accessTime = System.currentTimeMillis() + accessEntity.getTimeout();
        ae.accessEntity = accessEntity;
        return inshopContainer.putIfAbsent(accessEntity.getShopId(), ae) == null ? Access_Code.Ready : Access_Code.Running;
    }

    @Override
    public AccessResult checkInAccess(Integer shopId) {
        assert shopId != null : "shopId is null";
        AccessInfo ae = inshopContainer.get(shopId);
        AccessResult ar = new AccessResult();
        boolean b = ae != null && ae.accessTime > System.currentTimeMillis() && ae.lock.compareAndSet(0, 1);
        if (b) {
            //检查数据库门禁被禁用
            ShopEntity shopEntity = shopDao.selectByPrimaryKey(ae.accessEntity.getShopId());
            if (shopEntity == null || shopEntity.getState() == 1) {
                ae.accessEntity.setState(2);//设为开门被禁止标记
                b = false;
            }
        }

        ar.setAccess(b);
        if (b) {
            ae.accessEntity.setState(1);//设为开门成功标记
            try {
                BeanUtils.copyProperties(ar, ae.accessEntity);
            } catch (Exception e) {
                logger.warn("复制AccessInfo.accessEntity到AccessResult失败", e);
            }
        }
        return ar;
    }

    @Override
    public void inAccessInit(Integer shopId) {
        assert shopId != null : "shopId is null";
        inshopContainer.remove(shopId);
    }

    @Override
    public Access_Code addOutAccess(AccessEntity accessEntity) {
        assert accessEntity != null : "accessEntity is null";
        AccessInfo ae = new AccessInfo();
        ae.accessTime = System.currentTimeMillis() + accessEntity.getTimeout();
        ae.accessEntity = accessEntity;
        return outshopContainer.putIfAbsent(accessEntity.getShopId(), ae) == null ? Access_Code.Ready : Access_Code.Running;
    }

    @Override
    public AccessResult checkOutAccess(Integer shopId) {
        assert shopId != null : "shopId is null";

        AccessInfo ae = outshopContainer.get(shopId);
        boolean b = ae != null && ae.accessTime > System.currentTimeMillis() && ae.lock.compareAndSet(0, 1);
        AccessResult ar = new AccessResult();
        ar.setAccess(b);
        if (b) {
            ae.accessEntity.setState(1);//设为开门成功标记
            try {
                BeanUtils.copyProperties(ar, ae.accessEntity);
            } catch (Exception e) {
                logger.warn("复制AccessInfo.accessEntity到AccessResult失败", e);
            }
        }
        return ar;
    }

    @Override
    public void outAccessInit(Integer shopId) {
        assert shopId != null : "shopId is null";
        outshopContainer.remove(shopId);
    }

    private static class AccessInfo {
        long accessTime;
        AtomicInteger lock = new AtomicInteger(0);
        AccessEntity accessEntity;
    }
}
