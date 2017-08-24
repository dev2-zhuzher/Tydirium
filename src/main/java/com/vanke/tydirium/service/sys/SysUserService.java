package com.vanke.tydirium.service.sys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanke.tydirium.entity.sys.SysUser;

/**
 * 
 * 
 * @Description: 后台用户service接口实现
 *
 * @author: vanke-yuzn05 - vankeadmin
 * @date: 2017年8月24日 下午7:03:45
 */
public interface SysUserService {

	SysUser findByMobile(String mobile);

	SysUser findOne(Long userId);

	Page<SysUser> findAll(Pageable pageable);

	SysUser save(SysUser sysUser);

	void delete(Long userId);

	/**
	 * 根据用户名、手机号分页查询用户
	 * 
	 * @param name
	 *            用户姓名
	 * @param mobile
	 *            手机号码
	 * @param pageable
	 * @return
	 */
	Page<SysUser> findAll(String name, String mobile, Pageable pageable);

}
