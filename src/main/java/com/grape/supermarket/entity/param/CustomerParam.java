package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.CustomerEntity;

/**
 * Created by LXP on 2017/9/28.
 */

public class CustomerParam extends CustomerEntity {
	
    private SelectEntity select;
    
    private String queryCondition;

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
    
}
