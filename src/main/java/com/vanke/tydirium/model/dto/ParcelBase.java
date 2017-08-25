package com.vanke.tydirium.model.dto;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 添加邮包实体类
 *
 * @author: songjia
 * @date: 2017年8月25日 上午11:38:08
 */
public class ParcelBase implements Serializable {

	private static final long serialVersionUID = 400543535688891645L;

	// 邮包ID
	private Long parcelId;

	// 项目code
	private String projectCode;

	// 项目名称
	private String projectName;

	// 物品类型ID
	private Long itemId;

	// 取件码
	private String fetchCode;

	// 物流编号
	private String logisticCode;

	// 物流公司编号
	private String shipperCode;

	// 收件人房屋编号
	private String houseCode;

	// 收件人姓名
	private String receiverName;

	// 收件人手机号码
	private String receiverMobile;

	// 收件人地址
	private String receiveAddress;

	// 实际取件人手机号
	private String fetchMobile;

	public Long getParcelId() {
		return parcelId;
	}

	public void setParcelId(Long parcelId) {
		this.parcelId = parcelId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getFetchCode() {
		return fetchCode;
	}

	public void setFetchCode(String fetchCode) {
		this.fetchCode = fetchCode;
	}

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getFetchMobile() {
		return fetchMobile;
	}

	public void setFetchMobile(String fetchMobile) {
		this.fetchMobile = fetchMobile;
	}

}
