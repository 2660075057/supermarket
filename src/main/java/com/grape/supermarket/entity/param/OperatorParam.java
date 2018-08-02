package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.OperatorEntity;

import java.util.List;

/**
 * Created by LXP on 2017/9/26.
 */

public class OperatorParam extends OperatorEntity {
    private SelectEntity select;

    private boolean deleteMark;
    private List<Integer> ignoreId;//忽略id

    public OperatorParam() {
        super();
    }

    public OperatorParam(OperatorEntity entity) {
        super(entity.getOperId(),entity.getOperAccount(),entity.getOperPwd(),entity.getOperName(),entity.getSex(),entity.getPhone(),entity.getGroupId(),entity.getState(),entity.getOpenId(),entity.getMaintenanceCard(),entity.getCreateTime());
    }

    public boolean isDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(boolean deleteMark) {
        this.deleteMark = deleteMark;
    }

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public List<Integer> getIgnoreId() {
        return ignoreId;
    }

    public void setIgnoreId(List<Integer> ignoreId) {
        this.ignoreId = ignoreId;
    }
}
