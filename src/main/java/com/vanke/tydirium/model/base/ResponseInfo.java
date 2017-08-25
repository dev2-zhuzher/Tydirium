package com.vanke.tydirium.model.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 
 * @Description: 接口返回报文数据封装bean
 *
 * @author: songjia
 * @date: 2017年5月5日 下午6:29:56
 */
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class ResponseInfo implements Serializable {

	private static final long serialVersionUID = -2210269174549937804L;

	// 成功状态码
	public static final Integer SUCCESS = Integer.valueOf(0);

	// 失败状态码
	public static final Integer FAIL = Integer.valueOf(200);

	// 默认操作成功消息
	public static final String SUCCESS_MSG = "操作成功";

	// 接口状态码
	private Integer code;

	// 错误码
	private String errorCode;

	// 提示消息
	private String message;

	// 接口业务数据
	private Object result;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public ResponseInfo(Integer code, String errorCode, String message) {
		super();
		this.code = code;
		this.errorCode = errorCode;
		this.message = message;
	}

	public ResponseInfo(Integer code, Object result) {
		super();
		this.code = code;
		this.result = result;
		this.message = SUCCESS_MSG;
	}

	public ResponseInfo(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResponseInfo(Integer code) {
		super();
		this.code = code;
		this.message = SUCCESS_MSG;
	}

	public ResponseInfo() {
		super();
	}

	public static ResponseInfo getFailInstance(String errorMsg) {
		return new ResponseInfo(ResponseInfo.FAIL, errorMsg);
	}

	public static ResponseInfo getSuccessInstance(Object result) {
		return new ResponseInfo(ResponseInfo.SUCCESS, result);
	}
	
	public static ResponseInfo getSuccessInstance() {
		return new ResponseInfo(ResponseInfo.SUCCESS);
	}
}
