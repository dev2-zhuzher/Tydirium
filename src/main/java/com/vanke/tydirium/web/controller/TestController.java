package com.vanke.tydirium.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 * @Description: 测试controller
 *
 * @author: songjia
 * @date: 2017年8月21日 下午6:24:01
 */
@RestController
public class TestController {

	/**
	 * 测试
	 * 
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String index() {
		return "this is tydirium project";
	}
}
