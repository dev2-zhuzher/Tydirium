package com.vanke.tydirium.serviceimpl.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.model.base.Result;
import com.vanke.tydirium.model.bo.LogisticsInfo;
import com.vanke.tydirium.service.sys.BaseService;
import com.vanke.tydirium.tools.HttpTool;

/**
 * 
 * 
 * @Description: 系统基础服务
 *
 * @author: songjia
 * @date: 2017年8月21日 下午4:14:29
 */
@Service
public class BaseServiceImpl implements BaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

	// 物流信息请求URL
	@Value("${logistics_request_url}")
	private String logisticsRequestUrl;

	@Value("${payment_api_host}")
	private String paymentApiHost;

	/**
	 * 物流信息请求服务
	 * 
	 * @param shipperCode
	 *            快递公司编号
	 * @param logisticCode
	 *            快递单号
	 * @return
	 * @see com.vanke.tydirium.service.sys.BaseService#getLogisticsInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Result getLogisticsInfo(String shipperCode, String logisticCode) {
		String response = null;
		Integer responseCode = null; // 返回状态码
		try {
			response = HttpTool.sendGet(paymentApiHost + logisticsRequestUrl + "?shipper_code=" + shipperCode.toLowerCase() + "&logistic_code=" + logisticCode);
			JSONObject jsonObj = JSON.parseObject(response);
			if (jsonObj.containsKey("code")) {
				responseCode = jsonObj.getInteger("code");
				if (responseCode.equals(Integer.valueOf(0))) { // 成功的code
					return Result.getSuccessInstance(JSONObject.parseObject(response, LogisticsInfo.class));
				} else { // 失败
					LOGGER.error("请求物流信息失败，[response：{}]", response);
					return new Result(Result.FAIL);
				}
			}
			return Result.getFailInstance("请求参数非法，返回数据格式异常");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("请求物流信息异常：" + e.getMessage());
			return new Result(Result.FAIL);
		}
	}

}
