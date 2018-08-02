package com.grape.supermarket.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by LXP on 2017/11/27.
 */
@Controller
@RequestMapping("/device")
public class DeviceController {

    @RequestMapping("/welcome")
    public ModelAndView welcome(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("devicePage/welcome");
        String debug = request.getParameter("debug");
        mav.addObject("debug",debug);
        return mav;
    }

    @RequestMapping("/commodity")
    public ModelAndView commodity(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("devicePage/commodityList");
        String debug = request.getParameter("debug");
        mav.addObject("debug",debug);
        return mav;
    }

    @RequestMapping("/payPage")
    public ModelAndView payPage(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("devicePage/payPage");
        String debug = request.getParameter("debug");
        mav.addObject("debug",debug);
        return mav;
    }

    @RequestMapping("/paySuccess")
    public ModelAndView paySuccess(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("devicePage/paySuccess");
        String debug = request.getParameter("debug");
        mav.addObject("debug",debug);
        return mav;
    }

    @RequestMapping("/payFail")
    public ModelAndView payFail(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("devicePage/payFail");
        String debug = request.getParameter("debug");
        mav.addObject("debug",debug);
        return mav;
    }

    @RequestMapping("/detectionCommodity")
    public ModelAndView detectionCommodityList(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("devicePage/detectionCommodityList");
        String debug = request.getParameter("debug");
        mav.addObject("debug",debug);
        return mav;
    }
}
