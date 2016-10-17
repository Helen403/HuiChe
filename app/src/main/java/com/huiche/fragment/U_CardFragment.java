package com.huiche.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.huiche.PostResult.BusinessCardDetail;
import com.huiche.PostResult.BusinessCardInfo;
import com.huiche.R;
import com.huiche.activity.BusinessDetailActivity;
import com.huiche.activity.mine.MyCardQuanActivity;
import com.huiche.base.BaseFragment;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ImagLoadUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.WindowUtils;
import com.huiche.view.CircleImageView;
import com.huiche.view.WhewView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class U_CardFragment extends BaseFragment implements OnGetGeoCoderResultListener, View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; // 是否首次定位
    PoiSearch mPoiSearch;
    LatLng center = null;
//    int searchType = 2;  // 搜索的类型，在显示时区分
//    int radius = 380;
//    private int loadIndex = 0;
//    ImageView redbao;
    private Context mContext;
    private TextView tv_city;
    private ImageButton imb_titleBack;
    private TextView tv_titleText, tv_preferential;
    private RelativeLayout rl_restar;
    //搜索范围
    private double range = 0.05;
    private BusinessCardInfo businessCardInfo;
    private CircleImageView user_icon;
    //地图滑动时，开始和结束时的坐标
    private LatLng starLat, endLat;
    //百度地图反地址编码
    private GeoCoder geocoder;
//    private ReverseGeoCodeOption result;
//    private ReverseGeoCodeOption reverseGeoCodeOption;
    private boolean firstIn = true;//是否第一次加载数据

    /**
     * 位置回调
     */
    public BDLocationListener myListener = new MyLocationListenner();
    private View view;

    //波纹扩展
    private WhewView whewView_U_U;
    private WindowUtils windowUtils;
    private int iconHeight;
    private SoundPool soundPool;
    private int music;
    private static final int UU = 1;
    //延时停止动画
    private static final int stop_uu = 2;
    //延迟显示商家图标
    private static final int business_img = 3;

    private List<MarkerOptions> markerList;

    /**********************************/
    View dialogVV;

    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    ImageView tv8;
  CircleImageView circleImageView;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    int num = -1;
    //卡券id
    String id;
    //商家id
    String businessId;
    private RelativeLayout rl_mycard;
    private ImageView img_dele;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater
