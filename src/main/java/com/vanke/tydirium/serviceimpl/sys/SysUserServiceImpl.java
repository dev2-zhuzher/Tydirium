package com.vanke.tydirium.serviceimpl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vanke.tydirium.entity.sys.SysUser;
import com.vanke.tydirium.repository.sys.SysUserRepository;
import com.vanke.tydirium.service.sys.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserRepository sysUserRepository;

	@Override
	public SysUser findByMobile(String mobile) {
		return sysUserRepository.findByMobile(mobile);
	}

	@Override
	public SysUser findOne(Long userId) {
		return sysUserRepository.findOne(userId);
	}

	@Override
	public Page<SysUser> findAll(Pageable pageable) {
		return sysUserRepository.findAll(pageable);
	}

	@Override
	public SysUser save(SysUser sysUser) {
		return sysUserRepository.saveAndFlush(sysUser);
	}

	@Override
	public void delete(Long userId) {
		sysUserRepository.delete(userId);
	}

}
