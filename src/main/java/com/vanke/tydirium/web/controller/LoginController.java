package com.vanke.tydirium.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanke.tydirium.service.sys.SysUserService;
/**
 * 
 * @Description: 登陆操作
 *
 * @author: vanke-yuzn05 
 * @date: 2017年8月15日 上午11:46:13
 */
@Controller 
public class LoginController extends BaseController {

	@Autowired
	SysUserService sysUserService;

	/**
	 * 跳转至登陆页面
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	} 

}