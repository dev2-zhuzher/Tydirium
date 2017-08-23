package com.vanke.tydirium.service.lebang;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanke.tydirium.entity.lebang.SysLeBangRole;

/**
 * 
 * 
 * @Description: 乐邦角色与本地角色关联管理service
 *
 * @author: 郭时青
 * @date: 2017年8月23日 下午3:35:39
 */
public interface SysLeBangRoleService {
	
	/**
	 * 更新或新增
	 * @param role
	 */
	void save(SysLeBangRole role);
	/**
	 * 根据岗位信息查询
	 * @param leBangRoleCode
	 * @return
	 */
	SysLeBangRole findByLeBangRoleCode(String leBangRoleCode);
	
	/**
	 * 根据岗位信息分页查询
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
