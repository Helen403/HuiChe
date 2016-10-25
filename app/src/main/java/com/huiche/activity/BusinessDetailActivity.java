package com.huiche.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.PostResult.NotifyInfo;
import com.huiche.R;
import com.huiche.activity.mine.PayHelenActivity;
import com.huiche.activity.mine.WebViewActivity;
import com.huiche.adapter.BusinessDetailHelenGridviewAdapter;
import com.huiche.adapter.BusinessDetailHelenListViewAdapter;
import com.huiche.adapter.CardStoke_BusinessDetailAdapter;
import com.huiche.adapter.NotifyDetailAdapter;
import com.huiche.base.MyApplication;
import com.huiche.bean.CardStokeInfo;
import com.huiche.bean.ProductInfo;
import com.huiche.bean.StoreInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.constant.MyRequestCode;
import com.huiche.customer_view.MyGridView;
import com.huiche.share.onekeyshare.OnekeyShare;
import com.huiche.share.onekeyshare.OnekeyShareTheme;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.SetSizeUtils;
import com.huiche.utils.SystemBarUtil;
import com.huiche.utils.TimeUtil;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.WindowUtils;
import com.huiche.view.AddProductPopwindow;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.BufferShareViewDialog;
import com.huiche.view.CircleImageView;
import com.huiche.view.FitScrollListView;
import com.huiche.view.MyListView;
import com.huiche.view.ScrollViewExtend;
import com.huiche.view.ShareDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 商家详情
 *
 * @author Administrator
 */
public class BusinessDetailActivity extends Activity implements
        OnClickListener {
//    private final String TAG = "BusinessDetailActivity";
    private ImageButton titleBack;// 返回
    private TextView titleText;// 标题
//    private FitScrollGridView recommendGridView;// 店长推荐gridview
    private Context mContext;
    //    private TextView tv_payDetail;
//        private LinearLayout ll_complain_businessDetail;// 报错
    private ImageView iv_shoppingCar_icon;// 购物车图标
    private LinearLayout ll_phoneBook_businessDetial;// 预约
    private int page = 1;// 返回记录页数，默认第一页
    private int rows = 20;// 返回记录行数，默认20行
    private String businessStoreId;// 商家id
    //    private ViewPager viewPager;
    private TextView tv_storeName;// 商家名称
    private StoreInfo storeInfo;// 商家基本信息
    //    private TextView tv_appraiseNumber;// 评价人数
    private TextView tv_integralScale;// 消费100积分赠送的积分数
    private TextView tv_address;// 商家地址
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private CircleImageView civ_storeIcon;// 店铺头像
//    private final int RUNNING = 0;
//    private final int STOP = 1;
//    private final int LOAD_MORE = 2;
    //    private int currentItem;// 当前viewpager的索引
    private ScrollViewExtend sv_businessDetail;
    //    private LinearLayout ll_loaingMore;// 加载更多尾布局
    private ImageButton imb_share_businessDetail;// 分享
    private ImageButton imb_favorite_businessDetail;// 收藏
//    private LinearLayout ll_scorllContent;
    private LinearLayout ll_navigation_businessDetail;
//    private LinearLayout ll_navigation_buttom;
    private PopupWindow navipopupWindow;
    private WindowUtils windowUtils;
    private LinearLayout ll_isDouble_businessDetail;
    private LinearLayout ll_redPacket_businessDetail;
    private TextView tv_complaint_businessDetail;
//    private ImageView iv_emptyCircle;
    private TextView tv_storeDesc;//店铺简介
    private TextView tv_consume_businessDetail;//买单人数
    //双培积分倒计时
    private TextView tv_countDown_day;//天
    private TextView tv_countDown_hour;//时
    private TextView tv_countDown_minute;//分
    private TextView tv_countDown_second;//秒
    private int counMM;//分
    private int counHH;//时
    private int counSS;//秒
    private int counDD;//天
    private TextView tv_checkDetail_businessDetail;//查看更多
    //    private ImageView iv_mark_businessDetail;
    private LinearLayout ll_startAll_businessDetail;
    private TextView tv_pay_businessDetail;
    private LinearLayout ll_notify_businessDetail;//消息公告
    private LinearLayout ll_menber_onlyShare;//会员专享
    private List<CardStokeInfo> cardList = new ArrayList<>();//卡券列表
    private List<CardStokeInfo> cardData = new ArrayList<>();//卡券列表
    private List<CardStokeInfo> cardTemp = new ArrayList<>();//卡券列表
    private List<NotifyInfo> notifyList = new ArrayList<>();//公告列表
    private FitScrollListView lv_cardStock_businessDetial;
    //    private CardStoke_BusinessDetailAdapter cardAdapter;
    private LinearLayout ll_showAll_cardStoke;
    private CheckBox check_showAll_cardStoke;
    private String currentNotify, nextNotify;
    private final int UPDATE = 0;
    private TextView tv_current_notify;
    private TextView tv_next_notify;
    private boolean firstShow = true;
    private LinearLayout ll_photoAlbum_businessDetail;
    private TextView tv_photoCount;
    private ImageView iv_showPicture_businessDetail;
    private AlertDialog notifyDialog;//公告消息弹框
    private AlertDialog callDialog;//预约电话
    private List<ProductInfo> exchangeProductList = new ArrayList<>();//超值商品列表
    private TextView tv_all_hotProduct;//全部超值商品
//    private ImageView iv_exchangeIcon;//超值商品图片
//    private TextView tv_productName;//超值商品名称
//    private TextView tv_exchangeIntegral;//超值商品兑换积分值
//    private TextView tv_hasExchange;//已兑换
//    private TextView tv_doorPrice;//门店价
//    private TextView tv_exchangeNow;//立即兑换
//    private TextView tv_saveMoney;//节省金额
    //    private GradientTextView gtv_discount;//折扣
    private DecimalFormat formater1;//保留一位小数
//    private DecimalFormat formater2;//保留两位小数
    //    private RelativeLayout rl_gradientText;
    private AddProductPopwindow productPopwindow;
    private RelativeLayout ll_enter_hotproduct;
    private LinearLayout ll_hotproduct;
    private ShareDialog shareDialog;


    BufferShareViewDialog bufferShareViewDialog;
    /**
     * whether to allow Full Screen
     */
    public boolean isAllowFullScreen;
    /**
     * Is there a menu display。
     */
//    public boolean hasMenu;


    boolean can = true;
    //商家详情简介 初始为false 只显示三行
//    public boolean isMore = false;
    /********************************************************************/
    MyListView listView;
    BusinessDetailHelenListViewAdapter businessDetailHelenAdapter;


    BusinessDetailHelenGridviewAdapter businessDetailHelenGridviewAdapter;
    MyGridView gridView;

    public void dealLogicBeforeFindView() {
        mContext = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        dealLogicBeforeFindView();
        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }
        // 软件盘弹出问题
        // 处理 edittext与 软键盘 重叠。
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 沉浸式状态栏
        SystemBarUtil.initSystemBarElse(this);
        findViews();
        initData();
        setListeners();
    }

    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    public void findViews() {
        //总布局
        RelativeLayout content = new RelativeLayout(this);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);

        bufferShareViewDialog = new BufferShareViewDialog(mContext);
        bufferShareViewDialog.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_business_detail_new, null);
        content.addView(view);
        content.addView(bufferShareViewDialog);
        setContentView(content);


        /*************************************************************/
        titleBack = (ImageButton) findViewById(R.id.imb_titleBack_businessDetail);
        titleText = (TextView) findViewById(R.id.tv_titleText_businessDetail);
        tv_all_hotProduct = (TextView) findViewById(R.id.tv_all_hotProduct);
        tv_photoCount = (TextView) findViewById(R.id.tv_photoCount);
