package com.huiche.bean;

import java.util.List;
/***
 * 地级市
 * @author Administrator
 *
 */
public class CityInfo_Province {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CityInfo_Province [name=" + name + ", areaList=" + areaList
				+ ", getName()=" + getName() + ", getAreaList()="
				+ getAreaList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public List<String> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}
	String name;
	List<String> areaList;
}
