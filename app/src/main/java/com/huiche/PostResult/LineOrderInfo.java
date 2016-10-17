package com.huiche.PostResult;

import java.util.List;
/***
 * 
 * @author Administrator
 * 线下订单-全部订单的数据
 *
 */
public class LineOrderInfo {
	public String id;
	public boolean canCancel;
	public String paymentDate;
	public String checkNo;
	public String businessName;
	public String phone;
	public String status;
	public String orderType;
	public List<LineOrderDetail>productLists;
	public String no;
	public String allIntegral;
	public String payType;
	public String createDate;
	public String freeServiceQuota;
	public LineOrderInfo(){
		
	}

	public String getFreeServiceQuota() {
		return freeServiceQuota;
	}

	public void setFreeServiceQuota(String freeServiceQuota) {
		this.freeServiceQuota = freeServiceQuota;
	}

	public List<LineOrderDetail> getProductLists() {
		return productLists;
	}

	public void setProductLists(List<LineOrderDetail> productLists) {
		this.productLists = productLists;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isCanCancel() {
		return canCancel;
	}
	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
	

}
