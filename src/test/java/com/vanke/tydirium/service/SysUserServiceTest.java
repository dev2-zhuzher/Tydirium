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

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.sys.SysUserService;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SysUserServiceTest {

	@Autowired
	private SysUserService sysUserService;

	private static final String USER_MOBILE = "17688968886";

	private Long userId = 0L;

	private static Logger logger = Logger.getLogger(SysUserServiceTest.class);

	@Before
	public void setUp() throws Exception {
		logger.info("初始化用户......");
		SysUser user = new SysUser();
		user.setAvatarUrl("");
		user.setDeleted(0);
		user.setEmail("1@1.com");
		user.setFullName("jipeng");
		user.setLoginName("peng");
		user.setMobile(USER_MOBILE);
		user.setNickName("penn");
		user.setPassword("123456");
		sysUserService.save(user);
		userId = user.getId();
	}

	@Test
	public void testFindAll() {
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "id"));
		Page<SysUser> pager = sysUserService.findAll(pageable);
		List<SysUser> users = pager.getContent();
		Assert.assertEquals(1, users.size());
	}

	@Test
	public void testFindOne() {
		SysUser user = sysUserService.findOne(userId);
		Assert.assertNotNull(user);
	}

	@Test
	public void testFindByMobile() {
		SysUser user = sysUserService.findByMobile(USER_MOBILE);
		Assert.assertNotNull(user);
	}

	@After
	public void tearDown() throws Exception {
		sysUserService.delete(userId);
	}

}
