package com.vanke.tydirium.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 住这儿接口请求调用
 * 
 * @author Joey
 *
 */
public class ZhuZherApiRequester {

	@Value("${client_id}")
	private String client_id;
	@Value("${client_secret}")
	private String client_secret;
	@Value("${zhuzher_api_host}")
	private String zhuzher_api_host;
	@Value("${redirect_uri}")
	private String zhuzher_redirect_uri;
	
	private static final String token_uri = "/api/zhuzher/oauth/access_token";
	private static final String info_url = "/api/zhuzher/users/me";
	private static final String projects_url = "/api/zhuzher/users/me/projects";
	private static final String housekeeper_url = "/api/zhuzher/users/me/housekeeper";
	private static final String mainhouse_url = "/api/zhuzher/users/me/houses/main/project";
	private static final String houses_url = "/api/zhuzher/users/me/houses";
	private static final String grid_url = "/api/zhuzher/users/me/grids";
	
	// 开放接口,无需token
	private static final String keepers_url = "/api/partner/keepers/";
	private static final String grid_keeper_url = "/api/partner/keepers/houses/";
	
	
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
		params.put("redirect_uri", zhuzher_redirect_uri);
		return HttpUtil.sendPost(zhuzher_api_host + token_uri, params, null);
	}
	
	/**
	 * 获取用户详细信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String requestUserInfo(String accessToken) throws Exception {
		return HttpUtil.sendGet(zhuzher_api_host + info_url + "?access_token=" + accessToken);
	}
	
	/**
	 * 获取用户所属项目列表
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public Set<String> requestUserProjects(String accessToken) throws Exception {
		Set<String> result = new HashSet<String>();
		String response = HttpUtil.sendGet(zhuzher_api_host + projects_url + "?access_token=" + accessToken);
		JSONObject responseJson = JSON.parseObject(response);
		if(responseJson.containsKey("result")) {
			JSONArray projects = responseJson.getJSONObject("result").getJSONArray("items");
			for(int i = 0; i < projects.size(); ++i) {
				JSONObject item = projects.getJSONObject(i);
				result.add(item.getString("code"));
			}
		} else {
			logger.warn("请求住这儿用户项目信息失败：" + accessToken);
			throw new RuntimeException("请求接口失败!");
		}
		
		return result;
	}
	
	/**
	 * 请求管家信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String requestHouseKeeper(String accessToken) throws Exception {
		return HttpUtil.sendGet(zhuzher_api_host + housekeeper_url + "?access_token=" + accessToken);
	}

	/**
	 * 获取主房屋信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public String requestmainProject(String accessToken) throws Exception {
		return HttpUtil.sendGet(zhuzher_api_host + mainhouse_url + "?access_token=" + accessToken);
	}
	
	/**
	 * 获取主房屋信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public String requestHouses(String accessToken) throws Exception {
		return HttpUtil.sendGet(zhuzher_api_host + houses_url + "?access_token=" + accessToken);
	}
	
	/**
	 * 请求网格信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String requestGrid(String accessToken) throws Exception {
		return HttpUtil.sendGet(zhuzher_api_host + grid_url + "?access_token=" + accessToken);
	}
	
	/**
	 * 请求管家和网格信息
	 * 
	 * @param 项目编码
	 * @return
	 * @throws Exception
	 */
	public String requestKeppers(String projectCode) throws Exception {
		return HttpUtil.sendGet(zhuzher_api_host + keepers_url + projectCode);
	}
	
	/**
	 * @Description: 根据houseCode请求keeper和grid 
	 * @author huangk11  
	 * @date 2017年4月11日 
	 * @param houseCode
	 * @return
	 * @throws Exception
	 */
	public String requestKeeperAndGrid(String houseCode) throws Exception {
        return HttpUtil.sendGet(zhuzher_api_host + grid_keeper_url + houseCode);
    }


}
