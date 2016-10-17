package com.huiche.activity.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.PoiOverlay;


/**
 * Created by Administrator on 2016/8/4.
 */
public class CashActivity extends BaseActivity implements OnGetPoiSearchResultListener {


    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private boolean isFirstLoc = true; // 是否首次定位

    private PoiSearch mPoiSearch;

    private LatLng center = null;
    private int radius = 380;

    /**
     * 位置回调
     */
    public BDLocationListener myListener = new MyLocationListenner();

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_cash);
        mMapView = (MapView) findViewById(R.id.mapView);
//        redbao = (ImageView) findViewById(R.id.red_bao);
//        redbao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CashActivity.this, RedBaoDetailActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        // redbao = (ImageView) findViewById(R.id.red_bao);
//        redbao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CashActivity.this, RedBaoDetailActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void initData() {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        // 隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);


        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();


        //第一步，创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();

        // 第三步，设置POI检索监听者；
        mPoiSearch.setOnGetPoiSearchResultListener(this);


    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        //获取POI检索结果

//        Log.d("Helen", "检索到");


        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(CashActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();

            int searchType = 2;
            switch (searchType) {
                case 2:
                    showNearbyArea(center, radius);
                    break;
                case 3:
//                        showBound(searchbound);
                    break;
                default:
                    break;
            }

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }

            strInfo += "找到结果";
            Toast.makeText(CashActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
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

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                center = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(center).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


//                LatLng point = new LatLng(location.getLatitude(),
//                        location.getLongitude());


                //  第四步，发起检索请求；
                int loadIndex = 0;
                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword("美食"
                ).sortType(PoiSortType.distance_from_near_to_far).location(center)
                        .radius(radius).pageNum(loadIndex);
                mPoiSearch.searchNearby(nearbySearchOption);
            }


        }

//        public void onReceivePoi(BDLocation poiLocation) {
//        }
    }

    /**
     * 对周边检索的范围进行绘制
     *
     * @param center
     * @param radius
     */
    public void showNearbyArea(LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

        //最外围
        OverlayOptions ooCircle1 = new CircleOptions().fillColor(0x00FFFFFF)
                .center(center).stroke(new Stroke(3, 0xFFBEE3F0))
                .radius(radius);

        OverlayOptions ooCircle2 = new CircleOptions().fillColor(0x40C7E7EE)
                .center(center).stroke(new Stroke(3, 0xFFBEE3F0))
                .radius(radius - 100);

        OverlayOptions ooCircle3 = new CircleOptions().fillColor(0x60C7E7EE)
                .center(center).stroke(new Stroke(3, 0xFFBEE3F0))
                .radius(radius - 200);

        mBaiduMap.addOverlay(ooCircle1);
        mBaiduMap.addOverlay(ooCircle2);
        mBaiduMap.addOverlay(ooCircle3);
    }


    @Override
    public void setListeners() {
    }


    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 第五步，释放POI检索实例；
        mPoiSearch.destroy();


        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }


}
