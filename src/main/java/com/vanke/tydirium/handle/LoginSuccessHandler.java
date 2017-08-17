package com.vanke.tydirium.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.sys.SysUserService;

/**
 * 登录成功后记录日志
 * 
 * @author penn
 *
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SysUserService sysUserService;

	private static final Logger logger = Logger.getLogger(LoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
				logger.info("登录成功跳转首页...............");
				
				HttpSession session = (HttpSession) request;
				SysUser sysUser = sysUserService.findOne(((SysUser)authentication.getPrincipal()).getId());
				session.setAttribute("user", sysUser);
				redirectStrategy.sendRedirect(request, response, "main");
		
	}
}
