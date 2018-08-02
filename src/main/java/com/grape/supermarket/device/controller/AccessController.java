package com.grape.supermarket.device.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.common.util.SignUtil;
import com.grape.supermarket.device.service.AccessControlService;
import com.grape.supermarket.entity.AccessResult;
import com.grape.supermarket.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/10/25.
 */
@Controller
@RequestMapping("/device/access")
public class AccessController {

    @Autowired
    private AccessControlService accessControlService;

    private final String secret;

    public AccessController() {
        PropertiesLoader pl = new PropertiesLoader("config.properties");
        secret = pl.getProperty("security.secret");
    }

    @RequestMapping("/inShopVisit")
    @ResponseBody
    public ResultBean<Map<String, Object>> inShopVisit(HttpServletRequest request, Integer shopId, String token) {
        ResultBean<Map<String, Object>> rb = new ResultBean<>();
        if (shopId == null || token == null) {
            rb.setCode(ResultBean.FAIL);
            rb.setMessage("FAIL");
        } else {
            SignUtil.verifyThrow(request, secret);
            AccessResult accessResult = accessControlService.checkInAccess(shopId);
            Map<String, Object> m = new HashMap<>();
            m.put("visit", accessResult.isAccess());
            m.put("accessResultJson", JsonUtils.toJson(accessResult));
            m.put("token", token);
            SignUtil.fillMap(m);
            String sign = SignUtil.sign(m, secret);
            m.put("sign", sign);
            rb.setData(m);
        }

        return rb;
    }

    @RequestMapping("/outShopVisit")
    @ResponseBody
    public ResultBean<Map<String, Object>> outShopVisit(HttpServletRequest request, Integer shopId, String token) {
        ResultBean<Map<String, Object>> rb = new ResultBean<>();
        if (shopId == null || token == null) {
            rb.setCode(ResultBean.FAIL);
            rb.setMessage("FAIL");
        } else {
            SignUtil.verifyThrow(request, secret);
            AccessResult accessResult = accessControlService.checkOutAccess(shopId);
            Map<String, Object> m = new HashMap<>();
            m.put("visit", accessResult.isAccess());
            m.put("accessResultJson",JsonUtils.toJson(accessResult));
            m.put("token", token);
            SignUtil.fillMap(m);
            String sign = SignUtil.sign(m, secret);
            m.put("sign", sign);
            rb.setData(m);
        }

        return rb;
    }

    @RequestMapping("/inShopInit")
    @ResponseBody
    public ResultBean<Map<String, Object>> inShopInit(HttpServletRequest request, Integer shopId, String token) {
        ResultBean<Map<String, Object>> rb = new ResultBean<>();
        if (shopId == null || token == null) {
            rb.setCode(ResultBean.FAIL);
            rb.setMessage("FAIL");
        } else {
            SignUtil.verifyThrow(request, secret);
            accessControlService.inAccessInit(shopId);
            Map<String, Object> m = new HashMap<>();
            m.put("state", true);
            m.put("token", token);
            SignUtil.fillMap(m);
            String sign = SignUtil.sign(m, secret);
            m.put("sign", sign);
            rb.setData(m);
        }

        return rb;
    }

    @RequestMapping("/outShopInit")
    @ResponseBody
    public ResultBean<Map<String, Object>> outShopInit(HttpServletRequest request, Integer shopId, String token) {
        ResultBean<Map<String, Object>> rb = new ResultBean<>();
        if (shopId == null || token == null) {
            rb.setCode(ResultBean.FAIL);
            rb.setMessage("FAIL");
        } else {
            SignUtil.verifyThrow(request, secret);
            accessControlService.outAccessInit(shopId);
            Map<String, Object> m = new HashMap<>();
            m.put("state", true);
            m.put("token", token);
            SignUtil.fillMap(m);
            String sign = SignUtil.sign(m, secret);
            m.put("sign", sign);
            rb.setData(m);
        }

        return rb;
    }
}
