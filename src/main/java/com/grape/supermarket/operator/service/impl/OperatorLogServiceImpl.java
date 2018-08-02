package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.exception.BeanCopyException;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.dao.OperatorLogDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.OperatorLogEntity;
import com.grape.supermarket.entity.page.OperatorLogPageEntity;
import com.grape.supermarket.entity.param.OperatorLogParam;
import com.grape.supermarket.operator.service.OperatorLogService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by LXP on 2018/3/19.
 */
@Service
public class OperatorLogServiceImpl extends BasicService implements OperatorLogService {
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private OperatorLogDao operatorLogDao;
    @Autowired
    private OperatorDao operatorDao;

    @Override
    public Integer addLog(OperatorLogEntity operatorLog) {
        if(operatorLog == null){
            logger.error("operatorLog is null");
            return null;
        } else if(operatorLog.getCmd() == null){
            logger.error("cmd is null");
            return null;
        }
        operatorLogDao.insert(operatorLog);
        return operatorLog.getId();
    }

    @Override
    public OperatorLogEntity createOperatorLog(Integer operId, String clientIp, String cmd) {
        OperatorLogEntity operatorLogEntity = new OperatorLogEntity();
        operatorLogEntity.setOperId(operId);
        operatorLogEntity.setCmd(cmd);
        operatorLogEntity.setCilentIp(clientIp);
        return operatorLogEntity;
    }

    @Override
    public OperatorLogEntity selectByPrimaryKey(Integer key) {
        return operatorLogDao.selectByPrimaryKey(key);
    }

    @Override
    public List<OperatorLogPageEntity> operatorLogList(OperatorLogParam param, PageParam page) {
        SelectEntity selectEntity = initSelectEntity(page);
        selectEntity.setOrderBy("create_time desc");
        param = initOperatorLogParam(param);
        param.setSelect(selectEntity);
        List<OperatorLogEntity> log = operatorLogDao.selectByParam(param);
        List<OperatorLogPageEntity> data = new ArrayList<>(log.size());
        for (OperatorLogEntity logEntity : log) {
            OperatorLogPageEntity temp = convertPage(logEntity);

            data.add(temp);
        }
        return data;
    }

    @Override
    public int countOperatorLog(OperatorLogParam param) {
        param = initOperatorLogParam(param);
        SelectEntity selectEntity = new SelectEntity();
        selectEntity.setOrderBy("create_time desc");
        param.setSelect(selectEntity);
        return operatorLogDao.countByParam(param);
    }

    @Override
    public OperatorLogPageEntity logDetail(Integer id) {
        OperatorLogEntity logEntity = operatorLogDao.selectByPrimaryKey(id);
        if(logEntity != null){
            return convertPage(logEntity);
        }
        return null;
    }

    private OperatorLogPageEntity convertPage(OperatorLogEntity logEntity) {
        OperatorLogPageEntity pageEntity = new OperatorLogPageEntity();
        try {
            BeanUtils.copyProperties(pageEntity,logEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanCopyException(e);
        }
        OperatorEntity operator = operatorDao.selectByPrimaryKey(logEntity.getOperId());
        if (operator != null) {
            pageEntity.setOperatorName(operator.getOperName());
        }
        return pageEntity;
    }

    private OperatorLogParam initOperatorLogParam(OperatorLogParam param) {
        if (param == null) {
            param = new OperatorLogParam();
        } else {
            Calendar calendar = Calendar.getInstance();
            if (param.getStart_date() != null) {
                calendar.setTime(param.getStart_date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                param.setStart_date(calendar.getTime());
            }
            if (param.getEnd_date() != null) {
                calendar.setTime(param.getEnd_date());
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                param.setEnd_date(calendar.getTime());
            }
        }
        return param;
    }
}
