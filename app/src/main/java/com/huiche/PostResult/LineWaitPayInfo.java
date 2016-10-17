package com.huiche.PostResult;

import java.util.List;

public class LineWaitPayInfo {
	public String id;
	public String businessName;
	public String status;
	public String orderType;
	public String no;
	public String allIntegral;
	public String createDate;
	public boolean  canCancel;
	public String checkNo;
	public String businessStoreId;
	public String freeServiceQuota;
	public List<LineWaitDetail>productLists;
	public LineWaitPayInfo(){
		
	}

	public String getFreeServiceQuota() {
		return freeServiceQuota;
	}

	public void setFreeServiceQuota(String freeServiceQuota) {
		this.freeServiceQuota = freeServiceQuota;
	}

	public String getBusinessStoreId() {
		return businessStoreId;
	}

	public void setBusinessStoreId(String businessStoreId) {
		this.businessStoreId = businessStoreId;
	}

	public String getCheckNo() {
		return checkNo;
	}




	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}




	public boolean isCanCancel() {
		return canCancel;
	}



	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<LineWaitDetail> getProductLists() {
		return productLists;
	}
	public void setProductLists(List<LineWaitDetail> productLists) {
		this.productLists = productLists;
	}
	
	
	

}
