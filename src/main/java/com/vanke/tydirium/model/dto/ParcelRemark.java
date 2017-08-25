package com.vanke.tydirium.model.dto;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 邮包备注类
 *
 * @author: songjia
 * @date: 2017年8月25日 下午4:36:03
 */
public class ParcelRemark implements Serializable {

	private static final long serialVersionUID = 6970050395219876269L;

	// 邮包ID
	private Long parcelId;

	// 备注内容
	private String remark;

	// 备注人手机号
	private String mobile;

	public Long getParcelId() {
		return parcelId;
	}

	public void setParcelId(Long parcelId) {
		this.parcelId = parcelId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
