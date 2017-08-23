package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
<<<<<<< HEAD
 * 对外请求api配置
 * 
 * @author Joey
 *
=======
 * 
 * 
 * @Description: 对外请求api配置
 *
 * @author: 郭时青
 * @date: 2017年8月23日 上午11:55:31
>>>>>>> a3a8a58e5fd0508fae71a100de56fe7c41a05191
 */
@Configuration
public class ApiRequestConfig {
	

	@Bean
	public LeBangApiRequester zhuZherApiRequester() {
		return new LeBangApiRequester();
	}
	
}
