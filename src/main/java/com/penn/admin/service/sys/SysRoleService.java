package com.penn.admin.service.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.penn.admin.entity.sys.SysRole;

public interface SysRoleService {

	/**
	 * 分页查询所有角色
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SysRole> findAll(Pageable pageable);

	/**
	 * 根据角色名称查询
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SysRole> findAll(String roleName, Pageable pageable);

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	List<SysRole> findAll();

	/**
	 * 根据多个roleId查询角色
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<SysRole> findByRoleIdIn(Collection<Long> roleIds);

	/**
	 * 查找一个角色
	 * 
	 * @param roleId
	 * @return
	 */
	SysRole findOne(Long roleId);

	/**
	 * 根据角色名称查找角色
	 * 
	 * @param roleName
	 * @return
	 */
	SysRole findByName(String name);

	/**
	 * 编辑角色
	 * 
	 * @param sysRole
	 * @return
	 */
	SysRole save(SysRole sysRole);

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	void delete(Long roleId);

}
