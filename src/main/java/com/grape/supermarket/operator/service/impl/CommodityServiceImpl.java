package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.ExcelUtil;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.CommodityAttrDao;
import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.CommodityTypeDao;
import com.grape.supermarket.dao.PictureDao;
import com.grape.supermarket.entity.ImportResult;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.PictureEntity;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.entity.param.CommodityParam;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.operator.service.CommodityService;
import com.grape.supermarket.operator.service.ImportCallback;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/9/30.
 */
@Service
public class CommodityServiceImpl extends BasicService implements CommodityService {
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private CommodityTypeDao commodityTypeDao;
    @Autowired
    private CommodityAttrDao commodityAttrDao;
    @Autowired
    private PictureDao pictureDao;

    @Override
    public List<CommodityPageEntity> commodityList(CommodityParam param, PageParam page) {
        if(param == null){
            param = new CommodityParam();
        }
        param.setDeleteMark((byte) 0);
        if(page != null){
            SelectEntity select = param.getSelect();
            if(select == null){
                select = new SelectEntity();
            }
            select.setLimit(page.getLimit());
            param.setSelect(select);
        }
        List<CommodityEntity> commoditys = commodityDao.selectByParam(param);
        List<CommodityPageEntity> datas = new ArrayList<>(commoditys.size());
        try {
            for (CommodityEntity commodity : commoditys) {
                CommodityPageEntity cpe = new CommodityPageEntity();
                BeanUtils.copyProperties(cpe,commodity);
                //装填属性数据
                CommodityTypeEntity commType = commodityTypeDao.selectByPrimaryKey(commodity.getTypeId());
                cpe.setCommodityType(commType);
                //装填图片数据
                PictureEntity picParam = new PictureEntity();
                picParam.setType((byte) 1);
                picParam.setRecno(commodity.getCommId());
                List<PictureEntity> picture = pictureDao.selectByParam(picParam);
                cpe.setPicture(picture);

                datas.add(cpe);
            }
        } catch (IllegalAccessException|InvocationTargetException e) {
            throw new BeanCopyException(e);
        }
        return datas;
    }

    @Override
    public int countCommodity(CommodityParam param) {
        if(param == null){
            param = new CommodityParam();
        }
        param.setDeleteMark((byte) 0);
        return commodityDao.countByParam(param);
    }

    @Override
    public boolean updateCommodity(CommodityEntity entity, List<CommodityAttrEntity> attrs,List<PictureEntity> imgData) {
        if(entity.getCommId() == null){
            return false;
        }
        CommodityEntity ce = commodityDao.selectByBarcode(entity);
        if(ce != null){
            return false;
        }
        commodityDao.updateByPrimaryKeySelective(entity);
        //更新图片数据
        //先删除旧的
        PictureEntity deleteParam = new PictureEntity();
        deleteParam.setType((byte) 1);
        deleteParam.setRecno(entity.getCommId());
        pictureDao.deleteByParam(deleteParam);
        //插入新的
        PictureEntity insertParam = new PictureEntity();
        for (int i = 0; i < imgData.size(); i++) {
            insertParam.setType((byte) 1);
            insertParam.setRecno(entity.getCommId());
            insertParam.setImgUrl(imgData.get(i).getImgUrl());
            insertParam.setSort((byte) i);
            pictureDao.insert(insertParam);
        }

        updateAttrs(entity.getCommId(),attrs);
        return true;
    }

    @Override
    public boolean addCommodity(CommodityEntity entity, List<CommodityAttrEntity> attrs,List<PictureEntity> imgData) {
        entity.setCommId(null);

        CommodityEntity ce = commodityDao.selectByBarcode(entity);
        if(ce != null){
            return false;
        }
        commodityDao.insertSelective(entity);
        if(entity.getCommId() == null){
            String message= "commodityDao.insertSelective insert fail primary key not found,data->"+ JsonUtils.toJson(entity);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }
        PictureEntity insertParam = new PictureEntity();
        for (int i = 0; i < imgData.size(); i++) {
            insertParam.setType((byte) 1);
            insertParam.setRecno(entity.getCommId());
            insertParam.setImgUrl(imgData.get(i).getImgUrl());
            insertParam.setSort((byte) i);
            pictureDao.insert(insertParam);
        }

        updateAttrs(entity.getCommId(),attrs);
        return true;
    }
    
    @Override
    public void importCommodityByExcel(String excelType, InputStream excelStream, ImportCallback callback) throws IOException {
        List<String[]> excelData = ExcelUtil.readData(excelType,1,true,excelStream);
        for (String[] excelDatum : excelData) {
            Map<String, Object> temp = convertToCommodity(excelDatum);
            boolean b = (Boolean) temp.get("state");
            if (!b) {//转换实体失败
                if(callback != null){
                    ImportResult importResult = new ImportResult();
                    importResult.setState(false);
                    importResult.setFailMessage("非法数据行"+JsonUtils.toJson(excelDatum));
                    callback.handle(importResult);
                }
                continue;//继续下一行
            }

            CommodityEntity commodity = (CommodityEntity) temp.get("commodity");
            if(commodity.getTypeId() == null){
                if(callback != null){
                    ImportResult importResult = new ImportResult();
                    importResult.setState(false);
                    importResult.setFailMessage("无法找到商品分类"+JsonUtils.toJson(excelDatum));
                    callback.handle(importResult);
                }
                continue;
            }
            if (this.selectByBarcode(commodity.getBarcode()) != null) {
                if(callback != null){
                    ImportResult importResult = new ImportResult();
                    importResult.setState(false);
                    importResult.setFailMessage("商品条码重复"+JsonUtils.toJson(excelDatum));
                    callback.handle(importResult);
                }
                continue;
            }
            if(commodity.getCommPrice() < 1 || commodity.getCostPrice() < 1){
                if(callback != null){
                    ImportResult importResult = new ImportResult();
                    importResult.setState(false);
                    importResult.setFailMessage("进价或售价错误"+JsonUtils.toJson(excelDatum));
                    callback.handle(importResult);
                }
                continue;
            }
            if (commodity.getCommName().length() > 32) {
                if (callback != null) {
                    ImportResult importResult = new ImportResult();
                    importResult.setState(false);
                    importResult.setFailMessage("商品名称过长" + JsonUtils.toJson(excelDatum));
                    callback.handle(importResult);
                }
                continue;
            }
            //校验通过，插入数据
            int i = commodityDao.insertSelective(commodity);
            if (callback != null) {
                ImportResult importResult = new ImportResult();
                importResult.setState(i == 1);
                importResult.setFailMessage(i == 1 ? null : "未知错误");
                callback.handle(importResult);
            }
        }
    }

