package com.grape.supermarket.device.controller;

import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.common.util.SignUtil;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.OrderEntity;
import com.grape.supermarket.operator.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/11/27.
 */
@Controller
@RequestMapping("/device/pay")
public class PayController {
    private String secret;
    @Autowired
    private OrderService orderService;

    public PayController() {
        PropertiesLoader pl = new PropertiesLoader("config.properties");
        secret = pl.getProperty("security.secret");
    }

    @RequestMapping("/state")
    @ResponseBody
    public ResultBean<Map<String,Object>> state(String paymentId, HttpServletRequest request, String token){
        if(StringUtils.isEmpty(paymentId) || token == null){
            throw new FailMessageException("paymentId is null");
        }
        SignUtil.verifyThrow(request,secret);
        OrderEntity order = orderService.selectByPaymentId(paymentId);

        ResultBean<Map<String,Object>> rb = new ResultBean<>();
        if(order != null){
            Map<String,Object> m = new HashMap<>();
            m.put("orderState",order.getState());
            m.put("token",token);
            String sign = SignUtil.sign(m, secret);
            m.put("sign",sign);

            rb.setData(m);
        }else{
            rb.setMessage("not found order");
            rb.setCode(ResultBean.FAIL);
        }

        return rb;
    }

    @RequestMapping("/cancel")
    @ResponseBody
    public ResultBean<Map<String,Object>> cancel(String paymentId,HttpServletRequest request, String token){
        if(StringUtils.isEmpty(paymentId)){
            throw new FailMessageException("paymentId is null");
        }
        SignUtil.verifyThrow(request,secret);

        boolean b = orderService.cancelOrder(paymentId);

        Map<String,Object> m = new HashMap<>();
        m.put("state",b);
        m.put("token",token);
        String sign = SignUtil.sign(m, secret);
        m.put("sign",sign);

        ResultBean<Map<String,Object>> rb = new ResultBean<>();
        rb.setData(m);
        return rb;
    }
}
