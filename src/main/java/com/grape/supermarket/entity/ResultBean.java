package com.grape.supermarket.entity;

/**
 * Created by LXP on 2017/9/25.
 */

public class ResultBean<T> {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NOT_LOGIN = -1;
    public static final int NO_PERMISSION = -2;

    private T data;
    private int code = SUCCESS;
    private String message = "SUCCESS";

    public ResultBean() {
    }

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(Throwable t) {
        code = FAIL;
        message = t.getMessage();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
}
