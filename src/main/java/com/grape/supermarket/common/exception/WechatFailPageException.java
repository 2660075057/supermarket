package com.grape.supermarket.common.exception;

/**
 * Created by LXP on 2017/12/29.
 */

public class WechatFailPageException extends RuntimeException {
    private boolean needLog = true;

    public WechatFailPageException(){ }

    public WechatFailPageException(Throwable cause, boolean needLog) {
        super(cause);
        this.needLog = needLog;
    }

    public WechatFailPageException(boolean needLog) {
        this.needLog = needLog;
    }

    public boolean isNeedLog() {
        return needLog;
    }

    public void setNeedLog(boolean needLog) {
        this.needLog = needLog;
    }
}
