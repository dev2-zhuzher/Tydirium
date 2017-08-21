package com.vanke.tydirium.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
public class LogoutController extends BaseController{
	
	@Value("${lebang_api_host}")
	private String lebangApiHost;
	
	@RequestMapping(value = "/logout")
	public void logout(HttpServletResponse response){
		try {
			response.sendRedirect(lebangApiHost+"/api/lebang/oauth/signout?token="+OauthController.ACCESSTOKEN+"&next_url=" + URLEncoder.encode("http://127.0.0.1:8080/login", "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
