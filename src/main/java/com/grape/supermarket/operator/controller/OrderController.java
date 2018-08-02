package com.grape.supermarket.operator.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.page.OrderDetailPageEntity;
import com.grape.supermarket.entity.page.OrderPageEntity;
import com.grape.supermarket.entity.param.OrderParam;
import com.grape.supermarket.operator.service.OrderDetailService;
import com.grape.supermarket.operator.service.OrderService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * author huanghuang
 * 2017年11月17日 上午11:54:36
 */
@Controller
@RequestMapping("/operator/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/queryOrderList")
    @ResponseBody
    public ResultBean<List<OrderPageEntity>> queryOrderList(String param, String page, String date) {
    	OrderParam op = JsonUtils.fromJson(param, OrderParam.class);
    	PageParam p = JsonUtils.fromJson(page, PageParam.class);
    	if(StringUtils.isNotBlank(date)&&date.indexOf("~")>-1){
    		String[] dateArr = date.split("~");
    		op.setStartTime(strToDate(dateArr[0].trim()));
    		op.setEndTime(strToDate(dateArr[1].trim()));
    	}
    	op.setState((byte) 1);
    	ResultBean<List<OrderPageEntity>> rb = new ResultBean<List<OrderPageEntity>>();
    	List<OrderPageEntity> rows = orderService.selectOrderByParam(op, p);//得到分页的所有列
    	rb.setData(rows);
        return rb;
    }
    
    @RequestMapping("/queryOrderListCount")
    @ResponseBody
    public ResultBean<Integer> queryOrderListCount(String param, String date) {
    	OrderParam op = JsonUtils.fromJson(param, OrderParam.class);
    	if(StringUtils.isNotBlank(date)&&date.indexOf("~")>-1){
    		String[] dateArr = date.split("~");
    		op.setStartTime(strToDate(dateArr[0].trim()));
    		op.setEndTime(strToDate(dateArr[1].trim()));
    	}
    	op.setState((byte) 1);
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int total = orderService.countByParam(op);//得到记录的总条数
    	rb.setData(total);
    	return rb;
    }
    
    @RequestMapping(value="/detail",method=RequestMethod.GET)  
    public ModelAndView detail(Integer orderId){  
        ModelAndView modelAndView = new ModelAndView();
        OrderPageEntity orderPage = orderService.selectByPrimaryKey(orderId);
        modelAndView.addObject("orderPage", orderPage);
        modelAndView.setViewName("/operator/detail_order");  
        return modelAndView;  
    }
   
    @RequestMapping("/queryDetail")
    @ResponseBody
    public ResultBean<List<OrderDetailPageEntity>> queryOneOrder(Integer orderId) {
    	ResultBean<List<OrderDetailPageEntity>> rb = new ResultBean<List<OrderDetailPageEntity>>();
    	List<OrderDetailPageEntity> odpes = orderDetailService.selectOrderDetailByOrderId(orderId);//得到记录的总条数
    	rb.setData(odpes);
    	return rb;
    }
    
    private static Date strToDate(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = null;
	   try {
		   date = format.parse(str);
	   } catch (ParseException e) {
		   e.printStackTrace();
	   }
	   return date;
	}
    
    @RequestMapping("/downLoad")
    public void downLoad(OrderParam op, String date,Integer shopId, HttpServletResponse response) {
    	if(shopId != null){
    		op.setShopId(shopId);
    	}
    	op.setState((byte) 1);
    	if(StringUtils.isNotBlank(date)&&date.indexOf("~")>-1){
    		String[] dateArr = date.split("~");
    		op.setStartTime(strToDate(dateArr[0].trim()));
    		op.setEndTime(strToDate(dateArr[1].trim()));
    	}
    	orderService.exportSaleStatisticsDetail(op,response);
    }
}
