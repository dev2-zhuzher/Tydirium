package com.vanke.tydirium.web.controller.parcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanke.tydirium.model.base.ResponseInfo;
import com.vanke.tydirium.model.dto.Parcel;
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
	 * 新建代收邮包
	 * 
	 * @param parcel
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseInfo createParcel(@RequestBody Parcel parcel) {
		return this.parcelService.createParcel(parcel);
	}
}
