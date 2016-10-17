package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.huiche.R;
import com.huiche.activity.mine.JiFenShopping;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.PosterInfo;
import com.huiche.bean.RequestBaoBean;
import com.huiche.constant.HttpConstant;
import com.huiche.constant.MyRequestCode;
import com.huiche.fragment.IntegralShopFragment;
import com.huiche.fragment.MineHelenFragment;
import com.huiche.fragment.NearByFragme;
import com.huiche.fragment.ShoppingCartFragment;
import com.huiche.fragment.U_CardFragment;
import com.huiche.fragment.U_UFragment;
import com.huiche.listener.GridViewFromNearByListener;
import com.huiche.two_dimensioncode.CaptureActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.LoadImageUtil;
import com.huiche.view.BufferRedBaoViewDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnClickListener,
        OnCheckedChangeListener, GridViewFromNearByListener {
    /**
     * 附近
     */
    private RadioButton nearBy;
    /**
     * 积分商城
     */
    private RadioButton scoreMall;
    /**
     * 购物车
     */
    private RadioButton shoppingCart;
    /**
     * 我的
     */
    private RadioButton mine;
    /**
     * radioGroup
     */
    private RadioGroup radioGroup;
    /**
     * 碎片集合
     */
    private List<Fragment> listFragment = new ArrayList<>();
    /**
     * 当前碎片位置
     */
    private int currentTabIndex = 0;
    private RadioGroup rl_title_mainActivity;
    private Context mContext;
    private ShoppingCartFragment shoppingCartFragment;
    private U_UFragment uuFragment;
    private ImageView iv_uu_Fragment;
    private MineHelenFragment mineFragment;
    private NearByFragme nearByFragme;
    private U_CardFragment cardFragment;
    private IntegralShopFragment integralShopFragment;
    //	private boolean supportImmersive=false;//是否支持沉浸式（系统是否4.4以下不支持）
    private LinearLayout ll_content_main;
    private List<PosterInfo> buttomIconList;
    private LoadImageUtil loadImageUtil = new LoadImageUtil();
    private int sizeType = 2;
    public BufferRedBaoViewDialog bufferRedBaoViewDialog;
    private SharedPreferences share;
    RequestBaoBean requestBaoBean;
    //当请求到红包后，如果用户没有拆开就不能3秒请求红包
    public static boolean isRecieve = false;
    //当请求到一个红包后停止3秒请求一次
    public static boolean isHasRed = false;
    //请求红包的经纬度
    private double myLongitude;
    private double myLatitude;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {
        //总布局
        RelativeLayout content = new RelativeLayout(this);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);
        bufferRedBaoViewDialog = new BufferRedBaoViewDialog(mContext);
        bufferRedBaoViewDialog.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_main, content, false);
        content.addView(view);
        content.addView(bufferRedBaoViewDialog);
        setContentView(content);
        ll_content_main = (LinearLayout) findViewById(R.id.ll_content_main);
        nearBy = (RadioButton) findViewById(R.id.radioButton_nearBy_MainActivity);
        scoreMall = (RadioButton) findViewById(R.id.radioButton_scoreMall_MainActivity);
        shoppingCart = (RadioButton) findViewById(R.id.radioButton_shoppingCart_MainActivity);
        mine = (RadioButton) findViewById(R.id.radioButton_mine_MainActivity);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_MainActivity);
        iv_uu_Fragment = (ImageView) findViewById(R.id.iv_uu_Fragment);
        iv_uu_Fragment.setTag("1");
        share = getSharedPreferences("user_data", MODE_PRIVATE);
    }

    @Override
    public void initData() {
        //判断屏幕尺寸大小
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (width < 720) sizeType = 1;
        else if (width >= 720 && width < 1080) sizeType = 2;
        else sizeType = 3;
//        requreADFromWeb(20);
        initFragment();
        getNearData();

    }

    /**
     * 开始图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片每3秒滚动一次
        mHandler.postDelayed(mImageTimerTask, 3000);

    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startImageTimerTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopImageTimerTask();
    }

    ;
    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            mHandler.postDelayed(this, 3000);
            getRedPackData();
            once = true;
        }
    };


    /**
     * 位置回调
     */
    public BDLocationListener myListener = new MyLocationListener();
    LocationClient mLocationClient;

    /**
     * 每隔3秒请求网络数据获取红包
     */
    private void getNearData() {
        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        mLocationClient.requestLocation();
    }

    public void getRedPackData() {
        RequestParams params = new RequestParams();
        params.put("lg", myLongitude);
        params.put("wt", myLatitude);
        if (!MyApplication.token.equals("")) {
            params.put("token", MyApplication.token);
        } else {
            return;
        }
        //如果没红包再去请求 有红包就保留该红包
        if (isHasRed) {
            //判断用户有没有拆红包，如果才了就请求红包
            if (!isRecieve) {
                return;
            }
        }
        AsyncHttp.posts(HttpConstant.NEAR_RED_BAO, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // Log.i("Helen", response.toString());
                        String status = response.optString("status");
                        if (status.equals("0")) {
                            //返回的status ，为0出现红包按钮，页面变化从图1.0至图1.1。点击后展示至图1.2，
                            ImageView imageView = (ImageView) findViewById(R.id.iv_uu_Fragment);
                            imageView.setImageResource(R.drawable.xiaohongba_bu);
                            iv_uu_Fragment.setTag("2");
                            requestBaoBean = JSON.parseObject(response.toString(),
                                    RequestBaoBean.class);
                            if (requestBaoBean == null) return;
                            if (requestBaoBean.rows == null) return;
                            isHasRed = true;
                            //设置红包的数据
                            bufferRedBaoViewDialog.setData(requestBaoBean);
                        }
                        if (status.equals("1")) {
                            // 返回的status ，为1则没有商家红包，继续3秒请求一次接口。
//                                    ToastUtils.ToastShowShort(mContext, "请先登录");
                            // Log.i("Helen", "没有登录或者 周边无揽客");
                            ImageView imageView = (ImageView) findViewById(R.id.iv_uu_Fragment);
                            imageView.setImageResource(R.drawable.sou_00);
                            iv_uu_Fragment.setTag("1");
                        }
                    }
                }
        );
    }

    Boolean once = true;

    /***
     * 监听的回调
     *
     * @author Administrator
     */
    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation arg0) {
            if (once) {
                once = false;
                //纬度
                myLatitude = arg0.getLatitude();
//                Log.d("Helen", myLatitude + "");//22.525993
                //经度
                myLongitude = arg0.getLongitude();
//                Log.d("Helen", myLongitude + "");//113.351875
                /********************************************************/
                mLocationClient.stop();
            }
        }
    }

    /**
     * 获取底部按钮图标
     *
     * @param type
     */
    private void requreADFromWeb(int type) {
        RequestParams params = new RequestParams();
        params.put("positionId", type);
        params.put("isPhoneType", sizeType);//1:小 2:中3:大
        AsyncHttp.posts(HttpConstant.GET_HOME_AD, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
//                        Log.i("ss", response.toString());
                        String status = response.optString("status");
                        if (status.equals("0")) {
                            JSONArray array = response.optJSONArray("rows");
                            if (array.length() > 0) {
                                buttomIconList = JSON.parseArray(array.toString(), PosterInfo.class);
                                //动态加载
                                loadImageUtil.imageLoader.displayImage(buttomIconList.get(4).content, iv_uu_Fragment);
                                setBitmap();
                            }
                        }
                    }
                }
        );
    }

    /**
     * 根据url请求获取底部图标
     */
    private void setBitmap() {
        final List<Bitmap> bitmapList = new ArrayList<>();
//        for (int j = 0; j < buttomIconList.size(); j++) {
        String url = buttomIconList.get(0).content;
        AsyncHttpClient client = new AsyncHttpClient();
        // 指定文件类型
        String[] allowedContentTypes = new String[]{".*"};
//        String[] allowedContentTypes = new String[]{"image/png", "image/jpeg"};
        client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                //使用BitmapFactory创建图片
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                nearBy.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(bitmap), null, null);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void setListeners() {

        radioGroup.setOnCheckedChangeListener(this);
        iv_uu_Fragment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_uu_Fragment:
                radioGroup.clearCheck();
                if ("1".equals(iv_uu_Fragment.getTag())) {
                    if (MyApplication.token.equals("")) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    switchFragment(2);
                } else {
                    charRedBao();
                }


                break;
        }
    }

    /**
     * 用户拆红包则请求领取接口：
     */
    public void charRedBao() {
        if ("2".equals(iv_uu_Fragment.getTag())) {
            bufferRedBaoViewDialog.setVisibility(View.VISIBLE);
            ImageView imageView = (ImageView) findViewById(R.id.iv_uu_Fragment);
            bufferRedBaoViewDialog.setImageView(imageView);
        }
    }

    /**
     * 初始化fragment，加载fragment到集合中
     */

    private void initFragment() {
        nearByFragme = new NearByFragme();
        integralShopFragment = new IntegralShopFragment();
        mineFragment = new MineHelenFragment();

        listFragment.add(nearByFragme);
        listFragment.add(integralShopFragment);
        //卡券
        cardFragment = new U_CardFragment();
        listFragment.add(cardFragment);
        //listFragment.add(uuFragment);
//        listFragment.add(shoppingCartFragment);
        listFragment.add(mineFragment);
        radioGroup.check(R.id.radioButton_nearBy_MainActivity);
        switchFragment(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Intent intent = new Intent();
        switch (checkedId) {
            // 附近
            case R.id.radioButton_nearBy_MainActivity:
                switchFragment(0);
                break;
            // 积分商城
            case R.id.radioButton_scoreMall_MainActivity:
                //当用户没登录的时候，跳到登录界面
                if ((share.getString("token", "")).equals("")) {
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                } else {
//                    switchFragment(1);
//                    intent.setClass(MainActivity.this, IntegralShopActivity.class);
//                    startActivity(intent);



                    intent.setClass(MainActivity.this, JiFenShopping.class);
                    startActivity(intent);

                }


                break;
            // 购物车
            case R.id.radioButton_shoppingCart_MainActivity:
                //重新请求购物车列表
//                if ((share.getString("token", "")).equals("")) {
//                    intent.setClass(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    intent = new Intent(mContext, ShoppingCartActivity.class);
//                    startActivityForResult(intent, MyRequestCode.SHOPPING_CART_REQUEST);
//                }


                Intent intents = new Intent();
                intents.setClass(this, CaptureActivity.class);
                startActivity(intents);

//                switchFragment(3);
//                shoppingCartFragment.getProductList();
                break;
            // 我的
            case R.id.radioButton_mine_MainActivity:
                switchFragment(3);
                break;
            default:
                break;
        }
    }

    public void switchFragment(int targetTabIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = listFragment.get(currentTabIndex);
        Fragment targetFragment = listFragment.get(targetTabIndex);
        if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {
                transaction.hide(currentFragment).add(
                        R.id.frameLayout_MainActivity, targetFragment);
            } else {
                transaction.hide(currentFragment).show(targetFragment);
            }
        } else if (targetFragment.isAdded()) {
            transaction.show(targetFragment);
        } else {
            transaction.add(R.id.frameLayout_MainActivity, targetFragment)
                    .show(targetFragment);
        }
        transaction.commit();
        currentTabIndex = targetTabIndex;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onTrimMemory(int level) {

        super.onTrimMemory(level);
//		SystemBarUtil.initSystemBar(this);
    }

    @Override
    public void changColorSetData(String str, int position) {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //从城市选择中过来的数据，带了本城市。把数据chuan给碎片，进行加载数据
        if (resultCode == MyRequestCode.CityChoice_ResultCode) {
            String city = data.getStringExtra("cityName");
            if (city != null) {
                nearByFragme.changLocalCity(city);
            }
        } else if (requestCode == MyRequestCode.SHOPPING_CART_REQUEST && resultCode == MyRequestCode.SHOPPING_CART_RESULT) {
            //从购物车返回默认选择附近
            nearBy.setChecked(true);
        } else if (requestCode == MyRequestCode.CARD_MAPVIEW && resultCode == 1688) {
            //从卡券地图返回默认选择附近
            nearBy.setChecked(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((radioGroup.getCheckedRadioButtonId() == R.id.radioButton_scoreMall_MainActivity && keyCode == KeyEvent.KEYCODE_BACK) || bufferRedBaoViewDialog.getVisibility() == View.VISIBLE) {
            if (bufferRedBaoViewDialog.getVisibility() == View.VISIBLE) {
                bufferRedBaoViewDialog.setVisibility(View.GONE);
                return true;
            }
            if (integralShopFragment.goBack()) {
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
