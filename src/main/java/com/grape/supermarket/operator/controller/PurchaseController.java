package com.grape.supermarket.operator.controller;

import java.util.ArrayList;
import java.util.List;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.PurchaseDetailEntity;
import com.grape.supermarket.entity.db.PurchaseEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.CommodityTypePageEntity;
import com.grape.supermarket.entity.page.PurchasePageEntity;
import com.grape.supermarket.entity.param.CommodityTypeParam;
import com.grape.supermarket.entity.param.PurchaseParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.CommodityTypeService;
import com.grape.supermarket.operator.service.DiscountGroupService;
import com.grape.supermarket.operator.service.PurchaseService;
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
 * 2017年11月18日 上午10:40:17
 */
@Controller
@RequestMapping("/operator/purchase")
public class PurchaseController {
    
    @Autowired
    private ShopService shopService;
    
    @Autowired
    private CommodityTypeService commodityTypeService;
    
    @Autowired
    private DiscountGroupService discountGroupService;
    
    @Autowired
    private PurchaseService purchaseService;
    

    @RequestMapping("/queryPurchase")
    @ResponseBody
    public ResultBean<List<PurchasePageEntity>> queryPurchase(String page, String param) {
    	ResultBean<List<PurchasePageEntity>> rb = new ResultBean<List<PurchasePageEntity>>();
    	List<PurchasePageEntity> list = new ArrayList<>();
        try {
        	PurchaseParam entity = JsonUtils.fromJson(param, PurchaseParam.class);
            PageParam p = JsonUtils.fromJson(page, PageParam.class);
            list =  purchaseService.purchaseList(entity, p);
        }catch (Exception e){
            return null;
        }
        rb.setData(list);
        rb.setCode(ResultBean.SUCCESS);
        return rb;
    }
    
    
    @RequestMapping("/queryPurchaseCount")
    @ResponseBody
    public ResultBean<Integer> queryPurchaseCount(String param) {
    	PurchaseParam purchaseParam = JsonUtils.fromJson(param, PurchaseParam.class);
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int total = purchaseService.countPurchase(purchaseParam);//得到记录的总条数
    	rb.setData(total);
    	return rb;
    }
    
    
    @RequestMapping(value="/add",method=RequestMethod.GET)  
    public ModelAndView add(){  
		ModelAndView modelAndView = new ModelAndView();
		//得到站点
		OperatorSession operatorSession = OperatorSessionHelper
				.getSessionOrThrow();
		List<ShopEntity> shops = operatorSession.getOperatorInfo().getShops();
		modelAndView.addObject("shops", shops);
		//得到商品类型
		CommodityTypeParam param = new CommodityTypeParam();
		PageParam p = new PageParam();
		List<CommodityTypePageEntity> typelist = commodityTypeService
				.commodityTypeList(param, p);
		modelAndView.addObject("typelist", typelist);
		modelAndView.setViewName("/operator/add_purchase");
		return modelAndView;
    } 
    
    
    @RequestMapping("/addPurchase")
    @ResponseBody
    public ResultBean<String> addPurchase(String purchase, String goods) {
    	ResultBean<String> rb = new ResultBean<String>();
    	JSONArray jsonArray = JSONArray.fromObject(goods);//得到折扣单
    	List<PurchaseDetailEntity> purchaseDetails = new ArrayList<PurchaseDetailEntity>();
    	for(int ja=0,len=jsonArray.size(); ja<len; ja++){
    		PurchaseDetailEntity purchaseDetail = JsonUtils.fromJson(jsonArray.getJSONObject(ja).toString(), PurchaseDetailEntity.class);
    		purchaseDetails.add(purchaseDetail);
    	}
    	PurchaseEntity purchaseEntity = JsonUtils.fromJson(purchase, PurchaseEntity.class);
    	OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
    	Integer operId = operatorSession.getOperatorInfo().getOperId();//得到登录者的operId
    	purchaseEntity.setOperId(operId);
    	boolean addFlag = purchaseService.addPurchase(purchaseEntity, purchaseDetails);
    	if(addFlag){
    		rb.setCode(ResultBean.SUCCESS);
    	}else{
    		rb.setCode(ResultBean.FAIL);
    	}
    	rb.setData("");
    	return rb;
    }
    
    @RequestMapping(value="/detail",method=RequestMethod.GET)  
    public ModelAndView detail(){  
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/operator/detail_purchase");  
        return modelAndView;  
    }
    
    @RequestMapping("/queryDetail")
    @ResponseBody
    public ResultBean<PurchasePageEntity> queryDetail(Integer purId) {
    	ResultBean<PurchasePageEntity> rb = new ResultBean<PurchasePageEntity>();
    	PurchasePageEntity purchasePage = purchaseService.selectByPrimaryKey(purId);//得到订单详情
    	rb.setData(purchasePage);
    	return rb;
    }
    
    @RequestMapping("/deletePurchase")
    @ResponseBody
    public ResultBean<Integer> deletePurchase(Integer purId) {
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int state = purchaseService.deleteByPrimaryKey(purId);//得到记录的总条数
    	rb.setCode(state);
    	return rb;
    }
    
    @RequestMapping(value="/updatePage",method=RequestMethod.GET)  
    public ModelAndView updatePage(){
        ModelAndView modelAndView = new ModelAndView();
        //得到站点
		OperatorSession operatorSession = OperatorSessionHelper
				.getSessionOrThrow();
		List<ShopEntity> shops = operatorSession.getOperatorInfo().getShops();
		modelAndView.addObject("shops", shops);
		//得到商品类型
        CommodityTypeParam param = new CommodityTypeParam();
        PageParam p = new PageParam();
        List<CommodityTypePageEntity> typelist =  commodityTypeService.commodityTypeList(param,p);
        modelAndView.addObject("typelist", typelist);
        modelAndView.setViewName("/operator/edit_purchase");  
        return modelAndView;  
    }
    
    
    @RequestMapping("/updPurchase")
    @ResponseBody
    public ResultBean<String> updPurchase(String purchase, String goods) {
    	ResultBean<String> rb = new ResultBean<String>();
    	JSONArray jsonArray = JSONArray.fromObject(goods);//得到折扣单
    	List<PurchaseDetailEntity> purchaseDetails = new ArrayList<PurchaseDetailEntity>();
    	for(int ja=0,len=jsonArray.size(); ja<len; ja++){
    		PurchaseDetailEntity purchaseDetail = JsonUtils.fromJson(jsonArray.getJSONObject(ja).toString(), PurchaseDetailEntity.class);
    		purchaseDetails.add(purchaseDetail);
    	}
    	PurchaseEntity purchaseEntity = JsonUtils.fromJson(purchase, PurchaseEntity.class);
    	OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
    	Integer operId = operatorSession.getOperatorInfo().getOperId();//得到登录者的operId
    	purchaseEntity.setOperId(operId);
    	boolean updFlag = purchaseService.updPurchase(purchaseEntity, purchaseDetails);
    	if(updFlag){
    		rb.setCode(ResultBean.SUCCESS);
    	}else{
    		rb.setCode(ResultBean.FAIL);
    	}
    	rb.setData("");
    	return rb;
    }
    
}
