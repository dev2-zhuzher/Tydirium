package com.vanke.tydirium.repository.sys;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vanke.tydirium.entity.sys.SysModule;

public interface SysModuleRepository
		extends JpaRepository<SysModule, Serializable>, JpaSpecificationExecutor<SysModule> {

	Set<SysModule> findByIdIn(Collection<Long> moduleIds);

}
