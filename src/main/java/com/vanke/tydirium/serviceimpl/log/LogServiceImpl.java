package com.vanke.tydirium.serviceimpl.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanke.tydirium.entity.log.LogLogin;
import com.vanke.tydirium.repository.log.LogLoginRepository;
import com.vanke.tydirium.service.log.LogService;

/**
 * 
 * 
 * @Description: 系统日志service实现类
 *
 * @author: 郭时青
 * @date: 2017年8月25日 上午10:03:46
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Autowired
	private LogLoginRepository logLoginRepository;

	/**
	 * 保存登录日志
	 * 
	 * @param logLogin
	 * @return
	 * @see com.vanke.tydirium.service.log.LogService#saveLoginLog(com.vanke.tydirium.entity.log.LogLogin)
	 */
	@Override
	public LogLogin saveLoginLog(LogLogin logLogin) {
		return logLoginRepository.save(logLogin);
	}

	/**
	 * 根据sessionId查询登陆日志
	 * 
	 * @param sessionId
	 * @return
	 * @see com.vanke.tydirium.service.log.LogService#findLogLoginBySessionId(java.lang.String)
	 */
	@Override
	public LogLogin findLogLoginBySessionId(String sessionId) {
		return logLoginRepository.findLogLoginBySessionId(sessionId);
	}

}
