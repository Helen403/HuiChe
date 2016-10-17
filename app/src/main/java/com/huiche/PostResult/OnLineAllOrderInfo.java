package com.huiche.PostResult;

import java.util.List;

public class OnLineAllOrderInfo {
	public String cashQuota;
	public String paymentDate;
	public String businessName;
	public String allPrice;
	public String status;
	public String no;
	public String allIntegral;
	public String allRealPrice;
	public String demandInfo;
	public String integralQuota;
	public String id;
	public String postageType;
	public String payType;
	public String createDate;
	public List<OnLineAllOrderDetail> onlineProducts;
	public OnLineAllOrderInfo(){
		
	}
	public String getCashQuota() {
		return cashQuota;
	}
	public void setCashQuota(String cashQuota) {
		this.cashQuota = cashQuota;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(String allPrice) {
		this.allPrice = allPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAllIntegral() {
		return allIntegral;
	}
	public void setAllIntegral(String allIntegral) {
		this.allIntegral = allIntegral;
	}
	public String getAllRealPrice() {
		return allRealPrice;
	}
	public void setAllRealPrice(String allRealPrice) {
		this.allRealPrice = allRealPrice;
	}
	public String getDemandInfo() {
		return demandInfo;
	}
	public void setDemandInfo(String demandInfo) {
		this.demandInfo = demandInfo;
	}
	public String getIntegralQuota() {
		return integralQuota;
	}
	public void setIntegralQuota(String integralQuota) {
		this.integralQuota = integralQuota;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostageType() {
		return postageType;
	}
	public void setPostageType(String postageType) {
		this.postageType = postageType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<OnLineAllOrderDetail> getOnlineProducts() {
		return onlineProducts;
	}
	public void setOnlineProducts(List<OnLineAllOrderDetail> onlineProducts) {
		this.onlineProducts = onlineProducts;
	}
	
	
	

}