//        tv_productName = (TextView) findViewById(R.id.tv_productName);
//        gtv_discount = (GradientTextView) findViewById(R.id.gtv_discount);
//        rl_gradientText = (RelativeLayout) findViewById(R.id.rl_gradientText);
//        tv_exchangeIntegral = (TextView) findViewById(R.id.tv_exchangeIntegral);
//        tv_exchangeNow = (TextView) findViewById(R.id.tv_exchangeNow);
//        tv_saveMoney = (TextView) findViewById(R.id.tv_saveMoney);
//        tv_doorPrice = (TextView) findViewById(R.id.tv_doorPrice);
//        tv_hasExchange = (TextView) findViewById(R.id.tv_hasExchange);
        lv_cardStock_businessDetial = (FitScrollListView) findViewById(R.id.lv_cardStock_businessDetial);
        tv_storeDesc = (TextView) findViewById(R.id.tv_storeDesc);
        tv_pay_businessDetail = (TextView) findViewById(R.id.tv_pay_businessDetail);
        tv_countDown_day = (TextView) findViewById(R.id.tv_countDown_day);
        tv_countDown_hour = (TextView) findViewById(R.id.tv_countDown_hour);
        tv_countDown_minute = (TextView) findViewById(R.id.tv_countDown_minute);
        tv_countDown_second = (TextView) findViewById(R.id.tv_countDown_second);
        tv_checkDetail_businessDetail = (TextView) findViewById(R.id.tv_checkDetail_businessDetail);
        tv_consume_businessDetail = (TextView) findViewById(R.id.tv_consume_businessDetail);
        tv_storeName = (TextView) findViewById(R.id.tv_storeName_businessDetail);
        imb_share_businessDetail = (ImageButton) findViewById(R.id.imb_share_businessDetail);
        imb_favorite_businessDetail = (ImageButton) findViewById(R.id.imb_favorite_businessDetail);
        sv_businessDetail = (ScrollViewExtend) findViewById(R.id.sv_businessDetail);
        ll_notify_businessDetail = (LinearLayout) findViewById(R.id.ll_notify_businessDetail);
        ll_photoAlbum_businessDetail = (LinearLayout) findViewById(R.id.ll_photoAlbum_businessDetail);
//        ll_scorllContent = (LinearLayout) findViewById(R.id.ll_scorllContent);
        tv_address = (TextView) findViewById(R.id.tv_address_businessDetail);
        civ_storeIcon = (CircleImageView) findViewById(R.id.civ_storeIcon_businessDetail);
        tv_integralScale = (TextView) findViewById(R.id.tv_integralScale_businessDetail);
        tv_current_notify = (TextView) findViewById(R.id.tv_current_notify);
        tv_next_notify = (TextView) findViewById(R.id.tv_next_notify);
        ll_isDouble_businessDetail = (LinearLayout) findViewById(R.id.ll_isDouble_businessDetail);
        ll_showAll_cardStoke = (LinearLayout) findViewById(R.id.ll_showAll_cardStoke);
        ll_menber_onlyShare = (LinearLayout) findViewById(R.id.ll_menber_onlyShare);
        check_showAll_cardStoke = (CheckBox) findViewById(R.id.check_showAll_cardStoke);
        tv_complaint_businessDetail = (TextView) findViewById(R.id.tv_complaint_businessDetail);
        ll_phoneBook_businessDetial = (LinearLayout) findViewById(R.id.ll_phoneBook_businessDetial);
        ll_startAll_businessDetail = (LinearLayout) findViewById(R.id.ll_startAll_businessDetail);
        ll_navigation_businessDetail = (LinearLayout) findViewById(R.id.ll_navigation_businessDetail);
        ll_redPacket_businessDetail = (LinearLayout) findViewById(R.id.ll_redPacket_businessDetail);
