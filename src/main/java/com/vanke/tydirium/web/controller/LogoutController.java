package com.vanke.tydirium.web.controller;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vanke.tydirium.entity.log.LogLogin;
import com.vanke.tydirium.service.log.LogLoginService;

/**
 * 
 * 
 * @Description: 登出操作
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午2:20:33
 */
@Controller
public class LogoutController extends BaseController {

	public final static String TOKEN = "token";
	public final static String NEXTURL = "next_url";

	/** 鉴权退出服务地址 */
	@Value("${lebang_api_host_oauth_out}")
	private String lebangApiHostOauthOut;
	/** 跳转至登陆地址 */
	@Value("${redirect_uri_login}")
	private String redirectUriLogin;

	@Autowired
	private LogLoginService logLoginService;

	/**
	 * 用户登出
	 * 
	 * @param redirectAttr
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttr, HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getParameter("accessToken");
		// 清除session中的属性
		Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			logger.warn("remove session key : " + em.nextElement());
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		// 使session失效
		request.getSession().invalidate();
		// 更新登出日志
		LogLogin login = logLoginService.findLogLoginBySessionId(request.getSession().getId());
		if (login != null) {
			login.setLogoutTime(new Date());
			logLoginService.save(login);
		}
		// 跳转回登录页
		if (StringUtils.isNotEmpty(accessToken)) {
			redirectAttr.addAttribute(TOKEN, accessToken);
			redirectAttr.addAttribute(NEXTURL, redirectUriLogin);
			return "redirect:" + lebangApiHostOauthOut;
		}
		return "redirect:/login";
	}

}
