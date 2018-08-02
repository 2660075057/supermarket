package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.GroupEntity;

import java.util.List;

/**
 * Created by LXP on 2017/9/28.
 */

public interface GroupService {

    List<GroupEntity> groupList(PageParam page);

    /**
     * 删除权限组
     * @param groupId 权限组id
     * @return 返回0正常删除，返回1表示存在使用权限组的用户,返回-1表示失败
     */
    int deleteGroup(Integer groupId);

    boolean updateGroup(GroupEntity entity, List<Integer> permissionId);

    boolean addGroup(GroupEntity entity, List<Integer> permissionId);

	int countGroup();
	/**
	 *  查询一个权限组
	 * author huanghuang
	 * 2017年11月4日 下午4:58:20
	 * @param oper_id
	 * @return
	 */
	GroupEntity selectByPrimaryKey(Integer group_id);
}
