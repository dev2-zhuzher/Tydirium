package com.vanke.tydirium.repository.lebang;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vanke.tydirium.entity.lebang.SysLeBangRole;
import com.vanke.tydirium.entity.sys.SysRole;

public interface SysLeBangRoleRepository
		extends JpaRepository<SysLeBangRole, Serializable>, JpaSpecificationExecutor<SysLeBangRole> {

	Set<SysRole> findByIdIn(Collection<Long> roleIds);
	SysLeBangRole findByLeBangRoleCode(String leBangRoleCode);

}
