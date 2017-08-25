package com.vanke.tydirium.service.log;

import com.vanke.tydirium.entity.log.LogLogin;

/**
 * 
 * 
 * @Description: 系统日志service接口
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午12:37:22
 */
public interface LogService {

	/**
	 * 保存登陆日志
	 * 
	 * @param logLogin
	 * @return
	 */
	LogLogin saveLoginLog(LogLogin logLogin);

	/**
	 * 根据sessionId查询登陆日志
	 * 
	 * @param sessionId
	 * @return
	 */
	LogLogin findLogLoginBySessionId(String sessionId);

}
