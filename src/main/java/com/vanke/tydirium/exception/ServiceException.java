package com.vanke.tydirium.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -513333131902705626L;

	private static final String PRE_ERR_CODE = "S"; // (Service)

	private String errCode; // 异常对应的返回码
	private String errMsg; // 异常对应的描述信息

	public ServiceException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = PRE_ERR_CODE + errCode;
		this.errMsg = errMsg;
	}

	public ServiceException(Throwable cause, String errCode, String errMsg) {
		super(errMsg, cause);
		this.errCode = PRE_ERR_CODE + errCode;
		this.errMsg = errMsg;
	}

	protected ServiceException(String errCode, String errMsg, Throwable cause, boolean enableSuppression,
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