package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.dao.CommodityAttrDao;
import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.operator.service.CommodityAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LQW on 2017/11/8.
 */
@Service
public class CommodityAttrServiceImpl implements CommodityAttrService {
    @Autowired
    private CommodityAttrDao commodityAttrDao;

    @Override
    public List<CommodityAttrEntity> selectByCommId(Integer commId) {
        if(commId == null){
            return null;
        }
        return commodityAttrDao.selectByCommId(commId);
    }

    @Override
    public List<CommodityAttrEntity> selectCommAttrByCommId(Integer commId) {
        if(commId == null){
            return null;
        }
        return commodityAttrDao.selectCommAttrByCommId(commId);
    }
}
