package com.grape.supermarket.device.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.device.service.ElecTagService;
import com.grape.supermarket.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2018/3/22.
 */
@Controller
@RequestMapping("/device/elec")
public class ElecController {
    @Autowired
    private ElecTagService elecTagService;

    @RequestMapping("/unLock")
    @ResponseBody
    public ResultBean<Map<String,String>> unLockData(String jsonData){
        if(jsonData == null || jsonData.isEmpty()){
            throw new FailMessageException("input error");
        }
        List<String> epc = JsonUtils.fromJson(jsonData, new TypeReference<List<String>>() {});
        Map<String, String> data = elecTagService.disLock(epc);
        return new ResultBean<>(data);
    }
}
