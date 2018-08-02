package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.DepotPageEntity;
import com.grape.supermarket.entity.param.DepotParam;
import com.grape.supermarket.operator.service.DepotService;
import com.grape.supermarket.operator.service.ShopService;
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
@RequestMapping("/wechat/goods")
public class GoodsController {

    @Autowired
    DepotService depotService;
    @Autowired
    ShopService shopService;

    @RequestMapping
    public ModelAndView index(Integer shopId){
        ModelAndView mav = new ModelAndView("/wechatPage/goods");
        if(shopId != null){
            PageParam page = new PageParam();
            page.setPageCurrent(1);
            page.setPageSize(11);
            DepotParam param = new DepotParam();
            param.setShopId(shopId);
            List<DepotPageEntity> list = depotService.selectDepotByWeChat(param,page);
            ShopEntity shop = shopService.selectByPrimaryKey(shopId);
            mav.addObject("depotList",list);
            mav.addObject("shop",shop);
        }
        return mav;
    }

    @RequestMapping("/depotListParam")
    @ResponseBody
    public List<DepotPageEntity> depotListParam(String page,String req){
        DepotParam entity = JsonUtils.fromJson(req,DepotParam.class);
        PageParam p = JsonUtils.fromJson(page,PageParam.class);
        List<DepotPageEntity> list = depotService.selectDepotByWeChat(entity,p);
        return list;
    }
}
