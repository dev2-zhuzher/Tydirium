package com.penn.admin.serviceimpl.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.penn.admin.entity.sys.SysRole;
import com.penn.admin.repository.sys.SysRoleRepository;
import com.penn.admin.service.sys.SysRoleService;

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
				if (!StringUtils.isEmpty(name)) {
					expressions.add(cb.like(root.<String>get("name"), "%" + name + "%"));
				}
				return predicate;
			}
		}, pageable);
	}

	@Override
	public List<SysRole> findAll() {
		return sysRoleRepository.findAll(new Sort(Direction.DESC, "roleId"));
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
}