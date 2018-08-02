package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.AttributeDao;
import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.CommodityTypeAttrDao;
import com.grape.supermarket.dao.CommodityTypeDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.AttributeEntity;
import com.grape.supermarket.entity.db.CommodityTypeAttrKey;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.param.CommodityParam;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.operator.service.CommodityTypeService;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/9/30.
 */
@Service
public class CommodityTypeServiceImpl extends BasicService implements CommodityTypeService {
    @Autowired
    private CommodityTypeDao commodityTypeDao;

    @Autowired
    private CommodityTypeAttrDao commodityTypeAttrDao;

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private AttributeDao attributeDao;
    

    @Override
    public boolean addCommodityType(CommodityTypeEntity entity, List<Integer> attrIds) {
        entity.setTypeId(null);
        commodityTypeDao.insertSelective(entity);
        if (entity.getTypeId() == null) {
            String message = "commodityTypeDao.insertSelective fail,data->" + JsonUtils.toJson(entity);
            getLogger().warn(message);
            throw new RuntimeException(message);
        }
        updateTypeAttrs(entity.getTypeId(), attrIds);
        return true;
    }

    @Override
    public int deleteCommodityType(Integer typeId) {
        if (typeId == null) {
            return 1;
        }
        CommodityParam param = new CommodityParam();
        param.setTypeId(typeId);
        param.setDeleteMark((byte)0);
        boolean b = commodityDao.selectByParam(param).isEmpty();
        if (!b) {
            return 1;
        }
        deleteTypeAttr(typeId);
        CommodityTypeEntity entity = new CommodityTypeEntity();
        entity.setTypeId(typeId);
        entity.setDeleteMark((byte)1);
        commodityTypeDao.updateByPrimaryKeySelective(entity);
        return 0;
    }

    @Override
    public boolean updateCommodityType(CommodityTypeEntity entity, List<Integer> attrIds) {
        if (entity.getTypeId() == null) {
            return false;
        }
        commodityTypeDao.updateByPrimaryKeySelective(entity);
        updateTypeAttrs(entity.getTypeId(), attrIds);
        return true;
    }
    
    @Override
    public List<CommodityTypePageEntity> commodityTypeList(CommodityTypeParam param, PageParam page) {
        SelectEntity select = param.getSelect();
        if (select == null) {
            select = new SelectEntity();
            param.setSelect(select);
        }
        if (page != null) {
            int[] limit = page.getLimit();
            select.setLimit(limit);
        }
        List<CommodityTypeEntity> commodityTypeEntities = commodityTypeDao.selectByParam(param);
        List<CommodityTypePageEntity> datas = new ArrayList<>(commodityTypeEntities.size());
        try {
            for (CommodityTypeEntity commodityTypeEntity : commodityTypeEntities) {
                CommodityTypePageEntity ctp = new CommodityTypePageEntity();
                BeanUtils.copyProperties(ctp, commodityTypeEntity);
                List<AttributeEntity> attrs = attributeDao.selectByCommTypeId(commodityTypeEntity.getTypeId());
                ctp.setAttrs(attrs);
                datas.add(ctp);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
//            getLogger().warn(getClass()+".commodityTypeList拷贝实体出错",e);
            throw new BeanCopyException(e);
        }
        return datas;
    }

    @Override
    public List<CommodityTypePageEntity> commodityTree(CommodityTypeParam param) {
        List<CommodityTypePageEntity> resultNodes = new ArrayList<CommodityTypePageEntity>();//树形结构排序之后list内容
        List<CommodityTypePageEntity> datas = commodityTypeList(param,null);
        if(param.getTypeName() != null){ //判断有没有条件查询
        	resultNodes = datas;
        }else{
        	resultNodes = buildTree(datas);
        }
        
        return resultNodes;
    }

    /**
     * 构建树形结构list
     * @return 返回树形结构List列表
     */
    private List<CommodityTypePageEntity> buildTree(List<CommodityTypePageEntity> nodes) {
    	List<CommodityTypePageEntity> resultNodes = new ArrayList<CommodityTypePageEntity>();//树形结构排序之后list内容
    	for (CommodityTypePageEntity node : nodes) {
            if (node.getMasterId() == 0) {//通过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node,nodes,resultNodes);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }
    /**
     * 递归循环子节点
     *
     * @param node 当前节点
     */
    private void build(CommodityTypePageEntity node,List<CommodityTypePageEntity> nodes,List<CommodityTypePageEntity> resultNodes) {
        List<CommodityTypePageEntity> children = getChildren(node,nodes);
        if (!children.isEmpty()) {//如果存在子节点
            for (CommodityTypePageEntity child : children) {//将子节点遍历加入返回值中
                resultNodes.add(child);
                build(child,nodes,resultNodes);
            }
        }
    }
    /**
     * @param node
     * @return 返回
     */
    private List<CommodityTypePageEntity> getChildren(CommodityTypePageEntity node,List<CommodityTypePageEntity> nodes) {
        List<CommodityTypePageEntity> children = new ArrayList<CommodityTypePageEntity>();
        Integer id = node.getTypeId();
        for (CommodityTypePageEntity child : nodes) {
            if (id == child.getMasterId()) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

    
    @Override
    public int countByParam(CommodityTypeParam param) {
        return commodityTypeDao.countByParam(param);
    }

    @Override
    public List<CommodityTypeEntity> selectByMasterId(Integer masterId) {
        if(masterId == null){
            return null;
        }
        return commodityTypeDao.selectByMasterId(masterId);
    }

    private void updateTypeAttrs(Integer typeId, List<Integer> attrids) {
        if (typeId == null || attrids == null) {
            return;
        }
        deleteTypeAttr(typeId);
        for (Integer attrid : attrids) {
            CommodityTypeAttrKey record = new CommodityTypeAttrKey();
            record.setTypeId(typeId);
            record.setAttrId(attrid);
            commodityTypeAttrDao.insertSelective(record);
        }
    }

    private void deleteTypeAttr(Integer typeId) {
        CommodityTypeAttrKey delete = new CommodityTypeAttrKey();
        delete.setTypeId(typeId);
        commodityTypeAttrDao.deleteByPrimaryKey(delete);
    }
}
