package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.DepotEntity;
import com.grape.supermarket.entity.page.DepotPageEntity;
import com.grape.supermarket.entity.param.DepotParam;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LQW on 2017/10/10.
 */

public interface DepotService {

    /**
     * 根据主键删除库存记录
     *
     * @param depotId
     */
    int deleteById(Integer depotId);

    /**
     *新增库存
     * @param depotEntity
     */
    int insertDepot(DepotEntity depotEntity);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param depotId
     */
    DepotEntity selectById(Integer depotId);
    /**
     * 根据条件查询库存记录
     *
     */
    List<DepotEntity> selectByCondition(DepotParam depotParam,PageParam page);

    /**
     *增加库存数量
     * @param depotEntity
     */
    int addAmount(DepotEntity depotEntity);
    /**
     *转仓
     * @param depotEntity
     */
    int turnShopId(DepotEntity depotEntity);
    /**
     *设置报警阀值
     * @param depotEntity
     */
    int setThreshold(DepotEntity depotEntity);
    /**
     *启用库存
     */
    int openDepot(Integer depotId, Byte state);
    /**
     *关闭库存
     */
    int closeDepot(Integer depotId,Byte state);

    /**
     * 根据商店ID和商品ID查询库存
     * @param depotEntity
     * @return
     */
    DepotEntity selectByShopIdAanCommId(DepotEntity depotEntity);
    /**
     * 分页查询统计总记录数
     * @param depotParam
     * @return
     */
    int countDepotByCondition(DepotParam depotParam);
    /**
     * 微信端根据站点获取站点内销售信息
     * @param param
     * @return
     */
    List<DepotPageEntity> selectDepotByWeChat(DepotParam param, PageParam page);
    
    HashMap<Integer,Object> selectDepotCheck(String str, Integer shopid);
    
    void batchInsertDepot(List<DepotEntity> Delist);

	int costPrice(DepotParam param);
}
