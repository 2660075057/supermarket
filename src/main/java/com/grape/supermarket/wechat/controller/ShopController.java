package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.common.WechatSessionHelper;
import com.grape.supermarket.common.exception.WechatFailPageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.device.service.AccessControlService;
import com.grape.supermarket.entity.AccessEntity;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.ShopPageEntity;
import com.grape.supermarket.entity.param.ShopParam;
import com.grape.supermarket.event.WechatLocationEvent;
import com.grape.supermarket.operator.service.CustomerService;
import com.grape.supermarket.operator.service.ShopService;
import com.grape.supermarket.wechat.WechatSession;
import com.grape.supermarket.wechat.service.WechatLocaltionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/11/16.
 */
@Controller("WechatShopController")
@RequestMapping("/wechat/shop")
public class ShopController {
    private Logger logger = Logger.getLogger(getClass());
    private final int inShopTimeout = 10_000;
    private final int outShopTimeout = 10_000;

    @Autowired
    private WechatLocaltionService wechatLocaltionService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccessControlService accessControlService;

    @RequestMapping
    public ModelAndView main() {
        String openid = WechatSessionHelper.getSessionOrThrow().getOpenid();
        Double lon = null;
        Double lat = null;
        try {
            WechatLocationEvent.LocationData locationData = wechatLocaltionService.wechatUserLocation(openid);
            if (locationData != null) {
                logger.info("获取用户经纬度信息=======》" + locationData.getLatitude() + "|" + locationData.getLongitude());
                lon = locationData.getLongitude();
                lat = locationData.getLatitude();
            }
        } catch (Exception e) {
            lon = null;
            lat = null;
        }
        ShopParam param = new ShopParam();
        PageParam page = new PageParam();
        page.setPageSize(11);
        page.setPageCurrent(1);
        List<ShopPageEntity> list = shopService.shopList(param, page, 10, lon, lat);
        logger.info("返回站点信息===========" + list.size());
        ModelAndView mav = new ModelAndView("/wechatPage/shop");
        mav.addObject("shopList", list);
        return mav;
    }

    @RequestMapping("/shopListParam")
    @ResponseBody
    public List<ShopPageEntity> shopListParam(String page, String req) {
        String openid = WechatSessionHelper.getSessionOrThrow().getOpenid();
        Double lon = null;
        Double lat = null;
        try {
            WechatLocationEvent.LocationData locationData = wechatLocaltionService.wechatUserLocation(openid);
            if (locationData != null) {
                lon = locationData.getLongitude();
                lat = locationData.getLatitude();
            }
        } catch (Exception e) {
            lon = null;
            lat = null;
        }
        ShopParam entity = JsonUtils.fromJson(req, ShopParam.class);
        PageParam p = JsonUtils.fromJson(page, PageParam.class);
        List<ShopPageEntity> list = shopService.shopList(entity, p, 10, lon, lat);
        return list;
    }

    @RequestMapping("/inShopPage")
    public ModelAndView inShop(String state, HttpSession session) {
        return shopVisit(state, session, 0);
    }

    @RequestMapping("/outShopPage")
    public ModelAndView outShopPage(String state, HttpSession session) {
        return shopVisit(state, session, 1);
    }

    private ModelAndView shopVisit(String state, HttpSession session, int flag) {
        try {
            WechatSessionHelper.getSessionOrThrow();
            ModelAndView mav = new ModelAndView();
            if (flag == 0) {
                mav.addObject("timeout", inShopTimeout);
                mav.setViewName("/wechatPage/access/inShop");
            } else {
                mav.addObject("timeout", outShopTimeout);
                mav.setViewName("/wechatPage/access/outShop");
            }

            String[] args = state.split("VITIS");
            ShopEntity shop = shopService.selectShopById(Integer.valueOf(args[1]));
            if (shop != null) {
                session.setAttribute("shopId", args[1]);
                mav.addObject("state", state);
                return mav;
            } else {
                throw new IllegalArgumentException("shop not found");
            }
        } catch (Exception e) {
            throw new WechatFailPageException(e, false);
        }
    }

    @RequestMapping("/visit")
    @ResponseBody
    public ResultBean<Integer> visit(Boolean flag, HttpSession session) {
        WechatSession wechatSession = WechatSessionHelper.getSessionOrThrow();
        Map<String, Object> info = new HashMap<>();
        info.put("openId", wechatSession.getOpenid());

        ResultBean<Integer> rb = new ResultBean<>();
        Object obj = session.getAttribute("shopId");
        if (flag != null && obj != null) {
            Integer shopId = Integer.valueOf(obj.toString());
            AccessControlService.Access_Code access_code;
            if (flag) {
                AccessEntity access = new AccessEntity();
                access.setTimeout(inShopTimeout);//开门超时响应
                access.setType(1);
                access.setShopId(shopId);
                access.setInfo(info);
                session.setAttribute(AccessEntity.SESSION_ID, access);
                access_code = accessControlService.addInAccess(access);
                try {
                    customerService.addShopLog(wechatSession.getCoustId(), shopId, 0, (byte) 0);
                } catch (Exception e) {
                    logger.warn("添加顾客进店日志发生异常", e);
                }
            } else {
                AccessEntity access = new AccessEntity();
                access.setTimeout(outShopTimeout);//开门超时响应
                access.setType(1);
                access.setShopId(shopId);
                access.setInfo(info);
                session.setAttribute(AccessEntity.SESSION_ID, access);
                access_code = accessControlService.addOutAccess(access);
                try {
                    customerService.addShopLog(wechatSession.getCoustId(), shopId, 1, (byte) 0);
                } catch (Exception e) {
                    logger.warn("添加顾客出店日志发生异常", e);
                }
            }
            rb.setMessage(access_code.toString());
            if (access_code == AccessControlService.Access_Code.Ready) {
                rb.setData(1);
            } else if (access_code == AccessControlService.Access_Code.Running) {
                rb.setData(2);
            } else {
                rb.setData(3);
            }

        }
        return rb;
    }

    @RequestMapping("/visitState")
    @ResponseBody
    public ResultBean<AccessEntity> visiState(HttpSession session) {
        ResultBean<AccessEntity> rb = new ResultBean<>();
        AccessEntity ae = (AccessEntity) session.getAttribute(AccessEntity.SESSION_ID);
        rb.setData(ae);
        return rb;
    }

}
