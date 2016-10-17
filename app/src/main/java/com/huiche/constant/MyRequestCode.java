package com.huiche.constant;

public class MyRequestCode {
    /**
     * 跳转到cityChoice的结果码 对应 setResult;
     */
    public final static int CityChoice_ResultCode = 1000;
    /**
     * 跳转到cityChoice 请求码 对应 startActivityForResult；
     */
    public final static int CityChoice_RequesCode = 1001;
    /**
     * businessDetail跳转到loginAcitivity 请求码 对应 startActivityForResult；
     */
    public final static int LOGOIN_REQUEST = 1005;
    /**
     * loginAcitivity 返回码
     */
    public final static int LOGOIN_RESULT = 1006;
    /**
     * 首页跳转到购物车请求码
     */
    public final static int SHOPPING_CART_REQUEST = 1007;
    /**
     * 购物车返回首页结果码
     */
    public final static int SHOPPING_CART_RESULT = 1008;
    /**
     * 广播actionName
     */
    public final static String FINISH = "finish";

    //首页跳转到卡券地图请求码
    public final static int CARD_MAPVIEW = 1009;


}
