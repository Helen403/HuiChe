package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.mine.ChangePhoneActivity;
import com.huiche.adapter.DataAdapter_HotProduct;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.ProductInfo;
import com.huiche.bean.RecommendInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.lib.lib.LRecyclerView.recyclerview.HeaderSpanSizeLookup;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.LRecyclerView.recyclerview.ProgressStyle;
import com.huiche.lib.lib.LRecyclerView.util.RecyclerViewStateUtils;
import com.huiche.lib.lib.LRecyclerView.util.RecyclerViewUtils;
import com.huiche.lib.lib.LRecyclerView.view.LoadingFooter;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.share.onekeyshare.OnekeyShare;
import com.huiche.share.onekeyshare.OnekeyShareTheme;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.LoadImageUtil;
import com.huiche.utils.ToastUtils;
import com.huiche.view.AddProductPopwindow;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.ShareDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * 产品详情
 *
 * @author Administrator
 */
public class ProductDetailActivity extends BaseActivity implements
        OnClickListener, AddToShoppingCartListener {
    private Context mContext;
    private ImageButton titleBack;
    private TextView titleText;
    private ImageView iv_showPicture_productDetail;
    private ImageView iv_callPhone_productDetail;
    //    private FitScrollGridView gv_recommend_productDetail;
    private LRecyclerView mRecyclerView;
    private List<RecommendInfo> recommendList;
    private int addCount;// 购物车商品数量
    private TextView tv_shoppingCar_account;
    //    private ScrollView scrollView_productDetail;
    private ProductInfo productInfo;
    private TextView tv_productName_productDetail;// 商品名称
    private TextView tv_hasExchange_productDetail;// 已兑换
    private TextView tv_exchangeIntegral_productDetail;// 兑换积分值
    private TextView tv_storeName_productDetail;// 商家名称
    private TextView tv_storeAddress_productDetail;// 商家地址
    private LoadImageUtil imageUtil;
    private ImageButton imb_favorite_productDetail;// 收藏
    private SharedPreferences sharedPreferences;
    private final String TAG = "ProductDetailActivity";
    private List<String> productList = new ArrayList<String>();
    private int id;// 商品id
    private TextView tv_addProduct_toCart_productDetail;// 加入购物车
    private List<ProductInfo> exchangeProductList = new ArrayList<>();//超值商品列表
    private int page = 1;
    private int rows = 20;
    private int businessStoreId;
    //    private ProductDetailGridViewAdapter dataAdapter;
    private DataAdapter_HotProduct dataAdapter;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isRefresh;
    //    private View footerView;
    private View headerView;
    private TextView tv_settle_accounts_productDetail;//结算
    private String memberId;//会员id

    private ProductInfo productInfos;//要添加的商品
    private AddProductPopwindow addProductPopwindow;
    private ImageButton imb_share_productDetail;
    private LinearLayout ll_content_productDetail;
    private RelativeLayout rell_enter;
    private LinearLayout ll_enter_store;
    private ShareDialog shareDialog;


    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_product_detail_new);
        titleBack = (ImageButton) findViewById(R.id.imb_titleBack_productDetail);
        titleText = (TextView) findViewById(R.id.tv_titleText_productDetail);
        mRecyclerView = (LRecyclerView) findViewById(R.id.gv_recommend_productDetail);
        imb_favorite_productDetail = (ImageButton) findViewById(R.id.imb_favorite_productDetail);
        tv_shoppingCar_account = (TextView) findViewById(R.id.tv_shoppingCar_account_productDetail);
        tv_addProduct_toCart_productDetail = (TextView) findViewById(R.id.tv_addProduct_toCart_productDetail);
        tv_settle_accounts_productDetail = (TextView) findViewById(R.id.tv_settle_accounts_productDetail);
        rell_enter = (RelativeLayout) findViewById(R.id.rell_enter);
        ll_content_productDetail = (LinearLayout) findViewById(R.id.ll_content_productDetail);


        imb_share_productDetail = (ImageButton) findViewById(R.id.imb_share_productDetail);
        shareDialog = new ShareDialog(mContext);

