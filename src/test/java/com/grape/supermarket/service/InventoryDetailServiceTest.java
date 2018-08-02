package com.grape.supermarket.service;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.grape.supermarket.entity.page.InventoryDetailPageEntity;
import com.grape.supermarket.entity.param.InventoryDetailParam;
import com.grape.supermarket.operator.service.InventoryDetailService;

/**
 * 
 * author huanghuang 2017年10月18日 上午10:53:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class InventoryDetailServiceTest extends TestCase {
	@Autowired
	private InventoryDetailService inventoryDetailService;

	@Test
	public void inventoryDetailList() throws Exception {
		InventoryDetailParam param = new InventoryDetailParam();
		/*param.setInventoryId(1);*/
		param.setTypeId(2);
		param.setCommName("卫龙辣条");
		List<InventoryDetailPageEntity> mpes = inventoryDetailService
				.inventoryDetailList(param);
		System.out.println(mpes.toString());

	}

}