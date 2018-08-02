package com.grape.supermarket.wechat.service;

import java.util.Map;

/**
 * Created by LXP on 2017/10/23.
 */

public interface WechatBarcodeService {
    /**
     * 执行二维码携带的操作
     * @param ticket 二维码ticket
     * @return 微信可识别信息
     */
    String actionTicket(String ticket, Map<String,Object> messageData);

    /**
     * 创建一个永久二维码
     * @param barcodeId 二维码id
     * @return 微信二维码ticket,若创建失败返回null
     */
    String createPermanentCode(int barcodeId);

    class Action {
        private String action;//动作
        private Object data;//携带自定义数据

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
