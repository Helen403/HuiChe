package com.huiche.PostResult;

import java.io.Serializable;
import java.util.List;

/**
 * 省份
 * @author Administrator
 *
 */
public class Province implements Serializable{
	private static final long serialVersionUID = 1762589607474764894L;
	
	public String name;
	public List<City> cityList;
	
	public Province() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<City> getCityList() {
		return cityList;
	}
	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
	
	@Override
	public String toString() {
		return "Province [name=" + name + ", cityList=" + cityList + "]";
	}

}
