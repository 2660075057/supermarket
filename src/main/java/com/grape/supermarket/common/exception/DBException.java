package com.grape.supermarket.common.exception;

/**
 * Created by LXP on 2017/11/7.
 * 数据库访问异常，插入更新失败可以抛出此异常
 */

public class DBException extends RuntimeException {
    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }
}
