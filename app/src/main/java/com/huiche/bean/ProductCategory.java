package com.huiche.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 * 行业类型
 */
public class ProductCategory implements Serializable{
    public String pName;
    public List<SubCategory> sName;

    public ProductCategory() {

    }

    public List<SubCategory> getsName() {
        return sName;
    }

    public void setsName(List<SubCategory> sName) {
        this.sName = sName;
    }

    public String getpName() {

        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "pName='" + pName + '\'' +
                ", sName=" + sName +
                '}';
    }
}
