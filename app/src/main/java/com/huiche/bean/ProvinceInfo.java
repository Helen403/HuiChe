package com.huiche.bean;

import java.util.List;

/***
 * 省份信息
 * 
 * @author Administrator
 * 
 */
public class ProvinceInfo {
	@Override
	public String toString() {
		return "ProvinceInfo [name=" + name + ", cityList=" + cityList
				+ ", getName()=" + getName() + ", getCityList()="
				+ getCityList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CityInfo_Province> getCityList() {
		return cityList;
	}
	public void setCityList(List<CityInfo_Province> cityList) {
		this.cityList = cityList;
	}
	String name;
	List<CityInfo_Province> cityList;
}
