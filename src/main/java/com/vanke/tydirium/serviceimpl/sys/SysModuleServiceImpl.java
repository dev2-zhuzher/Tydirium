package com.vanke.tydirium.serviceimpl.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanke.tydirium.entity.sys.SysModule;
import com.vanke.tydirium.repository.sys.SysModuleRepository;
import com.vanke.tydirium.service.sys.SysModuleService;

@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService {

	@Autowired
	private SysModuleRepository sysModuleRepository;

	@Override
	public Page<SysModule> findAll(Pageable pageable) {
		return sysModuleRepository.findAll(pageable);
	}

	@Override
	public Page<SysModule> findAll(String moduleName, Pageable pageable) {
		return sysModuleRepository.findAll(new Specification<SysModule>() {
			@Override
			public Predicate toPredicate(Root<SysModule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				if (StringUtils.isNotEmpty(moduleName)) {
					predicate = cb.like(root.<String> get("name"), "%" + moduleName + "%");
//					expressions.add(cb.like(root.<String> get("moduleName"), "%" + moduleName + "%"));
				}
				return predicate;
			}
		}, pageable);
	}

	@Override
	public List<SysModule> findAll() {
		return sysModuleRepository.findAll(new Sort(Direction.DESC, "priority"));
	}

	@Override
	public Set<SysModule> findByModuleIdIn(Collection<Long> moduleIds) {
		return sysModuleRepository.findByIdIn(moduleIds);
	}

	@Override
	public SysModule save(SysModule sysModule) {
		return sysModuleRepository.save(sysModule);
	}

	@Override
	public SysModule findOne(Long moduleId) {
		if(moduleId == null) return null;
		return sysModuleRepository.findOne(moduleId);
	}

}
