package com.grape.supermarket.operator.controller;

import java.util.Arrays;
import java.util.List;

import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.ResultBean;
import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.operator.service.GroupService;
import com.grape.supermarket.operator.service.PermissionService;

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
@RequestMapping("/operator/group")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/queryGroup")
    @ResponseBody
    public ResultBean<List<GroupEntity>> queryOperator(PageParam page) {
    	ResultBean<List<GroupEntity>> rb = new ResultBean<List<GroupEntity>>();
    	List<GroupEntity> rows = groupService.groupList(page);//得到分页的所有列
    	rb.setData(rows);
        return rb;
    }
    
    @RequestMapping("/queryGroupCount")
    @ResponseBody
    public ResultBean<Integer> queryGroupCount(PageParam page) {
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int total = groupService.countGroup();//得到记录的总条数
    	rb.setData(total);
    	return rb;
    }
    
    @RequestMapping(value="/add",method=RequestMethod.GET)  
    public ModelAndView add(){  
        ModelAndView modelAndView = new ModelAndView();
    	List<PermissionEntity> permissions = permissionService.permissionList(new PermissionEntity());
        modelAndView.addObject("permissions", permissions);  
        modelAndView.setViewName("/operator/add_group");  
        return modelAndView;  
    } 
    
    @RequestMapping("/addGroup")
    @ResponseBody
    public ResultBean<String> addGroup(String group, String permissionIds) {
    	GroupEntity param = JsonUtils.fromJson(group, GroupEntity.class);
    	ResultBean<String> rb = new ResultBean<String>();
    	String[] permissionIdArr = permissionIds.split(",");
    	int len = permissionIdArr.length;
    	Integer[] intTemp = new Integer[len];
    	for (int i = 0; i < len; i++){
    		intTemp[i] = Integer.parseInt(permissionIdArr[i]);
    	}
    	String msg = "";
    	boolean addMark = groupService.addGroup(param, Arrays.asList(intTemp));//得到操作的标识
    	if(!addMark){
    		rb.setCode(ResultBean.FAIL);
    	}
    	rb.setData(msg);
    	return rb;
    }
    
    @RequestMapping(value="/edit",method=RequestMethod.GET)  
    public ModelAndView edit(){  
        ModelAndView modelAndView = new ModelAndView();
    	List<PermissionEntity> permissions = permissionService.permissionList(new PermissionEntity());
        modelAndView.addObject("permissions", permissions);  
        modelAndView.setViewName("/operator/upd_group");  
        return modelAndView;  
    }
    
    @RequestMapping("/queryOneGroup")
    @ResponseBody
    public ResultBean<GroupEntity> queryOneGroup(Integer groupId) {
    	ResultBean<GroupEntity> rb = new ResultBean<GroupEntity>();
    	GroupEntity group = groupService.selectByPrimaryKey(groupId);//得到权限组
    	rb.setData(group);
    	return rb;
    }
    
    @RequestMapping("/updateGroup")
    @ResponseBody
    public ResultBean<String> updateGroup(String group, String permissionIds) {
    	GroupEntity param = JsonUtils.fromJson(group, GroupEntity.class);
    	ResultBean<String> rb = new ResultBean<String>();
    	String[] permissionIdArr = permissionIds.split(",");
    	int len = permissionIdArr.length;
    	Integer[] intTemp = new Integer[len];
    	for (int i = 0; i < len; i++){
    		intTemp[i] = Integer.parseInt(permissionIdArr[i]);
    	}
    	String msg = "";
    	boolean updMark = groupService.updateGroup(param, Arrays.asList(intTemp));//得到操作的标识
    	if(!updMark){
    		rb.setCode(ResultBean.FAIL);
    	}
    	rb.setData(msg);
    	return rb;
    }
    
    @RequestMapping("/deleteGroup")
    @ResponseBody
    public ResultBean<Integer> deleteGroup(Integer groupId) {
    	ResultBean<Integer> rb = new ResultBean<Integer>();
    	int state = groupService.deleteGroup(groupId);//得到记录的总条数
    	//返回0正常删除，返回1表示存在使用权限组的用户,返回-1表示失败
    	rb.setCode(state);
    	return rb;
    }
    
}