//                .inflate(R.layout.activity_cash, container, false);
        context = getActivity();

        /******************************************************************/
        //总布局
        final RelativeLayout content = new RelativeLayout(context);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        final LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);
        view = inflater.inflate(R.layout.activity_cash, content, false);
        dialogVV = inflater.inflate(R.layout.card_item_dialog, content, false);
        dialogVV.setVisibility(View.GONE);
        tv1 = (TextView) dialogVV.findViewById(R.id.tv_1);
        tv2 = (TextView) dialogVV.findViewById(R.id.tv_2);
        tv3 = (TextView) dialogVV.findViewById(R.id.tv_3);
        tv4 = (TextView) dialogVV.findViewById(R.id.tv_4);
        tv5 = (TextView) dialogVV.findViewById(R.id.tv_5);
        tv6 = (TextView) dialogVV.findViewById(R.id.tv_6);
        tv7 = (TextView) dialogVV.findViewById(R.id.tv_7);
        tv8 = (ImageView) dialogVV.findViewById(R.id.tv_8);

        circleImageView = (CircleImageView) dialogVV.findViewById(R.id.icon);
        img_dele = (ImageView) dialogVV.findViewById(R.id.img_dele);
        dialogVV.setLayoutParams(rl);
        content.addView(view);
        content.addView(dialogVV);
        initImageLoader();
        /******************************************************************/
        return content;
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
                .cacheInMemory(true)
                        // 加载失败要显示图片
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
    }

    @Override
    public void findViews() {
        mContext = getActivity();
        mMapView = (MapView) view.findViewById(R.id.mapView);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        imb_titleBack = (ImageButton) view.findViewById(R.id.imb_titleBack);
        imb_titleBack.setVisibility(View.GONE);
        tv_titleText = (TextView) view.findViewById(R.id.tv_titleText);
        rl_restar = (RelativeLayout) view.findViewById(R.id.rl_restar);
        tv_preferential = (TextView) view.findViewById(R.id.tv_preferential);
        whewView_U_U = (WhewView) view.findViewById(R.id.whewView_U_U);
        user_icon = (CircleImageView) view.findViewById(R.id.user_icon);
        rl_mycard = (RelativeLayout) view.findViewById(R.id.rl_mycard);
        // 设置显示比例为屏幕宽度1/3
        windowUtils = new WindowUtils(mContext);
//        screenWidth = windowUtils.getdisplayWidth();
        int screenHeight = windowUtils.getdisplayHeight();
//        iconWidth = screenWidth / 3;
//        iconHeight = screenWidth / 3;
        int iconWidth = user_icon.getWidth();
        whewView_U_U.setCenterRadius(iconWidth / 2);
        //扩散宽度
        whewView_U_U.setMaxWidth(screenHeight / 2);
        //扩散速度
        whewView_U_U.setSpeed(2);
        whewView_U_U.setAlphaSpeed(1);
        whewView_U_U.setRippleWidthScale(8);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = soundPool.load(mContext, R.raw.hongbao_gq, 1);

    }

    @Override
    public void initData() {
        tv_titleText.setText("现金券");
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        LocationClient mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
        //第一步，创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();
        //将新的位置信息显示出来
        geocoder = GeoCoder.newInstance();
        geocoder.setOnGetGeoCodeResultListener(this);
    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
        rl_restar.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        rl_mycard.setOnClickListener(this);
        img_dele.setOnClickListener(this);
        //地图滑动监听
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                //开始滑动的时候
                tv_city.setText("正在获取位置信息");
                mBaiduMap.clear();
                dialogVV.setVisibility(View.GONE);
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                //在拖动过程中，如果动画是开启的让它停止
                if (whewView_U_U.isStarting()) {
                    whewView_U_U.stop();
                }
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                endLat = mapStatus.target;
//                double latitude = endLat.latitude;
//                double longitude = endLat.longitude;
                //反地址编码
                geocoder.reverseGeoCode(new ReverseGeoCodeOption().location(endLat));
//                //重新请求服务器数据
//                getDataForLat(latitude, longitude, range);


            }
        });

        /***
         * 地图覆盖物点击监听
         *
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                num = bundle.getInt("num");
                if (TextUtils.isEmpty(num + "") || num != -1) {
                    BusinessCardDetail businessCardDetail = businessCardInfo.businessStoreList.get(num);
                    id = businessCardDetail.getCoupons().getId();
                    businessId = businessCardDetail.getId();
                    dialogVV.setVisibility(View.VISIBLE);
                    imageLoader.displayImage(businessCardDetail.getCoupons().businessStoreImage, circleImageView, options);
                    tv1.setText(businessCardDetail.businessStoreName);
                    tv2.setText(businessCardDetail.businessArea);
                    String tmp = businessCardDetail.getCoupons().getPrice();
                    if (tmp.length() == 1) {
                        tv6.setText(tmp + " ");
                    } else {
                        tv6.setText(tmp);
                    }
                    tv7.setText("满" + businessCardDetail.getCoupons().getDeduction() + "可用");

                }
                return true;
            }
        });

    }


    @Override
    public void initViews() {

    }

    /**
     * @param view 点击事件
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imb_titleBack:
                break;
            case R.id.rl_restar:
                //重新定位
                reStarLocate();
                break;
            //进入商家详情
            case R.id.tv_3:
                intent.setClass(mContext, BusinessDetailActivity.class);
                intent.putExtra("businessStoreId", businessId);
                intent.putExtra("empty", "0");
                mContext.startActivity(intent);
                break;
            case R.id.tv_4:
                getCard();
                break;
            //进入我的卡券
            case R.id.rl_mycard:
                intent.setClass(mContext, MyCardQuanActivity.class);
                mContext.startActivity(intent);
                break;
            //取消卡券
            case R.id.img_dele:
                dialogVV.setVisibility(View.GONE);
                break;
        }
    }

    /***
     * 用户领取卡券
     */

    private void getCard() {
        SharedPreferences share = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("token", share.getString("token", ""));
        AsyncHttp.posts(HttpConstant.USER_GET_CARD, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                boolean success = response.optBoolean("success");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    if (success) {
                        showDialog(msg, 0);
                    }

                }
                if (status.equals("1")) {
                    int errcode = response.optInt("errorCode");
                    showDialog(msg, errcode);
                }

            }
        });
    }


    /**
     * 显示结果弹框
     */
    private AlertDialog notifyDialog;
    private TextView tv_dialogNormal_title;
    private TextView tv_cancelNormal;
    private TextView tv_confirmNormal;
    private TextView tv_dialogNormal_content;

    private void showDialog(String msg, int errorCode) {
        if (notifyDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = View.inflate(mContext, R.layout.dialog_normal, null);
            tv_dialogNormal_title = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_title);
            tv_cancelNormal = (TextView) dialogView.findViewById(R.id.tv_cancelNormal);
            tv_dialogNormal_content = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_content);
            tv_confirmNormal = (TextView) dialogView.findViewById(R.id.tv_confirmNormal);
            notifyDialog = builder.show();
            Window dialogWindow = notifyDialog.getWindow();
            dialogWindow.setContentView(dialogView);
            int width = windowUtils.getdisplayWidth();
            dialogWindow.setContentView(dialogView);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //显示比例宽度5/7
            lp.width = width * 5 / 7;
