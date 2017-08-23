package com.vanke.tydirium.configuration;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.tools.HttpTool;

/**
 * 乐邦接口请求调用
 * 
 * @author Joey
 *
 */
public class LeBangApiRequester {

	@Value("${client_id}")
	private String client_id;
	@Value("${client_secret}")
	private String client_secret;
	@Value("${lebang_api_host}")
	private String lebang_api_host;
	@Value("${redirect_url}")
	private String lebang_redirect_uri;
	
	private static final String token_uri = "/api/lebang/oauth/access_token";
	private static final String info_url = "/api/lebang/staffs/me/detail";
	private static final String jobs_url = "/api/lebang/staffs/me/jobs";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取AccessToken
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String requestAccessToken(String code) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", client_id);
		params.put("client_secret", client_secret);
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		params.put("redirect_uri", lebang_redirect_uri);
		return HttpTool.sendPost(lebang_api_host + token_uri, params, null);
	}
	
	/**
	 * 获取用户详细信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String requestUserInfo(String accessToken) throws Exception {
		return HttpTool.sendGet(lebang_api_host + info_url + "?access_token=" + accessToken);
	}
	
	/**
	 * 获取用户工作列表
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public List<Entry<String, String>> requestUserJobs(String accessToken) throws Exception {
		List<Entry<String, String>> result = new ArrayList<Entry<String, String>>();
		String response = HttpTool.sendGet(lebang_api_host + jobs_url + "?access_token=" + accessToken);
		JSONObject responseJson = JSON.parseObject(response);
		if(responseJson.containsKey("result")) {
			JSONArray projects = responseJson.getJSONArray("result");
			for(int i = 0; i < projects.size(); ++i) {
				JSONObject item = projects.getJSONObject(i);
				String role_code = item.getString("role_code");
				String project_code = item.getString("project_code");
				Entry<String, String> job = new SimpleEntry<String, String>(project_code, role_code);
				result.add(job);
			}
		} else {
			logger.warn("请求助这儿用户工作信息失败：" + accessToken);
			throw new RuntimeException("请求接口失败!");
		}
		
		return result;
	}
	
	
}
