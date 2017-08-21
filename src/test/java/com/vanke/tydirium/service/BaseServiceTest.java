package com.vanke.tydirium.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.BaseTest;
import com.vanke.tydirium.model.base.Result;
import com.vanke.tydirium.model.bo.LogisticsInfo;
import com.vanke.tydirium.model.bo.LogisticsInfo.TracesInfo;
import com.vanke.tydirium.service.sys.BaseService;

/**
 * 
 * 
 * @Description: 基础服务测试类
 *
 * @author: songjia
 * @date: 2017年8月21日 下午6:25:34
 */
public class BaseServiceTest extends BaseTest {

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
	private BaseService baseService;

	/**
	 * 物流信息请求服务测试
	 */
	@Test
	public void getLogisticsInfo() {
		Result result = this.baseService.getLogisticsInfo("ems", "9891077826312");

		if (result.getCode() == 0) {
			LogisticsInfo info = (LogisticsInfo) result.getResult();
			System.out.println(info.getLogistic_code());
			System.out.println(info.getShipper_code());
			System.out.println(info.getStatus());
			List<TracesInfo> traces = info.getTraces();
			for (TracesInfo tracesInfo : traces) {
				System.out.println(JSONObject.toJSONString(tracesInfo));
			}
		}
	}

}
