package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.page.OperatorLogPageEntity;
import com.grape.supermarket.entity.param.OperatorLogParam;
import com.grape.supermarket.operator.service.OperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by LXP on 2018/3/23.
 */
@Controller
@RequestMapping("/operator/operatorLog")
public class OperatorLogController {

    @Autowired
    private OperatorLogService operatorLogService;

    @RequestMapping(value = {"", "/main"})
    public ModelAndView main() {
        return new ModelAndView("operator/mainLog");
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultBean<List<OperatorLogPageEntity>> list(OperatorLogParam param, PageParam page) {
        List<OperatorLogPageEntity> data = operatorLogService.operatorLogList(param, page);
        return new ResultBean<>(data);
    }

    @RequestMapping("/count")
    @ResponseBody
    public ResultBean<Integer> count(OperatorLogParam param) {
        int count = operatorLogService.countOperatorLog(param);
        return new ResultBean<>(count);
    }

    @RequestMapping("/detail/{model}")
    public ModelAndView detail(@PathVariable String model) {
        return new ModelAndView("operator/logDetail/" + model);
    }

    @RequestMapping("/logInfo")
    @ResponseBody
    public ResultBean<OperatorLogPageEntity> logDetail(Integer id) {
        if (id == null) {
            throw new FailMessageException("id is null");
        }
        OperatorLogPageEntity pageEntity = operatorLogService.logDetail(id);
        return new ResultBean<>(pageEntity);
    }
}
