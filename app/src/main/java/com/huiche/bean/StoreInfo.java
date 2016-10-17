package com.huiche.bean;

import java.util.List;

import android.R.integer;

/**
 * 商家基本信息实体类
 *
 * @author Administrator
 */
public class StoreInfo {
    public String addTime;
    public String address;
    public String alreadyCollection;
    public String area;
    public BankMessage bankMessage;
    public String bankName;
    public String bankNo;
    public String bankUserName;
    public String businessArea;
    public String businessName;
    public String payCount;
    public List<String> businessStoreImages;

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<String> pictures;
    public double cash;
    public int category;
    public double changeIncome;
    public String city;
    public double freezeCash;
    public int id;
    public double integral;
    public int integralScale;
    public String isDouble;
    public int isExamine;
    public String isExchange;
    public boolean isHavePay;
    public String isIntegral;
    public String isValue;
    public boolean isVip;
    public double latitude;
    public int limitCashOutQuota;
    public double longitude;
    public double menberCostCash;
    public double menberCostIntegral;
    public int messageCount;
    public double messageMark;
    public String name;
    public String projectDesc;
    public String province;
    public String ptype;
    public String reward;
    public int serviceCost;
    public boolean status;
    public String storeDesc;
    public String stype;
    public String tel;
    public String vip;
    public Sdip sdip;

    public StoreInfo() {

    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public Sdip getSdip() {
        return sdip;
    }

    public void setSdip(Sdip sdip) {
        this.sdip = sdip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public void setIsHavePay(boolean isHavePay) {
        this.isHavePay = isHavePay;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlreadyCollection() {
        return alreadyCollection;
    }

    public void setAlreadyCollection(String alreadyCollection) {
        this.alreadyCollection = alreadyCollection;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BankMessage getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(BankMessage bankMessage) {
        this.bankMessage = bankMessage;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public List<String> getBusinessStoreImages() {
        return businessStoreImages;
    }

    public void setBusinessStoreImages(List<String> businessStoreImages) {
        this.businessStoreImages = businessStoreImages;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getChangeIncome() {
        return changeIncome;
    }

    public void setChangeIncome(double changeIncome) {
        this.changeIncome = changeIncome;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getFreezeCash() {
        return freezeCash;
    }

    public void setFreezeCash(double freezeCash) {
        this.freezeCash = freezeCash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public int getIntegralScale() {
        return integralScale;
    }

    public void setIntegralScale(int integralScale) {
        this.integralScale = integralScale;
    }

    public String getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(String isDouble) {
        this.isDouble = isDouble;
    }

    public int getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(int isExamine) {
        this.isExamine = isExamine;
    }

    public String getIsExchange() {
        return isExchange;
    }

    public void setIsExchange(String isExchange) {
        this.isExchange = isExchange;
    }

    public boolean isHavePay() {
        return isHavePay;
    }

    public void setHavePay(boolean isHavePay) {
        this.isHavePay = isHavePay;
    }

    public String getIsIntegral() {
        return isIntegral;
    }

    public void setIsIntegral(String isIntegral) {
        this.isIntegral = isIntegral;
    }

    public String getIsValue() {
        return isValue;
    }

    public void setIsValue(String isValue) {
        this.isValue = isValue;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getLimitCashOutQuota() {
        return limitCashOutQuota;
    }

    public void setLimitCashOutQuota(int limitCashOutQuota) {
        this.limitCashOutQuota = limitCashOutQuota;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getMenberCostCash() {
        return menberCostCash;
    }

    public void setMenberCostCash(double menberCostCash) {
        this.menberCostCash = menberCostCash;
    }

    public double getMenberCostIntegral() {
        return menberCostIntegral;
    }

    public void setMenberCostIntegral(double menberCostIntegral) {
        this.menberCostIntegral = menberCostIntegral;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public double getMessageMark() {
        return messageMark;
    }

    public void setMessageMark(double messageMark) {
        this.messageMark = messageMark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public int getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost) {
        this.serviceCost = serviceCost;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "addTime='" + addTime + '\'' +
                ", address='" + address + '\'' +
                ", alreadyCollection='" + alreadyCollection + '\'' +
                ", area='" + area + '\'' +
                ", bankMessage=" + bankMessage +
                ", bankName='" + bankName + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankUserName='" + bankUserName + '\'' +
                ", businessArea='" + businessArea + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessStoreImages=" + businessStoreImages +
                ", cash=" + cash +
                ", category=" + category +
                ", changeIncome=" + changeIncome +
                ", city='" + city + '\'' +
                ", freezeCash=" + freezeCash +
                ", id=" + id +
                ", integral=" + integral +
                ", integralScale=" + integralScale +
                ", isDouble='" + isDouble + '\'' +
                ", isExamine=" + isExamine +
                ", isExchange='" + isExchange + '\'' +
                ", isHavePay=" + isHavePay +
                ", isIntegral='" + isIntegral + '\'' +
                ", isValue='" + isValue + '\'' +
                ", isVip=" + isVip +
                ", latitude=" + latitude +
                ", limitCashOutQuota=" + limitCashOutQuota +
                ", longitude=" + longitude +
                ", menberCostCash=" + menberCostCash +
                ", menberCostIntegral=" + menberCostIntegral +
                ", messageCount=" + messageCount +
                ", messageMark=" + messageMark +
                ", name='" + name + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", province='" + province + '\'' +
                ", ptype='" + ptype + '\'' +
                ", reward='" + reward + '\'' +
                ", serviceCost=" + serviceCost +
                ", status=" + status +
                ", storeDesc='" + storeDesc + '\'' +
                ", stype='" + stype + '\'' +
                ", tel='" + tel + '\'' +
                ", vip='" + vip + '\'' +
                ", sdip=" + sdip +
                '}';
    }
}