//            lp.height = lp.width*10/16;
            dialogWindow.setAttributes(lp);
        } else {
            notifyDialog.show();
        }
        if (errorCode == 0) {
            //领取成功
            tv_dialogNormal_title.setText(msg);
            tv_dialogNormal_content.setText("您的卡券已经放入卡包");
            tv_cancelNormal.setText("关闭");
            tv_confirmNormal.setText("点击查看");
            tv_confirmNormal.setVisibility(View.VISIBLE);
        } else {
            tv_dialogNormal_title.setText("领取失败");
            tv_dialogNormal_content.setText(msg);
            tv_confirmNormal.setVisibility(View.GONE);
            tv_cancelNormal.setText("关闭");
        }
        tv_cancelNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDialog.dismiss();
            }
        });
        tv_confirmNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyCardQuanActivity.class);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        });

    }


    /**
     * 回到初始定位
     */
    public void reStarLocate() {
        mBaiduMap.clear();
        //将初始坐标射进去
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(center));
        //反地址编码
        geocoder.reverseGeoCode(new ReverseGeoCodeOption().location(center));

    }

    //地址与反地址编码
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        String city = reverseGeoCodeResult.getAddressDetail().city;
        String street = reverseGeoCodeResult.getAddressDetail().street;
        String strNumber = reverseGeoCodeResult.getAddressDetail().streetNumber;
        tv_city.setText(city + street + strNumber);
//        ToastUtils.ToastShowShort(mContext, reverseGeoCodeResult.getLocation() + "");
        getDataForLat(reverseGeoCodeResult.getLocation().latitude, reverseGeoCodeResult.getLocation().longitude, range);

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            //第一次登录显示位置信息
            if (isFirstLoc) {
                isFirstLoc = false;
                center = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(center).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                LatLng point = new LatLng(location.getLatitude(),
//                        location.getLongitude());
                //  ToastUtils.ToastShowShort(mContext, point + "");
                //显示位置信息
                if (location.getCity() != null) {
                    String city = location.getCity();
//                    String address = location.getAddrStr();
                    String strnumber = location.getStreetNumber();
//                    String sdfdf = location.getDistrict();
                    String street = location.getStreet();
                    tv_city.setText(city + street + strnumber);
                }
                //第一次登录时，根据坐标请求相关数据
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                getDataForLat(latitude, longitude, range);
            }
        }

