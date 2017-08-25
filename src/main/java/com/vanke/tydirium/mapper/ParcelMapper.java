package com.vanke.tydirium.mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 邮包管理mapper
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月25日 下午2:12:46
 */
public interface ParcelMapper {

	/**
	 * 分页查询代收邮包列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryParcelList();
}
