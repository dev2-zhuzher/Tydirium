package com.vanke.tydirium.serviceimpl.sys;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanke.tydirium.entity.sys.SysRole;
import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.repository.sys.SysRoleRepository;
import com.vanke.tydirium.service.sys.SysRoleService;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleRepository sysRoleRepository;

	@Override
	public Page<SysRole> findAll(Pageable pageable) {
		return sysRoleRepository.findAll(pageable);
	}

	@Override
	public Page<SysRole> findAll(String name, Pageable pageable) {
		return sysRoleRepository.findAll(new Specification<SysRole>() {
			@Override
			public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (StringUtils.isNotEmpty(name)) {
					expressions.add(cb.like(root.<String>get("name"), "%" + name + "%"));
				}
				return predicate;
			}
		}, pageable);
	}

	@Override
	public List<SysRole> findAll() {
		return sysRoleRepository.findAll(new Sort(Direction.DESC, "id"));
	}

	@Override
	public Set<SysRole> findByRoleIdIn(Collection<Long> roleIds) {
		return sysRoleRepository.findByIdIn(roleIds);
	}

	@Override
	public SysRole findByName(String name) {
		return sysRoleRepository.findByName(name);
	}

	@Override
	public SysRole findOne(Long roleId) {
		if (roleId == null)
			return null;
		return sysRoleRepository.findOne(roleId);
	}

	@Override
	public SysRole save(SysRole sysRole) {
		return sysRoleRepository.save(sysRole);
	}

	@Override
	public void delete(Long roleId) {
		sysRoleRepository.delete(roleId);
	}

	@Override
	public List<SysRole> rolesCheck(SysUser user, List<SysRole> roles) {
		if(user == null || CollectionUtils.isEmpty(user.getRoles())){
			return roles;
		}
		Iterator<SysRole> rolesIterator = roles.iterator();
		SysRole roleThis = null;
		SysRole userRoleThis = null;
		// 所有的role
		while (rolesIterator.hasNext()) {
			roleThis = rolesIterator.next();
			Iterator<SysRole> userRoles = user.getRoles().iterator();
			// 当前用户拥有的role
			while (userRoles.hasNext()) {
				userRoleThis = userRoles.next();
				if(Long.compare(roleThis.getId(), userRoleThis.getId()) == 0){
					roleThis.setIsCheck(true);
					break;
				}
			}
		}
		return roles;
	}
}