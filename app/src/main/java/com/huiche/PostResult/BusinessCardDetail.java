package com.huiche.PostResult;

/**
 * Created by Administrator on 2016/9/1.
 */
public class BusinessCardDetail {
    public String id;
    public String businessArea;
    public String businessStoreImage;
    public String payType;
    public String payCount;
    public double longitude;
    public double latitude;
    public String businessStoreName;
    public String businessDiscount;
    public String distinct;
    public BusinessCoupons coupons;
    public int deduction;
    public int price;

    public BusinessCardDetail() {

    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getBusinessDiscount() {
        return businessDiscount;
    }

    public void setBusinessDiscount(String businessDiscount) {
        this.businessDiscount = businessDiscount;
    }

    public String getBusinessStoreImage() {
        return businessStoreImage;
    }

    public void setBusinessStoreImage(String businessStoreImage) {
        this.businessStoreImage = businessStoreImage;
    }

    public String getBusinessStoreName() {
        return businessStoreName;
    }

    public void setBusinessStoreName(String businessStoreName) {
        this.businessStoreName = businessStoreName;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BusinessCoupons getCoupons() {
        return coupons;
    }

    public void setCoupons(BusinessCoupons coupons) {
        this.coupons = coupons;
    }


    public class BusinessCoupons {
        public int getLimit;
        public String createTime;
        public String businessName;
        public String usecode;
        public String endTime;
        public String id;
        public String menberGetCount;
        public String details;
        public String businessArea;
        public String businessStoreImage;
        public double longitude;
        public double latitude;
        public String menberUseCount;
        public String bussinessStoreId;
        public String price;

        public String getDeduction() {
            return deduction;
        }

        public void setDeduction(String deduction) {
            this.deduction = deduction;
        }

        public String deduction;


        public BusinessCoupons() {

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public int getGetLimit() {
            return getLimit;
        }

        public void setGetLimit(int getLimit) {
            this.getLimit = getLimit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getUsecode() {
            return usecode;
        }

        public void setUsecode(String usecode) {
            this.usecode = usecode;
        }
    }
}
