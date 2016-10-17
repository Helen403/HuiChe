package com.huiche.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/19 0019.
 * 行业类型--子类型（二级分类）
 */
public class SubCategory implements Serializable {
    public int id;
    public double integealBack;
    public String name;
    public int parent;
    public double serviceCost;
    public boolean isChecked;

    public SubCategory() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIntegealBack() {
        return integealBack;
    }

    public void setIntegealBack(double integealBack) {
        this.integealBack = integealBack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", integealBack=" + integealBack +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", serviceCost=" + serviceCost +
                '}';
    }
}
