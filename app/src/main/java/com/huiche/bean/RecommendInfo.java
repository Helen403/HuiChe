package com.huiche.bean;

import android.graphics.drawable.Drawable;


/**
 * 店长推荐实体类
 * @author Administrator
 *
 */
public class RecommendInfo {
	public Drawable icon;
	public String productName;
	public String menberPrice;
	public String integralExchange;
	
	
	public RecommendInfo() {
		super();
	}

	public RecommendInfo(Drawable icon, String productName, String menberPrice,
			String integralExchange) {
		super();
		this.icon = icon;
		this.productName = productName;
		this.menberPrice = menberPrice;
		this.integralExchange = integralExchange;
	}
	
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getproductName() {
		return productName;
	}
	public void setproductName(String productName) {
		this.productName = productName;
	}
	public String getMenberPrice() {
		return menberPrice;
	}
	public void setMenberPrice(String menberPrice) {
		this.menberPrice = menberPrice;
	}
	public String getIntegralExchange() {
		return integralExchange;
	}
	public void setIntegralExchange(String integralExchange) {
		this.integralExchange = integralExchange;
	}
	
	
}
