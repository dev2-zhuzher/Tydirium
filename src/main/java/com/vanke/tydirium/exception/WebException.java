package com.vanke.tydirium.exception;

public class WebException extends RuntimeException {

	private static final long serialVersionUID = 9161116401986631344L;

	private static final String PRE_ERR_CODE = "W"; // (Web)

	private String errCode; // 异常对应的返回码
	private String errMsg; // 异常对应的描述信息

	public WebException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = PRE_ERR_CODE + errCode;
		this.errMsg = errMsg;
	}

	public WebException(Throwable cause, String errCode, String errMsg) {
		super(errMsg, cause);
		this.errCode = PRE_ERR_CODE + errCode;
		this.errMsg = errMsg;
	}

	protected WebException(String errCode, String errMsg, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(errMsg, cause, enableSuppression, writableStackTrace);
		this.errCode = PRE_ERR_CODE + errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

}