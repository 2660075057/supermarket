package com.grape.supermarket.common.exception;

/**
 * Created by LXP on 2017/9/30.
 * bean转换异常，使用org.apache.commons.beanutils.BeanUtils转换时应重新捕捉后抛出此异常
 */

public class BeanCopyException extends RuntimeException {
    public BeanCopyException() {
    }

    public BeanCopyException(Throwable cause) {
        super(cause);
    }
}
