package com.grape.supermarket.dao;

import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PermissionEntity;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by LXP on 2017/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class GroupDaoTest extends TestCase {
    @Autowired
    private GroupDao groupDao;

    @Test
    public void testSelectByParam() throws Exception {
        GroupEntity groupEntity = groupDao.selectByPrimaryKey(1);
        System.out.println(groupEntity);
    }

}