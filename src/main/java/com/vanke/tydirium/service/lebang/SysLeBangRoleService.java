package com.vanke.tydirium.service.lebang;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanke.tydirium.entity.lebang.SysLeBangRole;

public interface SysLeBangRoleService {

	/**
	 * 更新或新增
	 * 
	 * @param role
	 */
	void save(SysLeBangRole role);

	/**
	 * 根据岗位信息查询
	 * 
	 * @param leBangRoleCode
	 * @return
	 */
	SysLeBangRole findByLeBangRoleCode(String leBangRoleCode);
	
	/**
	 * 根据岗位信息分页查询

	/**
	 * 根据岗位信息分页查询
	 * 
	 * @param findLeBangCode
	 * @param pageable
	 * @return
	 */
	Page<SysLeBangRole> findAll(String findLeBangCode,Pageable pageable);
	/**
	 * 通过id查询乐邦信息
	 * @param id
	 * @return
	 */
	SysLeBangRole findOne(Integer id);

}
