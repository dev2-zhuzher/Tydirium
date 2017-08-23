package com.vanke.tydirium.serviceimpl.lebang;



import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.repository.lebang.SysLeBangRoleRepository;
import com.vanke.tydirium.service.lebang.SysLeBangRoleService;

@Service
@Transactional
public class SysLeBangRoleServiceImpl implements SysLeBangRoleService {

	@Autowired
	private SysLeBangRoleRepository sysLeBangRoleRepository;

	@Override
	public void save(SysLeBangRole role) {
		sysLeBangRoleRepository.save(role);
	}

	@Override
	public SysLeBangRole findByLeBangRoleCode(String leBangRoleCode) {
		return sysLeBangRoleRepository.findByLeBangRoleCode(leBangRoleCode);
	}

	@Override
	public Page<SysLeBangRole> findAll(String findLeBangCode, Pageable pageable) {
		return sysLeBangRoleRepository.findAll(new Specification<SysLeBangRole>() {
			@Override
			public Predicate toPredicate(Root<SysLeBangRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(findLeBangCode)) {
					predicate = cb.like(root.<String> get("leBangRoleCode"), "%" + findLeBangCode + "%");
				}
				return predicate;
			}
		}, pageable);
	}

	@Override
	public SysLeBangRole findOne(Integer id) {
		return sysLeBangRoleRepository.findOne(id);
	}
	
	

}