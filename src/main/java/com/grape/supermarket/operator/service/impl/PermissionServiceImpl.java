package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.dao.PermissionDao;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.operator.service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * author huanghuang
 * 2017年11月6日 上午11:40:29
 */
@Service
public class PermissionServiceImpl extends BasicService implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

	@Override
	public List<PermissionEntity> permissionList(PermissionEntity param) {
		if(param==null){
			return null;
		}
		return permissionDao.selectByParam(param);
	}

   
}
