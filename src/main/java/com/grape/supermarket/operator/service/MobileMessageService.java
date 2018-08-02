package com.grape.supermarket.operator.service;

/**
 * 手机短信服务
 * @author xunpengliu
 */
public interface MobileMessageService {

    /**
     * 发送短信验证码服务
     * @return 发送状态，
     * 0发送成功
     * 1超过最大发信次数
     * -1失败，可以查看错误日志了解详细情况
     */
    int sendRegisterMessage(String mobile,String vcode);

}
