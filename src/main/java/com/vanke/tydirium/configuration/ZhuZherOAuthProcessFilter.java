package com.vanke.tydirium.configuration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.utils.ZhuZherApiRequester;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 前台住这儿用户OAuth认证Filter
 * 
 * @author Joey
 *
 */
public class ZhuZherOAuthProcessFilter extends AbstractAuthenticationProcessingFilter {
	
	private final Logger logger = Logger.getLogger(this.getClass()); 
	
	public static final String STATE = "state_code_anti_csrf";

	private static final String INTERCEPTOR_PROCESS_URL = "/oauth/token";
	
	@Autowired
	private ZhuZherApiRequester zhuZherApiRequester;

	public ZhuZherOAuthProcessFilter() {
		super(INTERCEPTOR_PROCESS_URL);
	}

	public ZhuZherOAuthProcessFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		Assert.notNull(defaultFilterProcessesUrl, "Configuration error :: DefaultFilterProcessesUrl must be specified");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		Authentication authResult = null;
		
		String code = request.getParameter("code");
		
// 		去掉对state的验证，这相当于往falcon的认证去掉了_csrf验证，完美的方式是有以下步骤。
//		HttpSession session = request.getSession();
//		if(null == session) {
//			logger.warn("住这儿用户因认证前无session认证失败：如果频繁出现考虑去掉state验证！");
//			unsuccessfulAuthentication(request, response, null);
//			return authResult;
//		}
//		String sessionState = (String) session.getAttribute(STATE);
//		if(null == sessionState || null == state || !sessionState.equalsIgnoreCase(state)) {
//			logger.warn("住这儿用户因认证前state认证失败：如果频繁出现考虑去掉state验证！");
//			unsuccessfulAuthentication(request, response, null);
//			return authResult;
//		}
		
		try {
			// 依据falcon的接口定义获取access_token等信息
			String tokenResp = zhuZherApiRequester.requestAccessToken(code);
			JSONObject tokenObj = JSON.parseObject(tokenResp);
			String accessToken = tokenObj.getString("access_token");
			String refreshToken = tokenObj.getString("refresh_token");
			Date expires = new Date(tokenObj.getLong("expires") * 1000);
			
			if(accessToken == null || accessToken.length() < 1) {
				logger.warn("认证失败：住这儿用户通过传入code获取accessToken失败！");
				return authResult;
			}
			
			// 获取user信息进行OAuth认证登录
			String info_resp = zhuZherApiRequester.requestUserInfo(accessToken);
			JSONObject infoObj = JSON.parseObject(info_resp);
			if(infoObj.getIntValue("code") != 0) {
				logger.warn("认证失败：住这儿用户通过accessToken从falcon获取用户信息失败！");
				unsuccessfulAuthentication(request, response, null);
				return authResult;
			}
			
			
			infoObj = infoObj.getJSONObject("result");
			SysUser aPrincipal = new SysUser();
			aPrincipal.setUpdateTime(new Date());
			aPrincipal.setSex(infoObj.getInteger("sex"));
			aPrincipal.setIdentityId(infoObj.getString("identity_id"));
			aPrincipal.setNickName(infoObj.getString("nickname"));
			
			aPrincipal.setCreateTime(new Date());
			aPrincipal.setMobile(infoObj.getString("mobile"));
			aPrincipal.setAvatarUrl(infoObj.getString("avatar_url"));
			aPrincipal.setFullName(infoObj.getString("fullname"));
			
			
			
			Object credentials = null;
			PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(aPrincipal, credentials);
			authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
			authResult = getAuthenticationManager().authenticate(authRequest);
		} catch (Exception e) {
			logger.warn("认证过程出现异常!");
//			logger.warn(ExceptionUtil.printFullStackTraceAndIgnoreLineFeed(e));
			unsuccessfulAuthentication(request, response, null);
		}
		
		return authResult;
	}

}
