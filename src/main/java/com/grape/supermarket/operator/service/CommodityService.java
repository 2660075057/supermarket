package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.db.PictureEntity;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.entity.param.CommodityParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by LXP on 2017/9/29.
 */

public interface CommodityService {
    /**
     * 普通商品列表
     * @param param 查询参数
     * @param page 分页查询参数
     */
    List<CommodityPageEntity> commodityList(CommodityParam param, PageParam page);

    /**
     * 统计商品数量
     * @param param 查询参数
     */
    int countCommodity(CommodityParam param);

    /**
     * 更新商品信息
     * @param entity 商品主属性
     * @param attrs 商品其他属性
     * @param imgData 商品图片url
     */
    boolean updateCommodity(CommodityEntity entity, List<CommodityAttrEntity> attrs,List<PictureEntity> imgData);

    /**
     * 添加商品信息
     * @param entity 商品主属性
     * @param attrs 商品其他属性
     * @param imgData 商品图片url
     */
    boolean addCommodity(CommodityEntity entity, List<CommodityAttrEntity> attrs,List<PictureEntity> imgData);

    boolean deleteCommodity(Integer commId);

    CommodityPageEntity selectByPrimaryKey(Integer commId);

    /**
     * 微信端商品搜索，查询未删除、库存表存在且是在销售的商品
     * @param param
     * @return
     */
    List<CommodityPageEntity> selectCommByWeChat(CommodityParam param, PageParam page);

    /**
     * 根据条码号获取未删除的商品
     * @param barcode
     * @return
     */
    CommodityEntity selectByBarcode(String barcode);

    /**
     * 导入商品信息
     * @param excelType excel文件流
     * @param excelStream excel文件流
     * @param callback 导入结果回调，每处理一条数据会回调一次，可为null
     * @throws IOException 读取流失败抛出
     */
    void importCommodityByExcel(String excelType,InputStream excelStream,ImportCallback callback) throws IOException;
    
    /**
     * 查询商品类别
     * @return
     */
    List<CommodityTypeEntity>selectType();
    
    /**
     * 查询商品子类
     * @return
     */
    List<CommodityTypeEntity>ziselectType(String typeid);
}
