package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.db.PermissionEntity;

import java.util.List;


/**
 * 
 * author huanghuang
 * 2017年11月6日 上午11:37:33
 */
public interface PermissionService {

	/**
	 * author huanghuang
	 * 2017年11月6日 上午11:38:49
	 * @param param
	 * @param page
	 * @return
	 */
	List<PermissionEntity> permissionList(PermissionEntity param);
}
