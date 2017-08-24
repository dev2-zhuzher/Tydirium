package com.vanke.tydirium.model.bo;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 本地登陆的数据传输对象
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月24日 下午5:05:30
 */
public class LoginUser implements Serializable {

	private static final long serialVersionUID = 6009279584873788009L;
	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 验证码
	 */
	private String checkCode;

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

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

}
