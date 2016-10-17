package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class OrderData {
    /**
     * allIntegral : 235.2
     * menberIntegral : 8031.996005
     * data : [{"allIntegral":235.2,"businessName":"美人制造","businessStore":{"businessDiscount":0,"distinct":0,"integralScakeTmp":100,"payCount":0,"payType":0,"serviceCost":100},"businessStoreId":1035,"menberCoupons":false,"productLists":[{"count":1,"name":"洗剪吹"}]}]
     */

    private double allIntegral;
    private double menberIntegral;
    /**
     * allIntegral : 235.2
     * businessName : 美人制造
     * businessStore : {"businessDiscount":0,"distinct":0,"integralScakeTmp":100,"payCount":0,"payType":0,"serviceCost":100}
     * businessStoreId : 1035
     * menberCoupons : false
     * productLists : [{"count":1,"name":"洗剪吹"}]
     */

    private List<DataBean> data;

    public OrderData() {

    }

    public double getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(double allIntegral) {
        this.allIntegral = allIntegral;
    }

    public double getMenberIntegral() {
        return menberIntegral;
    }

    public void setMenberIntegral(double menberIntegral) {
        this.menberIntegral = menberIntegral;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        private double allIntegral;
        private String businessName;
        /**
         * businessDiscount : 0
         * distinct : 0
         * integralScakeTmp : 100
         * payCount : 0
         * payType : 0
         * serviceCost : 100
         */

        private BusinessStoreBean businessStore;
        private int businessStoreId;
        private boolean menberCoupons;
        /**
         * count : 1
         * name : 洗剪吹
         */

        private List<ProductListsBean> productLists;

        public double getAllIntegral() {
            return allIntegral;
        }

        public void setAllIntegral(double allIntegral) {
            this.allIntegral = allIntegral;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public BusinessStoreBean getBusinessStore() {
            return businessStore;
        }

        public void setBusinessStore(BusinessStoreBean businessStore) {
            this.businessStore = businessStore;
        }

        public int getBusinessStoreId() {
            return businessStoreId;
        }

        public void setBusinessStoreId(int businessStoreId) {
            this.businessStoreId = businessStoreId;
        }

        public boolean isMenberCoupons() {
            return menberCoupons;
        }

        public void setMenberCoupons(boolean menberCoupons) {
            this.menberCoupons = menberCoupons;
        }

        public List<ProductListsBean> getProductLists() {
            return productLists;
        }

        public void setProductLists(List<ProductListsBean> productLists) {
            this.productLists = productLists;
        }

        public static class BusinessStoreBean {
            private int businessDiscount;
            private int distinct;
            private int integralScakeTmp;
            private int payCount;
            private int payType;
            private int serviceCost;

            public int getBusinessDiscount() {
                return businessDiscount;
            }

            public void setBusinessDiscount(int businessDiscount) {
                this.businessDiscount = businessDiscount;
            }

            public int getDistinct() {
                return distinct;
            }

            public void setDistinct(int distinct) {
                this.distinct = distinct;
            }

            public int getIntegralScakeTmp() {
                return integralScakeTmp;
            }

            public void setIntegralScakeTmp(int integralScakeTmp) {
                this.integralScakeTmp = integralScakeTmp;
            }

            public int getPayCount() {
                return payCount;
            }

            public void setPayCount(int payCount) {
                this.payCount = payCount;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public int getServiceCost() {
                return serviceCost;
            }

            public void setServiceCost(int serviceCost) {
                this.serviceCost = serviceCost;
            }
        }

        public static class ProductListsBean {
            private int count;
            private String name;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
