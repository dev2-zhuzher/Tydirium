package com.vanke.tydirium.web.controller.parcel;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.dto.ParcelBase;
import com.vanke.tydirium.model.dto.ParcelRemark;
import com.vanke.tydirium.service.parcel.ParcelService;

/**
 * 
 * 
 * @Description: 邮包controller
 *
 * @author: songjia
 * @date: 2017年8月25日 上午10:23:54
 */
@Controller
@RequestMapping("/admin/parcel")
public class ParcelController {

	@Autowired
	private ParcelService parcelService;

	/**
	 * 跳转至邮包列表首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toParcelListPage() {
		return "/parcel/list";
	}

	/**
	 * 分页查询代收邮包列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lists", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<List<Map<String, Object>>> getParcelList(@RequestBody ParcelBase parcelBase) {
		PageInfo<List<Map<String, Object>>> resultMap = this.parcelService.getParcelList(parcelBase);
		return resultMap;
	}

	/**
	 * 进入新建邮包页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String toCreatePage(Model model) {
		//model.addAttribute("itemList", this.parcelService.getParcelList());// 获取物品类别字典
		model.addAttribute("positionList", this.parcelService.getCurrentPickupList());// 获取当前项目取件位置字典
		return "";
	}

	/**
	 * 新建代收邮包
	 * 
	 * @param parcel
	 * @return
	 */
	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseInfo createParcel(@RequestBody ParcelBase parcel) {
		return this.parcelService.createParcel(parcel);
	}

	/**
	 * 进入邮包详情页面
	 * 
	 * @param model
	 * @param parceId
	 * @return
	 */
	@RequestMapping(value = "/{parceId}", method = RequestMethod.GET)
	public String parcelDetail(Model model, @PathVariable("parceId") Long parceId) {
		model.addAttribute("detail", this.parcelService.getParcelDetail(parceId));
		return "detail";
	}

	/**
	 * 添加邮包备注【注意：处于“未取”状态的才能添加备注】
	 * 
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/remark", method = RequestMethod.POST)
	@ResponseBody
	public ResponseInfo addRemark(@RequestBody ParcelRemark remark) {
		return this.parcelService.addRemark(remark);
	}

	/**
	 * 取件
	 * 
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value = "/pickup/{parcelId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseInfo pickupParcel(@PathVariable("parcelId") Long parcelId, @RequestBody String jsonParam) {
		return this.parcelService.pickupParcel(parcelId, jsonParam);
	}
}
