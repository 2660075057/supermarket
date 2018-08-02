package com.grape.supermarket.device.controller;

import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.dao.OperatorShopDao;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.OperatorShopKey;
import com.grape.supermarket.event.FireMessageEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LXP on 2018/1/16.
 */
@Controller
@RequestMapping("/device/event")
public class EventController {
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private OperatorShopDao operatorShopDao;

    @Autowired
    private ApplicationEventPublisher publish;

    private Map<String, Long> timer = new ConcurrentHashMap<>();

    @RequestMapping("/fire")
    @ResponseBody
    public ResultBean fire(Integer shopId, Long time) {
        if (shopId == null || time == null) {
            throw new FailMessageException("args error");
        }
        String timerKey = "fileEvent@" + shopId;
        Long aLong = timer.get(timerKey);
        if (aLong != null && aLong + 1800_000 > System.currentTimeMillis()) {//过滤半个小时发送过通知的站点
            logger.info("过滤重复发送火灾告警消息,shopId[" + shopId + "] time[" + time + "]");
            return new ResultBean();
        }
        List<OperatorShopKey> operatorIdList = operatorShopDao.getOperatorIdList(shopId);
        List<Integer> operIds = new ArrayList<>(operatorIdList.size());
        for (OperatorShopKey operatorShopKey : operatorIdList) {
            operIds.add(operatorShopKey.getOperId());
        }
        operIds.add(0);
        FireMessageEvent fme = new FireMessageEvent();
        fme.setNoticeOperatorId(operIds);
        fme.setShopId(shopId);
        fme.setTime(new Date(time));
        timer.put(timerKey, System.currentTimeMillis());
        publish.publishEvent(fme);

        return new ResultBean();
    }
}
