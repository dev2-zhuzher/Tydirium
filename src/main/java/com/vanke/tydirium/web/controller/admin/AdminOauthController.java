package com.vanke.tydirium.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.configuration.LeBangApiRequester;
import com.vanke.tydirium.web.controller.BaseController;

/**
 * 
 * 
 * @Description: oauth认证回调
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月24日 上午10:08:55
 */
@Controller
@RequestMapping("/oauth")
public class AdminOauthController extends BaseController {

	public static final String ACCESSTOKEN = "access_token";

	public static final String CODE = "code";

	public static final String PROJECTANDROLE = "projectAndRole";

	public static final String RESULT = "result";

	public static final String NICKNAME = "nickname";

	public static final String ROLECODE = "roleCode";

	@Autowired
	private LeBangApiRequester leBangApiRequester;

	/**
	 * 住这儿，乐邦登陆认证成功回调
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String oauth(Model model, HttpServletRequest request, HttpServletResponse response) {
		// 获取code
		String code = request.getParameter("code");
		String accessToken = request.getParameter("access_token");
		String roleCode = request.getParameter("roleCode");
		String projectCode = request.getParameter("projectCode");
		String msg = request.getParameter("msg");
		if (StringUtils.isNotEmpty(msg)) {
			model.addAttribute("msg", msg);
		}
		if (StringUtils.isNotEmpty(roleCode)) {
			model.addAttribute("roleCode", roleCode);
		}
		if (StringUtils.isNotEmpty(projectCode)) {
			model.addAttribute("projectCode", projectCode);
		}
		if (StringUtils.isNotEmpty(code)) {
			logger.info("用户认证，获取code值成功：" + code);
			// 通过code获取token信息
			try {
				String tokenResp = leBangApiRequester.requestAccessToken(code);
				JSONObject tokenObj = JSON.parseObject(tokenResp);
				if (tokenObj.containsKey("access_token") && StringUtils.isEmpty(accessToken)) {
					accessToken = tokenObj.getString("access_token");
				}
				if (StringUtils.isEmpty(accessToken)) {
					logger.warn("认证失败：住这儿用户通过传入code获取accessToken失败！");
					return "redirect:/auto/login";
				}
				// 获取当前登陆用户的项目和角色信息
				JSONArray projectAndRole = leBangApiRequester.requestProjectAndRole(accessToken);
				model.addAttribute(PROJECTANDROLE, projectAndRole);
				// 获取用户信息
				String userInfo = leBangApiRequester.requestUserInfo(accessToken);
				JSONObject userInfoJson = JSON.parseObject(userInfo);
				if (userInfoJson.containsKey(RESULT)) {
					userInfoJson = userInfoJson.getJSONObject(RESULT);
					if (userInfoJson.containsKey(NICKNAME)) {
						model.addAttribute(NICKNAME, userInfoJson.getString(NICKNAME));
					}
				}
				model.addAttribute(CODE, code);
				model.addAttribute(ACCESSTOKEN, accessToken);
				return "project";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("登陆失败:" + e.toString());
			}
			return "redirect:/auto/login";
		} else {
			logger.warn("认证失败：住这儿登陆获取code失败！");
		}
		return "redirect:/auto/login";
	}
}
