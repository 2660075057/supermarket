package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.OperatorEntity;

/**
 * Created by LXP on 2017/9/27.
 */

public class OperatorPageEntity extends OperatorEntity{
    public OperatorPageEntity() {
    }

    public OperatorPageEntity(OperatorEntity entity) {
        super(entity.getOperId(),entity.getOperAccount(),entity.getOperPwd(),entity.getOperName(),entity.getSex(),entity.getPhone(),entity.getGroupId(),entity.getState(),entity.getOpenId(),entity.getMaintenanceCard(),entity.getCreateTime());
        setUserGroup(entity.getUserGroup());
    }
}
