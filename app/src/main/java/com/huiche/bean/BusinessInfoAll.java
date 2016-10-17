package com.huiche.bean;

import java.util.List;

/**
 * 首页商家信息
 * @author Administrator
 *
 */
public class BusinessInfoAll {
//	 "activitiesIntegral": 1000, 
//     "addTime": "2015-12-19 14:42:58", 
//     "address": "东莞市莞城区市桥社区向阳路35号", 
//     "area": "莞城街道", 
//     "businessArea": "莞城/向阳路/咖啡饮品", 
//     "businessName": "秘密花园", 
//     "businessStoreImages": [


	private boolean isHavePay;

	public boolean isHavePay() {
		return isHavePay;
	}

	public void setIsHavePay(boolean isHavePay) {
		this.isHavePay = isHavePay;
	}



	/******************************************/
	/**
	 * 地域名称
	 */
	private String area;
	/**
	 * 商家图片
	 */
	private List<String> businessStoreImages;
	/**
	 * 商家姓名
	 */
	private String businessName;
	/**
	 * 商家id
	 */
	private String id;
	/**
	 * 商家位置及信息
	 */
	private String businessArea;
	/**
	 * 是否显示 兑的图标 ，有"兑" 为显示，空为 不显示;
	 */
	private String isExchange;
	/**
	 * 是否显示  积 的图标 ，有"积"为显示，空为 不显示;
	 */
	private String isIntegral;
	/**
	 * 是否显示  V 的图标 ，其值 为boolean类型  true 为显示，false 为不显示
	 */
	private String isVip;
	private String isCoupons;
	private String serviceCost;
	private String integralScale;
	public String getArea() {
		return area;
	}

	public String getIsCoupons() {
		return isCoupons;
	}

	public void setIsCoupons(String isCoupons) {
		this.isCoupons = isCoupons;
	}

	public String getServiceCost() {
		return serviceCost;
	}

	public String getIntegralScale() {
		return integralScale;
	}

	public void setIntegralScale(String integralScale) {
		this.integralScale = integralScale;
	}

	public void setServiceCost(String serviceCost) {
		this.serviceCost = serviceCost;
	}

	@Override
	public String toString() {
		return "BusinessInfoAll [area=" + area + ", businessStoreImages="
				+ businessStoreImages + ", businessName=" + businessName
				+ ", id=" + id + ", businessArea=" + businessArea
				+ ", isExchange=" + isExchange + ", isIntegral=" + isIntegral
				+ ", isVip=" + isVip + ", isValue=" + isValue + ", isDouble="
				+ isDouble + ", distinct=" + distinct + ", getArea()="
				+ getArea() + ", getBusinessStoreImages()="
				+ getBusinessStoreImages() + ", getBusinessName()="
				+ getBusinessName() + ", getId()=" + getId()
				+ ", getBusinessArea()=" + getBusinessArea()
				+ ", getIsExchange()=" + getIsExchange() + ", getIsIntegral()="
				+ getIsIntegral() + ", getIsVip()=" + getIsVip()
				+ ", getIsValue()=" + getIsValue() + ", getIsDouble()="
				+ getIsDouble() + ", getDistinct()=" + getDistinct()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	public void setArea(String area) {
		this.area = area;
	}
	public List<String> getBusinessStoreImages() {
		return businessStoreImages;
	}
	public void setBusinessStoreImages(List<String> businessStoreImages) {
		this.businessStoreImages = businessStoreImages;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public String getIsExchange() {
		return isExchange;
	}
	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}
	public String getIsIntegral() {
		return isIntegral;
	}
	public void setIsIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getIsValue() {
		return isValue;
	}
	public void setIsValue(String isValue) {
		this.isValue = isValue;
	}
	public String getIsDouble() {
		return isDouble;
	}
	public void setIsDouble(String isDouble) {
		this.isDouble = isDouble;
	}
	public String getDistinct() {
		return distinct;
	}
	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}
	/**
	 * 是否显示  值 的图标 ，有 "值" 为显示，空为 不显示;
	 */
	private String isValue;
	/**
	 * 是否显示  倍 的图标 ，有 "倍" 为显示，空为 不显示;
	 */
	private String isDouble;
	/**
	 * 商家距离本地的距离，如果为0，则定位不成功
	 */
	private String distinct;
	
}
