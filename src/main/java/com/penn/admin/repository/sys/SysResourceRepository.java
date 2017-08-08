package com.penn.admin.repository.sys;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.penn.admin.entity.sys.SysResource;

public interface SysResourceRepository
		extends JpaRepository<SysResource, Serializable>, JpaSpecificationExecutor<SysResource> {

	Set<SysResource> findByIdIn(Collection<Long> resourceIds);

}
