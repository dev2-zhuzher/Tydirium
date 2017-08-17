package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.vanke.tydirium.handle.LoginFailureHandler;
import com.vanke.tydirium.handle.LoginSuccessHandler;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 方法级别的权限控制
@Order(3)
public class WebAppConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("zhuZherAuthenticationProvider")
	private AuthenticationProvider zhuZherAuthenticationProvider;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http //
			.csrf() // 开启防止CSRF攻击
				.ignoringAntMatchers("/api/**", "/interfaces/**", "/payment/notify/**", "/druid/**") // 不需要防护的路径
				.and() //
			// 住这儿用户OAuth登录(前台)
			.addFilterBefore(zhuZherOAuthProcessFilter(), BasicAuthenticationFilter.class);
	}
	
	@Bean
	public ZhuZherOAuthProcessFilter zhuZherOAuthProcessFilter() {
		ZhuZherOAuthProcessFilter oauthProcessFilter = new ZhuZherOAuthProcessFilter();
		AuthenticationManager zhuZherAuthenticationManager = new ProviderManager(Arrays.<AuthenticationProvider>asList(zhuZherAuthenticationProvider));
		oauthProcessFilter.setAuthenticationManager(zhuZherAuthenticationManager);
		oauthProcessFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());//登录成功后跳转页面
		oauthProcessFilter.setAuthenticationFailureHandler(authenticationFailureHandler());//登录失败后跳转页面
		// 加入单账户多次登录控制策略，如需要对不同类型的用户客外进行控制则需要多份独立的sessionAuthenticationStrategy
		oauthProcessFilter.setSessionAuthenticationStrategy(new SessionAuthenticationStrategy(){
			@Override
			public void onAuthentication(Authentication authentication, HttpServletRequest request,
					HttpServletResponse response) throws SessionAuthenticationException {
				
			}});
		oauthProcessFilter.setFilterProcessesUrl("/oauth/token");
		oauthProcessFilter.setRememberMeServices(new RememberMeServices(){

			@Override
			public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
				return null;
			}

			@Override
			public void loginFail(HttpServletRequest request, HttpServletResponse response) {
				
			}

			@Override
			public void loginSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication successfulAuthentication) {
				
			}});
 		return oauthProcessFilter;
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new LoginFailureHandler();
	}
}