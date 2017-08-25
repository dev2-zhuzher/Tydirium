package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vanke.tydirium.interceptor.LoginInterceptor;

/**
 * 
 * 
 * @Description: 拦截器配置
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月24日 上午9:50:34
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	/**
	 * 注册拦截器
	 * 
	 * @param registry
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 登录拦截器
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

}
