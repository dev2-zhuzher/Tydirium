package com.vanke.tydirium.serviceimpl.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanke.tydirium.entity.log.LogLogin;
import com.vanke.tydirium.repository.log.LogLoginRepository;
import com.vanke.tydirium.service.log.LogLoginService;


@Service
@Transactional
public class LogLoginServiceImpl implements LogLoginService{

	@Autowired
	private LogLoginRepository logLoginRepository;
	
	@Override
	public LogLogin save(LogLogin logLogin) {
		return logLoginRepository.save(logLogin);
	}
	
	@Override
	public LogLogin findLogLoginBySessionId(String sessionId) {
		return logLoginRepository.findLogLoginBySessionId(sessionId);
	}
	
}
