package com.grape.supermarket.device.service;

import com.grape.supermarket.entity.AccessEntity;
import com.grape.supermarket.entity.AccessResult;

/**
 * Created by LXP on 2017/10/13.
 * 设备门禁服务
 */

public interface AccessControlService {
    enum Access_Code{Ready/*门禁准备完成*/,Running/*门禁运行中*/,Disable/*禁止入内*/}

    /**
     * 请求入店门禁
     * @param accessEntity 访问信息，检查此实体的state值获取处理结果
     */
    Access_Code addInAccess(AccessEntity accessEntity);

    /**
     * 检查是否有入店门禁通知
     * @param shopId 商店id
     */
    AccessResult checkInAccess(Integer shopId);

    void inAccessInit(Integer shopId);

    /**
     * 请求出店门禁
     * @param accessEntity 访问信息，检查此实体的state值获取处理结果
     */
    Access_Code addOutAccess(AccessEntity accessEntity);

    /**
     * 检查是否有出店门禁通知
     * @param shopId 商店id
     */
    AccessResult checkOutAccess(Integer shopId);

    void outAccessInit(Integer shopId);
}
