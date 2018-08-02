package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.page.OperatorPageEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by LXP on 2017/9/25.
 */

public interface OperatorService {
    enum LoginEnum {SUCCESS,DISABLE,PASSWORD_ERROR,NO_EXISTS,FAIL}

    enum AddEnum {SUCCESS,ACCOUNT_EXISTS,FAIL}

    enum UpdateEnum {SUCCESS, ACCOUNT_EXISTS, FAIL}
    /**
     * 根据id查询操作员
     * @param oper_id 操作员id
     * @return 若存在返回操作员信息
     */
    OperatorEntity selectByPrimaryKey(Integer oper_id);

    /**
     * 获取操作员列表，默认不包含自己以及超级管理员
     * @param param 查询参数
     * @param page 分页参数
     */
    List<OperatorPageEntity> operatorList(OperatorEntity param, PageParam page);

    List<OperatorEntity> selectOperator(OperatorEntity param);

    /**
     * 统计操作员列表，默认不包含自己以及超级管理员
     * @param param 查询参数
     */
    int countOperator(OperatorEntity param);

    /**
     * 删除操作员
     * @param oper_id 操作员id
     * @return 成功返回boolean
     */
    boolean deleteOperator(Integer oper_id);

    AddEnum addOperator(OperatorEntity operatorEntity, List<Integer> shopIds);

    UpdateEnum updateOperator(OperatorEntity operatorEntity,List<Integer> shopIds);

    LoginEnum login(OperatorEntity param, HttpServletRequest request);
}
