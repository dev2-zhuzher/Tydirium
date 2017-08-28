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

	// 运单编号
	private String logisticCode;

	// 物流公司编号
	private String shipperCode;

	// 收件人手机号码
	private String receiverMobile;

	// 收件人地址
	private String receiveAddress;

	// 收件人姓名
	private String receiverName;

	// 取件位置ID
	private Long positionId;

	// 物品类型ID集合，多个ID用逗号分隔
	private String itemIds;

	// 备注信息
	private String remark;

	// 项目code
	private String projectCode;

	// 项目名称
	private String projectName;

	// 取件码
	private String pickupCode;

	// 收件人房屋编号
	private String houseCode;

	// 实际取件人手机号
	private String pickupMobile;

	public Long getParcelId() {
		return parcelId;
	}

	public void setParcelId(Long parcelId) {
		this.parcelId = parcelId;
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getPickupCode() {
		return pickupCode;
	}

	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getPickupMobile() {
		return pickupMobile;
	}

	public void setPickupMobile(String pickupMobile) {
		this.pickupMobile = pickupMobile;
	}
	
	

}
