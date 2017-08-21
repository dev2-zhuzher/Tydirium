package com.vanke.tydirium.service.sys;

import com.vanke.tydirium.model.base.Result;

/**
 * 
 * 
 * @Description: 系统基础服务类
 *
 * @author: songjia
 * @date: 2017年8月21日 下午4:11:32
 */
public interface BaseService {

	/**
	 * 物流信息请求服务
	 * 
	 * @param shipperCode
	 *            快递公司编号
	 * @param logisticCode
	 *            快递单号
	 * @return
	 */
	Result getLogisticsInfo(String shipperCode, String logisticCode);
}
