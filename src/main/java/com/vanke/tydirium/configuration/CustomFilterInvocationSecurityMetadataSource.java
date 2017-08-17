package com.vanke.tydirium.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.vanke.tydirium.entity.sys.SysModule;
import com.vanke.tydirium.entity.sys.SysResource;
import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.service.sys.SysRoleService;

@Order(0)
@Aspect
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static final Logger logger = Logger.getLogger(CustomFilterInvocationSecurityMetadataSource.class);

	/**
	 * 拒绝请求的随机角色名称
	 */
	public static final SecurityConfig ROLE_REJECT_ALL = new CustomSecurityConfig("ROLE_REJECT_ALL_REQUEST_" + Math.random());

	/**
	 * DB预载入路径权限Mapper
	 */
	private Map<String, Set<ConfigAttribute>> rbacResouceMap;

	/**
	 * RedisTemplate
	 */
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 用于同步刷新权限数据的同步锁
	 */
	private AtomicLong syncTimeLock = new AtomicLong();

	/**
	 * RBAC权限有效期，过期尝试从Redis更新
	 */
	private static final Long RBAC_UPDATE_TIMEOUT = 1000L * 32; // ms

	/**
	 * 权限数据存储Key
	 */
	private static final String BLACKPEARL_RBAC_DATA_STORE_KEY = "blackpearl:rbac:data:store";

	/**
	 * 全局无需授权访问的uri通配符规则
	 */
	private final List<String> PERMIT_ALL_URI = Collections.unmodifiableList(Arrays.<String> asList("/favicon.ico", "/",
			"/index", "/error/**", "/_csrf", "/login", "/oauth", "/logout", "/admin/login", "/admin/dologin",
			"/admin/autologin", "/admin/oauth", "/admin/logout", "/merchant/login", "/merchant/dologin",
			"/merchant/logout", "/payment/notify/**", "/interfaces/statistics/sunshine/ranking", "/interfaces/share/**",
			"/interfaces/support/**", "/interfaces/goods/list", "/interfaces/goods/detail/**",
			"/interfaces/goods/info/**", "/interfaces/category/list", "/admin/qrcode/show/**", "/admin/qrcode/url/**",
			"/druid/*", "/druid/css/*", "/druid/js/*", "/interfaces/recommendation/**", "/interfaces/navigation/**",
			"/interfaces/goods/search", "/admin/goods/template/test", "/interfaces/convenience/**",
			"/interfaces/statistics/sunshine/topXUser", "/interfaces/advertisement/**",
			"/interfaces/order/orderops/status_code", "/interfaces/supplier/**", "/interfaces/user/activity/**",
			"/interfaces/surprise/list/**", "/interfaces/goods/list/**"));

	private final Collection<ConfigAttribute> PERMIT_ALL_ROLE_COLLECTION = Collections
			.unmodifiableCollection(Collections.<ConfigAttribute>emptySet());

	public CustomFilterInvocationSecurityMetadataSource(RedisConnectionFactory redisConnectionFactory) {
		// 此处使用独立的RedisTemplate，不共享序列化，防止异常
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();;
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setEnableDefaultSerializer(true);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.afterPropertiesSet();
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 拒绝服务
	 */
	private final Collection<ConfigAttribute> REJECT_ALL_RQUEST_ROLE_COLLECTION = Collections
			.unmodifiableCollection(Sets.<ConfigAttribute>newSet(ROLE_REJECT_ALL));

	@Autowired(required = true)
	private SysRoleService sysRoleService;

	private void AddUriMapperRole(Map<String, Set<ConfigAttribute>> rsMap, List<String> Uris,
			Collection<ConfigAttribute> roles) {
		for (String resourceUri : Uris) {
			if (rsMap.get(resourceUri) != null) {
				roles.forEach(role -> rsMap.get(resourceUri).add(new CustomSecurityConfig(role.getAttribute())));
			} else {
				Set<ConfigAttribute> confAttr = new HashSet<ConfigAttribute>();
				roles.forEach(role -> confAttr.add(new CustomSecurityConfig(role.getAttribute())));
				rsMap.put(resourceUri, confAttr);
			}
		}
	}

	/**
	 * 加载/重载路径与角色映射关系
	 * 
	 * @return
	 */
	public FilterInvocationSecurityMetadataSource loadResource() {
		logger.debug("CustomFilterInvocationSecurityMetadataSource()......");
		Map<String, Set<ConfigAttribute>> dbResouceMap = new HashMap<String, Set<ConfigAttribute>>();
		List<SysRole> roles = sysRoleService.findAll();// 获得所有角色
		for (SysRole role : roles) {
			Set<SysModule> modules = role.getModules();// 获得所有角色关联的模块信息
			for (SysModule module : modules) {
				Set<SysResource> resources = module.getResources();// 获得模块管理的资源信息
				for (SysResource resource : resources) {
					String[] resourceUris = resource.getUri().split(",");
					for (String resourceUri : resourceUris) {
						ConfigAttribute confAttr = new CustomSecurityConfig(role.getName());// 将角色名称交给SS
						resourceUri = resourceUri.trim();
						if (null != dbResouceMap.get(resourceUri)) {
							dbResouceMap.get(resourceUri).add(confAttr);// 将角色和资源关联
						} else {
							Set<ConfigAttribute> confAttrSet = new HashSet<ConfigAttribute>();
							confAttrSet.add(confAttr);
							dbResouceMap.put(resourceUri, confAttrSet);// 将角色和资源关联
						}
						logger.debug("\t" + resourceUri + " : " + confAttr);
					}
				}
			}
		}

		// 注入预置 角色权限<=>路径 映射
		AddUriMapperRole(dbResouceMap, PERMIT_ALL_URI, PERMIT_ALL_ROLE_COLLECTION);

		syncTimeLock.set(System.currentTimeMillis());
		rbacResouceMap = Collections.unmodifiableMap(dbResouceMap);
		logger.debug("role/uri mapper now is：" + dbResouceMap);
		rbacSyncToRedis(dbResouceMap);
		return this;
	}


	/**
	 * 同步权限信息到Redis中
	 */
	private void rbacSyncToRedis(Map<String, Set<ConfigAttribute>> dbResouceMap) {
		// 权限修改发送到Redis
		logger.info("sync RBAC data to redis.");
		redisTemplate.boundValueOps(BLACKPEARL_RBAC_DATA_STORE_KEY).set(dbResouceMap);
		logger.info("role/uri mapper now is：" + dbResouceMap);
	}

	/**
	 * 从Redis中同步信息到本地
	 */
	private void rbacSyncFromRedis() {
		Long currentTimeMillis = System.currentTimeMillis();
		Long syncTimeMillis = syncTimeLock.get();
		if (currentTimeMillis - syncTimeMillis > RBAC_UPDATE_TIMEOUT
				&& syncTimeLock.compareAndSet(syncTimeMillis, currentTimeMillis)) {
			// 执行更新操作，其他线程操作更好，在另外的分支上有
			try {
				@SuppressWarnings("unchecked")
				Map<String, Set<ConfigAttribute>> redisRBAC = (Map<String, Set<ConfigAttribute>>) redisTemplate
						.boundValueOps(BLACKPEARL_RBAC_DATA_STORE_KEY).get();
				if (redisRBAC != null && redisRBAC.size() > 0) {
					rbacResouceMap = Collections.unmodifiableMap(redisRBAC);
				}
			} catch (Exception e) {
				logger.warn("update rbac data error: " + ExceptionUtils.getRootCauseStackTrace(e));
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
		rbacSyncFromRedis(); // 触发超时检查
		logger.debug("CustomFilterInvocationSecurityMetadataSource.getAttributes()......");
		FilterInvocation filterInvocation = (FilterInvocation) obj;
		String requestUrl = filterInvocation.getRequestUrl();

		final Map<String, Set<ConfigAttribute>> resouceMap = rbacResouceMap;

		Iterator<String> iter = resouceMap.keySet().iterator();
		Collection<ConfigAttribute> attributes = null;

		while (iter.hasNext()) {
			String key = iter.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(key);
			if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
				logger.debug("\t" + requestUrl + " : " + key);
				Set<ConfigAttribute> neededRoles = resouceMap.get(key);
				// 此处对需要角色为空的已匹配URL调整为立即返回，由于有些地址有角色能访问，没有角色也能访问，允许没有角色访问的List为空的，
				// 当匹配到多条规则时，如果其中有一条为空，则表明此url不需要角色也能访问，固不需要匹配更多的角色直接返回即可
				if (neededRoles.isEmpty()){
					return neededRoles;
				}
				if (attributes == null) {
					attributes = new ArrayList<ConfigAttribute>();
				}
				attributes.addAll(resouceMap.get(key));
			}
		}

		if (attributes != null) {
			return attributes;
		}

		// 其他未授权地址一律拒绝访问
		return REJECT_ALL_RQUEST_ROLE_COLLECTION;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
