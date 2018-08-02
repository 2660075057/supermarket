package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.util.HashUtil;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.common.util.StringUtils;
import com.grape.supermarket.dao.*;
import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.*;
import com.grape.supermarket.entity.page.OperatorPageEntity;
import com.grape.supermarket.entity.param.OperatorParam;
import com.grape.supermarket.entity.param.ShopParam;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.OperatorLogService;
import com.grape.supermarket.operator.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by LXP on 2017/9/25.
 */
@Service
public class OperatorServiceImpl extends BasicService implements OperatorService {
    @Value("${pwd.salt}")
    private String pwdSalt;
    @Autowired
    private OperatorDao operatorDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private OperatorShopDao operatorShopDao;
    @Autowired
    private OperatorLogService operatorLogService;

    @Override
    public OperatorEntity selectByPrimaryKey(Integer oper_id) {
    	OperatorEntity operator = operatorDao.selectByPrimaryKey(oper_id);
    	if(operator!=null){
    		List<ShopEntity> shops = new ArrayList<>();
    		List<OperatorShopKey> operatorShopList = operatorShopDao.operatorShopKeyList(operator.getOperId());
    		if(operatorShopList!=null){
    			for(OperatorShopKey key : operatorShopList){
    				ShopEntity shopEntity = shopDao.selectByPrimaryKey(key.getShopId());
    				shops.add(shopEntity);
    			}
    		}
    		operator.setShops(shops);
    	}
        return operator;
    }

    @Override
    public List<OperatorPageEntity> operatorList(OperatorEntity param, PageParam page) {
        SelectEntity select = new SelectEntity();
        select.setOrderBy("oper_name asc");
        if (page != null) {
            int[] limit = page.getLimit();
            if (limit != null) {
                select.setLimitS(limit[0]);
                select.setLimitE(limit[1]);
            }
        }
        OperatorParam selectParam = new OperatorParam(param);
        selectParam.setDeleteMark(false);
        //设置忽略超级管理员和自己
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        selectParam.setIgnoreId(Arrays.asList(0,operatorSession.getOperatorInfo().getOperId()));
        selectParam.setSelect(select);
        List<OperatorEntity> operatorEntities = operatorDao.selectByParam(selectParam);
        List<OperatorPageEntity> datas = new ArrayList<>(operatorEntities.size());
        for (OperatorEntity operatorEntity : operatorEntities) {
            OperatorPageEntity ope = new OperatorPageEntity(operatorEntity);
            //得到站点 start
            List<OperatorShopKey> operatorShopList = operatorShopDao.operatorShopKeyList(operatorEntity.getOperId());
    		List<ShopEntity> shops = new ArrayList<>();
    		if(operatorShopList!=null){
    			for(OperatorShopKey key : operatorShopList){
    				ShopEntity shopEntity = shopDao.selectByPrimaryKey(key.getShopId());
    				shops.add(shopEntity);
    			}
    		}
    		ope.setShops(shops);
    		//得到站点 end
    		//得到权限 start
    		GroupEntity groupEntity = groupDao.selectByPrimaryKey(operatorEntity.getGroupId());
    		ope.setUserGroup(groupEntity);
    		//得到权限 end
            datas.add(ope);
        }
        return datas;
    }

    @Override
    public List<OperatorEntity> selectOperator(OperatorEntity param){
        OperatorParam selectParam = new OperatorParam(param);
        selectParam.setDeleteMark(false);
        return operatorDao.selectByParam(selectParam);
    }

    @Override
    public int countOperator(OperatorEntity param) {
        OperatorParam selectParam = new OperatorParam(param);
        selectParam.setDeleteMark(false);
        //设置忽略超级管理员和自己
        OperatorSession operatorSession = OperatorSessionHelper.getSessionOrThrow();
        selectParam.setIgnoreId(Arrays.asList(0,operatorSession.getOperatorInfo().getOperId()));
        return operatorDao.countByParam(selectParam);
    }

