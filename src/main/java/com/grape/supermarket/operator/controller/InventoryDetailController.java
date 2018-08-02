package com.grape.supermarket.operator.controller;

import com.grape.supermarket.entity.db.InventoryDetailEntity;
import com.grape.supermarket.entity.page.InventoryDetailPageEntity;
import com.grape.supermarket.entity.param.InventoryDetailParam;
import com.grape.supermarket.operator.service.InventoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQW on 2017/11/9.
 */
@Controller
@RequestMapping("/operator/inventorydetail")
public class InventoryDetailController {
    @Autowired
    private InventoryDetailService inventoryDetailService;

    @RequestMapping("/inventoryDetailList")
    @ResponseBody
    public List<InventoryDetailPageEntity> inventoryDetailList(HttpServletRequest request, HttpServletResponse response, InventoryDetailParam param) {
        List<InventoryDetailPageEntity> list = new ArrayList<>();
        try {
            list = inventoryDetailService.inventoryDetailList(param);
        }catch (Exception e){
            return null;
        }

        return list;
    }

    @RequestMapping("/inventoryDetailListByTypeId")
    @ResponseBody
    public List<InventoryDetailEntity> inventoryDetailListByTypeId(HttpServletRequest request, HttpServletResponse response, InventoryDetailParam param) {
        List<InventoryDetailEntity> list = new ArrayList<>();
        try {
            list = inventoryDetailService.inventoryDetailListByTypeId(param);
        }catch (Exception e){
            return null;
        }

        return list;
    }
}
