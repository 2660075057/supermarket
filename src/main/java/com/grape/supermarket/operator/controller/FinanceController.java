package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.page.FinanceDetailPageEntity;
import com.grape.supermarket.entity.page.FinancePageEntity;
import com.grape.supermarket.entity.param.FinanceDetailParam;
import com.grape.supermarket.operator.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQW on 2017/11/11.
 */
@Controller
@RequestMapping("/operator/finance")
public class FinanceController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/financeStatistics")
    @ResponseBody
    public List<FinancePageEntity> financeStatistics(HttpServletRequest request, HttpServletResponse response,FinancePageEntity param) {
        List<FinancePageEntity> list = new ArrayList<>();
        try {
            list =  orderService.financeStatistics(param);
        }catch (Exception e){
            return null;
        }

        return list;
    }

    @RequestMapping("/financeStatisticsDetail")
    @ResponseBody
    public List<FinanceDetailPageEntity> financeStatisticsDetail(HttpServletRequest request, HttpServletResponse response, String page, String req) {
        List<FinanceDetailPageEntity> list = new ArrayList<>();
        try {
            FinanceDetailParam entity = JsonUtils.fromJson(req,FinanceDetailParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  orderService.financeStatisticsDetail(entity,p);
        }catch (Exception e){
            return null;
        }

        return list;
    }

    @RequestMapping("/countStatisticsDetail")
    @ResponseBody
    public int countStatisticsDetail(HttpServletRequest request, HttpServletResponse response,FinanceDetailParam param) {
        int count =  orderService.countStatisticsDetail(param);
        return count;
    }

    @RequestMapping("/exportFinanceStatisticsDetail")
    public void exportFinanceStatisticsDetail(Integer shopId,String startDate,String endDate,HttpServletResponse response){
        if (shopId == null || startDate == null || endDate == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            orderService.exportFinanceStatisticsDetail(shopId,sdf.parse(startDate),sdf.parse(endDate),response);
        } catch (ParseException e) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"time format error");
            } catch (IOException e1) {
            }
        }
    }
}
