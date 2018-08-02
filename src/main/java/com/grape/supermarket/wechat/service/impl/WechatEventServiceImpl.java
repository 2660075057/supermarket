package com.grape.supermarket.wechat.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.util.HashUtil;
import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.common.util.XmlUtils;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.entity.WechatUserInfo;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.event.WechatLocationEvent;
import com.grape.supermarket.operator.service.CustomerService;
import com.grape.supermarket.operator.service.OperatorService;
import com.grape.supermarket.wechat.service.WechatBarcodeService;
import com.grape.supermarket.wechat.service.WechatEventService;
import com.grape.supermarket.wechat.service.WechatService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/10/23.
 */
@Service
public class WechatEventServiceImpl extends BasicService implements WechatEventService {
    private Logger logger = Logger.getLogger(getClass());
    private final String pwdSalt;
    private final String domain;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private WechatBarcodeService wechatBarcodeService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private OperatorDao operatorDao;

    public WechatEventServiceImpl() {
        PropertiesLoader pl = new PropertiesLoader("config.properties");
        pwdSalt = pl.getProperty("pwd.salt");
        PropertiesLoader pl2 = new PropertiesLoader("wechat.properties");
        domain = pl2.getProperty("domain");
    }

    @Override
    public String messageRoute(String body) {
        Map<String, Object> map = XmlUtils.toMap(body, "utf-8");
        Object messageType = map.get("MsgType");
        if ("event".equals(messageType)) {
            Object event = map.get("Event");
            if ("SCAN".equals(event)) {
                return scanBarcode(map);
            } else if ("subscribe".equals(event)) {
                return subscribe(map);
            } else if ("LOCATION".equals(event)) {
                return location(map);
            } else if ("CLICK".equals(event)) {
                return click(map);
            }
        } else if ("text".equals(messageType)) {
            String content = String.valueOf(map.get("Content"));
            if(content.startsWith("bind_operator")){
                return bindAccount(map);
            }else if(content.startsWith("unbind_operator")){
                return unbindAccount(map);
            }
        }
        return "success";
    }

    private String click(Map<String, Object> body) {
        if ("real_name_auth".equals(body.get("EventKey"))) {//实名认证按钮
            String openId = String.valueOf(body.get("FromUserName"));
            String myId = String.valueOf(body.get("ToUserName"));
            return getAuthMessage(myId, openId);
        }
        return "success";
    }

    /**
     * 扫描带参数二维码
     *
     * @param body body
     * @return 处理结果
     */
    private String scanBarcode(Map<String, Object> body) {
        String ticket = (String) body.get("Ticket");
        return wechatBarcodeService.actionTicket(ticket, body);
    }

