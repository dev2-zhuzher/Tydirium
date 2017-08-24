package com.vanke.tydirium.entity.lebang;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 乐邦角色关系基表
 * 
 * @author v-guosq02
 *
 * 
 * 
 * @Description: 乐邦项目角色对应本地的角色的关系
 *
 * @author: 郭时青
 * @date: 2017年8月22日 下午12:02:33
 */
@Entity
@Table(name = "sys_lebang_mate")
public class SysLeBangRole implements Serializable {

	private static final long serialVersionUID = 6553314407502979331L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 主键标识

	@NotBlank(message = "乐邦的角色代码不能为空")
	@Column(name = "lebang_role_code", nullable = false, length = 50)
	private String leBangRoleCode;// 根据token查询到的当前登陆用户的角色代码

	@Column(name = "role_id", length = 50)
	private Long roleId;// 邮包系统本地角色的ID
	
	//lebang角色描述
	@Column(name = "lebang_description")
	private String leBangDescription;
	
	//创建时间
	@Column(name = "create_time")
	private Date createTime;
	
	//更新时间
	@Column(name = "update_time")
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLeBangRoleCode() {
		return leBangRoleCode;
	}

	public void setLebangRoleCode(String leBangRoleCode) {
		this.leBangRoleCode = leBangRoleCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getLeBangDescription() {
		return leBangDescription;
	}

	public void setLeBangDescription(String leBangDescription) {
		this.leBangDescription = leBangDescription;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setLeBangRoleCode(String leBangRoleCode) {
		this.leBangRoleCode = leBangRoleCode;
	}

}
