package com.grape.supermarket.entity.param;

import java.util.Date;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.OrderEntity;

/**
 * Created by LQW on 2017/10/18.
 */

public class OrderParam extends OrderEntity {
    private int model = 1; //1代表用户模式 查询已支付且未删除订单
    private SelectEntity select;
    
    //开始时间
    private Date startTime;
    
    //结束时间
    private Date endTime;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
