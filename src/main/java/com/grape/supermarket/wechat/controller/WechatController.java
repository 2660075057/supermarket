package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.entity.WechatUserInfo;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.operator.service.CustomerService;
import com.grape.supermarket.wechat.WechatSession;
import com.grape.supermarket.wechat.entity.UserAuthData;
import com.grape.supermarket.wechat.service.PayService;
import com.grape.supermarket.wechat.service.WechatEventService;
import com.grape.supermarket.wechat.service.WechatService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by LXP on 2017/9/27.
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {
    private String subscribeUrl;
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatEventService wechatEventService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PayService payService;

    @Autowired
    public WechatController(@Qualifier("wechatProperties") PropertiesLoader pl) {
        subscribeUrl = pl.getProperty("subscribeUrl");
    }

    @RequestMapping(value = "/wechatInterface", method = RequestMethod.GET)
    @ResponseBody
    public String interfaceVerify(String signature, String timestamp, String nonce, String echostr) {
        if (signature == null || timestamp == null || nonce == null || echostr == null) {
            return "error";
        }
        WechatService.VerificationMessage message
                = new WechatService.VerificationMessage(signature, timestamp, nonce, echostr);
        boolean b = wechatService.checkWechatMessage(message);
        return b ? echostr : "error";
    }

    @RequestMapping(value = "/wechatInterface", method = RequestMethod.POST)
    @ResponseBody
    public String receiveMessage(@RequestBody String body) {
        logger.debug("wechatInterfacePost->\n" + body);
        if (body == null || body.isEmpty()) {
            return "error_body";
        }
        return wechatEventService.messageRoute(body);
    }

    @RequestMapping("/auth")
    public String auth(String code, String state, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state)) {
                return "redirect:/wechat/index/error";
            }
            //判断是否需要要跳转微信公众号界面
            WechatUserInfo userInfo;
            try {
                UserAuthData authData = wechatService.getAuthData(code);
                userInfo = wechatService.getUserInfo(authData.getOpenId());
            } catch (IOException e) {
                logger.warn("统一认证获取用户信息失败",e);
                return "redirect:"+subscribeUrl;//关注公众号
            }
            if (!userInfo.isSubscribe()) {
                logger.warn("统一认证"+userInfo+" 未关注公众号");
                return "redirect:"+subscribeUrl;//关注公众号
            }
            CustomerEntity customer = customerService.getCustomerByOpenId(userInfo.getOpenid());
            if (customer == null) {//已经关注，但是系统内没有用户数据
                customer = new CustomerEntity();
                customer.setOpenid(userInfo.getOpenid());
                customer.setNickname(userInfo.getNickname());
                customer.setSex(userInfo.getSex());
                customerService.addCustomer(customer);
            }

            WechatSession wechatSession = new WechatSession(customer);
            HttpSession session = request.getSession();
            session.setAttribute(WechatSession.SESSION_ID, wechatSession);
            if ("mainPage".equals(state)) {//跳转到主界面
                return "redirect:/wechat/index";
            } else if ("nearbyShop".equals(state)) {
                return "redirect:/wechat/shop";
            } else if ("myOrder".equals(state)) {
                return "redirect:/wechat/myOrder";
            } else if(state.startsWith("inShop")){
                if(wechatSession.getPhone() == null){
                    request.getSession().setAttribute("attestation","/wechat/shop/inShopPage?state="+state);
                    return "redirect:/wechat/attestation";
                }
                return "redirect:/wechat/shop/inShopPage?state="+state;
            } else if(state.startsWith("outShop")){
                if(wechatSession.getPhone() == null){
                    request.getSession().setAttribute("attestation","/wechat/shop/outShopPage?state="+state);
                    return "redirect:/wechat/index/attestation";
                }
                return "redirect:/wechat/shop/outShopPage?state="+state;
            }

            return "redirect:/wechatPage/main.jsp";//默认跳转主界面
        } catch (Exception e) {//异常记录日志并跳转到404页面
            logger.error("wechat auth error",e);
            return "redirect:/wechat/index/error";
        }
    }

    @RequestMapping(value = "/payNotify", method = RequestMethod.POST)
    @ResponseBody
    public String payNotify(@RequestBody String body) {
        return payService.noticeCheck(body);
    }

}
