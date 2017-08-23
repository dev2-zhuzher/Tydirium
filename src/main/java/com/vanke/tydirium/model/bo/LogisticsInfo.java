package com.vanke.tydirium.model.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 
 * @Description: 物流信息
 *
 * @author: songjia
 * @date: 2017年8月21日 下午4:26:21
 */
public class LogisticsInfo implements Serializable {

	private static final long serialVersionUID = -6961535250275041433L;

	// 快递公司编号
	@JSONField(name = "logistic_code")
	private String logisticCode;

	// 快递单号
	@JSONField(name = "shipper_code")
	private String shippercode;

	// 快递状态，可能值为 "在途中/签收/问题件" 以及空
	private String status;

	// 物流跟踪信息,数组类型，按照发生的时间升序排列
	private List<TracesInfo> traces;

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public String getShippercode() {
		return shippercode;
	}

	public void setShippercode(String shippercode) {
		this.shippercode = shippercode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TracesInfo> getTraces() {
		return traces;
	}

	public void setTraces(List<TracesInfo> traces) {
		this.traces = traces;
	}

	/**
	 * 
	 * 
	 * @Description: 物流跟踪信息
	 *
	 * @author: songjia
	 * @date: 2017年8月21日 下午4:30:37
	 */
	public static class TracesInfo implements Serializable {

		private static final long serialVersionUID = -7311323442751695093L;

		// 受理地点
		@JSONField(name = "accept_station")
		private String acceptStation;

		// 受理时间
		@JSONField(name = "accept_time")
		private String acceptTime;

		// 备注
		private String remark;

		public String getAcceptStation() {
			return acceptStation;
		}

		public void setAcceptStation(String acceptStation) {
			this.acceptStation = acceptStation;
		}

		public String getAcceptTime() {
			return acceptTime;
		}

		public void setAcceptTime(String acceptTime) {
			this.acceptTime = acceptTime;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public TracesInfo(String acceptStation, String acceptTime, String remark) {
			super();
			this.acceptStation = acceptStation;
			this.acceptTime = acceptTime;
			this.remark = remark;
		}

		public TracesInfo() {
			super();
		}

	}

}
