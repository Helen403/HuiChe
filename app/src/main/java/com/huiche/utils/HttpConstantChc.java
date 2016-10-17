package com.huiche.utils;


public final class HttpConstantChc {
	//请求接口头部
	public final static String HTTP_HEAD = "http://test.51ujf.cn/";
	
	//app登录
	public final static String USER_LOGIN=HTTP_HEAD+"login!appLogin.do";
	//线下订单全部
	public final static String ALL_LINE_ORDER=HTTP_HEAD+"offlineOrders!listByMenberId.do";
	//线下代付款订单
	public final static String LINE_WAIT_PAY=HTTP_HEAD+"offlineOrders!listByNoPay.do";
	//线下淡出代付款订单
	public final static String DELETE_LINE_ORDER=HTTP_HEAD+"offlineOrders!cancelOrder.do";
	
	//线下-待评价
	public final static String LINE_EVALUATE=HTTP_HEAD+"offlineOrders!listByIsEvaluationFalse.do";
	//线下订单-未消费
	public final static String LINE_UNPAY=HTTP_HEAD+"offlineOrders!listByMenberId.do";
	//线下订单-已完成
	public final static String LINE_FINISH_ORDER=HTTP_HEAD+"offlineOrders!listByMenberId.do";
	//个人中心--我的评价
	public final static String ONLINE_MUEVALUATE=HTTP_HEAD+"message!listByMenber.do";
	//个人中心--我的收货地址
	public final static String ADDRESS_LIST=HTTP_HEAD+"addressMenber!list.do";
	public final static String COLLECTION_SHOP=HTTP_HEAD+"collection!listByMenber.do";
	//个人中心--新增地址
	public final static String ADDNEWADDRESS=HTTP_HEAD+"addressMenber!add.do";
	//个人中心--城市列表
	public final static String CITYLIST=HTTP_HEAD+"js/province_bas.json";
	//个人中心--删除地址
	public final static String DELETE_ADDRESS=HTTP_HEAD+"addressMenber!delete.do";
	
	//个人中心--设置成默认地址
	public final static String SAVE_MOREN_ADDRESS=HTTP_HEAD+"addressMenber!setDefault.do";
	//个人中心--意见反馈
	public final static String FEED_BACK=HTTP_HEAD+"feedbackInformation!add.do";
	//个人中心--修改密码
	public final static String UPDATA_PWD=HTTP_HEAD+"menber!updatePwd.do";
	//个人中心--积分收入
	public final static String INTEGRAL_INCOME=HTTP_HEAD+"menberIntegralLog!listByPage.do";
	//收藏-商品
	public final static String COLLECTION_GOODS=HTTP_HEAD+"collection!listByMenberProduct.do";
	//取消收藏商家
	public final static String CANCEL_COLLECTION_GOODS=HTTP_HEAD+"collection!delete.do";
	//线上订单--全部订单
	public final static String LINE_ALL_ORDER=HTTP_HEAD+"OnlineOrder!listByStatus.do";
	
	//会员积分记录
	public final static String USER_INTEGRAL=HTTP_HEAD+"menber!getMessage.do";
	
	//上传图片
	public final static String POST_IMAGE=HTTP_HEAD+"image!add.do";
	//系统消息
	public final static String SYSTEM_MESSAGE=HTTP_HEAD+"systemMessage!listByMenberId.do";
	//线上订单--退款
	public final static String ONLINE_REBACK=HTTP_HEAD+"OnlineOrder!applyRefund.do";

	//修改用户绑定手机
	public final static String BIND_USER_PHONE=HTTP_HEAD+"menber!bindingPhone.do";
	//获取语音验证码
	public final static String GET_VOICE_CODE=HTTP_HEAD+"voice!voices.do";
	//获取会员信息
	public final static String GET_USER_MESSAGE=HTTP_HEAD+"menber!getById.do";

	//保存用户修改信息
	public final static String SAVE_USER_MESSAGE=HTTP_HEAD+"menber!update.do";
	//待使用卡券
	public final static String WAIT_USE_CARD_DATA=HTTP_HEAD+"mcoup!listByMenber.do";
	//获取卡券详情数据
	public final static String GET_CARD_DETAIL=HTTP_HEAD+"mcoup!get.do";
	//删除我的卡券
	public final static String DELETE_CARD= HTTP_HEAD+"mcoup!delete.do";

	//退出登录
	public final static String EXIT_LOGIN=HTTP_HEAD+"login!appLoginOut.do";

	//确认收货
	public final static String SURE_RECEIVE_GOODS=HTTP_HEAD+"OnlineOrder!confirmReceipt.do";

	//添加到购物车
	//public final static String ADD_GOODS_CART=HTTP_HEAD+"shopCar!add.do";
	public final static String ADD_GOODS_CART=HTTP_HEAD+"shoppingcart!addProductToCart.do";

	//系统公告信息
	public final static String GET_SYSTEM_NESSAGE=HTTP_HEAD+"notice!listByIdentityPage.do";
	//获取系统客户电话
	public final static String GET_SYSTEM_PHONE=HTTP_HEAD+"company!getCustomerService.do";
	//线下取消订单
	public final static String CANCEL_LINE_ORDER=HTTP_HEAD+"offlineOrders!cancelOrder.do";
	//修改地址
	public final static String EDIT_ADDRESS=HTTP_HEAD+"addressMenber!update.do";
	//我的红包
	public final static String MY_REDPACKET_LIST=HTTP_HEAD+"receive!findMenberRedcketLogList.do";
	//根据坐标获取商家卡券
	public final static String BUSINESS_CARD=HTTP_HEAD+"coup!listBusinessStoreByLocation.do";
	//首页红包详情
	public final static String RED_PACK_DETAIL=HTTP_HEAD+"mobile/laiSeeDetails.html?";
	
}
