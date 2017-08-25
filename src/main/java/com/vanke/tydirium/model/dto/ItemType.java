package com.vanke.tydirium.model.dto;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 物品类型实体类
 *
 * @author: songjia
 * @date: 2017年8月25日 下午1:45:45
 */
public class ItemType implements Serializable {

	private static final long serialVersionUID = 1528222540666190475L;

	// 物品类型ID
	private Long itemId;

	// 物品类型名称
	private String name;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
