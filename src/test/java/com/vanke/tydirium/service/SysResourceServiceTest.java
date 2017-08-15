package com.vanke.tydirium.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.vanke.tydirium.entity.sys.SysResource;
import com.vanke.tydirium.service.sys.SysResourceService;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SysResourceServiceTest {
	
	@Autowired
	private SysResourceService sysResourceService;
	
	private String name = "TEST";
	
	private Long id = 0L;
	
	private static Logger logger = Logger.getLogger(SysResourceServiceTest.class);
	
	@Before
	public void setUp() throws Exception {
		SysResource resource = new SysResource();
		for(int i = 0; i<10; i++){
			logger.info("初始化sysModule-------");
			resource.setId(id+i);
			resource.setName(name);
			resource.setDescription("module"+i);
			resource.setPriority(i);
			resource.setUri("http://www.baidu.com");
			sysResourceService.save(resource);
		}
		name = resource.getName();
		id = resource.getId();
	}
	
	
	@Test
	public void testFindAll(){
		List<SysResource> list = sysResourceService.findAll();
		Assert.assertNotNull(list);
	}
	
	
	@Test
	public void testFindAllPage(){
		Integer page = 0;
		Integer size = 10;
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysResource> pages = sysResourceService.findAll(pageable);
		List<SysResource> list = pages.getContent();
		Assert.assertEquals(10, list.size());
	}
	
	@Test
	public void testFindModuleName(){
		Integer page = 0;
		Integer size = 10;
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysResource> pages = sysResourceService.findAll(name,pageable);
		List<SysResource> list = pages.getContent();
		Assert.assertEquals(10, list.size());
	}
	
	@Test
	public void testFindOne(){
		SysResource module = sysResourceService.findOne(id);
		Assert.assertNotNull(module);
	}
	
	
}
