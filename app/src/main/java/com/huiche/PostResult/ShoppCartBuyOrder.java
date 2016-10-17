package com.huiche.PostResult;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class ShoppCartBuyOrder {

    /**
     * isEvaluation : false
     * id : 318183
     * price : 25
     * status : 2
     * orderType : 1
     * no : of1472291196000422
     * menberCoupons : false
     * integralScale : 100
     * createDate : 2016-08-27 17:46:36
     * realPrice : 22.5
     * integral : 250
     */

    private boolean isEvaluation;
    private int id;
    private int price;
    private int status;
    private int orderType;
    private String no;
    private boolean menberCoupons;
    private int integralScale;
    private String createDate;
    private double realPrice;
    private int integral;


    public ShoppCartBuyOrder(){

    }
    public boolean isIsEvaluation() {
        return isEvaluation;
    }

    public void setIsEvaluation(boolean isEvaluation) {
        this.isEvaluation = isEvaluation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public boolean isMenberCoupons() {
        return menberCoupons;
    }

    public void setMenberCoupons(boolean menberCoupons) {
        this.menberCoupons = menberCoupons;
    }

    public int getIntegralScale() {
        return integralScale;
    }

    public void setIntegralScale(int integralScale) {
        this.integralScale = integralScale;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
