package com.grape.supermarket.wechat.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.CommodityPageEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.param.CommodityParam;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.entity.param.ShopParam;
import com.grape.supermarket.operator.service.CommodityService;
import com.grape.supermarket.operator.service.CommodityTypeService;
import com.grape.supermarket.operator.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQW on 2017/11/16.
 */
@Controller
@RequestMapping("/wechat/commodity")
public class WechatCommodityController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CommodityTypeService commodityTypeService;

    @RequestMapping
    public ModelAndView main(String shopId,String typeId,String queryVal) {
        CommodityParam param = new CommodityParam() ;
        if(shopId != null && !shopId.isEmpty()){
            if(Integer.parseInt(shopId) != -1){
                param.setShopId(Integer.parseInt(shopId));
            }
        }
        if(typeId != null && !typeId.isEmpty()){
            if(Integer.parseInt(typeId) != -1){
                param.setShopId(Integer.parseInt(typeId));
            }
        }
        if(queryVal != null && !queryVal.isEmpty()){
            try {
                queryVal = new String(queryVal.getBytes("ISO-8859-1"),"UTF-8");
                param.setCommName(queryVal);
            } catch (UnsupportedEncodingException e) {
                queryVal="";
            }
        }
        PageParam page = new PageParam();
        page.setPageSize(11);
        page.setPageCurrent(1);
        List<CommodityPageEntity> list = commodityService.selectCommByWeChat(param,page);
        List<ShopEntity> shoplist = shopService.shopList(new ShopParam(),new PageParam());
        List<CommodityTypePageEntity> typelist = commodityTypeService.commodityTypeList(new CommodityTypeParam(),new PageParam());
        ModelAndView mav = new ModelAndView("/wechatPage/commoditySearch");
        mav.addObject("commList",list);
        mav.addObject("shoplist",shoplist);
        mav.addObject("typelist",typelist);
        mav.addObject("shopId",shopId);
        mav.addObject("typeId",typeId);
        mav.addObject("queryVal",queryVal);
        return mav;
    }

    @RequestMapping("/commListParam")
    @ResponseBody
    public List<CommodityPageEntity> commListParam(HttpServletRequest request, HttpServletResponse response, String req, String page){
        List<CommodityPageEntity> list = new ArrayList<>();
        try {
            CommodityParam entity = JsonUtils.fromJson(req,CommodityParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  commodityService.selectCommByWeChat(entity,p);
        }catch (Exception e){
            return null;
        }
        return list;
    }
}
