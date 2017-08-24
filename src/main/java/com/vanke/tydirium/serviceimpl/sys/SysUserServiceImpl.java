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

import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.repository.sys.SysUserRepository;
import com.vanke.tydirium.service.sys.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserRepository sysUserRepository;

	@Override
	public SysUser findByMobile(String mobile) {
		return sysUserRepository.findByMobile(mobile);
	}

	@Override
	public SysUser findOne(Long userId) {
		return sysUserRepository.findOne(userId);
	}

	@Override
	public Page<SysUser> findAll(Pageable pageable) {
		return sysUserRepository.findAll(pageable);
	}
	
	@Override
	public Page<SysUser> findAll(String nickName,String mobile, Pageable pageable) {
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
	@Override
	public SysUser save(SysUser sysUser) {
		return sysUserRepository.saveAndFlush(sysUser);
	}

	@Override
	public void delete(Long userId) {
		sysUserRepository.delete(userId);
	}
	
	@Override
	public SysUser findByCheck(String account, String password) throws Exception {
		SysUser sysUser = sysUserRepository.findByCheck(account, password);
		if(sysUser == null){
			throw new Exception("账号或密码错误");
		}
		return sysUser;
	}


}
