package com.grape.supermarket.dao;

import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.param.OperatorParam;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by LXP on 2017/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class OperatorDaoTest extends TestCase {
    @Autowired
    private OperatorDao operatorDao;

    @Test
    public void testSelectByParam() throws Exception {
        SelectEntity select = new SelectEntity();
        select.setOrderBy("oper_id desc");
        OperatorParam param = new OperatorParam();
        param.setSelect(select);
//        param.setOperAccount("test");
        List<OperatorEntity> operatorEntities = operatorDao.selectByParam(param);
    }

    @Test
    public void testSelectFullByParam() throws Exception {
        SelectEntity select = new SelectEntity();
        select.setOrderBy("oper_id desc");
        OperatorParam param = new OperatorParam();
        param.setSelect(select);
//        param.setOperAccount("test");
        List<OperatorEntity> operatorEntities = operatorDao.selectByParam(param);
        OperatorEntity operatorEntity = operatorEntities.get(0);
        System.out.println(operatorEntity.getUserGroup() == null);
        List<ShopEntity> shops = operatorEntity.getShops();
    }

}