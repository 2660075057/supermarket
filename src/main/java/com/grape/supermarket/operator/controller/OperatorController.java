package com.grape.supermarket.operator.controller;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.exception.FailMessageException;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.OperatorPageEntity;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.GroupService;
import com.grape.supermarket.operator.service.OperatorService;
import com.grape.supermarket.operator.service.OperatorService.AddEnum;
import com.grape.supermarket.operator.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * author huanghuang
 * 2017年11月1日 下午3:24:10
 */
@Controller
@RequestMapping("/operator/user")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;
    
    @Autowired
    private GroupService groupService;
    
    @Autowired
    private ShopService shopService;

    @RequestMapping("/queryOperator")
    @ResponseBody
    public ResultBean<List<OperatorPageEntity>> queryOperator(PageParam page) {
    	OperatorEntity param = new OperatorEntity();
    	ResultBean<List<OperatorPageEntity>> rb = new ResultBean<List<OperatorPageEntity>>();
    	List<OperatorPageEntity> rows = operatorService.operatorList(param, page);//得到分页的所有列
    	rb.setData(rows);
        return rb;
    }
    
    
    @RequestMapping("/queryOperatorCount")
    @ResponseBody
    public ResultBean<Integer> queryOperatorCount(PageParam page) {
    	OperatorEntity param = new OperatorEntity();
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int total = operatorService.countOperator(param);//得到记录的总条数
    	rb.setData(total);
    	return rb;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public ModelAndView loginPage(){
        return new ModelAndView("operator/login");
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Integer> login(String account, String pwd, HttpServletRequest request){
        if(StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)){
            throw new FailMessageException("account or pwd is enpty");
        }
        ResultBean<Integer> rb = new ResultBean<>();
        OperatorEntity param = new OperatorEntity();
        param.setOperAccount(account);
        param.setOperPwd(pwd);
        OperatorService.LoginEnum state = operatorService.login(param, request);
        if(state == OperatorService.LoginEnum.SUCCESS){
            rb.setCode(ResultBean.SUCCESS);
            rb.setData(1);
        }else if(state == OperatorService.LoginEnum.DISABLE){
            rb.setCode(ResultBean.FAIL);
            rb.setData(-1);
        }else{
            rb.setCode(ResultBean.FAIL);
            rb.setData(-2);
        }

        return rb;
	}

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new ModelAndView("redirect:operator/login");
    }

    @RequestMapping(value="/add",method=RequestMethod.GET)  
    public ModelAndView add(){  
        ModelAndView modelAndView = new ModelAndView();
    	List<GroupEntity> groups = groupService.groupList(null);
    	OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
    	List<ShopEntity> shops = operatorSession.getOperatorInfo().getShops();
    	modelAndView.addObject("shops", shops);  
        modelAndView.addObject("groups", groups);  
        modelAndView.setViewName("/operator/add_operator");  
        return modelAndView;  
    } 
    
    
    @RequestMapping("/addOperator")
    @ResponseBody
    public ResultBean<String> addOperator(String operator, String shopIds) {
    	OperatorEntity param = JsonUtils.fromJson(operator, OperatorEntity.class);
    	ResultBean<String> rb = new ResultBean<String>();
    	String[] shopIdArr = shopIds.split(",");
    	int len = shopIdArr.length;
    	Integer[] intTemp = new Integer[len];
    	for (int i = 0; i < len; i++){
    		intTemp[i] = Integer.parseInt(shopIdArr[i]);
    	}
    	String msg = "";
    	AddEnum resultEnum = operatorService.addOperator(param, Arrays.asList(intTemp));//得到操作的标识
    	if(AddEnum.FAIL.equals(resultEnum)){
    		rb.setCode(ResultBean.FAIL);
    		msg = "添加操作员失败";
    	}else if(AddEnum.ACCOUNT_EXISTS.equals(resultEnum)){
    		rb.setCode(ResultBean.FAIL);
    		msg = "用户名已经存在";
    	}else{
    		msg = "添加操作员成功";
    	}
    	rb.setData(msg);
    	return rb;
    }
    
    @RequestMapping(value="/edit",method=RequestMethod.GET)  
    public ModelAndView edit(){  
        ModelAndView modelAndView = new ModelAndView();
    	List<GroupEntity> groups = groupService.groupList(null);
    	OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
    	List<ShopEntity> shops = operatorSession.getOperatorInfo().getShops();
    	modelAndView.addObject("shops", shops);
        modelAndView.addObject("groups", groups);  
        modelAndView.setViewName("/operator/upd_operator");  
        return modelAndView;  
    } 
    
    
    
    @RequestMapping("/queryOneOperator")
    @ResponseBody
    public ResultBean<OperatorEntity> queryOneOperator(Integer oper_id) {
    	ResultBean<OperatorEntity> rb = new ResultBean<OperatorEntity>();
    	OperatorEntity operator = operatorService.selectByPrimaryKey(oper_id);//得到记录的总条数
    	rb.setData(operator);
    	return rb;
    }
    
    @RequestMapping("/updateOperator")
    @ResponseBody
    public ResultBean<OperatorService.UpdateEnum> updateOperator(String operator, String shopIds) {
    	OperatorEntity param = JsonUtils.fromJson(operator, OperatorEntity.class);

    	String[] shopIdArr = shopIds.split(",");
    	int len = shopIdArr.length;
    	Integer[] intTemp = new Integer[len];
    	for (int i = 0; i < len; i++){
    		intTemp[i] = Integer.parseInt(shopIdArr[i]);
    	}
        OperatorService.UpdateEnum result = operatorService.updateOperator(param, Arrays.asList(intTemp));//更新操作员
        ResultBean<OperatorService.UpdateEnum> rb = new ResultBean<>();
        rb.setData(result);
    	return rb;
    }

    @RequestMapping(value = "/personel_setting",method = RequestMethod.GET)
    public ModelAndView personelSetting(){
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        ModelAndView mav = new ModelAndView("/operator/personel_setting");
        OperatorEntity operator = operatorService.selectByPrimaryKey(session.getOperatorInfo().getOperId());
        mav.addObject("user",operator);
        mav.addObject("shops",session.getOperatorInfo().getShops());
        mav.addObject("group",session.getGroupInfo());
        return mav;
    }

    @RequestMapping(value = "/personel_setting",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Boolean> personelChange(OperatorEntity operator){
        if(operator == null){
            throw new FailMessageException("参数错误");
        }
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        operator.setOperId(session.getOperatorInfo().getOperId());
        operator.setOperAccount(null);
        operator.setState(null);
        operator.setGroupId(null);
        operator.setOpenId(null);
        OperatorService.UpdateEnum result = operatorService.updateOperator(operator, null);//更新操作员
        ResultBean<Boolean> rb = new ResultBean<>();
        rb.setData(result == OperatorService.UpdateEnum.SUCCESS);
        return rb;
    }

    @RequestMapping(value = "/unbind",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Boolean> unbind(){
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        OperatorEntity operator = new OperatorEntity();
        operator.setOperId(session.getOperatorInfo().getOperId());
        operator.setOpenId("");
        OperatorService.UpdateEnum result = operatorService.updateOperator(operator, null);//更新操作员
        ResultBean<Boolean> rb = new ResultBean<>();
        rb.setData(result == OperatorService.UpdateEnum.SUCCESS);
        return rb;
    }


}
