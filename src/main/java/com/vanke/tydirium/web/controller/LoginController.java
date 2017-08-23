package com.vanke.tydirium.web.controller;

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
	@Value("${zhuzher_api_host}")
	private String zhuzherApiHost;
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

	@Autowired
	SysUserService sysUserService;

	/**
	 * 跳转至登陆页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(Model model) {
		model.addAttribute("zhuzherApiHost", zhuzherApiHost);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientSecret", clientSecret);
		model.addAttribute("redirectUri", redirectUri);
		model.addAttribute("responseType", responseType);
		model.addAttribute("scopes", scopes);
		model.addAttribute("state", state);
		return "login";
	}

}