package com.huiche.bean;

import java.util.List;

/**
 * 商家详情当前商家商品列表实体类
 * @author Administrator
 *
 */
public class BusinessDetailInfo {
	
	public int browseCount;
	public String businessName;
	public int businessStoreId;
	public int collectionCount;
	public String easyInfo;
	public int exchangePeople;
	public List<String> goodsImgs;
	public List<String> goodsThumbs;
	
	public BusinessDetailInfo() {
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

	@Override
	public String toString() {
		return "BusinessDetailInfo [browseCount=" + browseCount
				+ ", businessName=" + businessName + ", businessStoreId="
				+ businessStoreId + ", collectionCount=" + collectionCount
				+ ", easyInfo=" + easyInfo + ", exchangePeople="
				+ exchangePeople + ", goodsImgs=" + goodsImgs
				+ ", goodsThumbs=" + goodsThumbs + "]";
	}
}
