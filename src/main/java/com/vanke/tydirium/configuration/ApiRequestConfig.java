package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * 
 * @Description: 对外请求api配置
 *
 * @author: 郭时青
 * @date: 2017年8月23日 上午11:55:31
 */
@Configuration
public class ApiRequestConfig {
	

	@Bean
	public LeBangApiRequester zhuZherApiRequester() {
		return new LeBangApiRequester();
	}
	
}
