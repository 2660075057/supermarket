package com.grape.supermarket.entity;

/**
 * Created by LXP on 2018/1/8.
 */

public class AccessResult extends AccessEntity {
    private boolean access;

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}
