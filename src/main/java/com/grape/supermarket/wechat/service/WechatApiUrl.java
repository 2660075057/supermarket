package com.grape.supermarket.wechat.service;

/**
 * Created by LXP on 2017/9/27.
 */

public interface WechatApiUrl {
    String accessToken = "https://api.weixin.qq.com/cgi-bin/token";
    String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    String orderquery = "https://api.mch.weixin.qq.com/pay/orderquery";
    String closeorder = "https://api.mch.weixin.qq.com/pay/closeorder";
    String createMenu = "https://api.weixin.qq.com/cgi-bin/menu/create";
    String deleteMenu = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    String createBarcode = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    String oauthToken = "https://api.weixin.qq.com/sns/oauth2/access_token";
    String userInfo = "https://api.weixin.qq.com/cgi-bin/user/info";
    String templateMessage = "https://api.weixin.qq.com/cgi-bin/message/template/send";
}