//        viewPager = (ViewPager) findViewById(R.id.vp_showPicture_businessDetail);
        iv_showPicture_businessDetail = (ImageView) findViewById(R.id.iv_showPicture_businessDetail);
        //服务器图片比例为440x267,需要根据比例来设置imageview的宽高
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = (int) (width * 267) / 440;
        ViewGroup.LayoutParams para = iv_showPicture_businessDetail.getLayoutParams();//获取按钮的布局
        para.height = height;
        iv_showPicture_businessDetail.setLayoutParams(para);


//        iv_exchangeIcon = (ImageView) findViewById(R.id.iv_exchangeIcon);
//        iv_emptyCircle = (ImageView) findViewById(R.id.iv_emptyCircle);
//        iv_mark_businessDetail = (ImageView) findViewById(R.id.iv_mark_businessDetail);
        ll_enter_hotproduct = (RelativeLayout) findViewById(R.id.ll_enter_hotproduct);
        ll_hotproduct = (LinearLayout) findViewById(R.id.ll_hotproduct);


        /*******************************************************************/
        listView = (MyListView) findViewById(R.id.listview);
        gridView = (MyGridView) findViewById(R.id.gridview);
        tv_all_hotProduct.setVisibility(View.GONE);
        //刚进入页面焦点在顶部
        sv_businessDetail.setFocusable(false);
        titleText.setFocusable(true);
        gridView.setFocusable(false);
        listView.setFocusable(false);
    }

    public void initData() {


        ShareSDK.initSDK(this);
        titleText.setText("商家详情");
        formater1 = new DecimalFormat("#0.0");
//        formater2 = new DecimalFormat("#0.00");
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String memberId = sharedPreferences.getString("id", "");
        getCollectionList(memberId);
        // 设置加载网络图片
        initImageLoader();
        // 设置店招显示比例16:9
        windowUtils = new WindowUtils(mContext);
        SetSizeUtils.setSizeOnlyWidthOf(mContext, tv_pay_businessDetail, 3, 2);
        // 根据businessStoreId获取商家基本信息
        Intent intent = getIntent();
        businessStoreId = intent.getStringExtra("businessStoreId");
        /******************************************************/
        String empty = intent.getStringExtra("empty");
        if (empty.equals("1")) {
            tv_pay_businessDetail.setBackgroundColor(Color.parseColor("#AFAFAF"));
            can = false;
        }


        /******************************************************/

        if (businessStoreId != null) {
            getExchangeProduct(page, rows);
            getStoreInfo();
            getCardQuan();
            initNotifyData();
        }
        getBusinessData(1);
    }

    /***************************************************************************/

    private void getBusinessData(int page) {
        //先加载1000rows
        RequestParams params = new RequestParams();
        params.put("businessStoreId", businessStoreId);
        params.put("page", page);
        params.put("rows", 1000);
        AsyncHttp.posts(HttpConstant.GET_STORE_PRODUCT_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "网络异常");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    JSONArray array = response.optJSONArray("rows");
                    if (array.length() > 0) {
                        ArrayList<ProductInfo> tempProductList = new ArrayList<ProductInfo>();//超值商品列表
                        ArrayList<ProductInfo> tempProductListNo = new ArrayList<ProductInfo>();//非超值商品列表
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            String discount = obj.optString("discount");
                            if (!discount.equals("")) {//有参数为超值商品
                                ProductInfo exchangeProduct = JSON.parseObject(obj.toString(), ProductInfo.class);
                                tempProductList.add(exchangeProduct);
                            } else {
                                ProductInfo exchangeProduct = JSON.parseObject(obj.toString(), ProductInfo.class);
                                tempProductListNo.add(exchangeProduct);
                            }

                        }
                        businessDetailHelenAdapter = new BusinessDetailHelenListViewAdapter(BusinessDetailActivity.this, tempProductList);
                        listView.setAdapter(businessDetailHelenAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                                intent.putExtra("id", exchangeProductList.get(position).getId());
                                intent.putExtra("businessStoreId", businessStoreId);
                                mContext.startActivity(intent);

                            }
                        });


                        businessDetailHelenGridviewAdapter = new BusinessDetailHelenGridviewAdapter(tempProductListNo);
                        gridView.setAdapter(businessDetailHelenGridviewAdapter);
                        gridView.setEnabled(false);
