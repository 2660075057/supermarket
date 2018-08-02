package com.grape.supermarket.wechat.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.util.HttpClientUtil;
import com.grape.supermarket.common.util.HttpResponce;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.wechat.service.WechatApiUrl;
import com.grape.supermarket.wechat.service.WechatBarcodeService;
import com.grape.supermarket.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by LXP on 2017/10/23.
 */
@Service
public class WechatBarcodeServiceImpl implements WechatBarcodeService {

    @Autowired
    private WechatService wechatService;

    private ConcurrentMap<String, Action> temporaryBarcode = new ConcurrentHashMap<>();

    @Override
    public String actionTicket(String ticket, Map<String, Object> messageData) {
        Action action = temporaryBarcode.get(ticket);
        if (action == null) {
//            ShopParam param = new ShopParam();
//            param.setBarcodeTicket(ticket);
//            List<ShopEntity> shops = shopDao.selectByParam(param);
//            if (!shops.isEmpty()) {//进店二维码
//                ShopEntity shop = shops.get(0);
//                if (shop.getState() != 0) {
//                    return "<xml>" +
//                            "    <ToUserName>" + messageData.get("FromUserName") + "</ToUserName>" +
//                            "    <FromUserName>" + messageData.get("ToUserName") + "</FromUserName>" +
//                            "    <CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
//                            "    <MsgType><![CDATA[text]]></MsgType>" +
//                            "    <Content><![CDATA[该商店暂时无法提供服务]]></Content>" +
//                            "</xml>";
//                }
//                accessControlService.addAccess(shop.getShopId());
//            }
            //微信二维码
            return "success";
        } else {//有action动作设置的临时二维码
            return "success";
        }
    }

    @Override
    public String createPermanentCode(int barcodeId) {
        if (barcodeId < 1 || barcodeId > 10_0000) {
            throw new IllegalArgumentException("barcodeId必须在1-100000之间");
        }
        String token = null;
        try {
            token = wechatService.getAccessToken();
        } catch (IOException e) {
            return null;
        }
        String url = WechatApiUrl.createBarcode + "?access_token=" + token;
        HttpResponce hr = HttpClientUtil.doPost(url, "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + barcodeId + "}}}", "UTF-8");
        if (hr.getState() == 200) {
            Map<String, Object> data = JsonUtils.fromJson(hr.getBody(), new TypeReference<Map<String, Object>>() {
            });
            return (String) data.get("ticket");
        }
        return null;
    }
}
