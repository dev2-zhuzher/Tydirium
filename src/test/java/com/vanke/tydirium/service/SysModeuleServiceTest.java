package com.vanke.tydirium.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
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

import com.vanke.tydirium.entity.sys.SysModule;
import com.vanke.tydirium.service.sys.SysModuleService;


@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SysModeuleServiceTest {
	
	@Autowired
	private SysModuleService SysModeulService;
	
	private Long moduleId = 0L;
	private String name = "学生";
	
	private static Logger logger = Logger.getLogger(SysModeuleServiceTest.class);
	
	@Before
	public void setUp() throws Exception {
		SysModule module = new SysModule();
		for(int i = 0; i<10; i++){
			logger.info("初始化sysModule-------");
			module.setId(moduleId+i);
			module.setName(name);
			module.setDescription("module"+i);
			module.setPriority(i);
			SysModeulService.save(module);
		}
		name = module.getName();
		moduleId = module.getId();
	}
	
	@Test
	public void testFindAllPage(){
		Integer page = 0;
		Integer size = 10;
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysModule> pages = SysModeulService.findAll(pageable);
		List<SysModule> list = pages.getContent();
		Assert.assertEquals(10, list.size());
	}
	
	@Test
	public void testCollection(){
		Set<Long> temp = new HashSet<>();
		temp.add(moduleId);
		Set<SysModule> resource = SysModeulService.findByModuleIdIn(temp);
		Assert.assertNotNull(resource);
	}
	
	@Test
	public void testFindModuleName(){
		Integer page = 0;
		Integer size = 10;
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		Page<SysModule> pages = SysModeulService.findAll(name,pageable);
		List<SysModule> list = pages.getContent();
		Assert.assertEquals(10, list.size());
	}
	
	@Test
	public void testFindAll(){
		List<SysModule> list = SysModeulService.findAll();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void testFindOne(){
 		SysModule module = SysModeulService.findOne(moduleId);
		Assert.assertNotNull(module);
	}
	

}
