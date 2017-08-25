package com.vanke.tydirium.serviceimpl.parcel;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vanke.tydirium.mapper.ParcelMapper;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.base.UserThreadLocal;
import com.vanke.tydirium.model.dto.ItemType;
import com.vanke.tydirium.model.dto.ParcelBase;
import com.vanke.tydirium.model.dto.ParcelDetail;
import com.vanke.tydirium.model.dto.ParcelRemark;
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
	private ParcelMapper parcelMapper;

	/**
	 * 分页查询代收邮包列表
	 * 
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#getParcelList()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo<List<Map<String, Object>>> getParcelList() {
		PageInfo<List<Map<String, Object>>> page = new PageInfo<List<Map<String, Object>>>();
		PageHelper.startPage(1, 10, true);
		List<Map<String, Object>> result = this.parcelMapper.queryParcelList();
		// 用PageInfo对结果进行包装
		page = new PageInfo(result);
		return page;
	}

	/**
	 * 新建代收邮包
	 * 
	 * @param parcel
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#createParcel(com.vanke.tydirium.model.bo.ParcelBase)
	 */
	@Override
	public ResponseInfo createParcel(ParcelBase parcel) {
		// 参数判断

		// 生成取件码

		// 根据手机号码查询houseCode[不是注册用户则为空]

		// 数据库插入邮包记录

		// 如果是注册用户，则推送消息；否则发送短信
		
		// TODO

		return ResponseInfo.getSuccessInstance();
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
		result = this.parcelMapper.queryItemTypeList();

		// 存储到缓存中
		this.redisCacheUtil.setList(RedisKeyCommonUtil.ITEM_TYPE_LIST_KEY, result);

		return result;
	}

	/**
	 * 查询当前项目取件位置列表
	 * 
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#getCurrentPickupList()
	 */
	@Override
	public List<Map<Long, Object>> getCurrentPickupList() {

		// 当前登录用户projectCode
		String projectCode = UserThreadLocal.getCurrUser().getProjectCode();

		// 从缓存中获取
		List<Map<Long, Object>> result = this.redisCacheUtil.getList(RedisKeyCommonUtil.PROJECT_PICKUP_POSITION_KEY.replace("${projectCode}", projectCode));
		if (result != null) {
			return result;
		}
		// 从数据库中获取
		result = this.parcelMapper.getPickupPositionList(projectCode);

		// 存储到缓存中
		this.redisCacheUtil.setList(RedisKeyCommonUtil.PROJECT_PICKUP_POSITION_KEY.replace("${projectCode}", projectCode), result);
		
		return result;
	}

	/**
	 * 根据邮包ID查询邮包详情
	 * 
	 * @param parceId
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#getParcelDetail(java.lang.Long)
	 */
	@Override
	public ParcelDetail getParcelDetail(Long parceId) {
		return this.parcelMapper.getParcelDetail(parceId);
	}

	/**
	 * 添加邮包备注【注意：处于“未取”状态的才能添加备注】
	 * 
	 * @param remark
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#addRemark(com.vanke.tydirium.model.dto.ParcelRemark)
	 */
	@Override
	public ResponseInfo addRemark(ParcelRemark remark) {
		// 判断邮包状态
		Integer status = this.parcelMapper.queryParcelStatus(remark.getParcelId());
		if (status == null || status.equals(Integer.valueOf(2))) {
			return ResponseInfo.getFailInstance("邮包不存在或邮包状态非法");
		}
		// 备注内容判断
		if (StringUtils.isBlank(remark.getRemark())) {
			return ResponseInfo.getFailInstance("备注内容不能为空");
		}
		
		// 填充备注人手机号码
		remark.setMobile(UserThreadLocal.getCurrUser().getMobile());
		
		// 插入备注记录 TODO

		return ResponseInfo.getSuccessInstance();
	}

	/**
	 * 取件
	 * 
	 * @param parcleId
	 * @param jsonParam
	 * @return
	 * @see com.vanke.tydirium.service.parcel.ParcelService#pickupParcel(java.lang.Long, java.lang.String)
	 */
	@Override
	public ResponseInfo pickupParcel(Long parcleId, String jsonParam) {
		// 参数判断
		if (StringUtils.isBlank(jsonParam)) {
			return ResponseInfo.getFailInstance("参数异常");
		}
		JSONObject json = JSONObject.parseObject(jsonParam);
		// 取件人手机号码
		String mobile = json.getString("mobile");
		if (StringUtils.isBlank(mobile)) {
			return ResponseInfo.getFailInstance("取件人手机不能为空");
		}
		
		// 取件更新邮包状态
		int row = this.parcelMapper.updateParcelStatus(parcleId, mobile);
		if (row == 0) {
			return ResponseInfo.getFailInstance("系统异常，请稍后重试");
		}
		
		return ResponseInfo.getSuccessInstance();
	}

}
