package com.vanke.tydirium.log;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.vanke.tydirium.entity.log.LogLogin;
import com.vanke.tydirium.service.log.LogLoginService;
/**
 * 
 * 
 * @Description: 登陆日志管理测试
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午6:18:58
 */
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LogLoginserviceTest {

	@Autowired
	private LogLoginService logLoginService;
	
	private String sessionId = "dceaf2c6-431e-4297-9985-d068e1ba5468";
	
	@Before
	public void setUp() throws Exception {
		LogLogin login = new LogLogin();
		login.setDescription("登录成功");
		login.setLoginIp("127.0.0.1");
		login.setLoginName("12345678912");
		login.setLoginTime(new Date());
		login.setSuccessful(Boolean.TRUE);
		login.setSessionId(sessionId);
		logLoginService.save(login);
	}
	/**
	 * 根据sessionId查询登陆日志
	 */
	@Test
	public void findLogLoginBySessionIdTest(){
		LogLogin login = logLoginService.findLogLoginBySessionId(sessionId);
		assertNotNull(login);
	}
	
}
