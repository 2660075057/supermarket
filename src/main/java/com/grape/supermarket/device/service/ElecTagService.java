package com.grape.supermarket.device.service;

import com.grape.supermarket.event.ChangeElecTagFailEvent;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/11/18.
 */

public interface ElecTagService {
    /**
     * 用于接收修改电子标签售出状态失败的异常事件
     * @param event 事件
     */
    void changeStateFail(ChangeElecTagFailEvent event);

    /**
     * 获取电子标签解锁数据
     * @param epc 电子标签数据
     * @return 电子标签数据与解锁后数据的映射
     */
    Map<String,String> disLock(List<String> epc);
}
