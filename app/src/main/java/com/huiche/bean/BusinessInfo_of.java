package com.huiche.bean;
/***
 * 更多中存储 gridview中数据信息
 * @author Administrator
 *
 */
public class BusinessInfo_of {
	private String id;
	private String name;
	@Override
	public String toString() {
		return "BusinessInfo_of [id=" + id + ", name=" + name + ", parent="
				+ parent + ", serviceCost=" + serviceCost + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(String serviceCost) {
		this.serviceCost = serviceCost;
	}
	private String parent;
	private String serviceCost;
}
