package com.penn.admin.service;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.penn.admin.entity.sys.SysRole;
import com.penn.admin.service.sys.SysRoleService;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SysRoleServiceTest {

	@Autowired
	private SysRoleService sysRoleService;

	private static String ROLE_NAME = "TEST_ROLE";

	private Long roleId = 0L;

	private static Logger logger = Logger.getLogger(SysRoleServiceTest.class);

	@Before
	public void setUp() throws Exception {
		logger.info("初始化角色......");
		SysRole role = new SysRole();
		role.setName(ROLE_NAME);
		role.setDescription("测试角色");
		sysRoleService.save(role);
		roleId = role.getId();
	}

	@Test
	public void testFindOne() {
		SysRole role = sysRoleService.findOne(roleId);
		Assert.assertNotNull(role);
	}

	@Test
	public void findByRoleName() {
		SysRole role = sysRoleService.findByName(ROLE_NAME);
		Assert.assertNotNull(role);
	}

	@After
	public void tearDown() throws Exception {
		sysRoleService.delete(roleId);
	}

}
