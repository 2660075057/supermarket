package com.grape.supermarket.operator.controller;

import com.grape.supermarket.entity.db.CommodityAttrEntity;
import com.grape.supermarket.operator.service.CommodityAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by LQW on 2017/11/8.
 */
@Controller
@RequestMapping("/operator/commodityattr")
public class CommodityAttrController {
    @Autowired
    private CommodityAttrService commodityAttrService;

    @RequestMapping("/selectByCommId")
    @ResponseBody
    public List<CommodityAttrEntity> selectByCommId(HttpServletRequest request, HttpServletResponse response, Integer commId) {

        return commodityAttrService.selectByCommId(commId);
    }
}
