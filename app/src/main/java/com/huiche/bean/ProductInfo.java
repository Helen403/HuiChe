package com.huiche.bean;

import java.util.List;

import android.R.integer;

/**
 * 商品信息实体类,可拓展通用
 */
public class ProductInfo {
	public int browseCount;
	public String businessName;//商家名称
	public String businessArea;//商家地址
	public int businessStoreId;//商家ID
	public int collectionCount;
	public double discount;//折扣（无参数：无折扣）
	public String easyInfo;
	public int exchangePeople;

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public List<String> goodsImgs;//商品图片
	public List<String> goodsThumbs;
	public int id;//商品ID
	public String info;
	public double integral;//积分价（兑换价）
	public boolean isDiscount;
	public double latitude;
	public double longitude;
	public double myLatitude;
	public double myLongitude;
	public int limitDiscountBuy;
	public double marketPrice;
	public String name;//商品名称
	public String no;
	public int number;
	public List<String> originalImgs;
	public double realPrice;
	public int sort;
	public int status;
	public int ver;

	public void setIsDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public int productTypeId;
	public String tel;
	
	
	public ProductInfo() {
		
	}

	public double getMyLatitude() {
		return myLatitude;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	public void setMyLatitude(double myLatitude) {
		this.myLatitude = myLatitude;
	}

	public double getMyLongitude() {
		return myLongitude;
	}

	public void setMyLongitude(double myLongitude) {
		this.myLongitude = myLongitude;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(int browseCount) {
		this.browseCount = browseCount;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public int getBusinessStoreId() {
		return businessStoreId;
	}

	public void setBusinessStoreId(int businessStoreId) {
		this.businessStoreId = businessStoreId;
	}

	public int getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}

	public String getEasyInfo() {
		return easyInfo;
	}

	public void setEasyInfo(String easyInfo) {
		this.easyInfo = easyInfo;
	}

	public int getExchangePeople() {
		return exchangePeople;
	}

	public void setExchangePeople(int exchangePeople) {
		this.exchangePeople = exchangePeople;
	}

	public List<String> getGoodsImgs() {
		return goodsImgs;
	}

	public void setGoodsImgs(List<String> goodsImgs) {
		this.goodsImgs = goodsImgs;
	}

	public List<String> getGoodsThumbs() {
		return goodsThumbs;
	}

	public void setGoodsThumbs(List<String> goodsThumbs) {
		this.goodsThumbs = goodsThumbs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getIntegral() {
		return integral;
	}

	public void setIntegral(double integral) {
		this.integral = integral;
	}

	public boolean isDiscount() {
		return isDiscount;
	}

	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getLimitDiscountBuy() {
		return limitDiscountBuy;
	}

	public void setLimitDiscountBuy(int limitDiscountBuy) {
		this.limitDiscountBuy = limitDiscountBuy;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<String> getOriginalImgs() {
		return originalImgs;
	}

	public void setOriginalImgs(List<String> originalImgs) {
		this.originalImgs = originalImgs;
	}

	public double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProductInfo [browseCount=" + browseCount + ", businessName="
				+ businessName + ", businessArea=" + businessArea
				+ ", businessStoreId=" + businessStoreId + ", collectionCount="
				+ collectionCount + ", discount=" + discount + ", easyInfo="
				+ easyInfo + ", exchangePeople=" + exchangePeople
				+ ", goodsImgs=" + goodsImgs + ", goodsThumbs=" + goodsThumbs
				+ ", id=" + id + ", info=" + info + ", integral=" + integral
				+ ", isDiscount=" + isDiscount + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", myLatitude=" + myLatitude
				+ ", myLongitude=" + myLongitude + ", limitDiscountBuy="
				+ limitDiscountBuy + ", marketPrice=" + marketPrice + ", name="
				+ name + ", no=" + no + ", number=" + number
				+ ", originalImgs=" + originalImgs + ", realPrice=" + realPrice
				+ ", sort=" + sort + ", status=" + status + ", productTypeId="
				+ productTypeId + ", tel=" + tel + "]";
	}


}
