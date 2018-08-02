package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.OperatorLogEntity;

/**
 * Created by LXP on 2018/3/23.
 */

public class OperatorLogPageEntity extends OperatorLogEntity {
    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