//        public void onReceivePoi(BDLocation poiLocation) {
//        }
    }

    public void getDataForLat(double latitude, double longitude, double range) {
        //开启U一U
        startUU();
        RequestParams params = new RequestParams();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("range", range);
        AsyncHttp.post(HttpConstantChc.BUSINESS_CARD, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                boolean success = response.optBoolean("success");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    if (success) {
                        String result = response.optJSONObject("rows").toString();
                        if (!result.equals("")) {
                            businessCardInfo = JSON.parseObject(result, BusinessCardInfo.class);
                            int storeNum = businessCardInfo.businessStoreList.size();
                            //当商家数量不为空时，在地图上根据经纬度添加卡券图标
                            if (businessCardInfo.businessStoreList.size() > 0) {
                                //3秒后再显示商家图标
                                mHandler.sendEmptyMessageDelayed(business_img, 3000);
                            }
                            tv_preferential.setText("附近" + storeNum + "个商家发券");
                            if (firstIn) {
                                String imgUrl = businessCardInfo.getMenber().wxHeadimage;
                                if (!imgUrl.equals("")) {
                                    ImagLoadUtils.setImage(imgUrl, user_icon, mContext);
                                    firstIn = false;
                                }
                            }
                        }

                    } else {
                        ToastUtils.ToastShowShort(mContext, msg);
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                }


            }
        });

    }

//    ArrayList<HashMap<String, Object>> markers = new ArrayList<HashMap<String, Object>>();

    /***
     * 根据经纬度添加覆盖物
     */
    public void AddMarker(BusinessCardInfo dataList) {
//        int length = dataList.businessStoreList.size();
        markerList = new ArrayList<>();
        markerList.clear();
        for (int i = 0; i < dataList.businessStoreList.size(); i++) {
            //覆盖物图标
            View view = LayoutInflater.from(mContext).inflate(R.layout.card_marker, null);
            //卡券金额
            String price = dataList.getBusinessStoreList().get(i).getCoupons().price;

            Bitmap bitmap = getViewBitmap(view, price);
            BitmapDescriptor centerBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
            double lat = dataList.getBusinessStoreList().get(i).getLatitude();
            double lon = dataList.getBusinessStoreList().get(i).getLongitude();
            LatLng blatlng = new LatLng(lat, lon);
            MarkerOptions ooMarker = new MarkerOptions().position(blatlng).icon(centerBitmap).zIndex(i).draggable(true);
            /*****************************************************************/
//            HashMap<String,Object> map = new HashMap<>();
//            map.put("ooMarker",ooMarker);
//            map.put("businessStoreList",dataList.businessStoreList.get(i));
//
//            markers.add(map);

            Bundle bundle = new Bundle();
            bundle.putInt("num", i);
            ooMarker.extraInfo(bundle);

            /*****************************************************************/

            mBaiduMap.addOverlay(ooMarker);
            //方便区分点击事件
            markerList.add(ooMarker);
        }
        //当显示完商家图标后关闭uu
        mHandler.sendEmptyMessageDelayed(stop_uu, 2000);
    }

    private Bitmap getViewBitmap(View addViewContent, String money) {
        addViewContent.setDrawingCacheEnabled(true);
        /**
         自定义覆盖物布局，将布局转化为bitmap，根部局必须为线性布局
         第二个参数是卡券的金额数
         */TextView tv_marker = (TextView) addViewContent.findViewById(R.id.tv_marker);
        tv_marker.setText("￥" + money);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        return Bitmap.createBitmap(cacheBitmap);
    }

    /**
     * 开始/结束U-U
     */
    private void startUU() {
        whewView_U_U.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case stop_uu:
                    //延迟关闭uu动画
                    whewView_U_U.stop();
                    break;
                //显示商家图标
                case business_img:
                    AddMarker(businessCardInfo);

                    break;
            }


//            if (msg.what == UU) {
//                // 循环播放音效10s一次
//                 mHandler.sendEmptyMessageDelayed(UU, 10000);
//                 soundPool.play(music, 1, 1, 0, 0, 1);
//            }


        }

    };

    @Override
    public void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
//        //关闭u1u动画
//        whewView_U_U.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
        // whewView_U_U.stop();
    }
}
