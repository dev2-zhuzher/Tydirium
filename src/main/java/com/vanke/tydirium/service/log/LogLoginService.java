package com.vanke.tydirium.service.log;

import com.vanke.tydirium.entity.log.LogLogin;
/**
 * 
 * 
 * @Description: 登陆日志service
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午12:37:22
 */
public interface LogLoginService {
	/**
	 * 保存登陆日志
	 * @param logLogin
	 * @return
	 */
	LogLogin save(LogLogin logLogin);
	/**
	 * 根据sessionId查询登陆日志
	 * @param sessionId
	 * @return
	 */
	LogLogin findLogLoginBySessionId(String sessionId);
	
}
