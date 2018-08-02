package com.grape.supermarket.entity;

/**
 * Created by LXP on 2018/4/20.
 */

public class ImportResult {
    private boolean state;
    private Object data;
    private String failMessage;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    @Override
    public String toString() {
        return "ImportResult{" +
                "state=" + state +
                ", data=" + data +
                ", failMessage='" + failMessage + '\'' +
                '}';
    }
}
