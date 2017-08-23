package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 
 * 
 * @Description: Spring-Session配置
 *
 * @author: songjia 
 * @date: 2017年8月23日 上午11:16:16
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 2)
// 2分钟失效
public class RedisSessionConfig {

}