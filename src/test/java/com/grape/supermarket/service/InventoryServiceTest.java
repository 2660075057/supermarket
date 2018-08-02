package com.grape.supermarket.service;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.grape.supermarket.entity.PageParam;
import com.grape.supermarket.entity.SelectEntity;
import com.grape.supermarket.entity.page.InventoryPageEntity;
import com.grape.supermarket.entity.param.InventoryParam;
import com.grape.supermarket.operator.service.InventoryService;

/**
 * 
 * author huanghuang 2017年10月18日 上午10:53:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class InventoryServiceTest extends TestCase {
	@Autowired
	private InventoryService inventoryService;

	@Test
	public void inventoryList() throws Exception {
   	 SelectEntity select = new SelectEntity();
   	InventoryParam param = new InventoryParam();
   	 param.setSelect(select);
   	 PageParam page = new PageParam();
   	 page.setPageCurrent(0);
   	 page.setPageSize(10);
   	 List<InventoryPageEntity> mpes = inventoryService.inventoryList(param, page);
   	 System.out.println(mpes.toString());
   
	}
	@Test
	public void countInventory() throws Exception {
		SelectEntity select = new SelectEntity();
		InventoryParam param = new InventoryParam();
		param.setSelect(select);
		int count = inventoryService.countInventory(param);
		System.out.println("count:" + count);
	}

}