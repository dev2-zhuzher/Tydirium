package com.vanke.tydirium.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.web.controller.BaseController;

/**
 * @deprecated:后台管理员登录渠道
 * 
 * @author v-guosq02
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminLoginController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 本地登陆（非oauth登陆）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

		String account = request.getParameter("account");

		SysUser sysUser = sysUserService.findByMobile(account);

		request.getSession().setAttribute("user", sysUser);

		return "main";
	}

}
