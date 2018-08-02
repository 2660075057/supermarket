package com.grape.supermarket.entity.page;

import com.grape.supermarket.entity.db.ElecTagEntity;

/**
 * Created by LXP on 2018/3/21.
 */

public class ElecTagPageEntity extends ElecTagEntity {
    private String barcode;
    private String commName;

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
