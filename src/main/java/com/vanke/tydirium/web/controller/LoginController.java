package com.vanke.tydirium.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	/** 鉴权服务地址 */
	@Value("${lebang_api_host}")
	private String lebangApiHost;
	/** 邮件申请获得的客户端ID */
	@Value("${client_id}")
	private String clientId;
	/** 秘钥 */
	@Value("${client_secret}")
	private String clientSecret;
	/** 跳转地址 */
	@Value("${redirect_uri}")
	private String redirectUri;
	@Value("${response_type}")
	private String responseType;
	@Value("${scopes}")
	private String scopes; 
	@Value("${state}")
	private String state; 
	
	private static final String token_uri = "/api/lebang/staffs/me/jobs/all";

	@Autowired
	SysUserService sysUserService;

	/**
	 * 跳转至登陆页面
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/login")
	public void login(Model model,HttpServletRequest request,HttpServletResponse response) {
		try {
			response.sendRedirect(lebangApiHost
					+token_uri+"?client_id="+clientId+"&client_secret="+clientSecret
					+"&redirect_uri="+redirectUri+"&response_type="+responseType+"&scopes="+scopes+"&state="+state
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}