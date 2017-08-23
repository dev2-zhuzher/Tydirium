package com.vanke.tydirium.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.tydirium.BaseTest;
import com.vanke.tydirium.mapper.SysUserMapper;

/**
 * 
 * 
 * @Description: mybatis集成测试
 *
 * @author: songjia
 * @date: 2017年8月23日 下午2:07:44
 */
public class SysUserMapperTest extends BaseTest {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Test
	public void testMybatis() {
		String nickName = this.sysUserMapper.queryNikcNameByMobile("17688968888");
		System.out.println(nickName);
	}
}
