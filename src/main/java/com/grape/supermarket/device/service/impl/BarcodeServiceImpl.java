package com.grape.supermarket.device.service.impl;

import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.ElecTagDao;
import com.grape.supermarket.device.service.BarcodeService;
import com.grape.supermarket.entity.CommodityPriceInfo;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.ElecTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/10/19.
 */
@Service
public class BarcodeServiceImpl implements BarcodeService {
    @Autowired
    private ElecTagDao elecTagDao;

    @Autowired
    private CommodityDao commodityDao;

    @Override
    public CommodityPriceInfo epcToCommodity(String data) {
        ElecTagEntity elecTag = elecTagDao.selectByData(data);
        if(elecTag != null){
            CommodityEntity commodity = commodityDao.selectByPrimaryKey(elecTag.getCommId());
            CommodityPriceInfo cpi = new CommodityPriceInfo();
            //填充数据
            cpi.setCommId(commodity.getCommId());//商品id
            cpi.setTypeId(commodity.getTypeId());//商品类型id
            cpi.setName(commodity.getCommName());//商品名称
            cpi.setPrice(commodity.getCommPrice());//商品设置价格
            cpi.setBarcode(commodity.getBarcode());//商品条码
            cpi.setElecId(elecTag.getElecId());
            cpi.setTagData(data);//epc数据
            cpi.setState(elecTag.getState());
            return cpi;
        }
        return null;
    }

    @Override
    public List<CommodityPriceInfo> epcToCommodity(List<String> data) {
        List<CommodityPriceInfo> commoditys = new ArrayList<>();
        for (String datum : data) {
            CommodityPriceInfo commodityPriceInfo = epcToCommodity(datum);
            if(commodityPriceInfo != null){
                commoditys.add(commodityPriceInfo);
            }
        }

        return commoditys;
    }
}
