package com.vanke.tydirium.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.vanke.tydirium.model.dto.ItemType;

/**
 * 
 * 
 * @Description: 物品类型Mapper接口
 *
 * @author: songjia
 * @date: 2017年8月25日 下午1:45:45
 */
public interface ItemTypeMapper {

	/**
	 * 查询物品类型列表集合
	 * 
	 * @return
	 */
	@Select("select id itemId, name from t_item_type where is_delete = false order by id")
	List<ItemType> queryItemTypeList();
}
