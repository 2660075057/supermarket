package com.grape.supermarket.wechat.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.util.*;
import com.grape.supermarket.entity.WechatUserInfo;
import com.grape.supermarket.operator.service.WechatConfigService;
import com.grape.supermarket.wechat.entity.UserAuthData;
import com.grape.supermarket.wechat.service.WechatApiUrl;
import com.grape.supermarket.wechat.service.WechatService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LXP on 2017/9/27.
 */
@Service
public final class WechatServiceImpl implements WechatService {
    private final String token;
    private final String appId;
    private final String appsecret;
    private final String domain;
    @Autowired
    private WechatConfigService wechatConfigService;

    private volatile String accessToken;

    private Logger logger = Logger.getLogger(this.getClass());
    private volatile long accessTokenUpdateTime = 0;

    private Map<String,UserAuthData> userAuthMap = new ConcurrentHashMap<>();
    private Map<String,Long> userAuthTTL = new ConcurrentHashMap<>();

    private Timer timer = new Timer("WechatService_userAuth_GC",true);

    @Autowired
    public WechatServiceImpl(@Qualifier("wechatProperties") PropertiesLoader pl) {
        token = pl.getProperty("token");
        appId = pl.getProperty("appId");
        appsecret = pl.getProperty("appsecret");
        domain = pl.getProperty("domain");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<String> removeList = new ArrayList<>(userAuthTTL.size()/3+6);
                Set<Map.Entry<String, Long>> entries = userAuthTTL.entrySet();
                for (Map.Entry<String, Long> entry : entries) {
                    if (entry.getValue() == null || entry.getValue() + 300_000 < System.currentTimeMillis()) {
                        removeList.add(entry.getKey());
                    }
                }
                for (String s : removeList) {
                    userAuthMap.remove(s);
                    userAuthTTL.remove(s);
                }
            }
        },0,300_000);
    }

    @Override
    public boolean checkWechatMessage(VerificationMessage message) {
        String signature = message.getSignature();
        String timestamp = message.getTimestamp();
        String nonce = message.getNonce();
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);

        String m = "";
        for (String s : arr) {
            m += s;
        }
        String sha1 = HashUtil.sha1(m);
        return sha1 != null && sha1.equalsIgnoreCase(signature);
    }

    @Override
    public String getAccessToken() throws IOException {
        if (accessTokenUpdateTime < System.currentTimeMillis()) {
            synchronized (WechatServiceImpl.class) {
                if (accessTokenUpdateTime < System.currentTimeMillis()) {
                    String url = WechatApiUrl.accessToken;
                    Map<String, String> param = new HashMap<>();
                    param.put("grant_type", "client_credential");
                    param.put("appid", appId);
                    param.put("secret", appsecret);
                    HttpResponce hr = HttpClientUtil.doGet(url, param, "utf-8");
                    if (hr.getState() == 200) {
                        String body = hr.getBody();
                        Map<String, Object> data = JsonUtils.fromJson(body, new TypeReference<Map<String, Object>>() {
                        });
                        String accessToken = (String) data.get("access_token");
                        if (accessToken != null) {
                            this.accessToken = accessToken;
                            int ut = 300;
                            try {
                                ut = (Integer) data.get("expires_in") - 300;
                            } catch (Exception e) {
                            }
                            accessTokenUpdateTime = System.currentTimeMillis() + ut * 1000;
                            return accessToken;
                        }
                        logger.error("获取accessToken失败，errcode->" + data.get("errcode"));
                    }
                    logger.error("http responce state->" + hr.getState() + " body->" + hr.getBody());
                    throw new IOException("http responce state->" + hr.getState() + " body->" + hr.getBody());
                }
            }
        }

        return accessToken;
    }

    @Override
    public void setWechatMenu() throws IOException {
        String accessToken = getAccessToken();
        //先删除菜单
        HttpResponce deleteHr = HttpClientUtil.doPost(WechatApiUrl.deleteMenu + "?access_token=" + accessToken);
        if (deleteHr.getState() == 200) {
            String wechatMenu = wechatConfigService.getWechatMenu();
            if (wechatMenu != null) {
                wechatMenu = wechatMenu.replace("##mainPage##", getAuthUrl("mainPage"))
                        .replace("##nearbyShop##", getAuthUrl("nearbyShop"))
                        .replace("##myOrder##", getAuthUrl("myOrder"));
                HttpResponce hr = HttpClientUtil.doPost(WechatApiUrl.createMenu + "?access_token=" + accessToken, wechatMenu, "utf-8");
                if (hr.getState() == 200) {
                    Map<String, String> data = JsonUtils.fromJson(hr.getBody(), new TypeReference<Map<String, String>>() {});
                    if ("0".equals(data.get("errcode"))) {
                        return;
                    }
                }

                deleteHr = hr;
            } else {
                return ;
            }
        }
        throw new IOException("初始化微信菜单失败,request->" + deleteHr);
    }

    @Override
    public UserAuthData getAuthData(String code) throws IOException {
        UserAuthData uadCache = userAuthMap.get(code);
        if (uadCache != null) {
            userAuthTTL.put(code,System.currentTimeMillis());
            return uadCache;
        }
        String url = WechatApiUrl.oauthToken + "?appid=" + appId +
                "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        HttpResponce hr = HttpClientUtil.doGet(url);
        if (hr.getState() == 200) {
            Map<String, Object> data = JsonUtils.fromJson(hr.getBody(), new TypeReference<Map<String, Object>>() {});
            UserAuthData uad = new UserAuthData();
            uad.setAccessToken((String) data.get("access_token"));
            uad.setOpenId((String) data.get("openid"));
            userAuthMap.put(code,uad);
            userAuthTTL.put(code,System.currentTimeMillis());
            return uad;
        }
        throw new SocketException("获取oauthToken失败,request->" + JsonUtils.toJson(hr));
    }

    @Override
    public WechatUserInfo getUserInfo(String openId) throws IOException {
        WechatUserInfo ce = new WechatUserInfo();
        ce.setOpenid(openId);
        String access_token = getAccessToken();
        String url = WechatApiUrl.userInfo + "?access_token=" + access_token + "&openid=" + openId;
        HttpResponce hr = HttpClientUtil.doGet(url);
        if (hr.getState() == 200) {
            Map<String, Object> map = JsonUtils.fromJson(hr.getBody(), new TypeReference<Map<String, Object>>() {
            });
            boolean subscribe = Integer.valueOf(1).equals(map.get("subscribe"));
            ce.setSubscribe(subscribe);
            if (subscribe) {
                //仅在关注后才能拉取到用户详细信息
                ce.setNickname(String.valueOf(map.get("nickname")));
                if (Integer.valueOf(2).equals(map.get("sex"))) {
                    ce.setSex("女");
                } else {
                    ce.setSex("男");
                }
            }
            return ce;
        }

        throw new SocketException("request fail state->"+hr.getState());
    }

    @Override
    public String getAuthUrl(String state) {
        try {
            if (state == null || state.length() > 128 || !state.matches("[a-zA-Z0-9]*")) {
                throw new IllegalArgumentException("state值不合法,value->"+state);
            }
            String baseUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid="+appId+"&redirect_uri="+
                    URLEncoder.encode(domain+"/wechat/auth","utf-8")+
                    "&response_type=code&scope=snsapi_userinfo&state=#[STATE]#wechat_redirect";
            return baseUrl.replace("#[STATE]",state);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        timer.cancel();
    }
}
