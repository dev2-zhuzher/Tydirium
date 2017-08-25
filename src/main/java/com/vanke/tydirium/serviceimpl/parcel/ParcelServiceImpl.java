package com.vanke.tydirium.serviceimpl.parcel;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanke.tydirium.mapper.ItemTypeMapper;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.dto.ItemType;
import com.vanke.tydirium.model.dto.Parcel;
import com.vanke.tydirium.service.parcel.ParcelService;
import com.vanke.tydirium.tools.RedisCacheUtil;
import com.vanke.tydirium.tools.RedisKeyCommonUtil;

/**
 * 
 * 
 * @Description: 邮包service服务实现类
 *
 * @author: songjia
 * @date: 2017年8月25日 上午11:54:12
 */
@Service
public class ParcelServiceImpl implements ParcelService {

	protected static final Logger logger = Logger.getLogger(ParcelServiceImpl.class);

	@Autowired
	private RedisCacheUtil redisCacheUtil;

	@Autowired
	private ItemTypeMapper itemTypeMapper;

	/**
	 * 新建代收邮包
	 * 
	 * @param parcel
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#createParcel(com.vanke.tydirium.model.bo.Parcel)
	 */
	@Override
	public ResponseInfo createParcel(Parcel parcel) {
		// 参数判断

		return ResponseInfo.getSuccessInstance(null);
	}

	/**
	 * 获取物品类型集合
	 * 
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#queryItemTypeList()
	 */
	@Override
	public List<ItemType> queryItemTypeList() {
		// 从缓存中获取
		List<ItemType> result = this.redisCacheUtil.getList(RedisKeyCommonUtil.ITEM_TYPE_LIST_KEY, ItemType.class);
		if (result != null) {
			return result;
		}
		// 从数据库获取
		result = this.itemTypeMapper.queryItemTypeList();

		// 存储到缓存中
		this.redisCacheUtil.setList(RedisKeyCommonUtil.ITEM_TYPE_LIST_KEY, result);

		return result;
	}

}
