package com.grape.supermarket.device.controller;

import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.common.util.SignUtil;
import com.grape.supermarket.device.service.BarcodeService;
import com.grape.supermarket.entity.CommodityPriceInfo;
import com.grape.supermarket.entity.PayBarcode;
import com.grape.supermarket.entity.PreOrder;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.operator.service.CountPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by LXP on 2017/10/24.
 */
@Controller
@RequestMapping("/device/commodity")
public class CommodityController {

    private final String secret;
    @Autowired
    private CountPriceService countPriceService;
    @Autowired
    private BarcodeService barcodeService;

    public CommodityController() {
        PropertiesLoader pl = new PropertiesLoader("config.properties");
        secret = pl.getProperty("security.secret");
    }

    @RequestMapping("/epcPrice")
    @ResponseBody
    public ResultBean<Map<String,Object>> commodityPrice(String[] epcs, Integer shopId, HttpServletRequest request, String token) {
        if (shopId == null || epcs == null || epcs.length == 0 || token == null) {
            throw new FailMessageException("params is error");
        }
        SignUtil.verifyThrow(request,secret);
        PreOrder preOrder = countPriceService.createPreOrderByEpc(new HashSet<>(Arrays.asList(epcs)), shopId);

        Map<String,Object> m = new HashMap<>();
        m.put("preOrder", JsonUtils.toJson(preOrder));
        m.put("token",token);
        SignUtil.fillMap(m);
        String sign = SignUtil.sign(m, secret);
        m.put("sign",sign);

        ResultBean<Map<String,Object>> rb = new ResultBean<>();
        rb.setData(m);
        return rb;
    }

    @RequestMapping("/payBarcode")
    @ResponseBody
    public ResultBean<Map<String,Object>> createPayBarcode(String[] epcs, Integer shopId, Integer preferential, Integer payment, HttpServletRequest request, String token) {
        if (preferential == null || payment == null || epcs == null || epcs.length == 0 || payment.compareTo(preferential) <= 0) {
            throw new FailMessageException("params is error");
        }
        SignUtil.verifyThrow(request,secret);

        PreOrder po = new PreOrder();
        po.setPayment(payment);
        po.setPreferential(preferential);
        PayBarcode payBarcode = countPriceService.createPayBarcode(new HashSet<>(Arrays.asList(epcs)), shopId, po);

        Map<String,Object> m = new HashMap<>();
        m.put("payBarcode", JsonUtils.toJson(payBarcode));
        m.put("token",token);
        SignUtil.fillMap(m);
        String sign = SignUtil.sign(m, secret);
        m.put("sign",sign);

        ResultBean<Map<String,Object>> rb = new ResultBean<>();
        rb.setData(m);
        return rb;
    }

    @RequestMapping("/commodityInfo")
    @ResponseBody
    public ResultBean<Map<String,Object>> commodityInfo(String[] epcs,HttpServletRequest request, String token) {
        SignUtil.verifyThrow(request,secret);

        List<CommodityPriceInfo> commoditys = barcodeService.epcToCommodity(Arrays.asList(epcs));

        Map<String,Object> m = new HashMap<>();
        m.put("commoditys", JsonUtils.toJson(commoditys));
        m.put("token",token);
        SignUtil.fillMap(m);
        String sign = SignUtil.sign(m, secret);
        m.put("sign",sign);

        ResultBean<Map<String,Object>> rb = new ResultBean<>();
        rb.setData(m);
        return rb;
    }
}
