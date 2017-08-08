package com.penn.admin.repository.sys;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.penn.admin.entity.sys.SysRole;

public interface SysRoleRepository extends JpaRepository<SysRole, Serializable>, JpaSpecificationExecutor<SysRole> {

	Set<SysRole> findByIdIn(Collection<Long> roleIds);

	Set<SysRole> findByNameIn(Collection<String> roleNames);

	SysRole findByName(String roleName);

}
