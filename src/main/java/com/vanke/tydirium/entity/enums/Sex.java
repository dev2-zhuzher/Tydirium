package com.vanke.tydirium.entity.enums;

/**
 * 性别
 * 
 * @author penn
 *
 */
public enum Sex {

	UNKNOW("未知", 0), MALE("男", 1), FEMALE("女", 2);

	private String name;
	private int index;

	private Sex(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Sex getSex(int index) {
		if (index == 1) {
			return MALE;
		} else if (index == 2) {
			return FEMALE;
		} else {
			return UNKNOW;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
