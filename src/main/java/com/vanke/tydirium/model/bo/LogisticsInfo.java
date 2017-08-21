package com.vanke.tydirium.model.bo;

import java.io.Serializable;
import java.util.List;

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
	private String logistic_code;

	// 快递单号
	private String shipper_code;

	// 快递状态，可能值为 "在途中/签收/问题件" 以及空
	private String status;

	// 物流跟踪信息,数组类型，按照发生的时间升序排列
	private List<TracesInfo> traces;

	public String getLogistic_code() {
		return logistic_code;
	}

	public void setLogistic_code(String logistic_code) {
		this.logistic_code = logistic_code;
	}

	public String getShipper_code() {
		return shipper_code;
	}

	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
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
		private String accept_station;

		// 受理时间
		private String accept_time;
		
		// 备注
		private String remark;

		public String getAccept_station() {
			return accept_station;
		}

		public void setAccept_station(String accept_station) {
			this.accept_station = accept_station;
		}

		public String getAccept_time() {
			return accept_time;
		}

		public void setAccept_time(String accept_time) {
			this.accept_time = accept_time;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public TracesInfo(String accept_station, String accept_time, String remark) {
			super();
			this.accept_station = accept_station;
			this.accept_time = accept_time;
			this.remark = remark;
		}

		public TracesInfo() {
			super();
		}

	}

}


