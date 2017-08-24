package com.vanke.tydirium.repository.sys;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vanke.tydirium.entity.sys.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Serializable>, JpaSpecificationExecutor<SysUser> {

	SysUser findByMobile(String mobile);

	@Query(value="select u from SysUser u where u.mobile = :account and u.password = :password")
	SysUser findByCheck(@Param("account")String account,@Param("password")String password);

}
