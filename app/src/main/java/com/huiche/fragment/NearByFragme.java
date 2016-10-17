package com.huiche.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.activity.BusinessDetailActivity;
import com.huiche.activity.ClassifyOfActivity;
import com.huiche.activity.HotProductActivityTest;
import com.huiche.activity.MoreProductActivity;
import com.huiche.activity.citychoice.CityChoice;
import com.huiche.activity.mine.CarBeautifulActivity;
import com.huiche.activity.mine.InsuranceActivity;
import com.huiche.activity.mine.MoreActivity;
import com.huiche.activity.mine.OilcardActivity;
import com.huiche.activity.mine.SearchActivity;
import com.huiche.adapter.AdapterListview_NearByFirstPage;
import com.huiche.adapter.Adapter_CitySelect_NearByFragmentFrist;
import com.huiche.adapter.GridView_HeadView;
import com.huiche.adapter.ViewPageAdaper_Poster;
import com.huiche.base.BaseFragment;
import com.huiche.bean.BusinessInfoAll;
import com.huiche.bean.InfoGridView;
import com.huiche.bean.PosterInfo;
import com.huiche.bean.ProvinceInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.constant.HttpConstantHao;
import com.huiche.constant.MyRequestCode;
import com.huiche.listener.FristPageListener_nearByFragment;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.CityChoiceUtils;
import com.huiche.utils.SetSizeUtils;
import com.huiche.utils.SharedPreferenceHelper;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.ViewPagerScroller;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NearByFragme extends BaseFragment implements OnClickListener,
        FristPageListener_nearByFragment, OnItemClickListener {
    /**
     * 碎片布局
     */
    private View contentView;
    /**
     * 碎片中的listview
     */
    private ListView listView;

    private Context mcontext;
    /***
     * listview 的头布局
     */
    private View headView;
    /***
     * 轮播图
     */
    private ViewPager viewPageHeadVeiw;
    /***
     * 碎片布局中的 加载餐饮美食， 休闲娱乐的 GridView。。。。
     */
    private GridView gridViewHeadView;
    /***
     * 加载信息位置信息的textview，位于listview头布局中
     */
    private TextView textHeadView;
    /***
     * 点击城市弹出 选择城市布局的 判断标记
     */
    private boolean ShowFlag = true;
    /**
     * viewpage的adapter布局集合
     */
    private List<View> list = new ArrayList<>();
    /**
     * 布局填充
     */
    private LayoutInflater inflater;
    /**
     * 存放gridview的adapter数据
     */
    private List<InfoGridView> infoList = new ArrayList<>();
    /**
     * gridview的图片
     */
    private int girdImage[] = {R.drawable.home_menu_food, R.drawable.home_menu_entertainment,
            R.drawable.home_menu_hotel, R.drawable.home_menu_beauty, R.drawable.home_menu_fitness,
            R.drawable.home_menu_financial, R.drawable.home_menu_recruitment, R.drawable.home_menu_insurance};
    /**
     * gridview的文字
     */
    private String gridText[] = {"美食", "娱乐", "酒店", "汽车美容", "健身美容",
            "金融街", "招聘", "保险"};
    /**
     * 标题布局
     */
    private LinearLayout titalbar;
    /**
     * 显示城市选择的textview (首页东莞那个)
     */
    private TextView cityName_titlebar;
    private List<String> listData = new ArrayList<>();
    private List<BusinessInfoAll> dataListItemEmpty = new ArrayList<>();
    /***
     * 显示选择城市的布局
     */
    private LinearLayout cityLayout_nearByFragment;
    /***
     * 显示选择城市的gridView
     */
    private GridView girdView_cityName_nearbyFragment;
    /***
     * 添加城市信息的 adapter
     */
    private Adapter_CitySelect_NearByFragmentFrist adapterGridview;
    /***
     * 用来调整框大小，根据屏幕大小
     */
    private LinearLayout cityAbout;
    /***
     * 点击跳转到 城市选择的Activity，更换按钮
     */
    private TextView moveToCityChoice;
    /***
     * 百度地图定位
     */
    public LocationClient mLocationClient = null;
    /**
     * 位置回调
     */
    public BDLocationListener myListener = new MyLocationListener();
    /***
     * 当前城市： XX市；点击城市后后显示的页面
     */
    private TextView curryCity;
    private LinearLayout ll_changeCity_main;
    private boolean refresh;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private LinearLayout ll_cityName_titleBar;
    private String locationCity;//定位的城市
    private boolean autoLocation;//是否为自动定位
    private int sizeType = 2;

    public NearByFragme() {
    }

    /***
     * 存放ProvinceInfo的集合(存放省份)
     */
    private List<ProvinceInfo> list_Province = new ArrayList<>();
    /***
     * 存放指定城市的数据
     */
    private List<String> listAssign = new ArrayList<>();
    /**
     * 当前定位的城市
     */
    public String cityCurryent;
    /**
     * 当前定位的省份
     */
    public String provinceCurryent;
    /**
     * 如果为true，则显示城市下方信息列表，listview数据，为false，则没有数据
     */
    private boolean showCityInloList;
    /**
     * 用来判断 用户是否是开启了 允许定位的，用户不允许后，百度地图会一直定位的,跳转到城市选择页面。
     */
    private int jumpCount = -1;
    /**
     * 定位后获取的纬度
     */
    private double myLatitude = -1.0;
    /**
     * 定位后获取的经度
     */
    private double myLongitude = -1.0;
    /**
     * 定位后获取的区域信息
     */
    private String area = "";
    /**
     * 每页显示数据行数
     */
    private int rows = 15;
    /**
     * 显示页数
     */
    private int page = 1;
    /**
     * 请求数据的时候，显示的布局，旋转的图片。LinearLayout，加载完数据后gone掉布局
     */
    private LinearLayout showInfo;
    /**
     * 旋转的imageview
     */
    private ImageView rotateImage;
    /**
     * 存放商家 列表的结合
     */
    private List<BusinessInfoAll> bussinessStrore_List = new ArrayList<>();
    /**
     * 首页 listview中每个Item中的数据的Adapter
     */
    private AdapterListview_NearByFirstPage adapterListview_NearByFirstPage;
    private PullToRefreshListView listViewPull;
    /**
     * 用来指定lisview的刷新数据后滑动的位置;
     */
    private int coutSelection = 0;
    /***
     * 是否 是加载当前的位置的数据，选择地区后改变area，用来判断 listview的下拉刷新。第一次的不带eara，以后 带eara；
     */
    private boolean changeDataFlag = true;
    private ImageView scanCode;
    /***
     * 定位是北京城市的时候 有区信息。
     */
    public String cityCurryentDirsbut;
    /**
     * listviw的headview中添加viewp和 点的父布局
     */
    private LinearLayout containFristHeadView;
    /**
     * ListPoint。存放点的集合
     */
    private List<ImageView> pointList = new ArrayList<>();
    /**
     * 判断请求省份集合后，时候有省份城市的信息信息。
     */
    protected boolean provinceListFlag = false;
    private final int STOP = 0;
    private final int RUNNING = 1;
    private List<PosterInfo> adList = new ArrayList<>();//首页广告列表信息
    private List<PosterInfo> buttomIconList = new ArrayList<>();//底部5个图标
    private List<PosterInfo> topIconList = new ArrayList<>();//首页8个图标


    TextView search;

    @Override
    public void findViews() {
        mcontext = getActivity();

        listViewPull = (PullToRefreshListView) contentView
                .findViewById(R.id.listView_nearByFragment_MainActivity);
        listViewPull.setMode(PullToRefreshBase.Mode.BOTH);
        listView = listViewPull.getRefreshableView();
        // myListView=listView.getRefreshableView();
        cityAbout = (LinearLayout) contentView
                .findViewById(R.id.cityAbout_nearByFragment);
        ll_changeCity_main = (LinearLayout) contentView
                .findViewById(R.id.ll_changeCity_main);
        cityLayout_nearByFragment = (LinearLayout) contentView
                .findViewById(R.id.cityLayout_nearByFragment);
        cityName_titlebar = (TextView) contentView
                .findViewById(R.id.cityName_titlebar);
        ll_cityName_titleBar = (LinearLayout) contentView
                .findViewById(R.id.ll_cityName_titleBar);
        moveToCityChoice = (TextView) contentView
                .findViewById(R.id.moveToCityChoice_nearByFragment);
        curryCity = (TextView) contentView
                .findViewById(R.id.curryCity_nearByFragment);
        girdView_cityName_nearbyFragment = (GridView) contentView
                .findViewById(R.id.girdView_cityName_nearbyFragment);
        scanCode = (ImageView) contentView.findViewById(R.id.imageRigth_titil_allScanCode);
        search = (TextView) contentView.findViewById(R.id.text_titil_all);
    }

    @SuppressLint("InlinedApi")
    @Override
    public void initData() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (width < 720) sizeType = 1;
        else if (width >= 720 && width < 1080) sizeType = 2;
        else sizeType = 3;
        initImageLoader();
//        requreADFromWeb(2);
        locate();
        SetSizeUtils.setSizeOnlyHeightOf(mcontext, cityAbout, 12);
        // listView.setMode(Mode.PULL_UP_TO_REFRESH);

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headView = inflater.inflate(R.layout.headview_nearby_fragment, null);
        initHeadView();
        listView.addHeaderView(headView);
        initListData();
        // getDetailAdress();
        listData.add("全城");
        adapterGridview = new Adapter_CitySelect_NearByFragmentFrist(
                getActivity(), listData, this);
        girdView_cityName_nearbyFragment.setAdapter(adapterGridview);
        adapterListview_NearByFirstPage = new AdapterListview_NearByFirstPage(
                getActivity(), bussinessStrore_List);
        listView.setAdapter(adapterListview_NearByFirstPage);
        girdView_cityName_nearbyFragment.setAdapter(adapterGridview);
    }

    /**
     * 请求首页的图标或广告图片
     */
    private void requreADFromWeb(final int type) {
        RequestParams params = new RequestParams();
        params.put("positionId", type);
        params.put("isPhoneType", sizeType);
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
                                if (type == 2) {
                                    adList.clear();
                                    adList = JSON.parseArray(array.toString(), PosterInfo.class);
                                    initViewPager();
                                } else if (type == 19) {
                                    topIconList.clear();
                                    //动态加载图标
                                    topIconList = JSON.parseArray(array.toString(), PosterInfo.class);
                                    if (topIconList.size() > 0) {
                                        infoList.clear();
                                        for (int i = 0; i < topIconList.size(); i++) {
                                            InfoGridView infoGridView = new InfoGridView();
                                            infoGridView.setUrl(topIconList.get(i).content);
                                            infoGridView.setInfo(gridText[i]);
                                            infoList.add(infoGridView);
                                        }
                                        GridView_HeadView adapter = new GridView_HeadView(
                                                getActivity(), infoList);
                                        gridViewHeadView.setAdapter(adapter);
                                    }
                                } else if (type == 20) {
                                    buttomIconList.clear();
                                    //动态加载底部5个图标
                                    buttomIconList = JSON.parseArray(array.toString(), PosterInfo.class);

                                }
                            }
                        }

                    }
                }

        );
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.shouye_beijt332)
                        // 加载中要显示图片
                .showImageForEmptyUri(R.drawable.shouye_beijt332)
                        // url为空要显示图片
                .showImageOnFail(R.drawable.shouye_beijt332)
                .cacheInMemory(true)
                        // 加载失败要显示图片
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
    }

    boolean oncehead = true;

    /**
     * 初始化轮播图数据
     */
    private void initViewPager() {
        pointList.clear();
        containFristHeadView.removeAllViews();
        //添加标示小圆点
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 30;
            imageView.setLayoutParams(layoutParams);
            containFristHeadView.addView(imageView);
            imageView.setImageDrawable(getActivity().getResources()
                    .getDrawable(R.drawable.point_select));
            pointList.add(imageView);
        }
        if (pointList.size() > 0)
            pointList.get(0).setSelected(true);
        viewPageHeadVeiw.setAdapter(new ViewPageAdaper_Poster(getActivity(), adList, imageLoader, options));
        //设置过渡切换时间
        ViewPagerScroller scroller = new ViewPagerScroller(context);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(viewPageHeadVeiw);//这个是设置切换过渡时间为1秒
        if (oncehead) {
            //定时循环播放
            handler.postDelayed(run, 3000);
            oncehead = false;
        }
        //触摸停止播放
        viewPageHeadVeiw.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacks(run);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        handler.removeCallbacks(run);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.postDelayed(run, 2000);
                        break;

                }
                return false;
            }
        });
        viewPageHeadVeiw.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {

                pointList.get(arg0 % pointList.size()).setSelected(true);
                for (int i = 0; i < pointList.size(); i++) {
                    if (i != (arg0 % pointList.size())) {
                        pointList.get(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /***
     * 请求首页ListView的数据
     */
    private void initListData() {


    }

    private Handler handler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            int a = viewPageHeadVeiw.getCurrentItem();
            viewPageHeadVeiw.setCurrentItem(a + 1);
            handler.postDelayed(this, 3000);
        }
    };

    /**
     * 初始化 headView
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("ClickableViewAccessibility")
    private void initHeadView() {

        viewPageHeadVeiw = (ViewPager) headView
                .findViewById(R.id.viewPageHeadVeiw_nearByFragment_MainActivity);

        //注意，首页轮播图给的比例是420x184
        WindowManager wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = (int) (width * 184) / 420;
        ViewGroup.LayoutParams para = viewPageHeadVeiw.getLayoutParams();//获取按钮的布局
        para.height = height;
        viewPageHeadVeiw.setLayoutParams(para);
        viewPageHeadVeiw.setBackgroundResource(R.drawable.shouye_beijt332);

        gridViewHeadView = (GridView) headView
                .findViewById(R.id.gridViewHeadView_nearByFragment_MainActivity);
        textHeadView = (TextView) headView
                .findViewById(R.id.textHeadView_nearByFragment_MainActivity);
        showInfo = (LinearLayout) headView
                .findViewById(R.id.showInfo_headview_nearByFragment);
        rotateImage = (ImageView) headView
                .findViewById(R.id.rotateImage_headview_nearByFragment);
        containFristHeadView = (LinearLayout) headView
                .findViewById(R.id.containFristHeadView_nearByFargment);
        Animation rotate = AnimationUtils.loadAnimation(getActivity(),
                R.anim.myrotate);
        rotateImage.setAnimation(rotate);
        SetSizeUtils.setSizeOnlyHeightOf(getActivity(), textHeadView, 15);
        SetSizeUtils.setSizeOnlyHeightOf(getActivity(), showInfo, 12);
        //获取图标
        requreADFromWeb(19);
        //默认图标
        for (int i = 0; i < gridText.length; i++) {
            InfoGridView infoGridView = new InfoGridView();
            infoGridView.setImage(girdImage[i]);
            infoGridView.setInfo(gridText[i]);
            infoList.add(infoGridView);
        }
        GridView_HeadView gridviewAdapter = new GridView_HeadView(
                getActivity(), infoList);
        gridViewHeadView.setAdapter(gridviewAdapter);
        listViewPull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                if (refreshView.isHeaderShown()) {
                    //下拉刷新
//                    bussinessStrore_List.clear();
                    refresh = true;
                    page = 1;
                    if (changeDataFlag) {
//                        chenckCityInfo(cityCurryent, "");
                        getBusinessStroreInfo("");
                    } else {
//                        chenckCityInfo(cityCurryent, area);
                        getBusinessStroreInfo(area);
                    }
                } else {
                    //上拉加载
                    page++;
                    if (changeDataFlag) {
                        chenckCityInfo(cityCurryent, "");
//                        getBusinessStroreInfo("");
                    } else {
//                        chenckCityInfo(cityCurryent, area);
                        getBusinessStroreInfo(area);


                    }
                }
            }
        });
    }

    @Override
    public void setListeners() {
        moveToCityChoice.setOnClickListener(this);
        ll_cityName_titleBar.setOnClickListener(this);
        scanCode.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        listViewPull.setOnScrollListener(new OnScrollListener() {
                                             @Override
                                             public void onScrollStateChanged(AbsListView view, int scrollState) {

                                             }

                                             @SuppressWarnings("deprecation")
                                             @Override
                                             public void onScroll(AbsListView view, int firstVisibleItem,
                                                                  int visibleItemCount, int totalItemCount) {
                                                 //选择城市显示则标题栏不渐变
                                                 if (cityLayout_nearByFragment.getVisibility() == View.GONE) {
                                                     if (firstVisibleItem == 1 || firstVisibleItem == 0) {
                                                         View views = listView.getChildAt(0);
                                                         if (null != views) {
                                                             int top = -views.getTop();
                                                             int headerHeight = viewPageHeadVeiw.getHeight() * 2 / 3;
                                                             //Log.i("top=", top + "");
                                                             //Log.i("headerHeight=", headerHeight + "");
                                                             if (top <= headerHeight && top >= 0) {
                                                                 // 获取当前位置占头布局高度的百分比
                                                                 float f = (float) top / (float) headerHeight;
                                                                 // Log.i("f=", f + "");
                                                                 if (f > 0.9) {
                                                                     titalbar.getBackground().setAlpha(255);
                                                                 } else {
                                                                     titalbar.getBackground().setAlpha(
                                                                             (int) (f * 255));
                                                                 }

                                                             }
                                                         }
                                                     } else {
                                                         titalbar.getBackground().setAlpha(255);
                                                     }
                                                 }
                                             }
                                         }

        );
        gridViewHeadView.setOnItemClickListener(new

                                                        OnItemClickListener() {

                                                            @SuppressWarnings("deprecation")
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view,
                                                                                    int position, long id) {


                                                                //购买保险
                                                                if (position == 4) {
                                                                    Intent intent = new Intent(getActivity(),
                                                                            InsuranceActivity.class);
                                                                    startActivity(intent);
                                                                    return;
                                                                }


                                                                //车主服务
                                                                if (position == 5) {
                                                                    Intent intent = new Intent(getActivity(),
                                                                            OilcardActivity.class);
                                                                    startActivity(intent);
                                                                    return;
                                                                }

                                                                //汽车美容
                                                                if (position == 3) {
                                                                    Intent intent = new Intent(getActivity(),
                                                                            CarBeautifulActivity.class);
                                                                    startActivity(intent);
                                                                    return;
                                                                }


                                                                if (position <= 5) {
                                                                    String info = infoList.get(position).getInfo();
                                                                    Intent intent = new Intent(getActivity(),
                                                                            ClassifyOfActivity.class);
                                                                    intent.putExtra("title", info);
                                                                    startActivity(intent);
                                                                }
                                                                if (position == 7) {
                                                                    String info = infoList.get(position).getInfo();
                                                                    Intent intent = new Intent(getActivity(),
                                                                            MoreProductActivity.class);
                                                                    intent.putExtra("title", info);
                                                                    startActivity(intent);
                                                                }
                                                                //超值
                                                                if (position == 6) {
                                                                    Intent intent = new Intent(getActivity(),
                                                                            HotProductActivityTest.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        }

        );
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initViews() {


    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.nearby_fragment_mainactivity,
                container, false);
        titalbar = (LinearLayout) contentView
                .findViewById(R.id.titalbar_nearByFragment_MainActivity);
        titalbar.getBackground().setAlpha(0);
        return contentView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_cityName_titleBar:
                if (ShowFlag) {
                    cityLayout_nearByFragment.setVisibility(View.VISIBLE);
                    titalbar.getBackground().setAlpha(255);
                    ShowFlag = false;
                    listView.setEnabled(false);
                } else {
                    cityLayout_nearByFragment.setVisibility(View.GONE);
                    listView.setEnabled(true);
                    ShowFlag = true;
                }

                break;
            /***
             * 跳转 到城市选择界面
             */
            case R.id.moveToCityChoice_nearByFragment:
                Intent intent = new Intent(mcontext, CityChoice.class);
                intent.putExtra("city", cityName_titlebar.getText() + "");
                startActivityForResult(intent, MyRequestCode.CityChoice_RequesCode);
                cityLayout_nearByFragment.setVisibility(View.GONE);
                listView.setEnabled(true);
                titalbar.getBackground().setAlpha(255);
                ShowFlag = true;
                break;
            case R.id.imageRigth_titil_allScanCode:
//                Intent intents = new Intent();
//                intents.setClass(getActivity(), CaptureActivity.class);
//                startActivity(intents);
                Intent intents = new Intent();
                intents.setClass(getActivity(), MoreActivity.class);
                startActivity(intents);


                break;
            default:
                break;
        }
    }

    /***
     * 监听的回调
     *
     * @author Administrator
     */
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {


            if (arg0 != null) {
                if (arg0.getCity() == null) {
                    if (jumpCount == -1) {
                        Intent i = new Intent(getActivity(), CityChoice.class);
                        // 无法定位后跳转过去
                        i.putExtra("error", "无法定位");
                        startActivityForResult(i,
                                MyRequestCode.CityChoice_RequesCode);
                        titalbar.getBackground().setAlpha(255);
                        jumpCount = 1;
                    }
                }
                SharedPreferenceHelper.setCityRecentInfo(mcontext,
                        arg0.getCity());
                cityCurryent = arg0.getCity();
                // String arr=arg0.getDistrict();
                provinceCurryent = arg0.getProvince().split("省")[0];
                boolean as = mLocationClient.isStarted();
                mLocationClient.stop();
                myLatitude = arg0.getLatitude();
                myLongitude = arg0.getLongitude();
                /********************************************************/
//                requreADFromWeb(2);
                /***********************************************************/
                area = arg0.getDistrict();
                getDetailAdress();
                autoLocation = true;
                chenckCityInfo(arg0.getCity(), "");
            } else {
                mLocationClient.stop();
                /********************************************************/
                //requreADFromWeb(2);
                /***********************************************************/

            }
            String[] province = arg0.getProvince().split("省");
            if (arg0.getCity() == null) {
                cityName_titlebar.setText("东莞");
                curryCity.setText("当前城市 :" + "东莞");
            } else {
                String str = arg0.getCity();
                String[] arr = str.split("市");
                cityName_titlebar.setText(arr[0]);
                curryCity.setText("当前城市 :" + arr[0]);
                if (arg0.hasAddr()) {
                    textHeadView.setText("当前位置 :" + arg0.getDistrict() + arg0.getStreet() + arg0.getStreetNumber());
                    requreADFromWeb(2);
                }
//				textHeadView.setText("当前位置 :" + arg0.getDistrict());
            }

        }

    }

    /**
     * GridView回调接口，改变当前的城市的选择。
     */

    /**
     * 获取商家列表信息
     */
    public void getBusinessStroreInfo(String src) {

        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        if (src.equals("")) {
            if (myLatitude != -1.0 && myLongitude != -1.0 && !area.equals("")) {
                params.put("myLatitude", myLatitude);
                params.put("myLongitude", myLongitude);
            }
        } else {
            params.put("area", src);
//            params.put("city", locationCity);
        }
        final BufferCircleDialog dialog = new BufferCircleDialog(mcontext);
        dialog.show("", true, null);
        AsyncHttp.posts(HttpConstantHao.GET_BUSINESSSTORE_INFO, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        dialog.dialogcancel();
                        showInfo.setVisibility(View.GONE);
                        ToastUtils.ToastShowLong(getActivity(), "数据加载失败，请检查网络");
                        listViewPull.onRefreshComplete();
                    }

//                    @Override
//                    public void onFinish() {
//                        dialog.dialogcancel();
//
//                        super.onFinish();
//                        listViewPull.onRefreshComplete();
//                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

                        super.onSuccess(statusCode, headers, response);
//                        StringBuffer sb = new StringBuffer(response.toString());
                        if (dialog.isShowDialog())
                            dialog.dialogcancel();
                        dataListItemEmpty.clear();
                        try {
                            if (response.getString("status").equals("0")) {
                                JSONArray rows = response.getJSONArray("rows");
                                if (rows.length() > 0) {
                                    for (int i = 0; i < rows.length(); i++) {
                                        BusinessInfoAll all = null;
                                        JSONObject obj = rows.getJSONObject(i);
                                        ProvinceInfo info = null;
                                        all = JSON.parseObject(obj.toString(),
                                                BusinessInfoAll.class);
                                        dataListItemEmpty.add(all);
//                                    Log.d("zane13", all.getIsDouble() + "");
                                    }
                                    showInfo.setVisibility(View.GONE);
                                    if (refresh) {
                                        bussinessStrore_List.clear();
                                        refresh = false;
                                    }
                                    bussinessStrore_List.addAll(dataListItemEmpty);
                                    adapterListview_NearByFirstPage.notifyDataSetChanged();
                                } else {
                                    showInfo.setVisibility(View.GONE);
                                    ToastUtils.ToastShowLong(mcontext, "没有更多数据");
                                }

                            } else {
                                showInfo.setVisibility(View.GONE);
                                ToastUtils.ToastShowLong(getActivity(),
                                        response.getString("msg") + "");
                            }
                            listViewPull.onRefreshComplete();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    /***
     * 初始化百度地图定位；
     */
    public void locate() {
        mLocationClient = new LocationClient(mcontext);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
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

    /***
     * 获取当前城市的 内部信息
     */
    public void getDetailAdress() {
        AsyncHttp.get("http://test.51ujf.cn/js/province_bas.json",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONArray response) {

                        super.onSuccess(statusCode, headers, response);
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                ProvinceInfo info = null;
                                info = JSON.parseObject(obj.toString(),
                                        ProvinceInfo.class);
                                int a = info.getCityList().size();
                                list_Province.add(info);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (list_Province.size() > 0) {
                            provinceListFlag = true;
                        }
                        getCityFromCityDetail(provinceListFlag);
                        // 根据获取的城市获取 省份
                        // CityChoiceUtils choiceUtils = new CityChoiceUtils();
                        // Log.d("zane",list_Province.get(3).getCityList().get(0).getAreaList().get(2)+"");

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONArray errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        ToastUtils.ToastShowLong(getActivity(), "数据加载失败，请检查网络");
                    }
                });
    }

    /**
     * 检测 当前定位的城市，是否有信息,有的话才可以显示商家列表
     */
    public void chenckCityInfo(final String city, final String src) {
        showCityInloList = false;
        RequestParams params = new RequestParams();
        if (city != null) {
            if (city.contains("市")) {
                locationCity = city.replaceAll("市", "");
            }
        }
        params.put("locationCity", locationCity);
        AsyncHttp.posts(HttpConstantHao.LOCATE_THE_CURRENT_CITY, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

//                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.getString("status").equals("0")) {
                                showCityInloList = true;
                                if (showCityInloList) {
                                    getBusinessStroreInfo(src);
                                }
                            } else {
                                ToastUtils.ToastShowLong(getActivity(),
                                        response.getString("msg") + "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable,
//                                errorResponse);
                        ToastUtils.ToastShowLong(getActivity(), "无当前定位城市，请更换城市");
                    }

                    @Override
                    public void onFinish() {

                        super.onFinish();
                    }
                });
    }

    /**
     * 从adatper 中传过来的数据 接口回调 修改当前选择的城市 ，刷新数据
     */
    @Override
    public void selectData(String str, int position) {

        adapterGridview.changeBackGroud(position);
        if (null != cityCurryent) {
            page = 1;
            coutSelection = 0;
            cityLayout_nearByFragment.setVisibility(View.GONE);
            listView.setEnabled(true);
            ShowFlag = true;
            if (position == 0) {
                textHeadView.setText("当前位置 :" + cityCurryent + str);
                area = "";
            } else {
                textHeadView.setText("当前位置 :" + str);
                area = str;
            }
//            bussinessStrore_List.clear();
//           chenckCityInfo(cityCurryent, area);
            changeDataFlag = false;
            refresh = true;
            getBusinessStroreInfo(area);

        }

    }

    /**
     * 从 城市选择界面传递过来的城市，进行相关操作
     *
     * @param city
     */
    public void changLocalCity(String city) {
        autoLocation = false;
        /*
      判断控件是否透明
     */
        boolean whetherLunceyFlag = true;
        if (list_Province.size() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requreADFromWeb(2);
                }
            }, 1500);


            // 已经通过网络获取了数据，不是空给的
            CityChoiceUtils choiceUtils = new CityChoiceUtils();
            //CityChoiceUtils.checkInfo(list, province, listAssign, i);
            String aa = choiceUtils.getCityList(list_Province, city + "市");
            if (!aa.equals("")) {
                // cityCurryent = arg0.getCity();
                // // String arr=arg0.getDistrict();
                // provinceCurryent = arg0.getProvince().split("省")[0];
                provinceCurryent = aa;
                cityCurryent = city + "市";
                if (null != cityCurryent) {
                    page = 1;
                    coutSelection = 0;
                    ShowFlag = true;
                    textHeadView.setText("当前位置 :" + city);
                    curryCity.setText("当前城市:" + city);
                    cityName_titlebar.setText(city);
                    area = "";
                    bussinessStrore_List.clear();
                    changeDataFlag = false;
                    getCityFromCityDetail(provinceListFlag);
//                    chenckCityInfo(cityCurryent, area);
                    chenckCityInfo(cityCurryent, "");
                    ToastUtils.ToastShowShort(getActivity(), city);
                }
            } else {
                ToastUtils.ToastShowShort(getActivity(), "所选取的城市没有商家信息，请更换城市");
            }
        }

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        mLocationClient.stop();
        mLocationClient = null;
    }

    /**
     * 根据所有的城市列表，获取里面城市里面的地域信息。
     */
    private void getCityFromCityDetail(boolean flag) {
        if (!flag) {
            return;
        }
        if (cityCurryent != null && provinceCurryent != null) {
            int cc = list_Province.size();
            for (int i = 0; i < list_Province.size(); i++) {
                String province = list_Province.get(i).getName() + "";
                if (province.equals(provinceCurryent)) {
                    if (CityChoiceUtils.checkInfo(list_Province, province,
                            listAssign, i)) {
                    } else {
                        listAssign.clear();
                        for (int j = 0; j < list_Province.get(i).getCityList()
                                .size(); j++) {
                            String city = list_Province.get(i).getCityList()
                                    .get(j).getName()
                                    + "";
                            if (city.equals(cityCurryent)) {
                                listAssign.addAll(list_Province.get(i)
                                        .getCityList().get(j).getAreaList());
                            }
                        }
                        break;
                    }

                }
            }
        }
        adapterGridview.reflishData(listAssign);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        position = position - 2;

        if (position >= 0) {
            BusinessInfoAll businessInfoAll = bussinessStrore_List.get(position);

            String businessStoreId = businessInfoAll.getId();
            Intent intent = new Intent(getActivity(), BusinessDetailActivity.class);
            intent.putExtra("businessStoreId", businessStoreId);

            if (!businessInfoAll.isHavePay()) {
                intent.putExtra("empty", "1");
            } else {
                intent.putExtra("empty", "2");
            }
            startActivity(intent);
        }
    }
}
