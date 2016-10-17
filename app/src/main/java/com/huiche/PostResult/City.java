package com.huiche.PostResult;

import java.io.Serializable;
import java.util.List;

/**
 * 城市
 * 
 * @author Administrator
 * 
 */
public class City implements Serializable{
	private static final long serialVersionUID = 289166750558972860L;
	public String name;
	public List<String> areaList;

	public City() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", areaList=" + areaList + "]";
	}

}
