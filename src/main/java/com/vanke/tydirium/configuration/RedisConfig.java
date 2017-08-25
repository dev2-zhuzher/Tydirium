package com.vanke.tydirium.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * 
 * @Description: redis配置
 *
 * @author: songjia
 * @date: 2017年8月25日 下午2:03:16
 */
@Configuration
public class RedisConfig {

	/**
	 * redis连接工厂配置
	 * 
	 * @return
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	/**
	 * redis操作模板配置
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean(name = "redisTemplate")
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
}
