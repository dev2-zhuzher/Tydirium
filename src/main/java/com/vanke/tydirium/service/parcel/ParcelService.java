package com.vanke.tydirium.service.parcel;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.dto.ItemType;
import com.vanke.tydirium.model.dto.ParcelBase;
import com.vanke.tydirium.model.dto.ParcelDetail;
import com.vanke.tydirium.model.dto.ParcelRemark;

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
	ResponseInfo createParcel(ParcelBase parcel);

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

	/**
	 * 查询当前项目取件位置列表
	 * 
	 * @return
	 */
	List<Map<Long, Object>> getCurrentPickupList();

	/**
	 * 根据邮包ID查询邮包详情
	 * 
	 * @param parceId
	 * @return
	 */
	ParcelDetail getParcelDetail(Long parceId);

	/**
	 * 添加邮包备注
	 * 
	 * @param remark
	 * @return
	 */
	ResponseInfo addRemark(ParcelRemark remark);

	/**
	 * 取件
	 * 
	 * @param parcelId
	 *            邮包ID
	 * @param jsonParam
	 *            Json参数，取件人手机号
	 * @return
	 */
	ResponseInfo pickupParcel(Long parcelId, String jsonParam);

}
