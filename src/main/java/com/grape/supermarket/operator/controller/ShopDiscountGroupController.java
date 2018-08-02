package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopDiscountGroupEntity;
import com.grape.supermarket.entity.page.ShopDiscountGroupPageEntity;
import com.grape.supermarket.entity.param.ShopDiscountGroupParam;
import com.grape.supermarket.operator.service.ShopDiscountGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQW on 2017/11/14.
 */
@Controller
@RequestMapping("/operator/shopdiscountgroup")
public class ShopDiscountGroupController {
    @Autowired
    private ShopDiscountGroupService shopDiscountGroupService;

    @RequestMapping("/insertSelective")
    @ResponseBody
    public int insertSelective(HttpServletRequest request, HttpServletResponse response,ShopDiscountGroupEntity param) {
        int t =  shopDiscountGroupService.insertSelective(param);
        return t;
    }

    @RequestMapping("/selectParam")
    @ResponseBody
    public List<ShopDiscountGroupPageEntity> selectParam(HttpServletRequest request, HttpServletResponse response, String page, String req) {
        List<ShopDiscountGroupPageEntity> list = new ArrayList<>();
        try {
            ShopDiscountGroupParam entity = JsonUtils.fromJson(req,ShopDiscountGroupParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  shopDiscountGroupService.selectParam(entity,p);
        }catch (Exception e){
            return null;
        }

        return list;
    }
    @RequestMapping("/countShopDisGroup")
    @ResponseBody
    public int countShopDisGroup(HttpServletRequest request, HttpServletResponse response,ShopDiscountGroupParam param) {
        int count =  shopDiscountGroupService.countShopDisGroup(param);
        return count;
    }

    @RequestMapping("/deleteByPrimaryKey")
    @ResponseBody
    public int deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response,Integer id) {
        int t =  shopDiscountGroupService.deleteByPrimaryKey(id);
        return t;
    }

    @RequestMapping("/selectByPrimaryKey")
    @ResponseBody
    public ShopDiscountGroupPageEntity selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response,Integer id) {
        return shopDiscountGroupService.selectByPrimaryKey(id);
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    @ResponseBody
    public int updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response,ShopDiscountGroupEntity param) {
        int t =  shopDiscountGroupService.updateByPrimaryKeySelective(param);
        return t;
    }
}
