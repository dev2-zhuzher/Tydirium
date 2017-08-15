package com.vanke.tydirium.serviceimpl.sys;

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

import com.vanke.tydirium.entity.sys.SysResource;
import com.vanke.tydirium.repository.sys.SysResourceRepository;
import com.vanke.tydirium.service.sys.SysResourceService;

@Service
@Transactional
public class SysResourceServiceImpl implements SysResourceService {

	@Autowired
	private SysResourceRepository sysResourceRepository;

	@Override
	public Page<SysResource> findAll(Pageable pageable) {
		return sysResourceRepository.findAll(pageable);
	}

	@Override
	public Page<SysResource> findAll(String resourceName, Pageable pageable) {
		return sysResourceRepository.findAll(new Specification<SysResource>() {
			@Override
			public Predicate toPredicate(Root<SysResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (!StringUtils.isEmpty(resourceName)) {
					predicate = cb.like(root.<String>get("name"), "%" + resourceName + "%");
//					expressions.add(cb.like(root.<String>get("resourceName"), "%" + resourceName + "%"));
				}
				return predicate;
			}
		}, pageable);
	}

	@Override
	public List<SysResource> findAll() {
		return sysResourceRepository.findAll(new Sort(Direction.DESC, "priority"));
	}

	@Override
	public Set<SysResource> findByResourceIdIn(Collection<Long> resourceIds) {
		return sysResourceRepository.findByIdIn(resourceIds);
	}

	public SysResource findOne(Long resourceId) {
		if (resourceId == null)
			return null;
		return sysResourceRepository.findOne(resourceId);
	}

	@Override
	public SysResource save(SysResource resource) {
		return sysResourceRepository.save(resource);
	}
}
