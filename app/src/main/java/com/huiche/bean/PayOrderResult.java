package com.huiche.bean;

/**
 * Created by Administrator on 2016/8/22.
 */
public class PayOrderResult {

    /**
     * success : true
     * status : 0
     * msg : success
     * rows : {"couponQuota":"0","cashQuota":151,"integralDeductible":0,"businessName":"香兰阁问题皮肤体验中心","allPrice":151,"no":"nip1471833813000654","payType":4,"deduction":"0","payTime":"2016-08-22 10:43:53","orderStatus":2,"haveCoupon":false,"sendIntegral":0}
     */

    private boolean success;
    private int status;
    private String msg;
    /**
     * couponQuota : 0
     * cashQuota : 151
     * integralDeductible : 0
     * businessName : 香兰阁问题皮肤体验中心
     * allPrice : 151
     * no : nip1471833813000654
     * payType : 4
     * deduction : 0
     * payTime : 2016-08-22 10:43:53
     * orderStatus : 2
     * haveCoupon : false
     * sendIntegral : 0
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
        private String couponQuota;
        private double cashQuota;
        private int integralDeductible;
        private String businessName;
        private String allPrice;
        private String no;
        private String checkNo;
        private int payType;
        private String deduction;
        private String payTime;
        private int orderStatus;
        private boolean haveCoupon;
        private double sendIntegral;

        public String getCheckNo() {
            return checkNo;
        }

        public void setCheckNo(String checkNo) {
            this.checkNo = checkNo;
        }

        public String getCouponQuota() {
            return couponQuota;
        }

        public void setCouponQuota(String couponQuota) {
            this.couponQuota = couponQuota;
        }

        public double getCashQuota() {
            return cashQuota;
        }

        public void setCashQuota(double cashQuota) {
            this.cashQuota = cashQuota;
        }

        public int getIntegralDeductible() {
            return integralDeductible;
        }

        public void setIntegralDeductible(int integralDeductible) {
            this.integralDeductible = integralDeductible;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(String allPrice) {
            this.allPrice = allPrice;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getDeduction() {
            return deduction;
        }

        public void setDeduction(String deduction) {
            this.deduction = deduction;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public boolean isHaveCoupon() {
            return haveCoupon;
        }

        public void setHaveCoupon(boolean haveCoupon) {
            this.haveCoupon = haveCoupon;
        }

        public double getSendIntegral() {
            return sendIntegral;
        }

        public void setSendIntegral(double sendIntegral) {
            this.sendIntegral = sendIntegral;
        }
    }

    public static class Coupon {
        public String businessName;
        public Double deduction;
        public String endTime;
        public String price;
        public String startTime;
        public String couponsTypeId;
        public Coupon(){

        }
        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getCouponsTypeId() {
            return couponsTypeId;
        }

        public void setCouponsTypeId(String couponsTypeId) {
            this.couponsTypeId = couponsTypeId;
        }

        public Double getDeduction() {
            return deduction;
        }

        public void setDeduction(Double deduction) {
            this.deduction = deduction;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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
    }


}
