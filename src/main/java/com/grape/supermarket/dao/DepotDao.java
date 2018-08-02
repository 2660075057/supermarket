package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.DepotEntity;
import com.grape.supermarket.entity.param.DepotParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepotDao {
    /**
     * 根据主键删除数据库的记录
     *
     * @param depotId
     */
    int deleteByPrimaryKey(Integer depotId);

    /**
     *
     * @param depotEntity
     */
    int insertSelective(DepotEntity depotEntity);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param depotId
     */
    DepotEntity selectByPrimaryKey(Integer depotId);
    /**
     * 根据条件查询库存记录
     *
     */
    List<DepotEntity> selectByCondition(DepotParam depotParam);

    /**
     *更新库存表
     * @param depotEntity
     */
    int updateByPrimaryKeySelective(DepotEntity depotEntity);

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

    void reduceCommodity(@Param("shopId") Integer shopId,@Param("commId") Integer commId);

    /**
     * 查询处于报警状态库存，告警库存必须处于出售状态且低于告警阀值
     * @return 告警库存
     */
    List<DepotEntity> selectAlarmDepot();

    /**
     * 微信端根据站点获取站点内销售信息
     * @param depotParam
     * @return
     */
    List<DepotEntity> selectDepotByWeChat(DepotParam depotParam);
}