package com.penn.admin.web.common;

public class ResponseData {

	int error;
	Object data;
	String msg;

	public ResponseData(int error, Object data, String msg) {
		this.error = error;
		this.data = data;
		this.msg = msg;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
