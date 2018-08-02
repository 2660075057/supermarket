package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopCommodityEntity;
import com.grape.supermarket.entity.db.ShopCommodityKey;
import com.grape.supermarket.entity.page.ShopCommodityPageEntity;
import com.grape.supermarket.entity.param.ShopCommodityParam;
import com.grape.supermarket.operator.service.ShopCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQW on 2017/11/1.
 */
@Controller
@RequestMapping("/operator/shopcommodity")
public class ShopCommodityController {
    @Autowired
    private ShopCommodityService shopCommodityService;


    @RequestMapping("/selectShopCommListParam")
    @ResponseBody
    public List<ShopCommodityPageEntity> selectShopCommListParam(HttpServletRequest request, HttpServletResponse response,String page, String req) {
        List<ShopCommodityPageEntity> list = new ArrayList<>();
        try {
            ShopCommodityParam shopCommodityParam = JsonUtils.fromJson(req,ShopCommodityParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  shopCommodityService.selectShopCommListParam(shopCommodityParam,p);
        }catch (Exception e){
            return null;
        }

        return list;
    }
    @RequestMapping("/countShopCommList")
    @ResponseBody
    public int countShopCommList(HttpServletRequest request, HttpServletResponse response, ShopCommodityParam param) {
        int count =  shopCommodityService.countShopCommList(param);
        return count;
    }

    @RequestMapping("/selectByPrimaryKey")
    @ResponseBody
    public ShopCommodityPageEntity selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response, ShopCommodityKey param) {
        ShopCommodityPageEntity shopCommodityPageEntity =  shopCommodityService.selectByPrimaryKey(param);
        return shopCommodityPageEntity;
    }

    @RequestMapping("/insertSelective")
    @ResponseBody
    public int insertSelective(HttpServletRequest request, HttpServletResponse response, ShopCommodityEntity param) {
        int t =  shopCommodityService.insertSelective(param);
        return t;
    }

    @RequestMapping("/deleteByPrimaryKey")
    @ResponseBody
    public int deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response, ShopCommodityKey param) {
        int t =  shopCommodityService.deleteByPrimaryKey(param);
        return t;
    }

    @RequestMapping("/updatePrice")
    @ResponseBody
    public int updatePrice(HttpServletRequest request, HttpServletResponse response, ShopCommodityEntity param) {
        int t =  shopCommodityService.updatePrice(param);
        return t;
    }
}
