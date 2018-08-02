package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.page.InventoryPageEntity;
import com.grape.supermarket.entity.param.InventoryParam;
import com.grape.supermarket.operator.service.InventoryService;
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
@RequestMapping("/operator/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping("/inventoryList")
    @ResponseBody
    public List<InventoryPageEntity> inventoryList(HttpServletRequest request, HttpServletResponse response, String page, String req) {
        List<InventoryPageEntity> list = new ArrayList<>();
        try {
            InventoryParam entity = JsonUtils.fromJson(req,InventoryParam.class);
            PageParam p = JsonUtils.fromJson(page,PageParam.class);
            list =  inventoryService.inventoryList(entity,p);
        }catch (Exception e){
            return null;
        }

        return list;
    }
    @RequestMapping("/countInventory")
    @ResponseBody
    public int countInventory(HttpServletRequest request, HttpServletResponse response,InventoryParam param) {
        int count =  inventoryService.countInventory(param);
        return count;
    }
}
