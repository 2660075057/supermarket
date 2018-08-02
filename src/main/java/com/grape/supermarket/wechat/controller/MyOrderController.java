package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.page.OrderPageEntity;
import com.grape.supermarket.operator.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by LXP on 2017/11/16.
 */
@Controller
@RequestMapping("/wechat/myOrder")
public class MyOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping
    public ModelAndView main(){
        ModelAndView mav = new ModelAndView("/wechatPage/myOrder");
        PageParam page = new PageParam();
        page.setPageSize(10);
        page.setPageCurrent(1);
        List<OrderPageEntity> list = orderService.wechatSelectOrderByCoustId(page);
        mav.addObject("orderList",list);
        return mav;
    }

    @RequestMapping("/orderListParam")
    @ResponseBody
    public List<OrderPageEntity> shopListParam(PageParam page){
        List<OrderPageEntity> list = orderService.wechatSelectOrderByCoustId(page);
        return list;
    }
    @RequestMapping("/wechatDeleteById")
    @ResponseBody
    public ResultBean<Boolean> wechatDeleteById(Integer orderId){
        boolean bl = orderService.wechatDeleteById(orderId);
        ResultBean<Boolean> rb = new ResultBean<>();
        rb.setData(bl);
        return rb;
    }
}
