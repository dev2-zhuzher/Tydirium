package com.penn.admin.service.sys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.penn.admin.entity.sys.SysUser;

public interface SysUserService {

	SysUser findByMobile(String mobile);

	SysUser findOne(Long userId);

	Page<SysUser> findAll(Pageable pageable);

	SysUser save(SysUser sysUser);

	void delete(Long userId);

}
