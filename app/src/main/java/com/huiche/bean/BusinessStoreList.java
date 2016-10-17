package com.huiche.bean;


import java.util.List;

public class BusinessStoreList {
	private String businessName;//商家名称
	private int id;//商家id
	public List<ShoppingCartProductInfo> cartMessages;
	private boolean isSelect=false;
	public BusinessStoreList(){

	}
	public BusinessStoreList(int id,String businessName){
		this.id = id;
		this.businessName = businessName;
	}
	public boolean isSelect() {
		return isSelect;
	}

	public void setIsSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ShoppingCartProductInfo> getCartMessages() {
		return cartMessages;
	}

	public void setCartMessages(List<ShoppingCartProductInfo> cartMessages) {
		this.cartMessages = cartMessages;
	}

	@Override
	public String toString() {
		return "BusinessStoreList [businessName=" + businessName + ", id=" + id
				+"]";
	}
	
	
}
