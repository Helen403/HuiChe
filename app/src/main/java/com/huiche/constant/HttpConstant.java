package com.huiche.constant;

/**
 * url 接口地址
 *
 * @author Administrator
 */
public class HttpConstant {
    /**
     * url请求头
     */
    public final static String HTTP_HEAD = "http://test.51ujf.cn/";
    /**
     * 获取购物车商品列表
     */
    public final static String GET_SHOPPINGCART_LIST = HTTP_HEAD + "shoppingcart!findProductFromCart.do";//旧接口"shopCar!view.do"
    /**
     * 通过商家ID获取当前商家商品列表
     */
    public final static String GET_STORE_PRODUCT_LIST = HTTP_HEAD + "product!listByBusinessStoreId.do";
    /**
     * 通过商家id获取当前商家基本信息
     */
    public final static String GET_STOREINFO = HTTP_HEAD + "businessStore!getById.do";
    /**
     * 会员登录
     */
    public final static String USER_LOGIN = HTTP_HEAD + "login!appLogin.do";
    /**
     * 通过搜索的关键字获取对应的商品列表
     */
    public final static String GET_SHOPPING_GOODS_LIST = HTTP_HEAD + "product!listByPage.do";
    /**
     * 通过搜索的关键字获取对应的商家列表
     */
    public final static String GET_SHOPPING_BUSINNESS_LIST = HTTP_HEAD + "businessStore!search.do";

    /**
     * 获取当前页面商家的公告列表
     */
    public final static String GET_NOTIFY = HTTP_HEAD + "storeFrontNtice!storeList.do";
    /**
     * 收藏当前页面商家
     */
    public final static String FAVORITE = HTTP_HEAD + "collection!add.do";
    /**
     * 通过会员ID返回收藏的商家列表
     */
    public final static String COLLECT_STORE = HTTP_HEAD + "collection!listByMenber.do";
    /**
     * 通过会员ID返回收藏的商品列表
     */
    public final static String COLLECT_PRODUCT = HTTP_HEAD + "collection!listByMenberProduct.do";
    /**
     * 通过商圈、热度、折扣、行业分类等查询商品列表(超值)
     */
    public final static String GET_HOT_PRODUCT = HTTP_HEAD + "product!listByDiscount.do";
    /**
     * 通过商品ID返回商品详情
     */
    public final static String GET_PRODUCT_DETAIL = HTTP_HEAD + "product!get.do";
    /**
     * 清空购物车商品(带token)
     */
    public final static String CLEAN_SHOPCART = HTTP_HEAD + "shoppingcart!cleanShoppingCart.do";//"shopCar!empty.do"
    /**
     * 添加商品到购物车(带token)
     */
    public final static String ADD_TO_SHORCART = HTTP_HEAD + "shoppingcart!addProductToCart.do";//"shopCar!add.do"
    /**
     * 通过地位置、商家所在区、行业类别、热度、距离等参数返回相应商家列表(首页6个分类)
     */
    public final static String GET_SEARCH_DO = HTTP_HEAD + "businessStore!tagSearch.do";
    /**
     * 返回所有行业列表
     */
    public final static String GET_PRODUCT_CATEGORY = HTTP_HEAD + "productCategory!listAll.do";
    /**
     * 返回所在城市所有商圈列表
     */
    public final static String GET_CITY_RANGE = HTTP_HEAD + "businessStore!getBusinessStoreAreaByDistinct.do";
    /**
     * 根据商家ID获取商家卡券列表
     */
    public final static String GET_CARD_BUSINESS = HTTP_HEAD + "coup!listByBusinessStoreId.do";
    /**
     * 获取首页广告的链接及图片
     */
    public final static String GET_HOME_AD = HTTP_HEAD + "ad!listByAdPosition.do";
    /**
     * 会员领取卡券
     */
    public final static String GET_CARD_QUAN = HTTP_HEAD + "mcoup!receive.do";
    /**
     * 通过订单ID获取
     */
    public final static String GET_OREDER_ID = HTTP_HEAD + "businessStore!getById.do";
    /**
     * 买单下单
     */
    public final static String GET_PAY_ADD = HTTP_HEAD + "pay!add.do";
    /**
     * 读取下单数据
     */
    public final static String GET_PAY_READ = HTTP_HEAD + "offlineOrders!getById.do";
    /**
     * 读取卡券
     */
    public final static String GET_READ_CARD = HTTP_HEAD + "mcoup!listByMenberAndBusinessStore.do";
    /**
     * 提交订单
     */
    public final static String COMMIT_ORDER = HTTP_HEAD + "offlineOrder!immediatelyRedemption.do";
    /**
     * 微信授权登录
     */
    public final static String WECHAT_LOGIN = HTTP_HEAD + "authorization!authorizationAndroid.do";
    /**
     * （立即结算）兑换订单信息
     */
    public final static String ORDER_MSG = HTTP_HEAD + "offlineOrders!getById.do";
    public static final String BUYOVER_SHOPPCART = HTTP_HEAD + "offlineOrder!redemption.do";
    public static final String DELETE_SHOPPCART_PRODUCT = HTTP_HEAD + "shoppingcart!deleteProductFromCart.do";
    public static final String UPDATE_PRODUCT_COUNT = HTTP_HEAD + "shoppingcart!altProductAccountFromCart.do";
    /**
     * 立即结算 – 支付(兑换流程)
     */
    public static final String SETTLE_PAY = HTTP_HEAD + "shoppingItem!buyOrder.do";
    //买单页面请求卡券

    public static final String REQUEST_CARD = HTTP_HEAD + "mcoup!listByMenberAndBusinessStore.do";
    /**
     * 立即结算 – 支付成功(兑换流程)
     */
    public static final String PAY_SUCCESS = HTTP_HEAD + "offlineOrders!paySuccess2.do";

    /**
     * 地图请求
     */
    public static final String CASH_MAP = HTTP_HEAD + "coup!listBusinessStoreByLocation.do";
    /**
     * 判断周围是否有商家红包
     */
    public static final String NEAR_RED_BAO = HTTP_HEAD + "shopper/showmanship!redBagId.do";

    /**
     * 用户拆红包则请求领取接口：
     */
    public static final String USER_REQUEST_RED_BAO = HTTP_HEAD + "receive!receiveRedBag.do";
    /**
     * 用户卡券领取接口：
     */
    public static final String USER_GET_CARD = HTTP_HEAD + "mcoup!receive.do";

}
