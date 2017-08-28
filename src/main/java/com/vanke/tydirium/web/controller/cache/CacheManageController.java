package com.vanke.tydirium.web.controller.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.tools.RedisCacheUtil;
import com.vanke.tydirium.tools.RedisKeyCommonUtil;

/**
 * 
 * 
 * @Description: 缓存控制器
 *
 * @author: songjia
 * @date: 2017年8月28日 上午9:57:42
 */
@RequestMapping("/cache")
public class CacheManageController {

	@Autowired
	private RedisCacheUtil redisCacheUtil;

	/**
	 * 清除邮包物品类型集合缓存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/item/type/clean", method = RequestMethod.GET)
	public ResponseInfo cleanItemTypeCache() {
		this.redisCacheUtil.delKey(RedisKeyCommonUtil.ITEM_TYPE_LIST_KEY);
		return ResponseInfo.getSuccessInstance();
	}

	/**
	 * 清除项目取件位置缓存
	 * 
	 * @param projectCode
	 * @return
	 */
	@RequestMapping(value = "/pickup/position/clean/{projectCode}", method = RequestMethod.GET)
	public ResponseInfo cleanPickupPositionCache(@PathVariable("projectCode") String projectCode) {
		this.redisCacheUtil.delKey(RedisKeyCommonUtil.PROJECT_PICKUP_POSITION_KEY.replace("${projectCode}", projectCode));
		return ResponseInfo.getSuccessInstance();
	}
}
