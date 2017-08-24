package com.vanke.tydirium.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * @Description: 后台登陆验证自定义注解
 *
 * @author: vanke-yuzn05 - vankeadmin
 * @date: 2017年8月24日 上午10:49:56
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminCheckLogin {
	// 是否需要验证登陆，默认为true，标识需要
	boolean needCheck() default true;
}
