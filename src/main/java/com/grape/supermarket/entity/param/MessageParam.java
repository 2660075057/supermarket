package com.grape.supermarket.entity.param;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.MessageEntity;

/**
 * 
 * author huanghuang
 * 2017年10月18日 上午10:24:23
 */

public class MessageParam extends MessageEntity {
    private SelectEntity select;
    private int boolUser=0;//是否显示所有留言
    

    public SelectEntity getSelect() {
        return select;
    }

    public void setSelect(SelectEntity select) {
        this.select = select;
    }

    public int getBoolUser() {
        return boolUser;
    }

    public void setBoolUser(int boolUser) {
        this.boolUser = boolUser;
    }
}
