package com.grape.supermarket.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by LXP on 2017/11/18.
 */

public class ChangeElecTagFailEvent extends ApplicationEvent {
    private Integer elecId;

    public ChangeElecTagFailEvent(Integer elecId) {
        super(elecId);
        this.elecId = elecId;
    }

    public Integer getElecId() {
        return elecId;
    }

    public void setElecId(Integer elecId) {
        this.elecId = elecId;
    }
}
