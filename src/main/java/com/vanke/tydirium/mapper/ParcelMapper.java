package com.vanke.tydirium.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.vanke.tydirium.model.dto.ItemType;
import com.vanke.tydirium.model.dto.ParcelBase;
import com.vanke.tydirium.model.dto.ParcelDetail;

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
	 * 查询物品类型列表集合
	 * 
	 * @return
	 */
	@Select("select id itemId, name from t_item_type where is_delete = false order by id")
	List<ItemType> queryItemTypeList();

	/**
	 * 根据项目code查询项目取件位置集合
	 * 
	 * @param projectCode
	 * @return
	 */
	@Select("select id, position from t_project_pickup where project_code = #{0}")
	List<Map<Long, Object>> getPickupPositionList(String projectCode);

	/**
	 * 分页查询代收邮包列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryParcelList(ParcelBase parcelBase);

	/**
	 * 查询邮包详情
	 * 
	 * @param parceId
	 * @return
	 */
	public ParcelDetail getParcelDetail(Long parceId);

	/**
	 * 取件更新邮包记录状态
	 * 
	 * @param parcleId
	 * @param mobile
	 * @return
	 */
	@Update("update t_parcel set status = 2, pickup_mobile = #{1}, pickup_time = now() where id = #{0} status = 1")
	int updateParcelStatus(Long parcleId, String mobile);

	/**
	 * 根据邮包ID查询邮包状态
	 * 
	 * @param parcelId
	 * @return
	 */
	@Select("select status from t_parcel where id = #{0}")
	Integer queryParcelStatus(Long parcelId);

}
