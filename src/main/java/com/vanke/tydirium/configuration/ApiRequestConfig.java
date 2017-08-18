package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对外请求api配置
 * 
 * @author Joey
 *
 */
@Configuration
public class ApiRequestConfig {
	

	@Bean
	public ZhuZherApiRequester zhuZherApiRequester() {
		return new ZhuZherApiRequester();
	}
	
}