//                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            }
//                        });

                        // 滚动到顶部：
                        sv_businessDetail.fullScroll(ScrollView.FOCUS_UP);


                    }
                }
            }
        });


    }

    public void getExchange(int position) {
        //立即兑换
        if (exchangeProductList.size() > 0) {
            if (productPopwindow == null)
                productPopwindow = new AddProductPopwindow(mContext);
            productPopwindow.show(findViewById(R.id.ll_content_businessDetail), exchangeProductList.get(position));
        }
    }


    /***************************************************************************/


    public void setListeners() {
        tv_all_hotProduct.setOnClickListener(this);
//        tv_exchangeNow.setOnClickListener(this);
        titleBack.setOnClickListener(this);
        ll_notify_businessDetail.setOnClickListener(this);
        tv_pay_businessDetail.setOnClickListener(this);
        tv_checkDetail_businessDetail.setOnClickListener(this);
        tv_complaint_businessDetail.setOnClickListener(this);
        ll_phoneBook_businessDetial.setOnClickListener(this);
        imb_favorite_businessDetail.setOnClickListener(this);
        imb_share_businessDetail.setOnClickListener(this);
        ll_showAll_cardStoke.setOnClickListener(this);
        ll_photoAlbum_businessDetail.setOnClickListener(this);
        ll_enter_hotproduct.setOnClickListener(this);
        ll_navigation_businessDetail.setOnClickListener(this);
        check_showAll_cardStoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cardList.size() > 0) {
                    if (isChecked) {
                        CardStoke_BusinessDetailAdapter cardAdapter = new CardStoke_BusinessDetailAdapter(mContext, cardList);
                        lv_cardStock_businessDetial.setAdapter(cardAdapter);
                    } else {
                        CardStoke_BusinessDetailAdapter cardAdapter = new CardStoke_BusinessDetailAdapter(mContext, cardTemp);
                        lv_cardStock_businessDetial.setAdapter(cardAdapter);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            // 返回
            case R.id.imb_titleBack_businessDetail:
                finish();
                break;
            //全部商品
            case R.id.tv_all_hotProduct:
//                intent.setClass(BusinessDetailActivity.this, BusinessHotProductActivity.class);
//                intent.putExtra("businessStoreId", businessStoreId);
//                startActivity(intent);

                break;
            // 相册
            case R.id.ll_photoAlbum_businessDetail:
                intent.setClass(mContext, PhotoAlbumActivity.class);
                intent.putStringArrayListExtra("img_list", (ArrayList<String>) storeInfo.pictures);
                startActivity(intent);
                break;
            case R.id.tv_exchangeNow:
//                //立即兑换
//                if (exchangeProductList.size() > 0) {
//                    if (productPopwindow == null)
//                        productPopwindow = new AddProductPopwindow(mContext);
//                    productPopwindow.show(findViewById(R.id.ll_content_businessDetail), exchangeProductList.get(0));
//                }
                break;
            case R.id.ll_notify_businessDetail:
                //弹出公告详情
                showNotifyDialog();
                break;
            case R.id.ll_showAll_cardStoke:
                if (check_showAll_cardStoke.isChecked()) {
                    check_showAll_cardStoke.setChecked(false);
                } else {
                    check_showAll_cardStoke.setChecked(true);
                }
                break;
            // 收藏
            case R.id.imb_favorite_businessDetail:
                collectFavorite();
                break;
            // 收藏
            case R.id.tv_checkDetail_businessDetail:
                tv_storeDesc.setSingleLine(false);//取消line=“n”固定值
                tv_storeDesc.setEllipsize(null);
                tv_checkDetail_businessDetail.setVisibility(View.GONE);
                break;
            // 我要买单
            case R.id.tv_pay_businessDetail:
                /*************************************************************/
                if (can) {
                    if (!MyApplication.token.equals("")) {
//                        intent.setClass(mContext, PayActivity.class);
//                        intent.putExtra("businessID", businessStoreId + "");
//                        intent.putExtra("businessName", storeInfo.businessName + "");
//

                        intent.setClass(mContext, PayHelenActivity.class);
                        startActivity(intent);


                    } else {
                        ToastUtils.ToastShowShort(BusinessDetailActivity.this, "用户无登录");
                        Intent intent1 = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent1);
                    }
                }
                /*************************************************************/
                break;
            // 预约
            case R.id.ll_phoneBook_businessDetial:
                showCallDialog();
                break;
            // 导航
            case R.id.ll_navigation_businessDetail:
                chooseNaviType();
                break;
            // 百度地图导航
            case R.id.tv_baiduNavi:
                if (storeInfo != null) {
                    openBaiduNavi();
                }
                break;
            // 高德地图导航
            case R.id.tv_gaodeNavi:
                if (storeInfo != null) {
                    openGaodeNavi();
                }
                break;
            // 取消导航
            case R.id.tv_cancelNavi:
                if (navipopupWindow != null) {
                    navipopupWindow.dismiss();
                }
                break;
            // 报错
            case R.id.tv_complaint_businessDetail:
//                intent.setClass(mContext, ReporterrorOrComplaintActivity.class);
//                startActivity(intent);
                String url = HttpConstant.HTTP_HEAD + "mobile/submitBug.html?id=" + businessStoreId + "&v=1000";
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra("title", "投诉");
                intent.putExtra("url", url);
                startActivity(intent);
                break;
            //分享
            case R.id.imb_share_businessDetail:

//                bufferShareViewDialog.show();
//                bufferShareViewDialog.setVisibility(View.VISIBLE);
                showShareDialog();
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

            //进入筛选过后的超值商品界面
            case R.id.ll_enter_hotproduct:
//                intent.setClass(BusinessDetailActivity.this, BusinessHotProductActivity.class);
//                intent.putExtra("businessStoreId", businessStoreId);
//                startActivity(intent);
                //businessStoreId
                break;
            default:
                break;
        }

    }


    private void showShareDialog() {
        shareDialog.show();
        shareDialog.img_pyq.setOnClickListener(this);
        shareDialog.img_qq.setOnClickListener(this);
        shareDialog.img_weixin.setOnClickListener(this);
        shareDialog.tv_share_cancel.setOnClickListener(this);


    }


    /*****************************************************************/


    /**
     * 商品列表
     */
    private void getExchangeProduct(int page, int rows) {
        //先加载1000rows
        RequestParams params = new RequestParams();
        params.put("businessStoreId", businessStoreId);
        params.put("page", page);
        params.put("rows", 1000);
        AsyncHttp.posts(HttpConstant.GET_STORE_PRODUCT_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "网络异常");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    JSONArray array = response.optJSONArray("rows");
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            String discount = obj.optString("discount");
                            if (!discount.equals("")) {//有参数为超值商品
                                ProductInfo exchangeProduct = JSON.parseObject(obj.toString(), ProductInfo.class);
                                exchangeProductList.add(exchangeProduct);
                            }
                        }
                        if (exchangeProductList.size() > 0) {
                            //显示第一个超值商品
                            ll_hotproduct.setVisibility(View.VISIBLE);
                            setExchangeData();
                        } else {
                            ll_hotproduct.setVisibility(View.GONE);
                        }
                    } else {
                        ll_hotproduct.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);

                }
            }
        });
    }

    /**
     * 设置超值商品只显示列表第一条数据显示
     */
    private void setExchangeData() {
        ProductInfo productInfo = exchangeProductList.get(0);
//        tv_productName.setText(productInfo.name);
//        String url = productInfo.goodsImgs.get(0);
//        imageLoader.displayImage(url, iv_exchangeIcon, options);
//        double saveMoney = productInfo.marketPrice - productInfo.integral / 10;
//        tv_saveMoney.setText("省" + formater2.format(saveMoney) + "元");
//        tv_hasExchange.setText("已兑换" + productInfo.exchangePeople + "件");
//        tv_exchangeIntegral.setText(productInfo.integral + "积分");
//        gtv_discount.setVisibility(View.VISIBLE);
//        gtv_discount.setText(productInfo.getDiscount() + "折");
        //设置显示比例
//        SetSizeUtils.setSizeOnlyWidthOf(mContext, rl_gradientText, 3, 1, 16, 10);
//        int displayWidth = getWindowManager().getDefaultDisplay().getWidth();
//        int width = displayWidth / 3;
        //设置内容宽度和图片宽度一致
//           //设置折扣显示比例16:10
//        int discountWidth = width / 2;
//        int discountHeight = (width / 2) * 10 / 16;
//        RelativeLayout.LayoutParams discountParams = new RelativeLayout.LayoutParams(discountWidth, discountHeight);
//        gtv_discount.setLayoutParams(discountParams);
//        //距离底部7/16height
//        gtv_discount.setPadding(0, 0, 0, discountHeight * 7 / 16);

    }

    /**
     * 公告消息循环
     */
    public void notifyRunning() {
        new Thread() {
            public void run() {
                while (true) {
                    for (int i = 0; i < notifyList.size(); i++) {
                        if (i == notifyList.size() - 1) {
                            currentNotify = notifyList.get(i).getContext();
                            nextNotify = notifyList.get(0).getContext();
                        } else {
                            currentNotify = notifyList.get(i).getContext();
                            nextNotify = notifyList.get(i + 1).getContext();
                        }
                        try {
                            Thread.sleep(3000);
                            mHandler.sendEmptyMessage(UPDATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }

            ;
        }.start();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE:
                    TranslateAnimation outAnim = (TranslateAnimation) AnimationUtils
                            .loadAnimation(mContext, R.anim.system_message_up);
                    TranslateAnimation inAnim = (TranslateAnimation) AnimationUtils
                            .loadAnimation(mContext, R.anim.system_message_into);
                    // 添加动画
                    tv_next_notify.setVisibility(View.VISIBLE);
                    if (firstShow) {
                        firstShow = false;
                        // tv_current_notify.setText(currentNotify);
                        // tv_next_notify.setVisibility(View.VISIBLE);
                        tv_next_notify.setText(nextNotify);
                        tv_current_notify.startAnimation(outAnim);
                        tv_next_notify.startAnimation(inAnim);
                    } else {
                        // tv_next_notify.setVisibility(View.VISIBLE);
                        tv_current_notify.setText(currentNotify);
                        tv_next_notify.setText(nextNotify);
                        tv_current_notify.startAnimation(outAnim);
                        tv_next_notify.startAnimation(inAnim);
                    }
                    break;

                default:
                    break;
            }

        }

        ;
    };

    /**
     * 弹出公告
     */
    private void showNotifyDialog() {
        if (notifyDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = View.inflate(mContext, R.layout.dialog_notify_detail, null);
            notifyDialog = builder.show();
            Window dialogWindow = notifyDialog.getWindow();
            dialogWindow.setContentView(dialogView);
            int width = windowUtils.getdisplayWidth();
            dialogWindow.setContentView(dialogView);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = width * 5 / 7;
            lp.height = width * 5 / 7;
            dialogWindow.setAttributes(lp);
            dialogWindow.setWindowAnimations(R.style.dialog_zoom);
            //        TextView notifyTitle = (TextView) dialogView.findViewById(R.id.tv_notifyTitle);
            ListView lv_notify_detail = (ListView) dialogView.findViewById(R.id.lv_notify_detail);
            ImageView iv_delete = (ImageView) dialogView.findViewById(R.id.iv_delete);
            if (notifyList.size() > 0) {
                lv_notify_detail.setAdapter(new NotifyDetailAdapter(mContext, notifyList));
            }
            iv_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDialog.dismiss();
                }
            });
        } else {
            notifyDialog.show();
        }


    }

    /**
     * 显示预约对话框
     */
    private void showCallDialog() {
        final String phoneNumber;
        if (storeInfo != null) {
            phoneNumber = storeInfo.tel;
        } else {
            ToastUtils.ToastShowShort(mContext, "电话号码获取失败");
            return;
        }
        if (callDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = View.inflate(mContext, R.layout.dialog_normal, null);
            callDialog = builder.show();
            // width为屏幕宽度5/6,height为5/12,width/2
            int width = windowUtils.getdisplayWidth();
            Window dialogWindow = callDialog.getWindow();
            dialogWindow.setContentView(dialogView);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = width * 5 / 7;
//        lp.height = width * 4 / 14;
            dialogWindow.setAttributes(lp);
            // find控件设置监听
            TextView tv_cancelNormal = (TextView) dialogView.findViewById(R.id.tv_cancelNormal);
            TextView title = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_title);
            TextView content = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_content);
            TextView tv_confirmNormal = (TextView) dialogView.findViewById(R.id.tv_confirmNormal);
