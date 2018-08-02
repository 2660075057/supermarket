package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.operator.service.CommodityAttrService;
import com.grape.supermarket.operator.service.CommodityService;
import com.grape.supermarket.operator.service.CountPriceService;
import com.grape.supermarket.operator.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2017/11/16.
 */
@Controller
@RequestMapping("/wechat/commoditydetail")
public class CommodityDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private CommodityAttrService commodityAttrService;
    @Autowired
    private CountPriceService countPriceService;
    @Autowired
    private CommodityService commodityService;

    @RequestMapping
    public ModelAndView main(Integer commId) {
        ModelAndView mav = new ModelAndView("/wechatPage/goods-detail-discount");
        if(commId !=null){
            CommodityPageEntity comm = commodityService.selectByPrimaryKey(commId);
            Map<Integer,Integer> map = new HashMap<>();
            List<ShopEntity> shopList = shopService.selectShopByCommId(commId);
            for(ShopEntity shop:shopList){
                CommodityEntity ce = countPriceService.getCommodityAndRealPrice(commId,shop.getShopId());
                map.put(shop.getShopId(),ce.getCommPrice());
            }
            List<CommodityAttrEntity> commAttr = commodityAttrService.selectCommAttrByCommId(commId);
            mav.addObject("shopList",shopList);
            mav.addObject("commAttr",commAttr);
            mav.addObject("comm",comm);
            mav.addObject("priceMap",map);
        }

        return mav;
    }

    @RequestMapping("/shopCommDetail")
    public ModelAndView shopCommDetail(Integer shopId,Integer commId,Integer amount) {
        ModelAndView mav = new ModelAndView("/wechatPage/goods-detail");
        if(commId != null && shopId != null){
            CommodityPageEntity ce = countPriceService.getCommodityAndRealPrice(commId,shopId);
            List<CommodityAttrEntity> commAttr = commodityAttrService.selectCommAttrByCommId(commId);
            mav.addObject("commAttr",commAttr);
            mav.addObject("comm",ce);
            mav.addObject("amount",amount);
            mav.addObject("shopId",shopId);
        }
        return mav;
    }
}
