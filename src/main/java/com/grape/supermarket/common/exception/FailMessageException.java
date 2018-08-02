package com.grape.supermarket.common.exception;

/**
 * Created by LXP on 2017/10/23.
 * 用于返回统一失败消息的异常，返回的json通常如下<br/>
 * {"data":null,"code":ResultBean.FAIL,"message":"YOU_MESSAGE"}
 */

public class FailMessageException extends RuntimeException{
    private String message;
    private int level=1;

    public FailMessageException(String message) {
        this.message = message;
    }

    public FailMessageException(String message, int level) {
        this.message = message;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