//        title.setText("预约电话");
            title.setVisibility(View.GONE);
            content.setText(phoneNumber);
            tv_confirmNormal.setText("拨打");
            tv_cancelNormal.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDialog.dismiss();
                }
            });
            tv_confirmNormal.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDialog.dismiss();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            });
        } else {
            callDialog.show();
        }
    }

    /**
     * 弹出地图客户端选择框
     */
    private void chooseNaviType() {
        getPopupWindow();
        navipopupWindow.showAtLocation(
                findViewById(R.id.ll_content_businessDetail), Gravity.BOTTOM,
                0, 0);
        // 修改Activity背景为半透明
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);

    }

    private void getPopupWindow() {
        if (null != navipopupWindow) {
            navipopupWindow.dismiss();
            return;
        } else {
            initNaviPopuptWindow();
        }

    }

    private void initNaviPopuptWindow() {
        View popupWindow_View = LayoutInflater.from(this).inflate(
                R.layout.item_popwindow_navi, null);
        navipopupWindow = new PopupWindow(popupWindow_View,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_baiduNavi = (TextView) popupWindow_View
                .findViewById(R.id.tv_baiduNavi);
        TextView tv_gaodeNavi = (TextView) popupWindow_View
                .findViewById(R.id.tv_gaodeNavi);
        TextView tv_cancelNavi = (TextView) popupWindow_View
                .findViewById(R.id.tv_cancelNavi);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, tv_baiduNavi, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, tv_gaodeNavi, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, tv_cancelNavi, 11);
        tv_baiduNavi.setOnClickListener(this);
        tv_gaodeNavi.setOnClickListener(this);
        tv_cancelNavi.setOnClickListener(this);
        navipopupWindow.setOutsideTouchable(true);
        navipopupWindow.setFocusable(true);
        // popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 设置点击返回键消失
        navipopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 点击其他地方消失
        popupWindow_View.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (navipopupWindow != null && navipopupWindow.isShowing()) {
                    navipopupWindow.dismiss();
                    // popupWindow = null;
                }
                return false;
            }
        });

        // 设置关闭pop时复原Activity背景
        navipopupWindow
                .setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        // 修改Activity背景
                        WindowManager.LayoutParams params = getWindow()
                                .getAttributes();
                        params.alpha = 1f;
                        getWindow().setAttributes(params);
                    }
                });

    }

    /**
     * 高德地图导航,坐标要转换(商家版用的是百度地图)
     */
    private void openGaodeNavi() {
        double[] lon = bdToGaoDe(storeInfo.latitude, storeInfo.longitude);
        try {
            String address = "androidamap://viewMap?sourceApplication="
                    + storeInfo.businessName + "&poiname=" + storeInfo.address
                    + "&lat=" + lon[1] + "&lon="
                    + lon[0] + "&dev=0";
            Intent intent = Intent.getIntent(address);
            if (isInstallByread("com.autonavi.minimap")) {
                startActivity(intent); // 启动调用
                navipopupWindow.dismiss();
                // Log.v("GasStation", "百度地图客户端已经安装");
            } else {
                // Log.v("GasStation", "没有安装百度地图客户端");
                ToastUtils.ToastShowShort(mContext, "未发现高德地图");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打开百度地图导航
     */
    private void openBaiduNavi() {
        try {
            String address = "intent://map/marker?location="
                    + storeInfo.latitude + "," + storeInfo.longitude
                    + "&title=" + storeInfo.businessName + "&content="
                    + storeInfo.address
                    + "&src=yourCompanyName|yourAppName#Intent;"
                    + "scheme=bdapp;package=com.baidu.BaiduMap;end";
            Intent intent = Intent.getIntent(address);
            if (isInstallByread("com.baidu.BaiduMap")) {
                startActivity(intent); // 启动调用
                navipopupWindow.dismiss();
                // Log.v("GasStation", "百度地图客户端已经安装");
            } else {
                // Log.v("GasStation", "没有安装百度地图客户端");
                ToastUtils.ToastShowShort(mContext, "未发现百度地图");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * bd09转成GCJ-02
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    private double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * GCJ-02转成bd09
     *
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    private double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    /**
     * 判断地图客户端是否安装
     *
     * @param packageName
     * @return
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 获取已收藏的商家列表id
     */
    private void getCollectionList(String memberId) {
        RequestParams params = new RequestParams();
        //Log.i(TAG + "menberId", memberId);
        params.put("menberId", memberId);
        params.put("page", 1);
        params.put("rows", 1000);
        if (MyApplication.token.equals(""))
            return;
        AsyncHttp.post(HttpConstant.COLLECT_STORE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable,
//                                errorResponse);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

//                        super.onSuccess(statusCode, headers, response);
                        String msg = response.optString("msg");
                        if (response.optString("status").equals("0")) {
                            // 记录已收藏商家id
                            JSONArray array = response
                                    .optJSONArray("rows");
                            if (array != null) {
                                if (array.length() > 0) {
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject obj = array.optJSONObject(i);
                                        if (obj != null) {
                                            String id = obj
                                                    .optString("businessStoreId");
                                            if (businessStoreId.equals(id)) {
                                                imb_favorite_businessDetail
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
     * 登录成功返回销毁当前页面
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MyRequestCode.LOGOIN_REQUEST) {
            finish();
        }
    }

    /**
     * 收藏当前页面商家
     */
    private void collectFavorite() {
        RequestParams params = new RequestParams();
        params.put("businessStoreId", businessStoreId);
        if (MyApplication.token.equals("")) {
//            ToastUtils.ToastShowShort(mContext,"请先登录");
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            startActivity(loginIntent);
//            startActivityForResult(loginIntent, MyRequestCode.LOGOIN_REQUEST);
            return;
        }
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(mContext);
        bufferCircleDialog.show("", true, null);
        AsyncHttp.post(HttpConstant.FAVORITE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        bufferCircleDialog.dialogcancel();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

                        super.onSuccess(statusCode, headers, response);
                        bufferCircleDialog.dialogcancel();
                        String msg = response.optString("msg");
                        String status = response.optString("status");
                        if (status.equals("0")) {
                            imb_favorite_businessDetail
                                    .setImageResource(R.drawable.star_ye);
                        }
                        if (!msg.equals("")) {
                            ToastUtils.ToastShowShort(mContext, msg);
                        }

                    }

                });

    }

    /**
     * 加载公告消息并上下滚动
     */
    private void initNotifyData() {
        RequestParams params = new RequestParams();
        params.put("businessStoreId", businessStoreId);
        AsyncHttp.posts(HttpConstant.GET_NOTIFY, params,
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

//                        super.onSuccess(statusCode, headers, response);
//                        Log.i("notify", response.toString());
                        String status = response.optString("status");
                        if (status.equals("0")) {
                            JSONArray array = response.optJSONArray("rows");
                            if (array.length() > 0) {
                                ll_notify_businessDetail.setVisibility(View.VISIBLE);
                                notifyList = JSON.parseArray(array.toString(), NotifyInfo.class);
                                TranslateAnimation inAnim = (TranslateAnimation) AnimationUtils
                                        .loadAnimation(mContext,
                                                R.anim.system_message_into);
                                tv_current_notify.setText("公告消息");
                                tv_current_notify.startAnimation(inAnim);
                                // 消息循环
                                notifyRunning();
                            } else {
                                ll_notify_businessDetail.setVisibility(View.GONE);
                            }
                        }
                    }

                });

    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_comments_head)
                        // 加载中要显示图片
                .showImageForEmptyUri(R.drawable.user_comments_head)
                        // url为空要显示图片
                .showImageOnFail(R.drawable.user_comments_head)
                        // 加载中要显示图片

                        // url为空要显示图片

                .cacheInMemory(true)
                        // 加载失败要显示图片
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
    }

    /**
     * 根据商家id获取商家基本信息
     */
    private void getStoreInfo() {
        RequestParams params = new RequestParams();
        params.put("id", businessStoreId);
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(mContext);
        bufferCircleDialog.show("正在加载", true, null);
        AsyncHttp.posts(HttpConstant.GET_STOREINFO, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        bufferCircleDialog.dialogcancel();

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

                        super.onSuccess(statusCode, headers, response);
                        bufferCircleDialog.dialogcancel();
//                        Log.i(TAG, response.toString());
                        if (response.optString("status").equals("0")) {
                            String rowsString = response.optString("rows");
                            if (!TextUtils.isEmpty(rowsString)) {
                                storeInfo = JSON.parseObject(rowsString,
                                        StoreInfo.class);
                                setStoreInfoData();
                                /****************************************/
//                                if (storeInfo!=null){
//                                    bufferShareViewDialog.setData(businessStoreId,storeInfo);
//                                }
                            }
                        }

                    }

                });

    }

    /**
     * 根据商家ID获取商家卡券
     */
    private void getCardQuan() {
        RequestParams params = new RequestParams();
        params.put("bussinessStoreId", businessStoreId);
        AsyncHttp.posts(HttpConstant.GET_CARD_BUSINESS, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                if (status.equals("0")) {
                    JSONArray rows = response.optJSONArray("rows");
                    if (rows.length() > 0) {
                        ll_menber_onlyShare.setVisibility(View.VISIBLE);
                        cardList = JSON.parseArray(rows.toString(), CardStokeInfo.class);
                        //填充数据
                        cardTemp.add(cardList.get(0));
                        CardStoke_BusinessDetailAdapter cardAdapter = new CardStoke_BusinessDetailAdapter(mContext, cardTemp);
                        lv_cardStock_businessDetial.setAdapter(cardAdapter);
                    }
                }

            }
        });

    }


    /**
     * 把请求到的商家基本信息填充到界面上
     */
    private void setStoreInfoData() {
        if (storeInfo != null) {
            //设置买单按钮
            boolean canBuy = storeInfo.isHavePay;
            if (canBuy) {
                tv_pay_businessDetail.setBackgroundColor(getResources().getColor(R.color.status_color));
                can = true;
            }
            //不可买单
            else {
                tv_pay_businessDetail.setBackgroundColor(Color.parseColor("#AFAFAF"));
                can = false;
            }
            tv_storeName.setText(storeInfo.businessName + "");
            tv_integralScale.setText("会员消费100送" + storeInfo.integralScale);
            tv_address.setText(storeInfo.address);
            //设置星级
            int mark = (int) storeInfo.messageMark;
            if (mark <= 5) {
                for (int i = 0; i < mark; i++) {
//                    ImageView iv = new ImageView(mContext);
                    ImageView iv = (ImageView) View.inflate(mContext, R.layout.item_star, null);
                    iv.setImageResource(R.drawable.star_red);
                    ll_startAll_businessDetail.addView(iv);
                }
                for (int j = 0; j < 5 - mark; j++) {
                    ImageView iv = (ImageView) View.inflate(mContext, R.layout.item_star, null);
                    iv.setImageResource(R.drawable.star_grey);
                    ll_startAll_businessDetail.addView(iv);
                }
            }
            TextView tvStar = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;

            tvStar.setLayoutParams(params);
            tvStar.setText(formater1.format(storeInfo.messageMark) + "");
            tvStar.setTextColor(getResources().getColor(R.color.text_color_red));
            ll_startAll_businessDetail.addView(tvStar);
            if (storeInfo.storeDesc != null) {
                if (storeInfo.storeDesc.equals("")) {
                    tv_storeDesc.setText("暂无简介");
                    tv_checkDetail_businessDetail.setVisibility(View.GONE);
                } else {
                    tv_storeDesc.setLines(3);
                    tv_storeDesc.setText(storeInfo.storeDesc);
                }
            } else {
                tv_storeDesc.setText("暂无简介");
                tv_checkDetail_businessDetail.setVisibility(View.GONE);
            }
            tv_consume_businessDetail.setText(storeInfo.payCount + "人买单");
            if (!storeInfo.isDouble.equals("")) {
                ll_isDouble_businessDetail.setVisibility(View.VISIBLE);
                ll_redPacket_businessDetail.setVisibility(View.GONE);
                if (storeInfo.sdip != null) {
                    String days = storeInfo.sdip.days;
                    //倒计时
                    long millisecond = Long.parseLong(storeInfo.sdip.seconds) * 10;
                    String timeStr = TimeUtil.getTimeFromMillisecond(millisecond);
                    String[] timeArr = timeStr.split(":");
                    String hh = timeArr[0];
                    String mm = timeArr[1];
                    String ss = timeArr[2];
                    counDD = Integer.parseInt(days);
                    counHH = Integer.parseInt(hh);
                    counMM = Integer.parseInt(mm);
                    counSS = Integer.parseInt(ss);
                    setTime();
                    countHandler.postDelayed(countRunnable, 1000);
                }

            } else {
                ll_isDouble_businessDetail.setVisibility(View.GONE);
                ll_redPacket_businessDetail.setVisibility(View.VISIBLE);
            }
            if (storeInfo.businessStoreImages != null) {
                imageLoader.displayImage(storeInfo.businessStoreImages.get(0),
                        civ_storeIcon, options);
                imageLoader.displayImage(storeInfo.businessStoreImages.get(0), iv_showPicture_businessDetail, options);
//                initViewPager();
            }
            if (storeInfo.pictures != null) {
                if (storeInfo.pictures.size() > 0) {
                    ll_photoAlbum_businessDetail.setVisibility(View.VISIBLE);
                    tv_photoCount.setText("相册" + storeInfo.pictures.size() + "张");
                } else {
                    ll_photoAlbum_businessDetail.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 限时抢购
     */
    private Handler countHandler = new Handler();
    private Runnable countRunnable = new Runnable() {
        @Override
        public void run() {
            setTime();
            computeTime();
            countHandler.postDelayed(this, 1000);
        }
    };

    private void setTime() {
        if (counDD < 10)
            //<10 前面加0
            tv_countDown_day.setText("0" + counDD);
        else
            tv_countDown_day.setText(counDD + "");
        if (counHH < 10)
            tv_countDown_hour.setText("0" + counHH);
        else
            tv_countDown_hour.setText(counHH + "");
        if (counMM < 10)
            tv_countDown_minute.setText("0" + counMM);
        else
            tv_countDown_minute.setText(counMM + "");
        if (counSS < 10)
            tv_countDown_second.setText("0" + counSS);
        else
            tv_countDown_second.setText(counSS + "");
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        counSS--;
        if (counSS < 0) {
            counMM--;
            counSS = 59;
            if (counMM < 0) {
                counMM = 59;
                counHH--;
                if (counHH < 0) {
                    // 倒计时结束
                    counHH = 59;
                    counDD--;
                }
            }

        }

    }

    /**
     * 把请求到的数据填充到界面上
     */
    private void setData() {

    }

//    /**
//     * 添加商品
//     */
//    @Override
//    public void addProduct(Object product) {
//        showAddProduct((RecommendInfo) product);
//    }


    /**
     * 处理返回键
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (KeyEvent.KEYCODE_BACK == keyCode &&View.VISIBLE== bufferShareViewDialog.getVisibility()) {
//            bufferShareViewDialog.setVisibility(View.GONE);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    //自定义分享内容

    /*********************************************/
    public void showShare(Context context, String platformToShare,
                          boolean showContentEdit) {
        String url = "https://test.51ujf.cn/mobile/details.html?id=" + businessStoreId;
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
        if (storeInfo.businessName != null) {
            oks.setTitle(storeInfo.businessName);
        } else {
            oks.setTitle("");
        }
        oks.setTitleUrl(url);// QQ分享
        oks.setText(storeInfo.storeDesc);
        if (storeInfo.businessStoreImages.get(0) != null) {
            oks.setImageUrl(storeInfo.businessStoreImages.get(0));
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
        //oks.setCustomerLogo(enableLogo, disableLogo, label, listener);

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
