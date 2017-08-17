package com.vanke.tydirium.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 登录失败后跳转地址
 * 
 * @author penn
 *
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private static final Logger logger = Logger.getLogger(LoginFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("登录失败跳转login页面................");
		redirectStrategy.sendRedirect(request, response,  "/login");
	}
}
