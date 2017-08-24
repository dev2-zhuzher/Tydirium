package com.vanke.tydirium.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @Description: 后台用户mapper接口【mybatis集成测试】
 *
 * @author: songjia
 * @date: 2017年8月23日 下午2:05:07
 */
public interface SysUserMapper {

	/**
	 * 根据手机号查询用户昵称
	 * 
	 * @param mobile
	 * @return
	 */
	@Select("select nick_name from sys_user where mobile = #{0} limit 1")
	String queryNikcNameByMobile(String mobile);
}
