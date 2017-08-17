package com.vanke.tydirium.configuration;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.service.sys.SysUserService;
import com.vanke.tydirium.utils.ZhuZherApiRequester;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前台住这儿用户OAuth认证管理器
 * 
 * @author Joey
 *
 */
@Component(value = "zhuZherAuthenticationProvider")
public class ZhuZherAuthenticationProvider implements AuthenticationProvider {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ZhuZherApiRequester zhuZherApiRequester;

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/**
		 * 认证方式及流程：       住这儿用户通过登录页进行OAuth，认证逻辑如下： 
		 *              -->用OAuth接口获得的信息*INFO*在db中以openId查找，如果找到，则通过认证
		 *              -->如果openId查找失败，则以*INFO*中的mobile在db中查找用户，如果找到，则对用户fullname进行比对，如果比对成功，则通过认证
		 *              -->如果通过认证成，则更新db中的用户信息
		 *              -->检查用户是否在本系统中被禁用，如果被禁用同样拒绝登录
		 */
//		SysUser remoteSysUser = (SysUser) authentication.getPrincipal();
//		
//		// 检查openId进行正常登录
//		SysUser dbSysUser = sysUserService.findByoAuthOpenId(remoteSysUser.getoAuth().getOpenId(), SysUser.USER_TYPE_CUSTOMER);
//		
//		if(dbSysUser == null) {
//			// 为让在falcon被删除的用户使用相同手机号能够在市集登录，需把市集原来的用户的手机号设置成一个不要达的手机号
//			SysUser oldSysUser = sysUserService.findByMobile(remoteSysUser.getMobile(), SysUser.USER_TYPE_CUSTOMER);
//			if(oldSysUser != null) {
//				String mobileInvalidStr = oldSysUser.getMobile() + "_" + Double.valueOf(100000 + Math.random() * 899999).intValue();
//				logger.warn("检测到曾经使用过的手机号以新用户身份登录，为保证新用户能正常登录，给老用号手机号添加随机字符串，手机号：" + oldSysUser.getMobile()
//						+ ", 原用户openId: " + oldSysUser.getoAuth().getOpenId() + ", 原账户手机号变更为：" + mobileInvalidStr
//						+ ", 新用户openId： " + remoteSysUser.getoAuth().getOpenId());
//				oldSysUser.setMobile(mobileInvalidStr);
//				oldSysUser = sysUserService.save(oldSysUser);
//			}
//			
//			dbSysUser = new SysUser();
//			
//			// 首次登录预先存入openId
//			dbSysUser.setoAuth(new SysUserOAuth());
//			dbSysUser.getoAuth().setOpenId(remoteSysUser.getoAuth().getOpenId());
//			// 设置无法用于登录的随机UUID作为密码方便rememberMe登录
//			dbSysUser.setPassword(UUID.randomUUID().toString());
//
//			// 标记用户类型
//			dbSysUser.setUserType(SysUser.USER_TYPE_CUSTOMER);
//			
//			dbSysUser.setCreateTime(new Date());
//		}
//		
//		// 更新数据库中的员工信息
//		dbSysUser.setUpdateTime(new Date());
//		dbSysUser.setSex(remoteSysUser.getSex());
//		dbSysUser.setIdentityId(remoteSysUser.getIdentityId());
//		dbSysUser.setNickName(remoteSysUser.getNickName());
//		dbSysUser.setMobile(remoteSysUser.getMobile());
//		dbSysUser.setState(remoteSysUser.getState());
//		dbSysUser.setAvatarUrl(remoteSysUser.getAvatarUrl());
//		dbSysUser.setFullName(remoteSysUser.getFullName());
//		
//		dbSysUser.getoAuth().setAccessToken(remoteSysUser.getoAuth().getAccessToken());
//		dbSysUser.getoAuth().setRefreshToken(remoteSysUser.getoAuth().getRefreshToken());
//		dbSysUser.getoAuth().setExpires(remoteSysUser.getoAuth().getExpires());
//		
//		// 更新用户所属项目信息数据信息(注意：只更新了项目信息，如果后台人为添加了非项目的组织结构信息，则该信息保留，后续需要考虑用户信息是否应该通过其他方式更新而非用户触发更新)
//		try {
//			Set<String> projectIds = zhuZherApiRequester.requestUserProjects(dbSysUser.getoAuth().getAccessToken());
//			Set<Organization> inDBOrganizations = organizationRepository.findByCodeIn(projectIds);
//			if(!inDBOrganizations.isEmpty()) {
//				Set<Organization> organizations = dbSysUser.getOrganizations();
//				Iterator<Organization> iter = organizations.iterator();
//				while(iter.hasNext()) if(iter.next().getType().equals(Organization.OrgType.PROJECT)) iter.remove();
//				organizations.addAll(inDBOrganizations);
//				dbSysUser.setOrganizations(organizations);
//			}
//		} catch (Exception e) {
//			logger.warn("更新住这儿用户附属项目信息失败~");
//			logger.warn(ExceptionUtil.printFullStackTraceAndIgnoreLineFeed(e));
//		}
//		
//		
//		dbSysUser = sysUserService.save(dbSysUser);
//		
//		UserDetails userDetails = userDetailsService.loadUserByMobile(dbSysUser.getMobile(), SysUser.USER_TYPE_CUSTOMER, null);
//		
//		// 状态检查
//		AuthenticationChecks.check(userDetails);
//		authentication = new PreAuthenticatedAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
