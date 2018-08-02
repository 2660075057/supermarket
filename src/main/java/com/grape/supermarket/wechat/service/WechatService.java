package com.grape.supermarket.wechat.service;

import com.grape.supermarket.entity.WechatUserInfo;
import com.grape.supermarket.wechat.entity.UserAuthData;

import java.io.IOException;

/**
 * Created by LXP on 2017/9/27.
 */

public interface WechatService {

    boolean checkWechatMessage(VerificationMessage message);

    /**
     * 获取access token
     * @return access token
     * @throws IOException 若不能获取到则抛出
     */
    String getAccessToken() throws IOException;

    void setWechatMenu() throws IOException;

    /**
     * 通过auth2.0 accessToken获取accessToken
     * @param code code
     * @throws IOException 若获取失败则抛出
     * @return 用户AccessData
     */
    UserAuthData getAuthData(String code) throws IOException;

    /**
     * 根据用户openid获取关注用户信息
     * @param openId openId
     * @throws IOException 若获取失败则抛出
     * @return 用户信息，若未关注返回null
     */
    WechatUserInfo getUserInfo(String openId) throws IOException;

    /**
     * 获取静默授权地址
     * @param state state,对应WechatController.auth中的跳转地址
     * @return 鉴权地址
     * @throws IllegalArgumentException state值不符合微信静默授权规范时抛出
     */
    String getAuthUrl(String state);

    class VerificationMessage{
        private String signature;
        private String timestamp;
        private String nonce;
        private String echostr;

        public VerificationMessage(String signature, String timestamp, String nonce, String echostr) {
            this.signature = signature;
            this.timestamp = timestamp;
            this.nonce = nonce;
            this.echostr = echostr;
        }

        public String getSignature() {
            return signature;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getNonce() {
            return nonce;
        }

        public String getEchostr() {
            return echostr;
        }
    }
}
