package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.OperatorLogEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by LXP on 2018/3/23.
 * 日志查询实体
 */

public class OperatorLogParam extends OperatorLogEntity {
    private SelectEntity select;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
