package com.vanke.tydirium.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.configuration.ZhuZherApiRequester;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.web.controller.BaseController;

@Controller
@RequestMapping(value = "/oauth")
public class OauthController extends BaseController{
	
	@Autowired
	private ZhuZherApiRequester zhuZherApiRequester;
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * zhuzher回调接收方法
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/token")
	public String oauth(Model model,HttpServletRequest request,HttpServletResponse response){
		//获取code
		String code = request.getParameter("code");
		String tokenResp;
		try {
			//通过code获取token信息
			tokenResp = zhuZherApiRequester.requestAccessToken(code);
			JSONObject tokenObj = JSON.parseObject(tokenResp);
			String accessToken = tokenObj.getString("access_token");
			if(accessToken == null || accessToken.length() < 1) {
				logger.warn("认证失败：住这儿用户通过传入code获取accessToken失败！");
				return "login";
			}
			// 获取user信息进行OAuth认证登录
			String info_resp = zhuZherApiRequester.requestUserInfo(accessToken);
			JSONObject infoObj = JSON.parseObject(info_resp);
			if(infoObj.getIntValue("code") != 0) {
				logger.warn("认证失败：住这儿用户通过accessToken从falcon获取用户信息失败！");
				return "login";
			}
			//数据录入
			SysUser sysUser = new SysUser();
			sysUser.setSex(sexUtil(infoObj.getJSONObject("result").getString("sex")));
			sysUser.setNickName(infoObj.getJSONObject("result").getString("nickname"));
			sysUser.setFullName(infoObj.getJSONObject("result").getString("fullname"));
			sysUser.setId(infoObj.getJSONObject("result").getLong("id"));
			sysUser.setMobile(infoObj.getJSONObject("result").getString("mobile"));
			sysUser.setAvatarUrl("");
			sysUser.setDeleted(0);
			sysUser.setEmail("");
			sysUser.setLoginName("");
			sysUser.setPassword("");
			sysUserService.save(sysUser);
			request.getSession().setAttribute("user", sysUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main";
	}
	
	private int sexUtil(String sex){
		if (sex.equals("MALE")) {
			return 1;
		} else if (sex.equals("FEMALE")) {
			return 2;
		} else {
			return 3;
		}
	}
}
