package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.entity.WechatBanner;
import com.grape.supermarket.operator.service.WechatConfigService;
import com.grape.supermarket.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by LXP on 2017/11/16.
 * 此过滤器不可调用WechatSessionHelper方法
 */
@Controller
@RequestMapping("/wechat/index")
public class IndexController {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatConfigService wechatConfigService;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request) {
        List<WechatBanner> banners = wechatConfigService.getWechatBanner();
        ModelAndView mav = new ModelAndView("/wechatPage/main");
        String contextPath = request.getContextPath();
        if (!contextPath.equals("/")) {
            for (WechatBanner banner : banners) {
                String imgUrl = banner.getImgUrl();
                if (imgUrl != null && !imgUrl.startsWith("http")) {
                    banner.setImgUrl(contextPath + imgUrl);
                }
            }
        }
        mav.addObject("banners", banners);
        return mav;
    }

    @RequestMapping("/authJump/{code}")
    public ModelAndView authJump(@PathVariable("code") String code) {
        String authUrl = wechatService.getAuthUrl(code);
        return new ModelAndView("redirect:" + authUrl);
    }

    @RequestMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("wechatPage/404");
    }
}
