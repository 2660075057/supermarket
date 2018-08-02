package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.page.CustomerPageEntity;
import com.grape.supermarket.entity.param.CustomerParam;

import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2017/9/28.
 */

public interface CustomerService {

    /**
     * 根据openid 获取顾客信息
     * @param openId 顾客信息
     * @return 顾客信息，不存在返回null
     */
    CustomerEntity getCustomerByOpenId(String openId);


    List<CustomerPageEntity> customerList(CustomerParam param, PageParam page);

    int countCustomer(CustomerParam param);

    /**
     * 添加一个用户，若openId冲突则进行更新操作
     * @param param 用户信息
     */
    CustomerEntity addCustomer(CustomerEntity param);

    /**
     * 更新用户
     * @param param 用户信息
     */
    void updateCustomer(CustomerEntity param);

    /**
     * 通过主键查询顾客信息
     * author huanghuang
     * 2017年11月9日 下午2:26:18
     * @param coustId
     * @return
     */
    CustomerPageEntity selectByPrimaryKey(Integer coustId, Date startTime, Date endTime);

    /**
     * 增加进出店日志
     *
     * @param coustId 顾客id
     * @param shopId     商店id
     * @param flag       flag==0为进店，flag==1为出店
     * @param type       入口类型type==0为微信操作
     */
    void addShopLog(Integer coustId, Integer shopId, int flag, byte type);
}
