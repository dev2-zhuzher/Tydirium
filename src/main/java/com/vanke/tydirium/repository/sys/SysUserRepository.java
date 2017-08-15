package com.vanke.tydirium.repository.sys;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vanke.tydirium.entity.sys.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Serializable>, JpaSpecificationExecutor<SysUser> {

	SysUser findByMobile(String mobile);

}
