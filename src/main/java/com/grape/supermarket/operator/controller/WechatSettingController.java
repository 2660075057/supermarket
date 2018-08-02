package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.WechatBanner;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.WechatConfigService;
import com.grape.supermarket.wechat.service.WechatService;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Created by LXP on 2018/1/11.
 */
@Controller
@RequestMapping("/operator/wechatSetting")
public class WechatSettingController {
    @Autowired
    private WechatConfigService wechatConfigService;
    @Autowired
    private WechatService wechatService;

    @RequestMapping("/wechatMenu")
    @ResponseBody
    public ResultBean<String> wechatMenu() {
        ResultBean<String> rb = new ResultBean<>();
        String wechatMenu = wechatConfigService.getWechatMenu();
        rb.setData(wechatMenu);
        return rb;
    }

    @RequestMapping("/saveWechatMenu")
    @ResponseBody
    public ResultBean<Boolean> saveWechatMenu(String menu){
        ResultBean<Boolean> rb = new ResultBean<>();

        wechatConfigService.setWechatMenu(menu);
        rb.setData(true);

        return rb;
    }

    @RequestMapping("/pullMenu")
    @ResponseBody
    public ResultBean<Boolean> pullMenu() {
        ResultBean<Boolean> rb = new ResultBean<>();
        try {
            wechatService.setWechatMenu();
            rb.setData(true);
        } catch (IOException e) {
            rb.setMessage(e.getMessage());
            rb.setData(false);
        }
        return rb;
    }
    
    @RequestMapping(value="/banner",method=RequestMethod.GET)  
    public ModelAndView add(){  
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/operator/banner");
		return modelAndView;
    }
    
    /**
     * 插入Banner
     * @param bannerArr
     * @return
     */
    @RequestMapping("/saveWechatBanner")
    @ResponseBody
    public ResultBean<Boolean> saveWechatBanner(String bannerArr){
        ResultBean<Boolean> rb = new ResultBean<>();
        List<WechatBanner> banners = JsonUtils.fromJson(bannerArr, new TypeReference<List<WechatBanner>>(){});;
        boolean bool = wechatConfigService.saveWechatBanner(banners);
        if(bool){
        	rb.setData(true);
        }else{
        	rb.setData(false);
        }
        return rb;
    }
    
    /**
     * 取出Banner
     * @param bannerArr
     * @return
     */
    @RequestMapping("/getWechatBanner")
    @ResponseBody
    public ResultBean<List<WechatBanner>> getWechatBanner(String bannerArr){
    	ResultBean<List<WechatBanner>> rb = new ResultBean<>();
    	List<WechatBanner> banners = wechatConfigService.getWechatBanner();
    	rb.setData(banners);
    	rb.setCode(ResultBean.SUCCESS);
    	return rb;
    }
}
