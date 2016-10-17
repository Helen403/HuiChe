package com.huiche.bean;

import java.util.List;

/***
 * 更多页面中的信息
 * @author Administrator
 *
 */
public class BusinessInfo {
//	  "pName": "餐饮美食",
//      "sName": [
//          {
//              "id": 391,
//              "integealBack": 10,
//              "name": "中餐",
//              "parent": 388,
//              "serviceCost": 10
	/***
	 * 一级菜单
	 */
	private String pName;
	@Override
	public String toString() {
		return "BusinessInfo [pName=" + pName + ", sName=" + sName + "]";
	}
	private List<BusinessInfo_of> sName;
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public List<BusinessInfo_of> getsName() {
		return sName;
	}
	public void setsName(List<BusinessInfo_of> sName) {
		this.sName = sName;
	}
	
}
