package com.grape.supermarket.operator.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.ElecTagEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.page.ElecTagPageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.operator.service.CommodityTypeService;
import com.grape.supermarket.operator.service.ElecTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by LQW on 2017/12/2.
 */
@Controller
@RequestMapping("/operator/electag")
public class ElecTagController {
    @Autowired
    private ElecTagService elecTagService;
    @Autowired
    private CommodityTypeService commodityTypeService;

    @RequestMapping(value = "/tagImport", method = RequestMethod.GET)
    public ModelAndView tagImport() {
        return new ModelAndView("operator/label_import");
    }

    @RequestMapping("/createEpc")
    public ModelAndView createEpcList() {
        ModelAndView mav = new ModelAndView("operator/create_epc");
        //得到商品类型
        CommodityTypeParam param = new CommodityTypeParam();
        PageParam p = new PageParam();
        List<CommodityTypePageEntity> typelist = commodityTypeService
                .commodityTypeList(param, p);
        mav.addObject("typelist", typelist);

        return mav;
    }

    @RequestMapping("/getEpc")
    @ResponseBody
    public ResultBean<Map<Integer, List<ElecTagEntity>>> getEpc(String jsonData, String jsonUserData) {
        if (jsonData == null || jsonData.isEmpty()
                || jsonUserData == null || jsonUserData.isEmpty()) {
            throw new FailMessageException("input error");
        }
        Map<Integer, Integer> data = JsonUtils.fromJson(jsonData, new TypeReference<Map<Integer, Integer>>() {
        });
        Map<String, String> userData = JsonUtils.fromJson(jsonUserData, new TypeReference<Map<String, String>>() {
        });
        Map<Integer, List<ElecTagEntity>> result = new TreeMap<>();
        for (Map.Entry<Integer, Integer> integerEntry : data.entrySet()) {
            Integer commId = integerEntry.getKey();
            Integer num = integerEntry.getValue();
            if (commId == null || num == null) {
                continue;
            }

            List<ElecTagEntity> elecTag = elecTagService.createElecTag(commId, userData, num);
            result.put(commId, elecTag);
        }

        return new ResultBean<>(result);
    }

    @RequestMapping(value = "/tagImport", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> tagImport(String elecTagJson) {
        if (elecTagJson == null || elecTagJson.isEmpty()) {
            throw new FailMessageException("input error");
        }
        List<ElecTagPageEntity> elecTag = JsonUtils.fromJson(elecTagJson, new TypeReference<List<ElecTagPageEntity>>() {
        });
        Integer download = elecTagService.importElecTag(elecTag);
        return new ResultBean<>(download);
    }

    @RequestMapping("/downLoad")
    public void downLoad(Integer handle, HttpServletResponse response) {
        elecTagService.downLoad(handle, response);
    }
}