    @Override
    public boolean deleteOperator(Integer oper_id) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity.setOperId(oper_id);
        boolean b = operatorDao.updateByPrimaryKeySelective(operatorEntity) > 0;
        OperatorLogEntity operatorLog
                = operatorLogService.createOperatorLog(session.getOperatorInfo().getOperId(),OperatorSessionHelper.getClientIp(), OperatorLogService.OPERATOR_DELETE);
        operatorLog.setDataId(String.valueOf(oper_id));
        operatorLogService.addLog(operatorLog);
        return b;
    }

    @Override
    public AddEnum addOperator(OperatorEntity operatorEntity, List<Integer> shopId) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();

        OperatorEntity param = new OperatorEntity();
        param.setOperAccount(operatorEntity.getOperAccount());
        boolean empty = operatorList(param, null).isEmpty();
        if(!empty){
            return AddEnum.ACCOUNT_EXISTS;
        }
        operatorEntity.setOperId(null);//自增控制id
        operatorEntity.setOperPwd(HashUtil.md5Upper(pwdSalt + operatorEntity.getOperPwd()));
        operatorDao.insertSelective(operatorEntity);
        Integer operId = operatorEntity.getOperId();
        if(operId == null){
            return AddEnum.FAIL;
        }
        //插入商店id信息
        for (Integer integer : shopId) {
            OperatorShopKey operatorShop = new OperatorShopKey(operId,integer);
            operatorShopDao.insertSelective(operatorShop);
        }

        OperatorLogEntity operatorLog
                = operatorLogService.createOperatorLog(session.getOperatorInfo().getOperId(),OperatorSessionHelper.getClientIp(), OperatorLogService.OPERATOR_ADD);
        Map<String,Object> data = new TreeMap<>();
        data.put("operatorData",operatorEntity);
        operatorLog.setData(JsonUtils.toJsonNonNull(data));
        operatorLogService.addLog(operatorLog);

        return AddEnum.SUCCESS;
    }

    @Override
    public UpdateEnum updateOperator(OperatorEntity operatorEntity, List<Integer> shopId) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();
        if (operatorEntity.getOperId() == null) {//检查
            return UpdateEnum.FAIL;
        }

        Integer operId = operatorEntity.getOperId();
        if (operatorEntity.getOperAccount() != null) {
            //检查用户名是否重复
            OperatorParam accountParam = new OperatorParam();
            accountParam.setOperAccount(operatorEntity.getOperAccount());
            List<OperatorEntity> operators = operatorDao.selectByParam(accountParam);
            if (!operators.isEmpty()) {
                if (!operators.get(0).getOperId().equals(operId)) {
                    return UpdateEnum.ACCOUNT_EXISTS;
                }
            }
        }

        if (shopId != null) {
            //删除之前的商店
            List<OperatorShopKey> operatorShopList = operatorShopDao.operatorShopKeyList(operId);//通过操作员id查询出其名下的站点
            if (operatorShopList != null) {
                for (OperatorShopKey key : operatorShopList) {
                    operatorShopDao.deleteByPrimaryKey(key);
                }
            }
            //插入新的商店
            for (Integer integer : shopId) {
                OperatorShopKey operatorShop = new OperatorShopKey(operId, integer);
                operatorShopDao.insertSelective(operatorShop);
            }
        }
        if (!StringUtils.isEmpty(operatorEntity.getOperPwd())) {
            operatorEntity.setOperPwd(HashUtil.md5Upper(pwdSalt + operatorEntity.getOperPwd()));
        } else {
            operatorEntity.setOperPwd(null);
        }
        operatorDao.updateByPrimaryKeySelective(operatorEntity);

        OperatorLogEntity operatorLog
                = operatorLogService.createOperatorLog(session.getOperatorInfo().getOperId(), OperatorSessionHelper.getClientIp(), OperatorLogService.OPERATOR_UPDATE);
        Map<String, Object> data = new TreeMap<>();
        data.put("operatorData", operatorEntity);
        data.put("shopId", shopId);
        operatorLog.setData(JsonUtils.toJsonNonNull(data));
        operatorLogService.addLog(operatorLog);

        return UpdateEnum.SUCCESS;
    }

    @Override
    public LoginEnum login(OperatorEntity param, HttpServletRequest request) {
        OperatorParam selectParam = new OperatorParam();
        selectParam.setOperAccount(param.getOperAccount());
        selectParam.setPhone(param.getPhone());
        selectParam.setDeleteMark(false);

        List<OperatorEntity> operators = operatorDao.selectByParam(selectParam);
        if (!operators.isEmpty()) {
            OperatorEntity oe = operators.get(0);
            if(oe.getState() == 1){
                return LoginEnum.DISABLE;
            }
            String pwdMd5 = HashUtil.md5Upper(pwdSalt + param.getOperPwd());
            if (oe.getOperPwd().equalsIgnoreCase(pwdMd5)) {
                List<PermissionEntity> permissions;
                Integer groupId = oe.getGroupId();
                GroupEntity userGroup = null;
                if (groupId == null) {
                    permissions = permissionDao.selectByParam(null);
                } else {
                    permissions = permissionDao.selectByGroupId(groupId);
                    userGroup = groupDao.selectByPrimaryKey(groupId);
                }
                OperatorEntity operatorEntity = new OperatorEntity(
                        oe.getOperId(), oe.getOperAccount(),
                        oe.getOperPwd(), oe.getOperName(), oe.getSex(),
                        oe.getPhone(), oe.getGroupId(), oe.getState(),
                        oe.getOpenId(),oe.getMaintenanceCard(),oe.getCreateTime()
                );
                if(oe.getOperId() == 0){//超级管理员
                    List<ShopEntity> shops = shopDao.selectByParam(new ShopParam());
                    operatorEntity.setShops(shops);
                }else{//其他普通管理员
                    List<ShopEntity> shops = shopDao.selectByOperId(oe.getOperId());
                    operatorEntity.setShops(shops);
                }
                operatorEntity.setOperPwd(param.getOperPwd());
                OperatorSession operatorSession = new OperatorSession(operatorEntity, userGroup, permissions);
                HttpSession session = request.getSession();
                session.setAttribute(OperatorSession.SESSION_ID, operatorSession);
                return LoginEnum.SUCCESS;
            } else {
                return LoginEnum.PASSWORD_ERROR;
            }
        }
        return LoginEnum.NO_EXISTS;
    }
}
