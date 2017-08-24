package com.vanke.tydirium.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vanke.tydirium.annotation.AdminCheckLogin;
import com.vanke.tydirium.constants.CommonConstants;

/**
 * 
 * 
 * @Description: 登陆拦截器
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月24日 上午9:36:06
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	protected static final Logger logger = Logger.getLogger(LoginInterceptor.class);

	/**
	 * 登陆拦截
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			// 判断目标接口上是否有AdminCheckLogin注解，有则验证登陆
			AdminCheckLogin adminCheckLogin = ((HandlerMethod) handler).getMethodAnnotation(AdminCheckLogin.class);
			if (adminCheckLogin != null && adminCheckLogin.needCheck()) {
				return this.checkLogin(request, response);
			}
		}
		return true;
	}

	/**
	 * 验证用户是否登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取用户信息session
		if (request.getSession().getAttribute(CommonConstants.SESSION_USER_KEY) == null) {
			// 重定向至登录页
			response.sendRedirect("/admin/login");
			return false;
		}
		return true;
	}

}
