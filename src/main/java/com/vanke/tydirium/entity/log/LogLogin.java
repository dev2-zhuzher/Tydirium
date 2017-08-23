package com.vanke.tydirium.entity.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
/**
 * 
 * 
 * @Description: 登陆日志实体bean
 *
 * @author: 郭时青
 * @date: 2017年8月23日 上午11:55:31
 */
@Entity
@Table(name = "log_login")
public class LogLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_id")
	private Long logId;// 主键标识

	@Column(name = "login_name" , nullable = false, length = 100)
	private String loginName;// 登录账号

	@Column(name = "successful" , nullable = false)
	@Type(type = "yes_no")
	private Boolean successful;// 是否成功

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_time" , nullable = false, updatable = false)
	private Date loginTime;// 登录时间

	@Column(name = "login_ip" , nullable = false, length = 40)
	private String loginIp;// 登陆IP

	@Column(name = "login_province" , nullable = true, length = 50)
	private String loginProvince;// 登录所在省份

	@Column(name = "login_city" , nullable = true, length = 50)
	private String loginCity;// 登录所在城市

	@Column(name = "description" , nullable = true)
	private String description;// 描述

	@Column(name = "session_id" , nullable = false, length = 36)
	private String sessionId;// 登陆的sessionID

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logout_time" , nullable = true)
	private Date logoutTime;// 登出时间

	public LogLogin() {

	}

	/**
	 * 
	 * @param loginName
	 *            登录账号
	 * @param successful
	 *            是否成功
	 * @param loginTime
	 *            登录时间
	 * @param loginIp
	 *            登录IP
	 * @param description
	 *            描述
	 */
	public LogLogin(String loginName, Boolean successful, Date loginTime, String loginIp, String description,
			String sessionId) {
		this.loginName = loginName;
		this.successful = successful;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.description = description;
		this.sessionId = sessionId;
	}


	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginProvince() {
		return loginProvince;
	}

	public void setLoginProvince(String loginProvince) {
		this.loginProvince = loginProvince;
	}

	public String getDescription() {
		return description;
	}

	public String getLoginCity() {
		return loginCity;
	}

	public void setLoginCity(String loginCity) {
		this.loginCity = loginCity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}