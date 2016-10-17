package com.huiche.constant;

public class HttpConstantHao {
    //public final static String HTTP_HEAD = "http://test.51ujf.cn/productCategory!listAll.do";
    public final static String HTTP_HEAD = "http://test.51ujf.cn/";
    /***
     * 返回所有行业列表
     */
    public final static String GET_BUSINESS_INFO = "http://test.51ujf.cn/productCategory!listAll.do";
    /**
     * 定位当前城市，检查是否能获取当前城市的信息。
     */
    public final static String LOCATE_THE_CURRENT_CITY = HTTP_HEAD + "base!setLocationInSession.do";
    /**
     * 获取商家列表
     */
    public final static String GET_BUSINESSSTORE_INFO = HTTP_HEAD + "businessStore!tagSearch.do";
    /**
     * 获取热门城市
     */
    public final static String GET_HOT_CITY = HTTP_HEAD + "opencity!getOpenCityList.do";
    /**
     * 获取行业类别，在分类中
     */
    public final static String GROUPED_BY_SECTOR = HTTP_HEAD + "productCategory!listAll.do";
}
