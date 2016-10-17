package com.huiche.bean;


import com.huiche.PostResult.ProductData;

public class ShoppingCartProductInfo {
	public String id;//购物车id
	public int count;
	public String createTime;
	public ProductData productP;
	private boolean isChoseProduct = false;//是否选中

	public ShoppingCartProductInfo(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public ProductData getProductP() {
		return productP;
	}

	public void setProductP(ProductData productP) {
		this.productP = productP;
	}

	public boolean isChoseProduct() {
		return isChoseProduct;
	}

	public void setIsChoseProduct(boolean isChoseProduct) {
		this.isChoseProduct = isChoseProduct;
	}




	
}
