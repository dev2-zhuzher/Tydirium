package com.vanke.tydirium.web.controller.admin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.vanke.tydirium.annotation.AdminCheckLogin;
import com.vanke.tydirium.configuration.LeBangApiRequester;
import com.vanke.tydirium.constants.CommonConstants;
import com.vanke.tydirium.entity.json.LeBangUser;
import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.entity.log.LogLogin;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.bo.LoginUser;
import com.vanke.tydirium.service.lebang.SysLeBangRoleService;
import com.vanke.tydirium.service.log.LogLoginService;
import com.vanke.tydirium.service.sys.SysRoleService;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.tools.CryptoUtil;
import com.vanke.tydirium.tools.HttpTool;
import com.vanke.tydirium.web.controller.BaseController;

/**
 * 
 * 
 * @Description: 台管理员登录渠道
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月24日 上午10:08:55
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminUserController extends BaseController {

	public static final String ACCESSTOKEN = "access_token";

	public static final String CODE = "code";

	public static final String NICKNAME = "nickname";

	public static final String ROLECODE = "roleCode";

	public static final String MSG = "msg";

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
	public String login(HttpServletRequest request) {
		SysUser user = (SysUser) request.getSession().getAttribute(CommonConstants.SESSION_USER_KEY);
		// 判断是否已登陆
		if (user != null) {
			return "redirect:/admin/index";
		}
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
		return "redirect:/admin/index";
	}

	/**
	 * 登陆成功跳转至后台首页
	 * 
	 * @return
	 */
	@AdminCheckLogin
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex() {
		return "index";
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
				LogLogin logLogin = new LogLogin(sysUser.getMobile(), Boolean.TRUE, new Date(), HttpTool.getRequestIp(request), "登录成功", request.getSession().getId());
				logLoginservice.save(logLogin);
				// 用户信息存入session
				request.getSession().setAttribute(CommonConstants.SESSION_USER_KEY, sysUserService.findByMobile(userInfoJson.getResult().getMobile()));
				request.getSession().setAttribute(ACCESSTOKEN, accessToken);
				logger.info("登入成功：" + sysUser.getFullName());
				return "redirect:/admin/index";
			} catch (Exception e) {
				logger.error("选择角色登陆失败，roleCode：" + roleCode + ",错误详情：" + e.toString());
				return "redirect:/admin/auto/login";
			}
		} else {
			logger.info("获取不到accessToken 或  roleCode ！");
			return "redirect:/admin/login";
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
	@AdminCheckLogin
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttr, HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getParameter("accessToken");
		// 使session失效
		request.getSession().invalidate();
		// 更新登出日志
		LogLogin login = logLoginService.findLogLoginBySessionId(request.getSession().getId());
		if (login != null) {
			login.setLogoutTime(new Date());
			logLoginService.save(login);
		}
		// 跳转回住这儿登录页
		if (StringUtils.isNotEmpty(accessToken)) {
			redirectAttr.addAttribute(TOKEN, accessToken);
			redirectAttr.addAttribute(NEXTURL, redirectUriLogin);
			return "redirect:" + lebangApiHostOauthOut;
		}
		return "redirect:/admin/login";
	}

	/**
	 * 校验账号密码登录
	 * 
	 * @param loginName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseInfo check(@RequestBody LoginUser loginUser, HttpSession httpSession) {

		// 用户名
		String loginName = loginUser.getLoginName();
		if (StringUtils.isBlank(loginName)) {
			return ResponseInfo.getFailInstance("账号不能为空");
		}
		// 密码
		String password = loginUser.getPassword();
		if (StringUtils.isBlank(password)) {
			return ResponseInfo.getFailInstance("密码不能为空");
		}
		// 验证码
		String checkCode = loginUser.getCheckCode();
		if (StringUtils.isBlank(checkCode)) {
			return ResponseInfo.getFailInstance("验证码不能为空");
		} else {
			// 从session中获取验证码
			String sessionCode = (String) httpSession.getAttribute("picCode");
			if (!StringUtils.equalsIgnoreCase(checkCode, sessionCode)) {
				return ResponseInfo.getFailInstance("验证码错误");
			}
		}
		
		SysUser sysUser = sysUserService.findByMobile(loginName);
		if (sysUser == null) {
			return ResponseInfo.getFailInstance("用户名不存在");
		}
		// 密码验证
		if (!CryptoUtil.validateHash(password, sysUser.getPassword())) {
			return ResponseInfo.getFailInstance("密码错误");
		}
		// 登陆成功，存储到session中
		httpSession.setAttribute(CommonConstants.SESSION_USER_KEY, sysUser);
		
		return ResponseInfo.getSuccessInstance(null);
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image/update", method = RequestMethod.GET)
	public void updateImageCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			BufferedImage bi = new BufferedImage(68, 22, BufferedImage.TYPE_INT_BGR);
			Graphics g = bi.getGraphics();
			Color c = new Color(250, 150, 255);
			g.setColor(c);
			g.fillRect(0, 0, 100, 50);
			// 验证码字符集合
			char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
			Random r = new Random();
			int len = ch.length;
			int index;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 4; ++i) {
				index = r.nextInt(len);
				// 设置验证码字符随机颜色
				g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
				// 画出对应随机的验证码字符
				g.drawString(ch[index] + "", (i * 15) + 3, 18);
				sb.append(ch[index]);
			}
			// 把验证码字符串放入Session
			request.getSession().setAttribute("picCode", sb.toString());
			// 在HttpServletResponse中写入验证码图片信息
			ImageIO.write(bi, "JPG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("生成验证码失败：" + e.toString());
		}
	}
}
