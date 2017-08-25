package com.vanke.tydirium.model.base;

import com.vanke.tydirium.entity.sys.SysUser;

/**
 * 
 * 
 * @Description: 用户线程本地变量
 *
 * @author: songjia
 * @date: 2017年8月14日 下午4:21:52
 */
public class UserThreadLocal {

	private static final ThreadLocal<SysUser> USERLOCAL = new ThreadLocal<SysUser>();

	/**
	 * 将用户设置到ThreadLocal中
	 * 
	 * @param user
	 */
	public static void setCurrUser(SysUser user) {
		USERLOCAL.set(user);
	}

	/**
	 * 从ThreadLocal中获取当前用户
	 * 
	 * @return
	 */
	public static SysUser getCurrUser() {
		return USERLOCAL.get();
	}

	/**
	 * 从ThreadLocal中删除当前用户
	 * 
	 * @return
	 */
	public static void removeCurrUser() {
		USERLOCAL.remove();
	}
}
