package com.grape.supermarket.operator.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.CustomerPageEntity;
import com.grape.supermarket.entity.page.OrderDetailPageEntity;
import com.grape.supermarket.entity.param.CustomerParam;
import com.grape.supermarket.operator.service.CustomerService;

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
 * 2017年11月1日 下午3:24:10
 */
@Controller
@RequestMapping("/operator/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/queryCustomer")
    @ResponseBody
    public ResultBean<List<CustomerPageEntity>> queryCustomer(String param, String page) {
    	CustomerParam cp = JsonUtils.fromJson(param, CustomerParam.class);
    	PageParam p = JsonUtils.fromJson(page, PageParam.class);
    	ResultBean<List<CustomerPageEntity>> rb = new ResultBean<List<CustomerPageEntity>>();
    	List<CustomerPageEntity> rows = customerService.customerList(cp, p);//得到分页的所有列
    	rb.setData(rows);
        return rb;
    }
    
    @RequestMapping("/queryCustomerCount")
    @ResponseBody
    public ResultBean<Integer> queryCustomerCount(String param) {
    	CustomerParam cp = JsonUtils.fromJson(param, CustomerParam.class);
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int total = customerService.countCustomer(cp);//得到记录的总条数
    	rb.setData(total);
    	return rb;
    }
    
    @RequestMapping(value="/detail",method=RequestMethod.GET)  
    public ModelAndView detail(){  
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/operator/detail_customer");  
        return modelAndView;  
    }
    
    @RequestMapping("/queryDetail")
    @ResponseBody
    public ResultBean<CustomerPageEntity> queryOneOperator(Integer coustId, String date) {
    	Date start = new Date();
        Calendar c = Calendar.getInstance();  
        c.setTime(start);  
        c.add(Calendar.DAY_OF_MONTH, 7);// 今天+7天  
    	Date end = c.getTime();
    	if(StringUtils.isNotBlank(date)&&date.indexOf("~")>-1){
    		String[] dateArr = date.split("~");
    		start = strToDate(dateArr[0].trim());
    		end = strToDate(dateArr[1].trim());
    	}
    	ResultBean<CustomerPageEntity> rb = new ResultBean<CustomerPageEntity>();
    	CustomerPageEntity customer = customerService.selectByPrimaryKey(coustId, start, end);//得到记录的总条数
    	rb.setData(customer);
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
}
