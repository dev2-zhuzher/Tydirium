package com.vanke.tydirium.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @Description: redis工具类
 *
 * @author: songjia 
 * @date: 2017年7月18日 上午9:57:20
 */
@Component
public class RedisCacheUtil {

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 添加缓存，永不过期
	 * 
	 * @param key
	 * @param obj
	 */
	public void set(String key, Serializable obj) {
		this.redisTemplate.opsForValue().set(key, obj);
	}
	
	/**
	 * 添加缓存，存在返回false；如果不存在，就存入reidis返回true
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean setNx(String key, Serializable obj) {
		return this.redisTemplate.opsForValue().setIfAbsent(key, obj);
	}

	/**
	 * 添加缓存，带有效时间(0表示永不过期)
	 * 
	 * @param key
	 *            缓存key
	 * @param obj
	 *            缓存value
	 * @param timeOut
	 *            缓存失效时间，为0表示永不过期
	 */
	public void setExpire(String key, Serializable obj, long timeOut) {
		this.redisTemplate.opsForValue().set(key, obj, timeOut, TimeUnit.SECONDS);
	}
	
	/**
	 * 添加缓存，带有效时间(0表示永不过期)
	 * 
	 * @param key
	 *            缓存key
	 * @param obj
	 *            缓存value
	 * @param timeOut
	 *            缓存失效时间，为0表示永不过期
	 * @param timeUnit
	 *            时间单位
	 */
	public void setExpire(String key, Serializable obj, long timeOut, TimeUnit timeUnit) {
		this.redisTemplate.opsForValue().set(key, obj, timeOut, timeUnit);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) this.redisTemplate.opsForValue().get(key);
	}

	/**
	 * 刷新缓存有效时间
	 * 
	 * @param key
	 * @param timeOut
	 * @param unit
	 */
	public void expireKey(String key, long timeOut, TimeUnit unit) {
		this.redisTemplate.expire(key, timeOut, unit);
	}
	
	
	/**
	 * 刷新缓存有效时间
	 * 
	 * @param key
	 * @param timeOut
	 */
	public void expireKey(String key, long timeOut) {
		this.redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return this.redisTemplate.hasKey(key);
	}

	/**
	 * 批量删除缓存
	 * 
	 * @param key
	 */
	public void delKey(String... keys) {
		for (String key : keys) {
			this.redisTemplate.delete(key);
		}
	}

	/**
	 * 根据通配符删除缓存
	 * 
	 * @param pattern
	 */
	public void delKeyByPattern(String... patterns) {
		for (String pattern : patterns) {
			Set<String> keys = redisTemplate.keys(pattern);
			if (keys.size() > 0) {
				redisTemplate.delete(keys);
			}
		}
	}

	/**
	 * 存储list，不带有效时间
	 * 
	 * @param key
	 * @param list
	 */
	public <T> void setList(String key, List<T> list) {
		for (T temp : list) {
			this.redisTemplate.opsForList().rightPush(key, temp);
		}
	}

	/**
	 * 保存list，带有效时间
	 * 
	 * @param key
	 * @param list
	 * @param timeOut
	 */
	public <T> void setListExpire(String key, List<T> list, long timeOut) {
		for (T temp : list) {
			this.redisTemplate.opsForList().rightPush(key, temp);
		}
		this.redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
	}

	/**
	 * 获取list
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key, Class<T> clazz) {
		if (!exists(key)) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		List<Object> objList = this.redisTemplate.opsForList()
				.range(key, 0, -1);
		for (Object object : objList) {
			list.add((T) object);
		}
		return list;
	}
	/**
	 * 获取list
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {
		if (!exists(key)) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		List<Object> objList = this.redisTemplate.opsForList()
				.range(key, 0, -1);
		for (Object object : objList) {
			list.add((T) object);
		}
		return list;
	}
}
