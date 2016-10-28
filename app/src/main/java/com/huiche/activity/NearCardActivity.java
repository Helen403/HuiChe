package com.huiche.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.huiche.R;
import com.huiche.bean.NearCardBean;
import com.huiche.bean.NearCardInfoBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.custemview.CircleImageView;
import com.huiche.view.WhewView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class NearCardActivity extends BaseActivity implements OnGetGeoCoderResultListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; // 是否首次定位
    PoiSearch mPoiSearch;
    LatLng center = null;
    private TextView tv_city;
    private ImageButton imb_titleBack;
    private TextView tv_titleText, tv_preferential;
    private RelativeLayout rl_restar;
    //搜索范围
    private double range = 0.05;
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

    //波纹扩展
    private WhewView whewView_U_U;
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

    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    ImageView tv8;
    CircleImageView circleImageView;
    private RelativeLayout rl_mycard;
    private ImageView img_dele;

    int num = -1;

    NearCardBean nearCardBeanTmp;
    View cardView;

    @Override
    public int getContentView() {
        return R.layout.activity_cash;
    }

    @Override
    public void findViews() {
        mMapView = (MapView) findViewById(R.id.mapView);
        tv_city = (TextView) findViewById(R.id.tv_city);
        rl_restar = (RelativeLayout) findViewById(R.id.rl_restar);
        tv_preferential = (TextView) findViewById(R.id.tv_preferential);
        whewView_U_U = (WhewView) findViewById(R.id.whewView_U_U);
        user_icon = (CircleImageView) findViewById(R.id.user_icon);
        rl_mycard = (RelativeLayout) findViewById(R.id.rl_mycard);
        // 设置显示比例为屏幕宽度1/3
        WindowManager wm = (WindowManager) contextAppliction.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int iconWidth = user_icon.getWidth();
        whewView_U_U.setCenterRadius(iconWidth / 2);
        //扩散速度
        whewView_U_U.setSpeed(2);
        whewView_U_U.setAlphaSpeed(1);
        whewView_U_U.setRippleWidthScale(8);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = soundPool.load(NearCardActivity.this, R.raw.hongbao_gq, 1);
    }


    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        cardView = inflater.inflate(R.layout.card_item_dialog, content, false);

        tv1 = (TextView) cardView.findViewById(R.id.tv_1);
        tv2 = (TextView) cardView.findViewById(R.id.tv_2);
        tv3 = (TextView) cardView.findViewById(R.id.tv_3);
        tv4 = (TextView) cardView.findViewById(R.id.tv_4);
        tv5 = (TextView) cardView.findViewById(R.id.tv_5);
        tv6 = (TextView) cardView.findViewById(R.id.tv_6);
        tv7 = (TextView) cardView.findViewById(R.id.tv_7);


        cardView.setVisibility(View.GONE);
        relativeLayout.addView(cardView);
    }

    @Override
    public void initData() {
        setLocation();
    }

    private void setLocation() {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        LocationClient mLocClient = new LocationClient(NearCardActivity.this);
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

        //地图滑动监听
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                //开始滑动的时候
                tv_city.setText("正在获取位置信息");
                mBaiduMap.clear();
//                dialogVV.setVisibility(View.GONE);
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
                if (TextUtils.isEmpty(num + "") || num != -1 || nearCardBeanTmp != null) {
                    bufferCircleView.show();
                    String vo_id = nearCardBeanTmp.data.get(num).vo_id;
                    Param param = new Param();
                    param.put("vo_id", vo_id);
                    StringBuffer sb = new StringBuffer();
                    sb.append("{").append("\"vo_id\":\"").append(vo_id).append("\"}");
                    param.put("key", getMd5Password(sb.toString()));
                    ControlUtils.postsEveryTime(Constants.Helen.NEARCARDINFO, param, NearCardInfoBean.class, new ControlUtils.OnControlUtils<NearCardInfoBean>() {
                        @Override
                        public void onSuccess(String url, NearCardInfoBean nearCardInfoBean, ArrayList<NearCardInfoBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                            bufferCircleView.hide();
                            cardView.setVisibility(View.VISIBLE);
                            tv1.setText(nearCardInfoBean.data.get(0).co_name);
                            tv2.setText(nearCardInfoBean.data.get(0).co_address);
                        }

                        @Override
                        public void onFailure(String url) {
                            bufferCircleView.hide();
                            T("请检测网络");
                        }
                    });


//                    BusinessCardDetail businessCardDetail = businessCardInfo.businessStoreList.get(num);
//                    id = businessCardDetail.getCoupons().getId();
//                    businessId = businessCardDetail.getId();
//                    dialogVV.setVisibility(View.VISIBLE);
//                    imageLoader.displayImage(businessCardDetail.getCoupons().businessStoreImage, circleImageView, options);
//                    tv1.setText(businessCardDetail.businessStoreName);
//                    tv2.setText(businessCardDetail.businessArea);
//                    String tmp = businessCardDetail.getCoupons().getPrice();
//                    if (tmp.length() == 1) {
//                        tv6.setText(tmp + " ");
//                    } else {
//                        tv6.setText(tmp);
//                    }
//                    tv7.setText("满" + businessCardDetail.getCoupons().getDeduction() + "可用");

                }
                return true;
            }
        });

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

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
                //显示位置信息
                if (location.getCity() != null) {
                    String city = location.getCity();
                    String strnumber = location.getStreetNumber();
                    String street = location.getStreet();
                    tv_city.setText(city + street + strnumber);
                }
                //第一次登录时，根据坐标请求相关数据
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //获取附近卡卷
                getDataForLat(latitude, longitude);
            }
        }
    }

    //获取附近卡卷
    private void getDataForLat(double latitude, double longitude) {
        whewView_U_U.start();
        L(latitude);
        L(longitude);
        Param param = new Param();
        param.put("lat", latitude);
        param.put("lng", longitude);
        StringBuffer sb = new StringBuffer();

        sb.append("{\"").append("lat").append("\":\"").append(latitude).append("\"");
        sb.append(",\"").append("lng").append("\":\"").append(longitude).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.NEARCARD, param, NearCardBean.class, new ControlUtils.OnControlUtils<NearCardBean>() {
            @Override
            public void onSuccess(String url, NearCardBean nearCardBean, ArrayList<NearCardBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(nearCardBean.msg);
                nearCardBeanTmp = nearCardBean;
                AddMarker(nearCardBean);
            }

            @Override
            public void onFailure(String url) {
                T("请检测网络");
            }
        });

    }


    /***
     * 根据经纬度添加覆盖物
     */
    public void AddMarker(NearCardBean nearCardBean) {
        markerList = new ArrayList<>();
        markerList.clear();
        int count = nearCardBean.data.size();
        for (int i = 0; i < count; i++) {
            //覆盖物图标
            View view = inflater.inflate(R.layout.card_marker, null);
            //卡券金额
            String price = nearCardBean.data.get(i).vo_money;

            Bitmap bitmap = getViewBitmap(view, price);
            BitmapDescriptor centerBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
            double lat = Double.parseDouble(nearCardBean.data.get(i).lat);
            double lon = Double.parseDouble(nearCardBean.data.get(i).lng);
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

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case stop_uu:
                    //延迟关闭uu动画
                    whewView_U_U.stop();
                    break;
                //显示商家图标
                case business_img:
//                    AddMarker();

                    break;
            }
        }
    };


    /**
     * 开始/结束U-U
     */
    private void startUU() {
        whewView_U_U.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
        //关闭u1u动画
        whewView_U_U.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
        whewView_U_U.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
        whewView_U_U.stop();
    }

}
