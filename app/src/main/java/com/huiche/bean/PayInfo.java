package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PayInfo {

    /**
     * success : true
     * status : 0
     * msg : success
     * rows : {"businessName":"何康的店铺","phone":"","isExchange":"","limitCashOutQuota":0,"isVip":false,"isValue":"","isIntegral":"","changeIncome":0,"tel":"13534676755","payCount":0,"businessRegistrationNo":"888888888888888","isDouble":"","integral":0,"distinct":0,"city":"汕头市","id":2929,"vip":"","area":"龙湖区","menberCostIntegral":0,"isExamine":1,"name":"何康","province":"广东","longitude":113.35225848655362,"integralScale":10,"businessDiscount":9.9,"businessStoreImages":["http://static.51ujf.cn/image/business/goods_img/1463579459499.png"],"feedBack":"RP差，拒收","alreadyCollection":false,"serviceCost":10,"reward":"","status":true,"identityPicture":["http://static.51ujf.cn/image/business/goods_img/1463579485662.png","http://static.51ujf.cn/image/business/goods_img/1463579486176.png","http://static.51ujf.cn/image/business/goods_img/1463579485661.png"],"menberCostCash":0,"category":455,"address":"不知道","stype":"面包糕点","freezeCash":0,"cash":0,"payType":0,"messageCount":0,"latitude":22.5260743814951,"messageMark":0,"ptype":"餐饮美食","addTime":"2016-05-18 21:33:36"}
     */

    private boolean success;
    private int status;
    private String msg;
    /**
     * businessName : 何康的店铺
     * phone :
     * isExchange :
     * limitCashOutQuota : 0
     * isVip : false
     * isValue :
     * isIntegral :
     * changeIncome : 0
     * tel : 13534676755
     * payCount : 0
     * businessRegistrationNo : 888888888888888
     * isDouble :
     * integral : 0
     * distinct : 0
     * city : 汕头市
     * id : 2929
     * vip :
     * area : 龙湖区
     * menberCostIntegral : 0
     * isExamine : 1
     * name : 何康
     * province : 广东
     * longitude : 113.35225848655362
     * integralScale : 10
     * businessDiscount : 9.9
     * businessStoreImages : ["http://static.51ujf.cn/image/business/goods_img/1463579459499.png"]
     * feedBack : RP差，拒收
     * alreadyCollection : false
     * serviceCost : 10
     * reward :
     * status : true
     * identityPicture : ["http://static.51ujf.cn/image/business/goods_img/1463579485662.png","http://static.51ujf.cn/image/business/goods_img/1463579486176.png","http://static.51ujf.cn/image/business/goods_img/1463579485661.png"]
     * menberCostCash : 0
     * category : 455
     * address : 不知道
     * stype : 面包糕点
     * freezeCash : 0
     * cash : 0
     * payType : 0
     * messageCount : 0
     * latitude : 22.5260743814951
     * messageMark : 0
     * ptype : 餐饮美食
     * addTime : 2016-05-18 21:33:36
     */

    private RowsBean rows;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String businessName;
        private String phone;
        private String isExchange;
        private int limitCashOutQuota;
        private boolean isVip;
        private String isValue;
        private String isIntegral;
        private int changeIncome;
        private String tel;
        private int payCount;
        private String businessRegistrationNo;
        private String isDouble;
        private int integral;
        private int distinct;
        private String city;
        private int id;
        private String vip;
        private String area;
        private int menberCostIntegral;
        private int isExamine;
        private String name;
        private String province;
        private double longitude;
        private int integralScale;
        private double businessDiscount;
        private String feedBack;
        private boolean alreadyCollection;
        private int serviceCost;
        private String reward;
        private boolean status;
        private int menberCostCash;
        private int category;
        private String address;
        private String stype;
        private int freezeCash;
        private int cash;
        private int payType;
        private int messageCount;
        private double latitude;
        private int messageMark;
        private String ptype;
        private String addTime;
        private List<String> businessStoreImages;
        private List<String> identityPicture;

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

        public int getLimitCashOutQuota() {
            return limitCashOutQuota;
        }

        public void setLimitCashOutQuota(int limitCashOutQuota) {
            this.limitCashOutQuota = limitCashOutQuota;
        }

        public boolean isIsVip() {
            return isVip;
        }

        public void setIsVip(boolean isVip) {
            this.isVip = isVip;
        }

        public String getIsValue() {
            return isValue;
        }

        public void setIsValue(String isValue) {
            this.isValue = isValue;
        }

        public String getIsIntegral() {
            return isIntegral;
        }

        public void setIsIntegral(String isIntegral) {
            this.isIntegral = isIntegral;
        }

        public int getChangeIncome() {
            return changeIncome;
        }

        public void setChangeIncome(int changeIncome) {
            this.changeIncome = changeIncome;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getPayCount() {
            return payCount;
        }

        public void setPayCount(int payCount) {
            this.payCount = payCount;
        }

        public String getBusinessRegistrationNo() {
            return businessRegistrationNo;
        }

        public void setBusinessRegistrationNo(String businessRegistrationNo) {
            this.businessRegistrationNo = businessRegistrationNo;
        }

        public String getIsDouble() {
            return isDouble;
        }

        public void setIsDouble(String isDouble) {
            this.isDouble = isDouble;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getDistinct() {
            return distinct;
        }

        public void setDistinct(int distinct) {
            this.distinct = distinct;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getMenberCostIntegral() {
            return menberCostIntegral;
        }

        public void setMenberCostIntegral(int menberCostIntegral) {
            this.menberCostIntegral = menberCostIntegral;
        }

        public int getIsExamine() {
            return isExamine;
        }

        public void setIsExamine(int isExamine) {
            this.isExamine = isExamine;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getIntegralScale() {
            return integralScale;
        }

        public void setIntegralScale(int integralScale) {
            this.integralScale = integralScale;
        }

        public double getBusinessDiscount() {
            return businessDiscount;
        }

        public void setBusinessDiscount(double businessDiscount) {
            this.businessDiscount = businessDiscount;
        }

        public String getFeedBack() {
            return feedBack;
        }

        public void setFeedBack(String feedBack) {
            this.feedBack = feedBack;
        }

        public boolean isAlreadyCollection() {
            return alreadyCollection;
        }

        public void setAlreadyCollection(boolean alreadyCollection) {
            this.alreadyCollection = alreadyCollection;
        }

        public int getServiceCost() {
            return serviceCost;
        }

        public void setServiceCost(int serviceCost) {
            this.serviceCost = serviceCost;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getMenberCostCash() {
            return menberCostCash;
        }

        public void setMenberCostCash(int menberCostCash) {
            this.menberCostCash = menberCostCash;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStype() {
            return stype;
        }

        public void setStype(String stype) {
            this.stype = stype;
        }

        public int getFreezeCash() {
            return freezeCash;
        }

        public void setFreezeCash(int freezeCash) {
            this.freezeCash = freezeCash;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getMessageMark() {
            return messageMark;
        }

        public void setMessageMark(int messageMark) {
            this.messageMark = messageMark;
        }

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public List<String> getBusinessStoreImages() {
            return businessStoreImages;
        }

        public void setBusinessStoreImages(List<String> businessStoreImages) {
            this.businessStoreImages = businessStoreImages;
        }

        public List<String> getIdentityPicture() {
            return identityPicture;
        }

        public void setIdentityPicture(List<String> identityPicture) {
            this.identityPicture = identityPicture;
        }
    }
}
