package com.grape.supermarket.operator.controller;

import com.grape.supermarket.entity.db.AttributeEntity;
import com.grape.supermarket.operator.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LQW on 2017/11/7.
 */
@Controller
@RequestMapping("/operator/attribute")
public class AttributeController {
    @Autowired
    private AttributeService attributeService;

    @RequestMapping("/selectAll")
    @ResponseBody
    public List<AttributeEntity> selectAll(){
        return attributeService.selectAll();
    }
}
