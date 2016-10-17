package com.huiche.PostResult;

public class CollectionShop {
	public String businessStoreId;
	public String businessName;
	public String phone;
	public String isExchange;
	public String reward;
	public String isIntegeral;
	public String photo;
	public String id;
	public String vip;
	public String time;
	public String productCount;
	public String level;
	public String businessArea;
	public String businessAddress;


	/***************************************************/

	private boolean isHavePay;

	public boolean isHavePay() {
		return isHavePay;
	}

	public void setIsHavePay(boolean isHavePay) {
		this.isHavePay = isHavePay;
	}



	/**************************************************/

	
	public CollectionShop(){
		
	}

	public String getBusinessStoreId() {
		return businessStoreId;
	}

	public void setBusinessStoreId(String businessStoreId) {
		this.businessStoreId = businessStoreId;
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

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getIsIntegeral() {
		return isIntegeral;
	}

	public void setIsIntegeral(String isIntegeral) {
		this.isIntegeral = isIntegeral;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	@Override
	public String toString() {
		return "CollectionShop [businessStoreId=" + businessStoreId
				+ ", businessName=" + businessName + ", phone=" + phone
				+ ", isExchange=" + isExchange + ", reward=" + reward
				+ ", isIntegeral=" + isIntegeral + ", photo=" + photo + ", id="
				+ id + ", vip=" + vip + ", time=" + time + ", productCount="
				+ productCount + ", level=" + level + ", businessArea="
				+ businessArea + ", businessAddress=" + businessAddress + "]";
	}

}
