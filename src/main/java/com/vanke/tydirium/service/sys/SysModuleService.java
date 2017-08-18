package com.vanke.tydirium.service.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanke.tydirium.entity.sys.SysModule;
import com.vanke.tydirium.entity.sys.SysRole;

public interface SysModuleService {

	/**
	 * 分页查询全部模块
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SysModule> findAll(Pageable pageable);
	
	/**
	 * 根据模块名称查询
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SysModule> findAll(String moduleName, Pageable pageable);

	/**
	 * 查询全部模块
	 * 
	 * @param pageable
	 * @return
	 */
	List<SysModule> findAll();

	/**
	 * 根据多个moduleId查询模块
	 * 
	 * @param moduleIds
	 * @return
	 */
	Set<SysModule> findByModuleIdIn(Collection<Long> moduleIds);

	/**
	 * 新增或者更新模块
	 * 
	 * @param sysModule
	 * @return
	 */
	SysModule save(SysModule sysModule);

	/**
	 * 查找一个模块
	 * 
	 * @param moduleId
	 * @return
	 */
	SysModule findOne(Long moduleId);
	/**
	 * 查找当前的角色下存在的关联模块
	 * @param role
	 * @param modules
	 * @return
	 */
	List<SysModule> modulesCheck(SysRole role, List<SysModule> modules);
}
