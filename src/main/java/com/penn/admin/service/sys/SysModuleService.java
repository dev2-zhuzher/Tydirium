package com.penn.admin.service.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.penn.admin.entity.sys.SysModule;

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
}
