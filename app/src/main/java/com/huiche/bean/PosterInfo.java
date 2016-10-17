package com.huiche.bean;

/**
 * Created by Administrator on 2016/8/8 0008.
 * 轮播图实体类
 */
public class PosterInfo {
    public String adUrl;//广告url
    public String content;//广告图片地址
    public String operationType;//点击内容后的操作类型（0：无操作；1：跳转到网页；2：进入原生商家详情）

    public PosterInfo() {
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "PosterInfo{" +
                "adUrl='" + adUrl + '\'' +
                ", content='" + content + '\'' +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
