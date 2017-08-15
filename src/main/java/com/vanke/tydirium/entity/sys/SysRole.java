package com.vanke.tydirium.entity.sys;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6553314407502979331L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;// 主键标识

	@NotBlank(message = "角色名称不能为空")
	@Column(name = "name", nullable = false, length = 50, unique = true)
	private String name;// 角色名称

	@NotBlank(message = "模块描述不能为空")
	@Column(name = "description", nullable = false)
	private String description;// 角色描述

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "relation_role_module", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "module_id"))
	@OrderBy("priority desc") // 根据创建时间进行排序
	private Set<SysModule> modules = new LinkedHashSet<SysModule>();// 角色关联的权限

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SysModule> getModules() {
		return modules;
	}

	public void setModules(Set<SysModule> modules) {
		this.modules = modules;
	}

}
