package com.vanke.tydirium.service.parcel;

import com.vanke.tydirium.entity.enums.ShipperCodeEnum;
import com.vanke.tydirium.model.base.ResponseInfo;

/**
 * 
 * 
 * @Description: 物流信息服务类
 *
 * @author: songjia
 * @date: 2017年8月21日 下午4:11:32
 */
public interface LogisticsService {

	/**
	 * 物流信息请求服务
	 * 
	 * @param shipperCode
	 *            快递公司&物流编号枚举类
	 * @param logisticCode
	 *            快递单号
	 * @return
	 */
	ResponseInfo getLogisticsInfo(ShipperCodeEnum shipperEnum, String logisticCode);
}
