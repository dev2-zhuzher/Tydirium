package com.vanke.tydirium.service.parcel;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.vanke.tydirium.BaseTest;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.dto.ItemType;
import com.vanke.tydirium.model.dto.ParcelBase;

/**
 * 
 * 
 * @Description: 邮包服务ParcelService测试类
 *
 * @author: songjia
 * @date: 2017年8月25日 下午2:14:22
 */
public class ParcelServiceTest extends BaseTest {

	@Autowired
	private ParcelService parcelService;

	/**
	 * 新建代收邮包测试
	 */
	@Test
	public void createParcel() {
		ParcelBase bean = new ParcelBase();
		bean.setLogisticCode("9891077826312");
		bean.setShipperCode("ems");
		bean.setReceiverMobile("13168796024");
		bean.setReceiveAddress("深圳梅林万科");
		bean.setReceiverName("宋佳");
		bean.setPositionId(1L);
		
		ResponseInfo response = this.parcelService.createParcel(bean);
		System.out.println(JSONObject.toJSONString(response));
	}
	
	/**
	 * 获取物品类型集合测试
	 */
	@Test
	public void queryItemTypeList() {
		List<ItemType> list = this.parcelService.queryItemTypeList();
		for (ItemType itemType : list) {
			System.out.println(JSONObject.toJSONString(itemType));
		}
	}
	
	
	
	/**
	 * 查询当前项目取件位置列表测试
	 */
	@Test
	public void queryCurrentPickupList() {
		List<Map<Long, Object>> list = this.parcelService.getCurrentPickupList();
		for (Map<Long, Object> map : list) {
			System.out.println(map.get("id"));
			System.out.println(map.get("position"));
		}
	}
}
