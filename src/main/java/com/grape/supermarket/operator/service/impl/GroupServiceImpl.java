package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.dao.GroupDao;
import com.grape.supermarket.dao.GroupPermissionDao;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.dao.PermissionDao;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.GroupPermissionEntity;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.entity.param.OperatorParam;
import com.grape.supermarket.operator.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 2017/9/28.
 */
@Service
public class GroupServiceImpl extends BasicService implements GroupService  {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private GroupPermissionDao groupPermissionDao;

    @Autowired
    private OperatorDao operatorDao;
    
    @Autowired
    private PermissionDao permissionDao;
    

    @Override
    public List<GroupEntity> groupList(PageParam page) {
        SelectEntity select = new SelectEntity();
        select.setOrderBy("group_name");
        if(page != null){
            int[] limit = page.getLimit();
            select.setLimitS(limit[0]);
            select.setLimitE(limit[1]);
        }
        List<GroupEntity> ges = groupDao.selectByParam(new GroupEntity(),select);//得到权限组
        if(ges!=null){
        	for(int l=0,len=ges.size();l<len;l++){
        		List<GroupPermissionEntity> gpe = groupPermissionDao.selectList(ges.get(l).getGroupId());//得到权限组的权限关联信息
        		if(gpe!=null){
        			List<PermissionEntity> permissions = new ArrayList<>();
        			for(int g=0,glen=gpe.size();g<glen;g++){
        				PermissionEntity permissionEntity = permissionDao.selectByPrimaryKey(gpe.get(g).getpId());//得到具体权限
        				permissions.add(permissionEntity);
        			}
        			ges.get(l).setPermissions(permissions);
        		}
        	}
        }
        return ges;
    }

    @Override
    public int deleteGroup(Integer groupId) {
        if(groupId == null){
            return -1;
        }
        OperatorParam selecrOper = new OperatorParam();
        selecrOper.setGroupId(groupId);
        boolean empty = operatorDao.selectByParam(selecrOper).isEmpty();
        if(!empty){
            return 1;
        }
        groupPermissionDao.deleteByGroupId(groupId);
        groupDao.deleteByPrimaryKey(groupId);

        return 0;
    }

    @Override
    public boolean updateGroup(GroupEntity entity, List<Integer> permissionIds) {
        if(entity == null || entity.getGroupId() == null
                || StringUtils.isEmpty(entity.getGroupName())){
            return false;
        }
        groupDao.updateByPrimaryKeySelective(entity);
        setGroupPermission(entity.getGroupId(),permissionIds);

        return true;
    }

    @Override
    public boolean addGroup(GroupEntity entity, List<Integer> permissionIds) {
        if(StringUtils.isEmpty(entity.getGroupName())){
            return false;
        }
        entity.setGroupId(null);
        groupDao.insertSelective(entity);
        setGroupPermission(entity.getGroupId(),permissionIds);

        return true;
    }

    private void setGroupPermission(Integer groupId, List<Integer> permissionIds){
        if(permissionIds != null){
            groupPermissionDao.deleteByGroupId(groupId);
            for (Integer integer : permissionIds) {
                GroupPermissionEntity insert = new GroupPermissionEntity(groupId,integer);
                groupPermissionDao.insert(insert);
            }
        }
    }

	@Override
	public int countGroup() {
		return groupDao.countGroup();
	}

	@Override
	public GroupEntity selectByPrimaryKey(Integer groupId) {
		GroupEntity groupEntity = groupDao.selectByPrimaryKey(groupId);
		List<GroupPermissionEntity> gpe = groupPermissionDao.selectList(groupId);//得到权限组的权限关联信息
		if(gpe!=null){
			List<PermissionEntity> permissions = new ArrayList<>();
			for(int g=0,glen=gpe.size();g<glen;g++){
				PermissionEntity permissionEntity = permissionDao.selectByPrimaryKey(gpe.get(g).getpId());//得到具体权限
				permissions.add(permissionEntity);
			}
			groupEntity.setPermissions(permissions);
		}
		return groupEntity;
	}
}
