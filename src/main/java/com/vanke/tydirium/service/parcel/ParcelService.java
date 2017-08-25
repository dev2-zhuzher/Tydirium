package com.vanke.tydirium.service.parcel;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.dto.ItemType;
import com.vanke.tydirium.model.dto.Parcel;

/**
 * 
 * 
 * @Description: 邮包service服务接口
 *
 * @author: songjia
 * @date: 2017年8月25日 上午11:53:49
 */
public interface ParcelService {

	/**
	 * 新建代收邮包
	 * 
	 * @param parcel
	 * @return
	 */
	ResponseInfo createParcel(Parcel parcel);

	/**
	 * 获取物品类型集合
	 * 
	 * @return
	 */
	List<ItemType> queryItemTypeList();

	/**
	 * 分页查询代收邮包列表
	 * 
	 * @return
	 */
	PageInfo<List<Map<String, Object>>> getParcelList();

}
