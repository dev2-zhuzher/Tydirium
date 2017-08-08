package com.penn.admin.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.penn.admin.entity.enums.Sex;

@Entity
@Table(name = "sys_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "mobile" }) })
public class SysUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4720312215559366801L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "login_name", nullable = false, length = 64)
	private String loginName;

	@Column(name = "password", nullable = false, length = 64)
	private String password;

	@Column(name = "nick_name", nullable = true, length = 20)
	private String nickName;

	@Column(name = "full_name", nullable = true, length = 20)
	private String fullName;

	@Enumerated(EnumType.STRING)
	@Column(name = "sex", nullable = false, length = 10)
	private Sex sex = Sex.UNKNOW;

	@Column(name = "avatar_url", nullable = true, length = 100)
	private String avatarUrl;

	@Column(name = "mobile", nullable = false, unique = true)
	private String mobile;

	@Column(name = "email", nullable = true, length = 50)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "deleted")
	private Integer deleted = 0;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "relation_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@OrderBy("id desc")
	private Set<SysRole> roles = new LinkedHashSet<SysRole>();// 用户关联的角色

	/**
	 * 将设置创建时间的动作交个jpa处理,减少业务层面的重复的代码
	 */
	@PrePersist
	private void prePersist() {
		this.createTime = new Date();
		this.updateTime = new Date();
	}

	/**
	 * 将设置更新时间的动作交个jpa处理,减少业务层面的重复的代码
	 */
	@PreUpdate
	private void preUpdate() {
		this.updateTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Sex getSex() {
		return sex;
	}

	/**
	 * 赋值性别
	 * 
	 * @param 1男,2女
	 */
	public void setSex(int sex) {
		this.sex = Sex.getSex(sex);
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

}
