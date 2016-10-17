package com.huiche.PostResult;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class BusinessCardInfo {
    public double longitude;
    public  double latitude;
    public double range;
    public List<BusinessCardDetail>businessStoreList;
    public BusinessMember menber;
    public BusinessCardInfo(){

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public List<BusinessCardDetail> getBusinessStoreList() {
        return businessStoreList;
    }

    public void setBusinessStoreList(List<BusinessCardDetail> businessStoreList) {
        this.businessStoreList = businessStoreList;
    }

    public BusinessMember getMenber() {
        return menber;
    }

    public void setMenber(BusinessMember menber) {
        this.menber = menber;
    }

   public static class BusinessMember{
        public String id;
        public String wxHeadimage;
        public String name;
        public BusinessMember(){

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWxHeadimage() {
            return wxHeadimage;
        }

        public void setWxHeadimage(String wxHeadimage) {
            this.wxHeadimage = wxHeadimage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
