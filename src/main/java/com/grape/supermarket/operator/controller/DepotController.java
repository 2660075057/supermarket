package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.DepotEntity;
import com.grape.supermarket.entity.param.DepotParam;
import com.grape.supermarket.operator.service.DepotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LQW on 2017/11/9.
 */
@Controller
@RequestMapping("/operator/depot")
public class DepotController {
    @Autowired
    private DepotService depotService;

    @RequestMapping("/selectByCondition")
    @ResponseBody
    public List<DepotEntity> selectByCondition(HttpServletRequest request, HttpServletResponse response, String page, String req) {
        List<DepotEntity> list = new ArrayList<>();
        try {
            DepotParam entity = JsonUtils.fromJson(req,DepotParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  depotService.selectByCondition(entity,p);
        }catch (Exception e){
            return null;
        }

        return list;
    }
    
    @RequestMapping("/costPrice")
    @ResponseBody
    public ResultBean<Integer> CostPrice(HttpServletRequest request, HttpServletResponse response, String req) {
    	ResultBean<Integer> rb = new ResultBean<>();
    	DepotParam entity = JsonUtils.fromJson(req,DepotParam.class);
    	rb.setData(depotService.costPrice(entity));
        return rb;
    }
    
    @RequestMapping("/countDepotByCondition")
    @ResponseBody
    public int countDepotByCondition(HttpServletRequest request, HttpServletResponse response,DepotParam param) {
        int count =  depotService.countDepotByCondition(param);
        return count;
    }

    @RequestMapping("/selectById")
    @ResponseBody
    public DepotEntity selectById(HttpServletRequest request, HttpServletResponse response,Integer depotId) {
        return depotService.selectById(depotId);
    }

    @RequestMapping("/insertDepot")
    @ResponseBody
    public int insertDepot(HttpServletRequest request, HttpServletResponse response,DepotEntity param) {
        int t =  depotService.insertDepot(param);
        return t;
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public int deleteById(HttpServletRequest request, HttpServletResponse response,Integer depotId) {
        int t =  depotService.deleteById(depotId);
        return t;
    }

    @RequestMapping("/addAmount")
    @ResponseBody
    public int addAmount(HttpServletRequest request, HttpServletResponse response,DepotEntity param) {
        int t =  depotService.addAmount(param);
        return t;
    }

    @RequestMapping("/turnShopId")
    @ResponseBody
    public int turnShopId(HttpServletRequest request, HttpServletResponse response,DepotEntity param) {
        int t =  depotService.turnShopId(param);
        return t;
    }

    @RequestMapping("/setThreshold")
    @ResponseBody
    public int setThreshold(HttpServletRequest request, HttpServletResponse response,DepotEntity param) {
        int t =  depotService.setThreshold(param);
        return t;
    }

    @RequestMapping("/openDepot")
    @ResponseBody
    public int openDepot(HttpServletRequest request, HttpServletResponse response,Integer depotId, Byte state) {
        int t =  depotService.openDepot(depotId,state);
        return t;
    }

    @RequestMapping("/closeDepot")
    @ResponseBody
    public int closeDepot(HttpServletRequest request, HttpServletResponse response,Integer depotId, Byte state) {
        int t =  depotService.closeDepot(depotId,state);
        return t;
    }

    @RequestMapping("/selectByShopIdAanCommId")
    @ResponseBody
    public DepotEntity selectByShopIdAanCommId(HttpServletRequest request, HttpServletResponse response,DepotEntity param) {
        return  depotService.selectByShopIdAanCommId(param);
    }
    
    @RequestMapping("/selectWriteDepot")
    @ResponseBody
    public ResultBean<HashMap<Integer,Object>> selectWriteDepot(HttpSession session,String str,Integer shopid) {
    	ResultBean<HashMap<Integer,Object>> rb = new ResultBean<>();
    	HashMap<Integer,Object> map = depotService.selectDepotCheck(str,shopid);
    	List<DepotEntity> list = (List<DepotEntity>) map.get(0);
    	session.setAttribute("list", list);
    	rb.setData(map);
    	return rb;
    }
    
    @RequestMapping("/importDepot")
    @ResponseBody
    public ResultBean<String> importDepot(HttpSession session) {
    	 ResultBean<String> rb = new ResultBean<>();
    	List<DepotEntity> Delist = new ArrayList<>();
    	Delist = (List<DepotEntity>) session.getAttribute("list");
    	depotService.batchInsertDepot(Delist);
    	rb.setData("success");
		return rb;
    	
    }

}
