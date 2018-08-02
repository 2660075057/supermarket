package com.grape.supermarket.entity.param;

import java.util.Date;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.ShopLogEntity;

/**
 * 
 * author huanghuang
 * 2017年11月9日 下午5:08:15
 */
public class ShopLogParam extends ShopLogEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    
    
}
