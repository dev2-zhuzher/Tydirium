package com.vanke.tydirium.entity.enums;

/**
 * 
 * 
 * @Description: 快递公司&物流编号枚举类
 *
 * @author: songjia
 * @date: 2017年8月22日 下午4:17:13
 */
public enum ShipperCodeEnum {

	ANE("ANE", "安能物流"),

	AXD("AXD", "安信达快递"),

	BFDF("BFDF", "百福东方"),

	BQXHM("BQXHM", "北青小红帽"),

	CCES("CCES", "CCES快递"),

	CITY100("CITY100", "城市100"),

	COE("COE", "COE东方快递"),

	CSCY("CSCY", "长沙创一"),

	DBL("DBL", "德邦"),

	DHL("DHL", "DHL"),

	DSWL("DSWL", "D速物流"),

	DTWL("DTWL", "大田物流"),

	EMS("EMS", "EMS"),

	FAST("FAST", "快捷速递"),

	FEDEX("FEDEX", "FedEx联邦快递"),

	FKD("FKD", "飞康达"),

	GDEMS("GDEMS", "广东邮政"),

	GSD("GSD", "共速达"),

	GTO("GTO", "国通快递"),

	GTSD("GTSD", "高铁速递"),

	HFWL("HFWL", "汇丰物流"),

	HHTT("HHTT", "天天快递"),

	HLWL("HLWL", "恒路物流"),

	HOAU("HOAU", "天地华宇"),

	hq568("hq568", "华强物流"),

	HTKY("HTKY", "百世汇通"),

	HXLWL("HXLWL", "华夏龙物流"),

	HYLSD("HYLSD", "好来运快递"),

	JD("JD", "京东快递"),

	JGSD("JGSD", "京广速递"),

	JJKY("JJKY", "佳吉快运"),

	JTKD("JTKD", "捷特快递"),

	JXD("JXD", "急先达"),

	JYKD("JYKD", "晋越快递"),

	JYM("JYM", "加运美"),

	JYWL("JYWL", "佳怡物流"),

	LB("LB", "龙邦快递"),

	LHT("LHT", "联昊通速递"),

	MHKD("MHKD", "民航快递"),

	MLWL("MLWL", "明亮物流"),

	NEDA("NEDA", "能达速递"),

	QCKD("QCKD", "全晨快递"),

	QFKD("QFKD", "全峰快递"),

	QRT("QRT", "全日通快递"),

	SAWL("SAWL", "圣安物流"),

	SDWL("SDWL", "上大物流"),

	SF("SF", "顺丰快递"),

	SFWL("SFWL", "盛丰物流"),

	SHWL("SHWL", "盛辉物流"),

	ST("ST", "速通物流"),

	STO("STO", "申通快递"),

	SURE("SURE", "速尔快递"),

	TSSTO("TSSTO", "唐±申通"),

	UAPEX("UAPEX", "全一快递"),

	UC("UC", "优速快递"),

	WJWL("WJWL", "万家物流"),

	WXWL("WXWL", "万象物流"),

	XBWL("XBWL", "新邦物流"),

	XFEX("XFEX", "信丰快递"),

	XYT("XYT", "希优特"),

	YADEX("YADEX", "源安达快递"),

	YCWL("YCWL", "远成物流"),

	YD("YD", "韵达快递"),

	YFEX("YFEX", "越丰物流"),

	YFHEX("YFHEX", "原飞航物流"),

	YFSD("YFSD", "亚风快递"),

	YTKD("YTKD", "运通快递"),

	YTO("YTO", "圆通速递"),

	YZPY("YZPY", "邮政平邮/小包"),

	ZENY("ZENY", "增益快递"),

	ZHQKD("ZHQKD", "汇强快递"),

	ZJS("ZJS", "宅急送"),

	ZTE("ZTE", "众通快递"),

	ZTKY("ZTKY", "中铁快运"),

	ZTO("ZTO", "中通速递"),

	ZTWL("ZTWL", "中铁物流"),

	ZYWL("ZYWL", "中邮物流");

	// 快递公司编号
	private String code;

	// 快递公司名称
	private String company;

	public String getCode() {
		return code;
	}

	public String getCompany() {
		return company;
	}

	private ShipperCodeEnum(String code, String company) {
		this.code = code;
		this.company = company;
	}

}
