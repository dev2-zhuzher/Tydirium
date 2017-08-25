package com.vanke.tydirium.serviceimpl.Logistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.entity.enums.ShipperCodeEnum;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.bo.LogisticsInfo;
import com.vanke.tydirium.service.Logistics.LogisticsService;
import com.vanke.tydirium.tools.HttpTool;

/**
 * 
 * 
 * @Description: 物流信息服务实现类
 *
 * @author: songjia
 * @date: 2017年8月21日 下午4:14:29
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsServiceImpl.class);

	// 物流信息请求URL
	@Value("${logistics_request_url}")
	private String logisticsRequestUrl;

	/**
	 * 物流信息请求服务
	 * 
	 * @param shipperEnum
	 *            快递公司&物流编号枚举类
	 * @param logisticCode
	 *            快递单号
	 * @return
	 * @see com.vanke.tydirium.service.sys.LogisticsService#getLogisticsInfo(com.vanke.tydirium.entity.enums.ShipperCodeEnum,
	 *      java.lang.String)
	 */
	@Override
	public ResponseInfo getLogisticsInfo(ShipperCodeEnum shipperEnum, String logisticCode) {
		String response = null;
		Integer responseCode = null; // 返回状态码
		try {
			response = HttpTool.sendGet(logisticsRequestUrl + "?shipper_code=" + shipperEnum.name().toLowerCase() + "&logistic_code=" + logisticCode);
			JSONObject jsonObj = JSON.parseObject(response);
			if (jsonObj.containsKey("code")) {
				responseCode = jsonObj.getInteger("code");
				if (responseCode.equals(Integer.valueOf(0))) { // 成功的code
					return ResponseInfo.getSuccessInstance(JSONObject.parseObject(response, LogisticsInfo.class));
				} else { // 失败
					LOGGER.error("请求物流信息失败，[response：{}]", response);
					return new ResponseInfo(ResponseInfo.FAIL);
				}
			}
			return ResponseInfo.getFailInstance("请求参数非法，返回数据格式异常");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("请求物流信息异常：" + e.getMessage());
			return new ResponseInfo(ResponseInfo.FAIL);
		}
	}

}
