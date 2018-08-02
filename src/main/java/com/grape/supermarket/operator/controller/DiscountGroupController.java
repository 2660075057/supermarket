package com.grape.supermarket.operator.controller;

import java.util.ArrayList;
import java.util.List;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.DiscountEntity;
import com.grape.supermarket.entity.db.DiscountGroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.page.DiscountGroupPageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.entity.param.DiscountGroupParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.CommodityTypeService;
import com.grape.supermarket.operator.service.DiscountGroupService;
import com.grape.supermarket.operator.service.GroupService;
import com.grape.supermarket.operator.service.OperatorService;
import com.grape.supermarket.operator.service.ShopService;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * author huanghuang
 * 2017年11月1日 下午3:24:10
 */
@Controller
@RequestMapping("/operator/discountGroup")
public class DiscountGroupController {

    @Autowired
    private OperatorService operatorService;
    
    @Autowired
    private GroupService groupService;
    
    @Autowired
    private ShopService shopService;
    
    @Autowired
    private CommodityTypeService commodityTypeService;
    
    @Autowired
    private DiscountGroupService discountGroupService;
    

    @RequestMapping("/queryDiscountGroup")
    @ResponseBody
    public ResultBean<List<DiscountGroupPageEntity>> queryDiscountGroup(String page, String param) {
    	ResultBean<List<DiscountGroupPageEntity>> rb = new ResultBean<List<DiscountGroupPageEntity>>();
    	List<DiscountGroupPageEntity> list = new ArrayList<>();
        try {
        	DiscountGroupParam entity = JsonUtils.fromJson(param, DiscountGroupParam.class);
            PageParam p = JsonUtils.fromJson(page, PageParam.class);
            list =  discountGroupService.discountGroupList(entity, p);
        }catch (Exception e){
            return null;
        }
        rb.setData(list);
        rb.setCode(ResultBean.SUCCESS);
        return rb;
    }
    
    
    @RequestMapping("/queryDiscountGroupCount")
    @ResponseBody
    public ResultBean<Integer> queryDiscountGroupCount(String param) {
    	DiscountGroupParam discountGroupParam = JsonUtils.fromJson(param, DiscountGroupParam.class);
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int total = discountGroupService.countDiscountGroup(discountGroupParam);//得到记录的总条数
    	rb.setData(total);
    	return rb;
    }
    
    @RequestMapping(value="/detail",method=RequestMethod.GET)  
    public ModelAndView detail(Integer groupId){  
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("groupId", groupId);
        modelAndView.setViewName("/operator/detail_discountGroup");  
        return modelAndView;  
    }
    
    @RequestMapping("/queryDetail")
    @ResponseBody
    public ResultBean<DiscountGroupPageEntity> queryDetail(Integer groupId) {
    	ResultBean<DiscountGroupPageEntity> rb = new ResultBean<DiscountGroupPageEntity>();
    	DiscountGroupPageEntity discountGroup = discountGroupService.selectByPrimaryKey(groupId);//得到记录的总条数
    	rb.setData(discountGroup);
    	return rb;
    }
    
    @RequestMapping(value="/add",method=RequestMethod.GET)  
    public ModelAndView add(){  
        ModelAndView modelAndView = new ModelAndView();
        CommodityTypeParam param = new CommodityTypeParam();
        PageParam p = new PageParam();
        List<CommodityTypePageEntity> typelist =  commodityTypeService.commodityTypeList(param,p);
        modelAndView.addObject("typelist", typelist);
        modelAndView.setViewName("/operator/add_shopCommDiscount");  
        return modelAndView;  
    } 
    
    
    @RequestMapping("/addDiscount")
    @ResponseBody
    public ResultBean<String> addOperator(String discountGroup, String discounts) {
    	ResultBean<String> rb = new ResultBean<String>();
    	JSONArray jsonArray = JSONArray.fromObject(discounts);//得到折扣单
    	List<DiscountEntity> ds = new ArrayList<DiscountEntity>();
    	for(int ja=0,len=jsonArray.size(); ja<len; ja++){
    		DiscountEntity discountEntity = JsonUtils.fromJson(jsonArray.getJSONObject(ja).toString(), DiscountEntity.class);
    		ds.add(discountEntity);
    	}
    	DiscountGroupEntity disGroup = JsonUtils.fromJson(discountGroup, DiscountGroupEntity.class);
    	OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
    	Integer operId = operatorSession.getOperatorInfo().getOperId();//得到登录者的operId
    	disGroup.setOperId(operId);
    	boolean addFlag = discountGroupService.addDiscountGroup(disGroup, ds);
    	if(addFlag){
    		rb.setCode(ResultBean.SUCCESS);
    	}else{
    		rb.setCode(ResultBean.FAIL);
    	}
    	rb.setData("");
    	return rb;
    }
    
    @RequestMapping("/deleteDiscountGroup")
    @ResponseBody
    public ResultBean<Integer> deleteDiscountGroup(Integer groupId) {
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int state = discountGroupService.deleteByPrimaryKey(groupId);//得到记录的总条数
    	rb.setCode(state);
    	return rb;
    }
    
    @RequestMapping(value="/copyGroup",method=RequestMethod.GET)  
    public ModelAndView edit(){
        ModelAndView modelAndView = new ModelAndView();
        CommodityTypeParam param = new CommodityTypeParam();
        PageParam p = new PageParam();
        List<CommodityTypePageEntity> typelist =  commodityTypeService.commodityTypeList(param,p);
        modelAndView.addObject("typelist", typelist);
        modelAndView.setViewName("/operator/copy_shopCommDiscount");  
        return modelAndView;  
    }
    
}
