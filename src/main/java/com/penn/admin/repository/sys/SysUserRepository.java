package com.penn.admin.repository.sys;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.penn.admin.entity.sys.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Serializable>, JpaSpecificationExecutor<SysUser> {

	SysUser findByMobile(String mobile);

}
