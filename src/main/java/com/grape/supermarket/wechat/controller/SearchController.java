package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.entity.param.ShopParam;
import com.grape.supermarket.operator.service.CommodityTypeService;
import com.grape.supermarket.operator.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by LXP on 2017/11/16.
 */
@Controller
@RequestMapping("/wechat/search")
public class SearchController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private CommodityTypeService commodityTypeService;
    @RequestMapping
    public ModelAndView index(){
        List<ShopEntity> shoplist = shopService.shopList(new ShopParam(),new PageParam());
        List<CommodityTypePageEntity> typelist = commodityTypeService.commodityTypeList(new CommodityTypeParam(),new PageParam());
        ModelAndView mav = new ModelAndView("/wechatPage/search");
        mav.addObject("shoplist",shoplist);
        mav.addObject("typelist",typelist);
        return mav;
    }
}
