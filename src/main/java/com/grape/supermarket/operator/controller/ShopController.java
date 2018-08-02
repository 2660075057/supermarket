package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.param.ShopParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/10/23.
 */
@Controller
@RequestMapping("/operator/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopDao shopDao;

    @RequestMapping("/editShopPage")
    public ModelAndView editShopPage(Integer shopid){
        ModelAndView mav = new ModelAndView("operator/edit_shop");
        boolean b = shopService.canDelete(shopid);
        mav.addObject("canDelete", b);
        return mav;
    }

    @RequestMapping("/shopList")
    @ResponseBody
    public List<ShopEntity> shopList(String page,String req) {
        ShopParam entity = JsonUtils.fromJson(req, ShopParam.class);
        PageParam p = JsonUtils.fromJson(page, PageParam.class);
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        if (operatorSession.getOperatorInfo().getOperId() != 0) {
            List<Integer> idRange = getIdRange(operatorSession.getOperatorInfo().getOperId());
            entity.setIdRange(idRange);
        }

        return shopService.shopList(entity, p);
    }
    @RequestMapping("/countShop")
    @ResponseBody
    public int countShop(HttpServletRequest request, HttpServletResponse response,ShopParam param) {
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        if (operatorSession.getOperatorInfo().getOperId() != 0) {
            List<Integer> idRange = getIdRange(operatorSession.getOperatorInfo().getOperId());
            param.setIdRange(idRange);
        }
        int count =  shopService.countShop(param);
        return count;
    }

    @RequestMapping("/addShop")
    @ResponseBody
    public boolean addShop(HttpServletRequest request, HttpServletResponse response,ShopEntity param) {
        boolean flg =  shopService.addShop(param);
        return flg;
    }

    @RequestMapping("/deleteShop")
    @ResponseBody
    public ResultBean<Boolean> deleteShop(Integer shopId) {
        boolean b = shopService.deleteShop(shopId);
        return new ResultBean<>(b);
    }

    @RequestMapping("/selectByPrimaryKey")
    @ResponseBody
    public ShopEntity selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response,Integer shopId) {
        return shopService.selectByPrimaryKey(shopId);
    }

    @RequestMapping("/updateShop")
    @ResponseBody
    public boolean updateShop(HttpServletRequest request, HttpServletResponse response,ShopEntity param) {
        return shopService.updateShop(param);
    }

    @RequestMapping("/shopBarcode")
    public ModelAndView shopCode(Integer shopId){
        if(shopId == null){
            throw new IllegalArgumentException("shopId error");
        }

        ModelAndView mav = new ModelAndView("operator/shopBarcode");
        String inBarcode = shopService.getInAccessBarcode(shopId);
        String outBarcode = shopService.getOutAccessBarcode(shopId);
        if(inBarcode == null || outBarcode == null){
            throw new FailMessageException("生成二维码失败");
        }
        mav.addObject("inBarcode",inBarcode);
        mav.addObject("outBarcode",outBarcode);
        return mav;
    }

    private List<Integer> getIdRange(Integer operId) {
        List<ShopEntity> shops = shopDao.selectByOperId(operId);
        List<Integer> idRange = new ArrayList<>(shops.size());
        for (ShopEntity shop : shops) {
            idRange.add(shop.getShopId());
        }
        if(idRange.isEmpty()){
            idRange.add(-1);
        }
        return idRange;
    }
}
