package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.OperatorLogEntity;
import com.grape.supermarket.entity.page.OperatorLogPageEntity;
import com.grape.supermarket.entity.param.OperatorLogParam;

import java.util.List;

/**
 * Created by LXP on 2018/3/19.
 */

public interface OperatorLogService {
    String OPERATOR_ADD="0102";
    String OPERATOR_UPDATE ="0102";
    String OPERATOR_DELETE="0102";

    String COMMODITY_PRICE_ADD = "0702";
    String COMMODITY_PRICE_UPDATE = "0703";
    String COMMODITY_PRICE_DELETE = "0704";
    String ELEC_TAG_IMPORT = "1201";

    /**
     * 添加日志
     * @param operatorLog 操作日志
     * @return 若添加成功返回日志id
     */
    Integer addLog(OperatorLogEntity operatorLog);

    OperatorLogEntity createOperatorLog(Integer operId,String clientIp,String cmd);

    OperatorLogEntity selectByPrimaryKey(Integer key);

    /**
     * 根据条件获取日志记录
     * @param param 条件参数
     * @param page 分页参数，null默认第一页第10条
     */
    List<OperatorLogPageEntity> operatorLogList(OperatorLogParam param, PageParam page);

    /**
     * 根据条件统计日志记录
     * @param param 条件参数
     */
    int countOperatorLog(OperatorLogParam param);

    OperatorLogPageEntity logDetail(Integer id);
}
