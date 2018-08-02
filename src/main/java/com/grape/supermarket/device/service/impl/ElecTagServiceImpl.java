package com.grape.supermarket.device.service.impl;

import com.grape.supermarket.dao.ElecTagDao;
import com.grape.supermarket.device.service.ElecTagService;
import com.grape.supermarket.entity.db.ElecTagEntity;
import com.grape.supermarket.event.ChangeElecTagFailEvent;
import com.grape.supermarket.operator.service.ElecTagDefinition;
import com.grape.supermarket.operator.service.ElecTagDefinitionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LXP on 2017/11/18.
 */
@Service
public class ElecTagServiceImpl implements ElecTagService {
    private Logger logger = Logger.getLogger(getClass());

    private Map<Integer, Long> changeStateFailMap = new ConcurrentHashMap<>();
    private Timer timer;
    @Autowired
    private ElecTagDao elecTagDao;
    @Value("${elecTag.version}")
    private int elecTagVersion;

    public ElecTagServiceImpl() {
        timer = new Timer("ElecTagServiceImpl", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                List<Integer> removeList = new ArrayList<>(changeStateFailMap.size() / 3 + 6);
                Set<Map.Entry<Integer, Long>> entries = changeStateFailMap.entrySet();
                for (Map.Entry<Integer, Long> entry : entries) {
                    if (entry.getValue() + 1200_000 < now) {
                        removeList.add(entry.getKey());
                    }
                }
                for (Integer integer : removeList) {
                    changeStateFailMap.remove(integer);
                }
            }
        }, 600_000, 1200_000);
    }

    @EventListener
    @Override
    public void changeStateFail(ChangeElecTagFailEvent event) {
        changeStateFailMap.put(event.getElecId(), System.currentTimeMillis());
    }

    @Override
    public Map<String, String> disLock(List<String> epc) {
        Map<String, String> result = new TreeMap<>();
        if (epc != null && !epc.isEmpty()) {
            for (String s : epc) {
                ElecTagEntity elecTag = elecTagDao.selectByData(s);
                if (elecTag != null) {
                    String oldTag = elecTag.getData().toUpperCase();
                    ElecTagDefinition elecTagDefinition = ElecTagDefinitionFactory.getInstance().getDefinition(elecTagVersion);
                    if (elecTagDefinition == null) {
                        logger.error("未知的条码解析规则");
                        return result;
                    }
                    try {
                        String disLock = elecTagDefinition.disLock(oldTag);
                        if (disLock != null) {
                            result.put(oldTag, disLock);
                        }
                    } catch (Exception e) {
                        logger.warn("解锁标签数据[" + oldTag + "]失败,函数无法解锁此数据", e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        timer.cancel();
    }
}
