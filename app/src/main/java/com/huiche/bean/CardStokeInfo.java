package com.huiche.bean;

/**
 * Created by Administrator on 2016/8/5 0005.
 * 商家卡券
 */
public class CardStokeInfo {
    public int id;
    public String bussinessStoreId;
    public String businessName;
    public String businessStoreImage;
    public String inventory;
    public String deduction;
    public String price;
    public String startTime;
    public String endTime;
    public String getLimit;
    public String menberGetCount;
    public String menberUseCount;
    public String details;
    public String createtime;

    public CardStokeInfo() {

    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessStoreImage() {
        return businessStoreImage;
    }

    public void setBusinessStoreImage(String businessStoreImage) {
        this.businessStoreImage = businessStoreImage;
    }

    public String getBussinessStoreId() {
        return bussinessStoreId;
    }

    public void setBussinessStoreId(String bussinessStoreId) {
        this.bussinessStoreId = bussinessStoreId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGetLimit() {
        return getLimit;
    }

    public void setGetLimit(String getLimit) {
        this.getLimit = getLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getMenberGetCount() {
        return menberGetCount;
    }

    public void setMenberGetCount(String menberGetCount) {
        this.menberGetCount = menberGetCount;
    }

    public String getMenberUseCount() {
        return menberUseCount;
    }

    public void setMenberUseCount(String menberUseCount) {
        this.menberUseCount = menberUseCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "CardStokeInfo{" +
                "businessName='" + businessName + '\'' +
                ", id=" + id +
                ", bussinessStoreId='" + bussinessStoreId + '\'' +
                ", businessStoreImage='" + businessStoreImage + '\'' +
                ", inventory='" + inventory + '\'' +
                ", deduction='" + deduction + '\'' +
                ", price='" + price + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", getLimit='" + getLimit + '\'' +
                ", menberGetCount='" + menberGetCount + '\'' +
                ", menberUseCount='" + menberUseCount + '\'' +
                ", details='" + details + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
