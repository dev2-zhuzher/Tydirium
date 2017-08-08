package com.penn.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.penn.admin.entity.sys.SysUser;
import com.penn.admin.service.sys.SysUserService;
import com.penn.admin.web.common.ResponseData;

@RestController
@RequestMapping(value = "/index")
public class IndexController extends BaseController {

	@Autowired
	SysUserService sysUserService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ResponseData hello(HttpServletRequest request) {
		return new ResponseData(0, "hello", "success");
	}

	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public ResponseData session(HttpSession session) {
		SysUser user = sysUserService.findOne(1L);
		session.setAttribute("user", user);
		return new ResponseData(0, user, "success");
	}

}