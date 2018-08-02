package com.grape.supermarket.operator.controller;

import com.alibaba.fastjson.JSONObject;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.CommodityTypeEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.operator.service.CommodityTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2017/11/2.
 */
@Controller
@RequestMapping("/operator/commoditytype")
public class CommodityTypeController {

    @Autowired
    private CommodityTypeService commodityTypeService;

    @RequestMapping("/commodityTree")
    @ResponseBody
    public String commodityTree(HttpServletRequest request, HttpServletResponse response, String req) {
        List<CommodityTypePageEntity> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        String str = "";
        try {
            CommodityTypeParam param = JsonUtils.fromJson(req,CommodityTypeParam.class);
            list =  commodityTypeService.commodityTree(param);
            
            map.put("rows", list);
            map.put("total", list.size());
            str  = JSONObject.toJSONString(map);
           
        }catch (Exception e){
            return null;
        }
		return str;

    }
    
    @RequestMapping("/commodityTypeList")
    @ResponseBody
    public List<CommodityTypePageEntity> commodityTypeList(HttpServletRequest request, HttpServletResponse response, String page, String req) {
        List<CommodityTypePageEntity> list = new ArrayList<>();
        try {
            CommodityTypeParam param = JsonUtils.fromJson(req,CommodityTypeParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  commodityTypeService.commodityTypeList(param,p);
        }catch (Exception e){
            return null;
        }

        return list;
    }

    @RequestMapping("/countByParam")
    @ResponseBody
    public int countByParam(HttpServletRequest request, HttpServletResponse response,CommodityTypeParam param) {
        int count =  commodityTypeService.countByParam(param);
        return count;
    }

    @RequestMapping("/addCommodityType")
    @ResponseBody
    public boolean addCommodityType(HttpServletRequest request, HttpServletResponse response,String req, String attrIds) {
        boolean flg = false;
        try {
            CommodityTypeParam param = JsonUtils.fromJson(req,CommodityTypeParam.class);
            List<Integer> list = new ArrayList<>();
            String[] sarr  = attrIds.split(",");
            for (int i=0,z=sarr.length;i<z;i++){
                list.add(Integer.parseInt(sarr[i]));
            }
            flg =  commodityTypeService.addCommodityType(param,list);
        }catch (Exception e){
            return flg;
        }
        return flg;
    }

    @RequestMapping("/deleteCommodityType")
    @ResponseBody
    public int deleteCommodityType(HttpServletRequest request, HttpServletResponse response,Integer typeId) {
        int count = commodityTypeService.deleteCommodityType(typeId);
        if(count == 0){
            List<CommodityTypeEntity> list = commodityTypeService.selectByMasterId(typeId);
            if(list != null){
                for(CommodityTypeEntity entity : list){
                    entity.setMasterId(0);
                    commodityTypeService.updateCommodityType(entity,null);
                }
            }
        }
        return count;
    }

    @RequestMapping("/updateCommodityType")
    @ResponseBody
    public boolean updateCommodityType(HttpServletRequest request, HttpServletResponse response,String req, String attrIds) {
        boolean flg = false;
        try {
            CommodityTypeParam param = JsonUtils.fromJson(req,CommodityTypeParam.class);
            List<Integer> list = new ArrayList<>();
            String[] sarr  = attrIds.split(",");
            for (int i=0,z=sarr.length;i<z;i++){
                list.add(Integer.parseInt(sarr[i]));
            }
            flg =  commodityTypeService.updateCommodityType(param,list);
        }catch (Exception e){
            return flg;
        }
        return flg;
    }

}
