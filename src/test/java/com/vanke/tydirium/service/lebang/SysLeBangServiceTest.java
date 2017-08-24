package com.vanke.tydirium.service.lebang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

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

import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.entity.log.LogLogin;


/**
 * 乐邦service测试类
 * @author v-guosq02
 *
 */
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SysLeBangServiceTest {

	
	@Autowired
	private SysLeBangRoleService sysLeBangRoleService;
	
	@Before
	public void setUp() throws Exception {
		SysLeBangRole sysLeBangRole = sysLeBangRoleService.findByLeBangRoleCode("LB20056");
		
		sysLeBangRole.setLebangRoleCode("LB20056");
		sysLeBangRole.setLeBangDescription("测试数据");
		sysLeBangRole.setRoleId(null);
		sysLeBangRole.setCreateTime(new Date());
		sysLeBangRoleService.save(sysLeBangRole);
	}
	
	@Test
	public void findByLeBangRoleCodeTest(){
		SysLeBangRole sysLeBangRole = sysLeBangRoleService.findByLeBangRoleCode("LB20056");
		assertNotNull(sysLeBangRole);
	}
	
	@Test
	public void findAllTest(){
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "id"));
		Page<SysLeBangRole> pager = sysLeBangRoleService.findAll("LB20056", pageable);
		assertEquals(1, pager.getContent().size());
	}
	
	@Test
	public void findOneTest(){
		SysLeBangRole sysLeBangRole = sysLeBangRoleService.findOne(15);
		assertNotNull(sysLeBangRole);
	}
}
