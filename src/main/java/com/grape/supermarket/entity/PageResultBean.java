package com.grape.supermarket.entity;

import java.util.List;

/**
 * Created by LXP on 2017/9/25.
 */

public class PageResultBean<T> {
    public static final int NOT_LOGIN = -1;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = -2;

    private List<T> data;
    private int pageCurrent;
    private int pageSize;
    private int code = SUCCESS;
    private String message = "SUCCESS";

    public PageResultBean() {
    }

    public PageResultBean(List<T> data, int pageCurrent, int pageSize) {
        this.data = data;
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
    }

    public PageResultBean(Throwable t) {
        code = FAIL;
        message = t.getMessage();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