//        tv_addProduct_toCart_productDetail = (TextView) findViewById(R.id.tv_addProduct_toCart_productDetail);
//        tv_hasExchange_productDetail = (TextView) findViewById(R.id.tv_hasExchange_productDetail);
//        tv_productName_productDetail = (TextView) findViewById(R.id.tv_productName_productDetail);
//        tv_storeName_productDetail = (TextView) findViewById(R.id.tv_storeName_productDetail);
//        tv_storeAddress_productDetail = (TextView) findViewById(R.id.tv_storeAddress_productDetail);
//        tv_exchangeIntegral_productDetail = (TextView) findViewById(R.id.tv_exchangeIntegral_productDetail);
//        scrollView_productDetail = (ScrollView) findViewById(R.id.scrollView_productDetail);
//        tv_shoppingCar_account = (TextView) findViewById(R.id.tv_shoppingCar_account_productDetail);
//        iv_showPicture_productDetail = (ImageView) findViewById(R.id.iv_showPicture_productDetail);
//        iv_callPhone_productDetail = (ImageView) findViewById(R.id.iv_callPhone_productDetail);
        initHeadView();
    }

    /**
     * 初始化头部和尾布局
     */
    private void initHeadView() {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.product_detail_header, null);
        //findview(headerView)
        tv_hasExchange_productDetail = (TextView) headerView.findViewById(R.id.tv_hasExchange_productDetail);
        tv_productName_productDetail = (TextView) headerView.findViewById(R.id.tv_productName_productDetail);
        tv_storeName_productDetail = (TextView) headerView.findViewById(R.id.tv_storeName_productDetail);
        tv_storeAddress_productDetail = (TextView) headerView.findViewById(R.id.tv_storeAddress_productDetail);
        tv_exchangeIntegral_productDetail = (TextView) headerView.findViewById(R.id.tv_exchangeIntegral_productDetail);
        iv_showPicture_productDetail = (ImageView) headerView.findViewById(R.id.iv_showPicture_productDetail);
        iv_callPhone_productDetail = (ImageView) headerView.findViewById(R.id.iv_callPhone_productDetail);
        ll_enter_store = (LinearLayout) headerView.findViewById(R.id.ll_enter_store);
        //服务器图片比例为440x267,需要根据比例来设置imageview的宽高
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = (int) (width * 267) / 440;
        ViewGroup.LayoutParams para = iv_showPicture_productDetail.getLayoutParams();//获取按钮的布局
        para.height = height;
        iv_showPicture_productDetail.setLayoutParams(para);
    }

    @Override
    public void initData() {
        titleText.setText("产品详情");
        // 防止gridview为默认项显示不了gridview之上的布局
//        mRecyclerView.setFocusable(false);
        imageUtil = new LoadImageUtil();
        id = getIntent().getIntExtra("id", -1);
        businessStoreId = getIntent().getIntExtra("businessStoreId", -1);
        productInfo = new ProductInfo();
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        memberId = sharedPreferences.getString("id", "");
        initRecyclerView();
        // 根据memberId获取商品收藏列表，遍历判断当前商品是否已经收藏
        getCollectionList(memberId);
        // 根据id获取商品详情
        if (id != -1)
            getProductDetail();
        if (businessStoreId != -1)
            getExchangeProduct(page, rows);

    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        LoadImageUtil loadImageUtil = new LoadImageUtil();
        dataAdapter = new DataAdapter_HotProduct(mContext, loadImageUtil, true);
//        mRecyclerViewAdapter = new LRecyclerViewAdapter(mContext, dataAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((LRecyclerViewAdapter) mRecyclerView.getAdapter(), gridLayoutManager.getSpanCount()));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //设置样式以及设置头部尾部必须在调用setAdapter后调用否则不生效
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);
        mRecyclerView.setRefreshing(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
//            @Override
//            public void onRefresh() {
//                //刷新整个页面的数据
//                isRefresh = true;
//                page = 1;
//                getCollectionList(memberId);
//                getProductDetail();
//                getExchangeProduct(page, rows);
//            }
//
//            @Override
//            public void onScrollUp() {
//
//            }
//
//            @Override
//            public void onScrollDown() {
//
//            }
//
//            @Override
//            public void onBottom() {
//                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
//                if (state == LoadingFooter.State.Loading) {
////                    Log.d(TAG, "the state is Loading, just wait..");
//                    return;
//                }
////                if (mCurrentCounter < TOTAL_COUNTER) {
//                // loading more rows必须小于显示条目数否则看不到底部加载更多
//                RecyclerViewStateUtils.setFooterViewState(ProductDetailActivity.this, mRecyclerView, rows, LoadingFooter.State.Loading, null);
//                page++;
//                getExchangeProduct(page, rows);
//            }
//
//            @Override
//            public void onScrolled(int distanceX, int distanceY) {
//
//            }
//        });
    }

    @Override
    public void setListeners() {
        titleBack.setOnClickListener(this);
        imb_favorite_productDetail.setOnClickListener(this);
        iv_callPhone_productDetail.setOnClickListener(this);
        tv_addProduct_toCart_productDetail.setOnClickListener(this);
        tv_settle_accounts_productDetail.setOnClickListener(this);
        rell_enter.setOnClickListener(this);
        ll_enter_store.setOnClickListener(this);

        //点击添加按钮
        dataAdapter.setAddProductListener(new AddToShoppingCartListener() {
            @Override
            public void addProduct(Object product) {
                if (product instanceof ProductInfo) {
                    productInfos = ((ProductInfo) product);
                    if (addProductPopwindow == null)
                        addProductPopwindow = new AddProductPopwindow(mContext);
                    addProductPopwindow.show(ll_content_productDetail, productInfos);
                }

            }
        });


        imb_share_productDetail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imb_titleBack_productDetail:
                finish();
                break;
            // 收藏商品
            case R.id.imb_favorite_productDetail:
                collectFavorite();
                break;
            // 分享
            case R.id.imb_share_productDetail:
                showShareDialog();
                // share();
                break;
            //点击微信分享

            case R.id.tv_weixin:
                showShare(mContext, "Wechat", true);
                shareDialog.dismiss();
                break;
            //朋友圈
            case R.id.tv_pyq:
                showShare(mContext, "WechatMoments", true);
                shareDialog.dismiss();

                break;
            //QQ
            case R.id.tv_qq:
                showShare(mContext, "QQ", true);
                shareDialog.dismiss();
                break;
            case R.id.tv_share_cancel:
                shareDialog.dismiss();
                break;


            // 客服热线
            case R.id.iv_callPhone_productDetail:
                if (productInfo != null) {
                    if (!productInfo.tel.equals("")) {
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + productInfo.tel));
                        startActivity(intent);
                    }
                }
                break;
            // 加入购物车
            case R.id.tv_addProduct_toCart_productDetail:
                addToshoppingCart();
                break;
            //结算该商品
            case R.id.tv_settle_accounts_productDetail:
                settleOrder();
                break;
            //进入购物车
            case R.id.rell_enter:
                Intent intents = new Intent();
                intents.setClass(ProductDetailActivity.this, ShoppingCartActivity.class);
                startActivity(intents);
                break;
            case R.id.ll_enter_store:
                intent.setClass(ProductDetailActivity.this, BusinessDetailActivity.class);
                intent.putExtra("businessStoreId", businessStoreId + "");
                intent.putExtra("empty", "0");
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //下单

    /**
     * 下单
     */
    private void settleOrder() {
        RequestParams params = new RequestParams();
        params.put("productId", id);
        params.put("count", 1);
        if (MyApplication.token.equals("")) {
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(loginIntent);
            return;
        }
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.COMMIT_ORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                dialog.dialogcancel();
//                Log.i(TAG,response.toString());
                int status = response.optInt("status");
                int errorCode = response.optInt("errorCode");
                String msg = response.optString("msg");
//                - status=0时，下单成功，进入订单页面；
//                - status=10时，用户未登录，进入登录页面登录后重走流程；
//                - status=1，errorCode=5时，用户未绑手机，进入绑定手机页面绑定后重走流程；
//                - status=1，errorCode!=5时，报错返回；
                if (status == 0) {//下单成功
                    JSONObject obj = response.optJSONObject("rows");
                    JSONObject obj2 = obj.optJSONObject("value");
                    int offlineOrderId = obj2.optInt("id");
                    Intent intent = new Intent(mContext, ExchangeOrderActivity.class);
                    intent.putExtra("offlineOrderId", offlineOrderId);
                    mContext.startActivity(intent);
                } else if (status == 10) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    //用户没登录
                } else if (status == 1) {
                    if (errorCode == 5) {
                        //用户未绑手机
                        Intent intent = new Intent(mContext, ChangePhoneActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        ToastUtils.ToastShowShort(mContext, msg);
                    }
                }
            }
        });
    }


    /**
     * 分享到朋友圈或qq
     */
    private void share() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle("优积分");  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText("优积分 积分商城 盛大起航");  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 商品列表
     */
    private void getExchangeProduct(int page, int rows) {
        RequestParams params = new RequestParams();
        params.put("businessStoreId", businessStoreId);
        params.put("page", page);
        params.put("rows", rows);
        AsyncHttp.post(HttpConstant.GET_STORE_PRODUCT_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "网络异常");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                String msg = response.optString("msg");
//                Log.i(TAG, response.toString());
                if (status.equals("0")) {
                    JSONArray array = response.optJSONArray("rows");
                    if (array.length() > 0) {
                        exchangeProductList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            String discount = obj.optString("discount");
                            if (!discount.equals("")) {//有参数为超值商品
                                ProductInfo exchangeProduct = JSON.parseObject(obj.toString(), ProductInfo.class);
                                exchangeProductList.add(exchangeProduct);
                            }
                        }
                        if (exchangeProductList.size() > 0) {
                            if (isRefresh) {
                                dataAdapter.clear();
                            }
                            dataAdapter.addAll(exchangeProductList);
                            if (isRefresh) {
                                isRefresh = false;
                                mRecyclerView.refreshComplete();
                                mRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                            }
                        } else {
                            ToastUtils.ToastShowShort(mContext, msg);
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
                        }
                    }
                }
            }
        });
    }

    /**
     * 设置店长推荐数据
     */
    private void setExchangeData() {

    }

    /**
     * 添加到购物车
     */
    private void addToshoppingCart() {
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(ProductDetailActivity.this);
        RequestParams params = new RequestParams();
        params.put("productId", id);
        params.put("count", 1);
        bufferCircleDialog.show("正在加载", true, null);
        AsyncHttp.post(HttpConstant.ADD_TO_SHORCART, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (bufferCircleDialog.isShowDialog()) {
                    bufferCircleDialog.dialogcancel();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

//                super.onSuccess(statusCode, headers, response);
                if (bufferCircleDialog.isShowDialog()) {
                    bufferCircleDialog.dialogcancel();
                }
//                Log.i(TAG, response.toString());
                boolean success = response.optBoolean("success");
                String status = response.optString("status");
                if (status.equals("0")) {
                    if (success) {
                        ToastUtils.ToastShowShort(mContext, "添加成功");
                    } else {
                        ToastUtils.ToastShowShort(mContext, "添加失败");
                    }

                } else {
                    ToastUtils.ToastShowShort(mContext, "添加失败");
                }
            }
        });

    }

    /**
     * 获取已收藏的商品列表id,并遍历比较当前商品是否已收藏
     */
    private void getCollectionList(String memberId) {
        if (MyApplication.token.equals("")) return;
        RequestParams params = new RequestParams();
        params.put("menberId", memberId);
        params.put("page", 1);
        params.put("rows", 1000);
        AsyncHttp.post(HttpConstant.COLLECT_PRODUCT, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

                        super.onSuccess(statusCode, headers, response);
                        String msg = response.optString("msg");
                        if (response.optString("status").equals("0")) {
                            // 记录已收藏商家id
                            JSONArray array = response
                                    .optJSONArray("rows");
                            if (array != null) {
                                if (array.length() > 0) {
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject obj = array.optJSONObject(i);
                                        JSONObject product = obj
                                                .optJSONObject("product");
                                        if (product != null) {
                                            String productId = product
                                                    .optString("id");
                                            // Log.i(TAG+"productId",
                                            // productId);
                                            if (productId.equals(id + "")) {
                                                // 已收藏的商品
                                                imb_favorite_productDetail
                                                        .setImageResource(R.drawable.star_ye);
                                            }
                                        }
                                    }

                                }
                            }

                        } else {
                            if (!TextUtils.isEmpty(msg))
                                ToastUtils.ToastShowShort(mContext, msg);
                        }
                    }

                });
    }

    /**
     * 收藏当前页面商家
     */
    private void collectFavorite() {
        if (MyApplication.token.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请先登录");
            return;
        }
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialogs = new BufferCircleDialog(ProductDetailActivity.this);
        RequestParams params = new RequestParams();
        params.put("productId", id);
        bufferCircleDialogs.show("正在加载", true, null);
        AsyncHttp.post(HttpConstant.FAVORITE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        if (bufferCircleDialogs.isShowDialog()) {
                            bufferCircleDialogs.dialogcancel();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

                        super.onSuccess(statusCode, headers, response);
                        if (bufferCircleDialogs.isShowDialog()) {
                            bufferCircleDialogs.dialogcancel();
                        }
                        String msg = response.optString("msg");
                        String status = response.optString("status");
                        if (status.equals("0")) {
                            imb_favorite_productDetail
                                    .setImageResource(R.drawable.star_ye);
                        }
                        if (!msg.equals("")) {
                            ToastUtils.ToastShowShort(mContext, msg);
                        }

                    }

                });

    }

    /**
     * 根据id获取商品详情
     */
    private void getProductDetail() {
        RequestParams params = new RequestParams();
        //缓冲小菊花
        final BufferCircleDialog cbufferCircleDialog = new BufferCircleDialog(ProductDetailActivity.this);
        params.put("id", id);
        if (!isRefresh) {
            cbufferCircleDialog.show("正在加载", true, null);
        }
        AsyncHttp.posts(HttpConstant.GET_PRODUCT_DETAIL, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        if (cbufferCircleDialog.isShowDialog()) {
                            cbufferCircleDialog.dialogcancel();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (cbufferCircleDialog.isShowDialog()) {
                            cbufferCircleDialog.dialogcancel();
                        }
                        String msg = response.optString("msg");
                        String status = response.optString("status");
                        String rows = response.optString("rows");
                        if (status.equals("0")) {
                            productInfo = JSON.parseObject(rows,
                                    ProductInfo.class);
                            if (productInfo != null) {
                                setData();
                            }
                        } else {
                            ToastUtils.ToastShowShort(mContext, msg);
                        }

                    }

                });

    }

    /**
     * 填充数据
     */
    private void setData() {
        tv_productName_productDetail.setText(productInfo.name + "");
        tv_hasExchange_productDetail.setText("已兑换" + productInfo.exchangePeople
                + "件");
        tv_exchangeIntegral_productDetail.setText(productInfo.integral + "积分");
        tv_storeName_productDetail.setText(productInfo.businessName + "");
        tv_storeAddress_productDetail.setText(productInfo.businessArea + "");
        if (productInfo.goodsThumbs != null) {
            imageUtil.imageLoader.displayImage(productInfo.goodsImgs.get(0),
                    iv_showPicture_productDetail, imageUtil.options);
        }

    }

    @Override
    public void addProduct(Object product) {
        addCount++;
        tv_shoppingCar_account.setText(addCount + "");
    }

    private void showShareDialog() {
        shareDialog.show();
        shareDialog.img_pyq.setOnClickListener(this);
        shareDialog.img_qq.setOnClickListener(this);
        shareDialog.img_weixin.setOnClickListener(this);
        shareDialog.tv_share_cancel.setOnClickListener(this);

    }


    //自定义分享内容

    /*********************************************/
    public void showShare(Context context, String platformToShare,
                          boolean showContentEdit) {
        String url = HttpConstant.HTTP_HEAD + "mobile/commodityDetail.html?id=" + businessStoreId + "&productId=" + id;
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        // ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC 第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        // oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        if (productInfo.name != null) {
            oks.setTitle(productInfo.name);
        } else {
            oks.setTitle("");
        }
        oks.setTitleUrl(url);// QQ分享
        oks.setText(productInfo.businessName + "-" + productInfo.businessArea);
        if (productInfo.goodsImgs.get(0) != null) {
            oks.setImageUrl(productInfo.goodsImgs.get(0));
        } else {
            oks.setImageUrl("");
        }
        oks.setVenueName("优积分");
        oks.setVenueDescription("This is a beautiful place!");
        if (platformToShare.equals(Wechat.NAME)
                || platformToShare.equals(WechatMoments.NAME)) {
            oks.setUrl(url); // 微信不绕过审核分享链接
            MyApplication.isWXlogin = false;
        }
        Bitmap enableLogo = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ic_launcher);
        Bitmap disableLogo = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ic_launcher);
        String label = "ShareSDK";
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
            }
        };
//        oks.setCustomerLogo(enableLogo, disableLogo, label, listener);

        // 为EditPage设置一个背景的View
        // oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
        // };
        // oks.setImageArray(AVATARS); //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }


}
