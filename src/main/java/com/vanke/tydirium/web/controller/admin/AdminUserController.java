package com.vanke.tydirium.web.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.configuration.LeBangApiRequester;
import com.vanke.tydirium.entity.json.LeBangUser;
import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.entity.log.LogLogin;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.lebang.SysLeBangRoleService;
import com.vanke.tydirium.service.log.LogLoginService;
import com.vanke.tydirium.service.sys.SysRoleService;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.tools.HttpTool;
import com.vanke.tydirium.web.controller.BaseController;

/**
 * 
 * 
 * @Description: 台管理员登录渠道
 *
 * @author: vanke-yuzn05 - vankeadmin
 * @date: 2017年8月24日 上午10:08:55
 */
@Controller
public class AdminUserController extends BaseController {

	public static final String ACCESSTOKEN = "access_token";

	public static final String CODE = "code";

	public static final String NICKNAME = "nickname";

	public static final String ROLECODE = "roleCode";

	public static final String MSG = "msg";

	public static final String USER = "user";

	public static final String RESULT = "result";

	public static final String PROJECTANDROLE = "projectAndRole";

	public static final String DESCRIPTION = "description";

	public static final String PROJECTCODE = "projectCode";

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

	/** 鉴权服务地址 */
	@Value("${lebang_api_host_oauth}")
	private String lebangApiHostOauth;

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

	@Value("${sign}")
	private String sign;

	@Autowired
	SysUserService sysUserService;

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	SysLeBangRoleService sysLeBangRoleService;

	@Autowired
	LeBangApiRequester leBangApiRequester;

	@Autowired
	private LogLoginService logLoginservice;

	/**
	 * 跳转至登陆页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

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

		// 登陆成功，跳转到首页
		return "main";
	}

	/**
	 * 登陆成功跳转至后台首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		return "main";
	}

	/**
	 * 登录页，点击住这儿按钮跳转地址
	 * 
	 * @param attr
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/auto/login", method = RequestMethod.GET)
	public String autoLogin(RedirectAttributes attr) {
		// attr会代入url作为参数
		String state = UUID.randomUUID().toString();
		attr.addAttribute("client_id", clientId);
		attr.addAttribute("client_secret", clientSecret);
		attr.addAttribute("redirect_uri", redirectUri);
		attr.addAttribute("response_type", responseType);
		attr.addAttribute("scopes", scopes);
		attr.addAttribute("state", state);
		attr.addAttribute("sign", sign);
		return "redirect:" + lebangApiHostOauth;
	}

	/**
	 * 住这儿，乐邦登陆认证成功之后的回调
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/oauth/token", method = RequestMethod.GET)
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
				logger.error("登陆失败:" + e.toString());
			}
			return "redirect:/auto/login";
		} else {
			logger.warn("认证失败：住这儿登陆获取code失败！");
		}
		return "redirect:/auto/login";
	}

	/**
	 * 根据当前登陆用户选择的项目角色进行登陆
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/oauth/login", method = RequestMethod.POST)
	public String oauthLogin(Model model, RedirectAttributes redirectAttr, HttpServletRequest request) {
		String code = request.getParameter(CODE);
		logger.info("当前登陆用户code:" + code);
		String accessToken = request.getParameter(ACCESSTOKEN);
		logger.info("当前登陆用户accessToken:" + accessToken);
		String roleCode = request.getParameter(ROLECODE);
		logger.info("当前登陆用户roleCode:" + roleCode);
		String projectCode = request.getParameter(PROJECTCODE);
		String description = request.getParameter(DESCRIPTION);
		if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(roleCode)) {
			try {
				// 获取乐邦角色与本地角色的关系
				SysLeBangRole lebangRole = sysLeBangRoleService.findByLeBangRoleCode(roleCode);
				// 获取本地角色
				Collection<Long> roleIds = new ArrayList<Long>();
				if (lebangRole != null && lebangRole.getRoleId() != null) {
					roleIds.add(lebangRole.getRoleId());
				}
				Set<SysRole> roles = sysRoleService.findByRoleIdIn(roleIds);
				// 获取用户信息
				String userInfo = leBangApiRequester.requestUserInfo(accessToken);
				LeBangUser userInfoJson = JSON.parseObject(userInfo, LeBangUser.class);
				SysUser sysUser = sysUserService.findByMobile(userInfoJson.getResult().getMobile());
				// 如果无此user，则新增
				if (sysUser == null) {
					sysUser = new SysUser();
					sysUser.setNickName(userInfoJson.getResult().getNickname());
					sysUser.setFullName(userInfoJson.getResult().getFullName());
					sysUser.setMobile(userInfoJson.getResult().getMobile());
					sysUser.setJobCanEdit(userInfoJson.getResult().getJobCanEdit());
					sysUser.setIdentityId(userInfoJson.getResult().getIdentityId());
					sysUser.setAvatarUrl(userInfoJson.getResult().getAvatarUrl());
					sysUser.setRoles(roles);
					sysUserService.save(sysUser);
				} else {
					// 更新角色信息
					sysUser.setRoles(roles);
					sysUserService.save(sysUser);
				}
				// 如果当前乐邦角色与本地角色无关联数据，则新增，并跳转
				if (CollectionUtils.isEmpty(roles)) {
					if (lebangRole == null) {
						lebangRole = new SysLeBangRole();
						// 乐邦角色code
						lebangRole.setLebangRoleCode(roleCode);
						// 描述
						lebangRole.setLeBangDescription(description);
						sysLeBangRoleService.save(lebangRole);
					}
					redirectAttr.addAttribute(CODE, code);
					redirectAttr.addAttribute(ACCESSTOKEN, accessToken);
					redirectAttr.addAttribute(ROLECODE, roleCode);
					redirectAttr.addAttribute(PROJECTCODE, projectCode);
					redirectAttr.addAttribute(MSG, "当前角色无权登入系统");
					// 无权进入：跳至选择项目角色页面
					return "redirect:/oauth/token";
				}
				// 记录登录成功日志
				LogLogin logLogin = new LogLogin(sysUser.getMobile(), Boolean.TRUE, new Date(),
						HttpTool.getRequestIp(request), "登录成功", request.getSession().getId());
				logLoginservice.save(logLogin);
				// 用户信息存入session
				request.getSession().setAttribute(USER,
						sysUserService.findByMobile(userInfoJson.getResult().getMobile()));
				request.getSession().setAttribute(ACCESSTOKEN, accessToken);
				logger.info("登入成功：" + sysUser.getFullName());
				return "redirect:/main";
			} catch (Exception e) {
				logger.error("选择角色登陆失败，roleCode：" + roleCode + ",错误详情：" + e.toString());
				return "redirect:/auto/login";
			}
		} else {
			logger.info("获取不到accessToken 或  roleCode ！");
			return "redirect:/login";
		}
	}

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
