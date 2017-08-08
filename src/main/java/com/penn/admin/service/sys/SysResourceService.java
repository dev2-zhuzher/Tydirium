package com.penn.admin.service.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.penn.admin.entity.sys.SysResource;

public interface SysResourceService {

	/**
	 * 分页查询所有资源
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SysResource> findAll(Pageable pageable);

	/**
	 * 根据资源名称查询
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SysResource> findAll(String resourceName, Pageable pageable);

	/**
	 * 查询所有资源
	 * 
	 * @return
	 */
	List<SysResource> findAll();

	/**
	 * 根据多个resourceId查询资源
	 * 
	 * @param resourceIds
	 * @return
	 */
	Set<SysResource> findByResourceIdIn(Collection<Long> resourceIds);

	/**
	 * 新增或者更新资源
	 * 
	 * @param resource
	 * @return
	 */
	SysResource save(SysResource resource);

	/**
	 * 查找一个资源
	 * 
	 * @param resourceId
	 * @return
	 */
	SysResource findOne(Long resourceId);

}
