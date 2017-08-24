package com.vanke.tydirium.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanke.tydirium.exception.WebException;
import com.vanke.tydirium.model.base.ResponseInfo;

/**
 * 
 * 
 * @Description: 基础Controller
 *
 * @author: songjia 
 * @date: 2017年8月24日 下午7:18:48
 */
@Controller
public class BaseController {

	protected static final Logger logger = Logger.getLogger(BaseController.class);

	protected String REDIRECT = "redirect:"; // 执行redirect操作的前缀

	// 当前页
	protected static final String PAGE = "page";

	// 每页显示记录数
	protected static final String SIZE = "size";

	// 每页默认显示记录数
	protected static final String DEFAULT_PAGE_SIZE = "10";

	// 当前默认第一页
	protected static final String DEFAULT_PAGE = "0";

	/**
	 * 用于处理异常的
	 *
	 * @return
	 */
	@ExceptionHandler({ WebException.class })
	@ResponseBody
	public ResponseInfo webException(WebException e) {
		e.printStackTrace();
		return ResponseInfo.getFailInstance(e.getErrMsg());
	}
}
