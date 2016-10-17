package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PayDetailOrder {

    /**
     * success : true
     * status : 0
     * msg : success
     * rows : {"businessStoreId":2434,"businessName":"天虹商务酒店","productLists":[],"menberCoupons":false,"businessStore":{"payType":0,"payCount":0,"serviceCost":100,"businessDiscount":0,"integralScakeTmp":100,"distinct":0},"allIntegral":300,"menberIntegral":9954.4}
     */

    private boolean success;
    private int status;
    private String msg;
    /**
     * businessStoreId : 2434
     * businessName : 天虹商务酒店
     * productLists : []
     * menberCoupons : false
     * businessStore : {"payType":0,"payCount":0,"serviceCost":100,"businessDiscount":0,"integralScakeTmp":100,"distinct":0}
     * allIntegral : 300
     * menberIntegral : 9954.4
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
        private int businessStoreId;
        private String businessName;
        private boolean menberCoupons;
        /**
         * payType : 0
         * payCount : 0
         * serviceCost : 100
         * businessDiscount : 0
         * integralScakeTmp : 100
         * distinct : 0
         */

        private BusinessStoreBean businessStore;
        private int allIntegral;
        private double menberIntegral;
        private List<?> productLists;

        public int getBusinessStoreId() {
            return businessStoreId;
        }

        public void setBusinessStoreId(int businessStoreId) {
            this.businessStoreId = businessStoreId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public boolean isMenberCoupons() {
            return menberCoupons;
        }

        public void setMenberCoupons(boolean menberCoupons) {
            this.menberCoupons = menberCoupons;
        }

        public BusinessStoreBean getBusinessStore() {
            return businessStore;
        }

        public void setBusinessStore(BusinessStoreBean businessStore) {
            this.businessStore = businessStore;
        }

        public int getAllIntegral() {
            return allIntegral;
        }

        public void setAllIntegral(int allIntegral) {
            this.allIntegral = allIntegral;
        }

        public double getMenberIntegral() {
            return menberIntegral;
        }

        public void setMenberIntegral(double menberIntegral) {
            this.menberIntegral = menberIntegral;
        }

        public List<?> getProductLists() {
            return productLists;
        }

        public void setProductLists(List<?> productLists) {
            this.productLists = productLists;
        }

        public static class BusinessStoreBean {
            private int payType;
            private int payCount;
            private int serviceCost;
            private int businessDiscount;
            private int integralScakeTmp;
            private int distinct;

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public int getPayCount() {
                return payCount;
            }

            public void setPayCount(int payCount) {
                this.payCount = payCount;
            }

            public int getServiceCost() {
                return serviceCost;
            }

            public void setServiceCost(int serviceCost) {
                this.serviceCost = serviceCost;
            }

            public int getBusinessDiscount() {
                return businessDiscount;
            }

            public void setBusinessDiscount(int businessDiscount) {
                this.businessDiscount = businessDiscount;
            }

            public int getIntegralScakeTmp() {
                return integralScakeTmp;
            }

            public void setIntegralScakeTmp(int integralScakeTmp) {
                this.integralScakeTmp = integralScakeTmp;
            }

            public int getDistinct() {
                return distinct;
            }

            public void setDistinct(int distinct) {
                this.distinct = distinct;
            }
        }
    }
}
