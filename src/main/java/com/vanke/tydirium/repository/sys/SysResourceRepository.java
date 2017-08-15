package com.vanke.tydirium.repository.sys;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vanke.tydirium.entity.sys.SysResource;

public interface SysResourceRepository
		extends JpaRepository<SysResource, Serializable>, JpaSpecificationExecutor<SysResource> {

	Set<SysResource> findByIdIn(Collection<Long> resourceIds);

}
