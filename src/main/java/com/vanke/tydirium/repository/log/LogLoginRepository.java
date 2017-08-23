package com.vanke.tydirium.repository.log;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vanke.tydirium.entity.log.LogLogin;

public interface LogLoginRepository extends JpaRepository<LogLogin, Serializable>, JpaSpecificationExecutor<LogLogin> {

	LogLogin findLogLoginBySessionId(String sessionId);

}