    private String subscribe(Map<String, Object> body) {
        String openId = String.valueOf(body.get("FromUserName"));
        String myId = String.valueOf(body.get("ToUserName"));
        WechatUserInfo userInfo;
        try {
            userInfo = wechatService.getUserInfo(openId);
        } catch (IOException e) {
            userInfo = new WechatUserInfo();
            userInfo.setOpenid(openId);
            userInfo.setNickname(StringUtils.randomStr(10));
        }
        if (userInfo != null) {
            try {
                CustomerEntity ce = new CustomerEntity();
                PropertyUtils.copyProperties(ce, userInfo);
                CustomerEntity customer = customerService.addCustomer(ce);
                if (customer.getPhone() == null) {
                    return getAuthMessage(myId, openId);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error("转换微信用户详细到数据库用户详细时出错");
            }
        }
        return "success";
    }

    private String location(Map<String, Object> body) {
        try {
            WechatLocationEvent.LocationData locationData = new WechatLocationEvent.LocationData();
            locationData.setOpenId((String) body.get("FromUserName"));
            locationData.setLatitude(Double.valueOf((String) body.get("Latitude")));
            locationData.setLongitude(Double.valueOf((String) body.get("Longitude")));
            locationData.setPrecision(Double.valueOf((String) body.get("Precision")));
            locationData.setCreateTime(System.currentTimeMillis());
            applicationEventPublisher.publishEvent(new WechatLocationEvent(locationData));
        } catch (NumberFormatException e) {
            logger.error("转换微信消息到用户地理位置失败", e);
        }
        return "success";
    }

    private String bindAccount(Map<String, Object> body){
        String content = String.valueOf(body.get("Content"));
        String openId = String.valueOf(body.get("FromUserName"));
        String myId = String.valueOf(body.get("ToUserName"));
        String[] param = content.split(" ");
        if(param.length > 2){
            OperatorEntity p = new OperatorEntity();
            p.setOpenId(openId);
            boolean empty = operatorService.selectOperator(p).isEmpty();
            if(!empty){
                return getMessageText(myId,openId,"已绑定其他账号，发送unbind_operator快速解绑");
            }

            OperatorEntity p2 = new OperatorEntity();
            p2.setOperAccount(param[1]);
            List<OperatorEntity> opers = operatorService.selectOperator(p2);
            if(!opers.isEmpty()){
                OperatorEntity operator = opers.get(0);
                String pwdMd5 = HashUtil.md5Upper(pwdSalt + param[2]);
                if(pwdMd5 != null && pwdMd5.equals(operator.getOperPwd())){
                    OperatorEntity up = new OperatorEntity();
                    up.setOperId(operator.getOperId());
                    up.setOpenId(openId);
                    int i = operatorDao.updateByPrimaryKeySelective(up);
                    if(i > 0){
                        return getMessageText(myId,openId,"绑定成功");
                    }else{
                        return getMessageText(myId,openId,"绑定失败，请稍后再试");
                    }
                }
                return getMessageText(myId,openId,"绑定失败，用户名或密码错误");
            }
        }
        return getMessageText(myId,openId,"绑定失败，信息格式有误");
    }

    private String unbindAccount(Map<String, Object> body) {
        String openId = String.valueOf(body.get("FromUserName"));
        String myId = String.valueOf(body.get("ToUserName"));
        OperatorEntity p = new OperatorEntity();
        p.setOpenId(openId);
        List<OperatorEntity> operators = operatorService.selectOperator(p);
        if(!operators.isEmpty()){
            OperatorEntity up = new OperatorEntity();
            up.setOperId(operators.get(0).getOperId());
            up.setOpenId("");
            int i = operatorDao.updateByPrimaryKeySelective(up);
            String message = i > 0 ? "解绑成功" : "解绑失败，请稍后再试";
            return getMessageText(myId,openId,message);
        }else {
            return getMessageText(myId,openId,"您还未绑定账号，发送“bind_operator 用户名 密码”快速解绑");
        }
    }

    private String getMessageText(String from,String to,String message){
        return "<xml>" +
                "    <ToUserName>" + to + "</ToUserName>" +
                "    <FromUserName>" + from + "</FromUserName>" +
                "    <CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
                "    <MsgType><![CDATA[text]]></MsgType>" +
                "    <Content><![CDATA["+message+"]]></Content>" +
                "</xml>";
    }

    private String getAuthMessage(String from,String to){
        Map<String,Object> msgData = new HashMap<>();
        msgData.put("ToUserName",to);
        msgData.put("FromUserName",from);
        msgData.put("CreateTime",System.currentTimeMillis());
        msgData.put("MsgType","news");

        List<Map<String,Object>> articleItems = new ArrayList<>();
        articleItems.add(getArticleItem("立即完成注册即可开门", "10秒完成注册，开门体验无人超市",
                domain + "/wechatPage/images/welcome.png",
                domain + "/wechat/attestation?openId=" + to));

        Map<String,Object> articleWarp = new HashMap<>(8);
        articleWarp.put("item",articleItems);

        msgData.put("Articles",articleWarp);
        msgData.put("ArticleCount",articleItems.size());
        System.out.println(XmlUtils.toXml(msgData,"xml"));

        return XmlUtils.toXml(msgData,"xml");
    }

    private Map<String,Object> getArticleItem(String title,String description,String picUrl,String url){
        Map<String,Object> data = new HashMap<>();
        data.put("Title",title);
        data.put("Description",description);
        data.put("PicUrl",picUrl);
        data.put("Url",url);
        return data;
    }
}