    private Map<String,Object> convertToCommodity(String[] data){
        Map<String,Object> m = new HashMap<>(8);
        if(data == null || data.length < 5){
            m.put("state", Boolean.FALSE);
            return m;
        }
        for (String datum : data) {//初步检查各个值是否符合要求
            if(datum == null || datum.trim().isEmpty()){
                m.put("state", Boolean.FALSE);
                return m;
            }
        }
        CommodityEntity commodity = new CommodityEntity();
        commodity.setBarcode(data[0].trim());
        commodity.setCommName(data[1].trim());

        //搜索商品类型
        CommodityTypeParam param = new CommodityTypeParam();
        param.setTypeName(data[2].trim());
        List<CommodityTypeEntity> typeEntities = commodityTypeDao.selectByParam(param);
        for (CommodityTypeEntity typeEntity : typeEntities) {
            if(typeEntity.getTypeName().equals(data[2])){
                commodity.setTypeId(typeEntity.getTypeId());
                break;
            }
        }

        try {
            double cost = Double.parseDouble(data[3].trim());
            commodity.setCostPrice((int) (cost * 100));//转换为分，去尾
            double price = Double.parseDouble(data[4].trim());
            commodity.setCommPrice((int) (price * 100));//转换为分，去尾
        } catch (NumberFormatException e) {
            m.put("state", Boolean.FALSE);
        }

        if(!m.containsKey("state")){
            m.put("state", Boolean.TRUE);
            m.put("commodity",commodity);
        }
        return m;
    }

    @Override
    public boolean deleteCommodity(Integer commId) {
        if(commId == null){
            return false;
        }
        CommodityEntity up = new CommodityEntity();
        up.setCommId(commId);
        up.setDeleteMark((byte) 1);
        commodityDao.updateByPrimaryKeySelective(up);
        return true;
    }

    @Override
    public CommodityPageEntity selectByPrimaryKey(Integer commId) {
        CommodityEntity commodityEntity = commodityDao.selectByPrimaryKey(commId);
        CommodityPageEntity cpe = null;
        if(commodityEntity != null){
            cpe = new CommodityPageEntity();
            CommodityTypeEntity commType;
            commType = commodityTypeDao.selectByPrimaryKey(commodityEntity.getTypeId());
            try {
                BeanUtils.copyProperties(cpe,commodityEntity);
                cpe.setCommodityType(commType);
            }catch (IllegalAccessException|InvocationTargetException e) {
                throw new BeanCopyException(e);
            }

            PictureEntity picParam = new PictureEntity();
            picParam.setType((byte) 1);
            picParam.setRecno(commodityEntity.getCommId());
            List<PictureEntity> picture = pictureDao.selectByParam(picParam);
            cpe.setPicture(picture);
        }

        return cpe;
    }

    @Override
    public List<CommodityPageEntity> selectCommByWeChat(CommodityParam param, PageParam page) {
        if(page != null){
            SelectEntity select = param.getSelect();
            if(select == null){
                select = new SelectEntity();
            }
            select.setLimit(page.getLimit());
            param.setSelect(select);
        }
        List<CommodityEntity> data = commodityDao.selectCommByWeChat(param);
        List<CommodityPageEntity> commoditys = new ArrayList<>(data.size());
        for (CommodityEntity datum : data) {
            CommodityPageEntity cpe = new CommodityPageEntity();
            try {
                BeanUtils.copyProperties(cpe,datum);
            } catch (Exception e) {
                throw new BeanCopyException(e);
            }
            List<PictureEntity> picture = getCommPicture(cpe.getCommId());
            cpe.setPicture(picture);
            commoditys.add(cpe);
        }
        return commoditys;
    }

    @Override
    public CommodityEntity selectByBarcode(String barcode) {
        if(barcode == null){
            return null;
        }
        CommodityEntity ce = new CommodityEntity();
        ce.setBarcode(barcode);
        return commodityDao.selectByBarcode(ce);
    }

    private void updateAttrs(Integer commId,List<CommodityAttrEntity> attrs){
        if(commId == null || attrs == null){
            return ;
        }
        commodityAttrDao.deleteByCommId(commId);
        for (CommodityAttrEntity attr : attrs) {
            attr.setCommId(commId);
            commodityAttrDao.insertSelective(attr);
        }
    }

    private List<PictureEntity> getCommPicture(Integer commId){
        PictureEntity param = new PictureEntity();
        param.setType((byte) 1);
        param.setRecno(commId);
        return pictureDao.selectByParam(param);
    }

	@Override
	public List<CommodityTypeEntity> selectType() {
		// TODO Auto-generated method stub
		return commodityDao.selectType();
	}

	@Override
	public List<CommodityTypeEntity> ziselectType(String typeid) {
		// TODO Auto-generated method stub
		return commodityDao.ziselectType(typeid);
	}
}
