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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sys_module")
public class SysModule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4394133878538063114L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;// 主键标识

	@NotBlank(message = "模块名称不能为空")
	@Column(name = "name", nullable = false, length = 20)
	private String name;// 模块名称

	@Column(name = "description", nullable = true, length = 50)
	private String description;// 描述

	@NotNull(message = "显示级别不能为空")
	@Column(name = "priority", nullable = false)
	private Integer priority = 0;// 优先级

	@Column(name = "icon", nullable = true, length = 20)
	private String icon;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "relation_module_resource", joinColumns = @JoinColumn(name = "module_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	@OrderBy("priority desc")
	private Set<SysResource> resources = new LinkedHashSet<SysResource>();// 模块关联的资源
	@Transient
	private boolean isCheck = false; // 是否被当前角色选中 

	public boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Set<SysResource> getResources() {
		return resources;
	}

	public void setResources(Set<SysResource> resources) {
		this.resources = resources;
	}

}
