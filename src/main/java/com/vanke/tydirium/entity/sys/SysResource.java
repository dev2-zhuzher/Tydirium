package com.vanke.tydirium.entity.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sys_resource")
public class SysResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712750187427267072L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;// 主键标识

	@NotBlank(message = "资源名称不能为空")
	@Column(name = "name", nullable = false, length = 20)
	private String name;// 资源名称

	@Column(name = "description", nullable = true, length = 50)
	private String description;// 描述

	@NotBlank(message = "资源地址不能为空")
	@Column(name = "uri", nullable = false, length = 100)
	private String uri;// 资源对应的URI

	@NotNull(message = "显示级别不能为空")
	@Column(name = "priority", nullable = false)
	private Integer priority = 0;// 优先级
	@Transient
	private boolean isCheck = false; // 是否被当前模块选中 

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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
