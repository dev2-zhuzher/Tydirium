package com.vanke.tydirium.tools;

/**
 * 
 * 
 * @Description: redis-key工具类
 *
 * @author: songjia
 * @date: 2017年7月5日 上午11:22:55
 */
public class RedisKeyCommonUtil {
	
	// 物品类型列表缓存key
	public static final String ITEM_TYPE_LIST_KEY = "tydirium:item_type_list";
	
	// 项目取件位置缓存key
	public static final String PROJECT_PICKUP_POSITION_KEY = "tydirium:pickup_position_${projectCode}";
}
