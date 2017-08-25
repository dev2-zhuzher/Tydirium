package com.vanke.tydirium.model.base;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 分页基础信息类
 *
 * @author: songjia
 * @date: 2017年8月25日 下午6:41:07
 */
public class PageBase implements Serializable {

	private static final long serialVersionUID = -7314758158046537271L;

	// 当前默认第一页
	private static final Integer DEFAULT_PAGENUM = Integer.valueOf(1);

	// 每页默认显示10条记录
	private static final Integer DEFAULT_PAGESIZE = Integer.valueOf(10);

	// 当前第几页
	private Integer pageNum = DEFAULT_PAGENUM;

	// 每页显示记录数
	private Integer pageSize = DEFAULT_PAGESIZE;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
