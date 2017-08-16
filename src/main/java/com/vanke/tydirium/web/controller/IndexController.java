package com.vanke.tydirium.web.controller;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vanke.tydirium.entity.sys.SysModule;
import com.vanke.tydirium.entity.sys.SysResource;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.web.common.ResponseData;

@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController {

	@Autowired
	SysUserService sysUserService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ResponseData hello(HttpServletRequest request) {
		return new ResponseData(0, "hello", "success");
	}

	/**
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public String session(HttpSession session) {
		SysUser user = new SysUser();
		user.setDeleted(1);
		user.setEmail("88@163.com");
		user.setFullName("yuzhouneng");
		user.setNickName("yuzn");
		
		SysRole role = new SysRole();
		role.setDescription("超级管理员");
		role.setName("ROLE_ADMIN");
		role.setId(Long.valueOf("1"));
		SysRole role1 = new SysRole();
		role1.setDescription("超级管理员1");
		role1.setName("ROLE_ADMIN1");
		role1.setId(Long.valueOf("2"));
		
		SysModule module = new SysModule();
		module.setDescription("权限管理");
		module.setIcon("<!>123</i>");
		module.setId(Long.valueOf("1"));
		module.setName("权限管理");
		module.setPriority(1);  // 显示级别
		SysModule module1 = new SysModule();
		module1.setDescription("权限管理1");
		module1.setIcon("<i class='layui-icon'></i>");
		module1.setId(Long.valueOf("2"));
		module1.setName("权限管理1");
		module1.setPriority(2);  // 显示级别
		
		SysResource  resource = new SysResource();
		resource.setDescription("资源");
		resource.setId(Long.valueOf("1"));
		resource.setName("资源");
		resource.setUri("/index/session");
		SysResource  resource1 = new SysResource();
		resource1.setDescription("资源1");
		resource1.setId(Long.valueOf("2"));
		resource1.setName("资源1");
		resource1.setUri("/admin/sys/users");
		
		Set<SysRole>  roles = new  LinkedHashSet<SysRole>();
		Set<SysModule>  modules = new  LinkedHashSet<SysModule>();
		Set<SysResource>  resources = new  LinkedHashSet<SysResource>();
		
		resources.add(resource);
		resources.add(resource1);
		
		module.setResources(resources);
		module1.setResources(resources);
		
		modules.add(module);
		modules.add(module1);
		
		role.setModules(modules);
		role1.setModules(modules);
		
		roles.add(role);
		roles.add(role1);
		user.setRoles(roles); 
		session.setAttribute("user", user);
		
		return "main";
	}

}