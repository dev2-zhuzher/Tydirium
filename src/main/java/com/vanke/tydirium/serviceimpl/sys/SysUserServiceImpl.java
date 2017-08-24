package com.vanke.tydirium.serviceimpl.sys;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.repository.sys.SysUserRepository;
import com.vanke.tydirium.service.sys.SysUserService;

/**
 * 
 * 
 * @Description: 后台用户service接口实现类
 *
 * @author: vanke-yuzn05 - vankeadmin
 * @date: 2017年8月24日 下午7:01:45
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserRepository sysUserRepository;

	/**
	 * 根据手机号码查询用户信息
	 * 
	 * @param mobile
	 * @return
	 * @see com.vanke.tydirium.service.sys.SysUserService#findByMobile(java.lang.String)
	 */
	@Override
	public SysUser findByMobile(String mobile) {
		return sysUserRepository.findByMobile(mobile);
	}

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 * @see com.vanke.tydirium.service.sys.SysUserService#findOne(java.lang.Long)
	 */
	@Override
	public SysUser findOne(Long userId) {
		return sysUserRepository.findOne(userId);
	}

	/**
	 * 分页查询用户列表
	 * 
	 * @param pageable
	 * @return
	 * @see com.vanke.tydirium.service.sys.SysUserService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<SysUser> findAll(Pageable pageable) {
		return sysUserRepository.findAll(pageable);
	}

	/**
	 * 分页查询用户列表(带查询条件)
	 * 
	 * @param nickName
	 * @param mobile
	 * @param pageable
	 * @return
	 * @see com.vanke.tydirium.service.sys.SysUserService#findAll(java.lang.String,
	 *      java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<SysUser> findAll(String nickName, String mobile, Pageable pageable) {
		return sysUserRepository.findAll(new Specification<SysUser>() {
			@Override
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (StringUtils.isNotEmpty(nickName)) {
					expressions.add(cb.like(root.<String>get("nickName"), "%" + nickName + "%"));
				}
				if (StringUtils.isNotEmpty(mobile)) {
					expressions.add(cb.like(root.<String>get("mobile"), "%" + mobile + "%"));
				}
				return predicate;
			}
		}, pageable);
	}

	/**
	 * 保存/更新用户
	 * 
	 * @param sysUser
	 * @return
	 * @see com.vanke.tydirium.service.sys.SysUserService#save(com.vanke.tydirium.entity.sys.SysUser)
	 */
	@Override
	public SysUser save(SysUser sysUser) {
		return sysUserRepository.saveAndFlush(sysUser);
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @see com.vanke.tydirium.service.sys.SysUserService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long userId) {
		sysUserRepository.delete(userId);
	}
}
