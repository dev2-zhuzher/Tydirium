package com.vanke.tydirium.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vanke.tydirium.BaseTest;
import com.vanke.tydirium.entity.enums.ShipperCodeEnum;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.bo.LogisticsInfo;
import com.vanke.tydirium.model.bo.LogisticsInfo.TracesInfo;
import com.vanke.tydirium.service.sys.LogisticsService;

/**
 * 
 * 
 * @Description: 物流信息服务测试类
 *
 * @author: songjia
 * @date: 2017年8月21日 下午6:25:34
 */
public class LogisticsServiceTest extends BaseTest {

	/**
	 * GET请求测试
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/test").param("key", "value")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	/**
	 * POST请求测试
	 * 
	 * @throws Exception
	 */
	public void testPost() throws Exception {
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("post请求URL").contentType(contentType).content("JSON参数字符串")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Autowired
	private LogisticsService logisticsService;

	/**
	 * 物流信息请求服务测试
	 */
	@Test
	public void getLogisticsInfo() {
		ResponseInfo result = this.logisticsService.getLogisticsInfo(ShipperCodeEnum.EMS, "9891077826312");

		if (result.getCode() == 0) {
			LogisticsInfo info = (LogisticsInfo) result.getResult();
			System.out.println(info.getLogisticCode());
			System.out.println(info.getShippercode());
			System.out.println(info.getStatus());
			List<TracesInfo> traces = info.getTraces();
			for (TracesInfo tracesInfo : traces) {
				System.out.println(tracesInfo.getAcceptStation());
				System.out.println(tracesInfo.getAcceptTime());
				System.out.println(tracesInfo.getRemark());
			}
		}
	}

}
