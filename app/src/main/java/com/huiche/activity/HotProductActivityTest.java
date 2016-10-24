package com.huiche.activity;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.adapter.DataAdapter_HotProduct;
import com.huiche.adapter.ProductGridViewAdapter;
import com.huiche.adapter.PtypeCategoryAdapter;
import com.huiche.adapter.RecyclerAdapter_CityRange;
import com.huiche.adapter.RecyclerAdapter_HotProduct;
import com.huiche.adapter.StypeCategoryAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.ProductCategory;
import com.huiche.bean.ProductInfo;
import com.huiche.bean.SubCategory;
import com.huiche.constant.HttpConstant;
import com.huiche.lib.lib.LRecyclerView.progressindicator.AVLoadingIndicatorView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.HeaderSpanSizeLookup;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.LRecyclerView.recyclerview.ProgressStyle;
import com.huiche.lib.lib.LRecyclerView.util.RecyclerViewStateUtils;
import com.huiche.lib.lib.LRecyclerView.view.LoadingFooter;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.LoadImageUtil;
import com.huiche.utils.SetSizeUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.AddProductPopwindow;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 超值商品
 *
 * @author Administrator
 */
public  class HotProductActivityTest extends BaseActivity implements
        OnClickListener, CheckBox.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {
    private Context mContext;
    private TextView tv_titleText;
    private ImageButton imb_titleBack;
    private LinearLayout ll_hotProduct;
    private GridView mGridView;
    private ProductGridViewAdapter mAdapter;
    private final int LOAD_MORE = 0;
    private int page = 1;
    private int rows = 10;//10
    private final String TAG = "HotProductActivityTest";
    private List<ProductInfo> allInfos = new ArrayList<ProductInfo>();
    private List<ProductInfo> tmepInfos = new ArrayList<ProductInfo>();
    private LRecyclerView mRecyclerView;
    private LoadImageUtil loadImageUtil;
    private RecyclerAdapter_HotProduct recyclerViewAdapter;
    private GridLayoutManager gridLayoutManager;
    private int firstVisiableItem = -1;
    private CheckBox firstCB;
    private CheckBox secondCB;
    private CheckBox thirdCB;
    private LinearLayout ll_beforeTwo_select;
    private LinearLayout ll_orderBy;
    private LinearLayout ll_recyclerView;
    private List<ProductCategory> productCategoryList = new ArrayList<>();
    public List<String> pNameList = new ArrayList<>();
    public List<String> sNameList = new ArrayList<>();
    private ListView lv_ptype_classify;
    private ListView lv_stype_classify;
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private List<String> rangeList = new ArrayList<>();
    private String firstCheckStr = "";//第一个筛选
    private RecyclerView recyclerView_cityRange;
    private RadioGroup radio_orderBy;
    private RadioButton radioBtn_priceOrderBy;//价格排序
    private RadioButton radioBtn_goodOrderBy;//人气排序
    private RadioButton radioBtn_nearByOrderBy;//距离排序
    private View view_shadow_category;
    private View view_shadow_cityRange;
    private View view_shadow_orderBy;
    private DataAdapter_HotProduct dataAdapter;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isRefresh;
    private int mCurrentCounter = 0;
    private int TOTAL_COUNTER = 100;
    private int SHOW_COUNT;//每页显示的数量
    private AVLoadingIndicatorView avi_loading;
    //    private int addCount = 1;//添加商品数量
    private ProductInfo productInfo;//要添加的商品
    private AddProductPopwindow addProductPopwindow;
//    private LinearLayout ll_content_hotProduct_all;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_hot_product_test);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
//        ll_content_hotProduct_all = (LinearLayout) findViewById(R.id.ll_content_hotProduct_all);
        avi_loading = (AVLoadingIndicatorView) findViewById(R.id.avi_loading);
        firstCB = (CheckBox) findViewById(R.id.firstCB_classifyOf_hotProduct);
        secondCB = (CheckBox) findViewById(R.id.secondCB_classifyOf_hotProduct);
        thirdCB = (CheckBox) findViewById(R.id.thirdCB_classifyOf_hotProduct);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        radio_orderBy = (RadioGroup) findViewById(R.id.radioGroup_orderBy);
        radioBtn_priceOrderBy = (RadioButton) findViewById(R.id.radioBtn_priceOrderBy);
        radioBtn_goodOrderBy = (RadioButton) findViewById(R.id.radioBtn_goodOrderBy);
        radioBtn_nearByOrderBy = (RadioButton) findViewById(R.id.radioBtn_nearByOrderBy);
        mRecyclerView = (LRecyclerView) findViewById(R.id.recyclerView_hotProduct);
        ll_hotProduct = (LinearLayout) findViewById(R.id.ll_classifyOf_hotProduct);
        ll_beforeTwo_select = (LinearLayout) findViewById(R.id.ll_beforeTwo_select);
        ll_orderBy = (LinearLayout) findViewById(R.id.ll_orderBy);
        ll_recyclerView = (LinearLayout) findViewById(R.id.ll_recyclerView);
        lv_ptype_classify = (ListView) findViewById(R.id.lv_ptype_classify);
        lv_stype_classify = (ListView) findViewById(R.id.lv_stype_classify);
        view_shadow_cityRange = findViewById(R.id.view_shadow_cityRange);
        view_shadow_category = findViewById(R.id.view_shadow_category);
        view_shadow_orderBy = findViewById(R.id.view_shadow_orderBy);
        recyclerView_cityRange = (RecyclerView) findViewById(R.id.recyclerView_cityRange);
        recyclerView_cityRange.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView_cityRange.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initData() {
        tv_titleText.setText("超值");
        SetSizeUtils.setSizeOnlyHeightOf(mContext, ll_hotProduct, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_priceOrderBy, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_goodOrderBy, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_nearByOrderBy, 11);
        loadImageUtil = new LoadImageUtil();
        initRecyclerView();
        getProductCategory();
        getProductRange();
        getProduct(page, rows);
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        dataAdapter = new DataAdapter_HotProduct(mContext, loadImageUtil, false);
//        dataAdapter = new DataAdapter_HotProduct(mContext, loadImageUtil, allInfos);
//        mRecyclerViewAdapter = new LRecyclerViewAdapter(mContext, dataAdapter);
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((LRecyclerViewAdapter) mRecyclerView.getAdapter(), gridLayoutManager.getSpanCount()));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //设置样式必须在调用setAdapter后调用否则不生效
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setRefreshing(true);
//        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
//            @Override
//            public void onRefresh() {
//                isRefresh = true;
//                tmepInfos.clear();
//                page = 1;
//                getProduct(page, rows);
//            }
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
//                RecyclerViewStateUtils.setFooterViewState(HotProductActivityTest.this, mRecyclerView, rows, LoadingFooter.State.Loading, null);
//                page++;
//                getProduct(page, rows);
////
//            }
//
//            @Override
//            public void onScrolled(int distanceX, int distanceY) {
//
//            }
//        });

        //点击添加按钮
        dataAdapter.setAddProductListener(new AddToShoppingCartListener() {
            @Override
            public void addProduct(Object product) {
                if (product instanceof ProductInfo) {
                    productInfo = ((ProductInfo) product);
                    if (addProductPopwindow == null)
                        addProductPopwindow = new AddProductPopwindow(mContext);
                    addProductPopwindow.show(findViewById(R.id.ll_content_hotProduct_all), productInfo);

                }
            }
        });
    }
    @Override
    public void setListeners() {
        firstCB.setOnCheckedChangeListener(this);
        secondCB.setOnCheckedChangeListener(this);
        thirdCB.setOnCheckedChangeListener(this);
        radio_orderBy.setOnCheckedChangeListener(this);
        imb_titleBack.setOnClickListener(this);
        view_shadow_orderBy.setOnTouchListener(this);
        view_shadow_category.setOnTouchListener(this);
        view_shadow_cityRange.setOnTouchListener(this);
        //一级菜单item点击事件
        lv_ptype_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PtypeCategoryAdapter adapter = (PtypeCategoryAdapter) adapterView.getAdapter();
                adapter.setSelection(position);
                adapter.notifyDataSetInvalidated();
                subCategoryList = productCategoryList.get(position).sName;
                setStypeList();
            }
        });
    }

    /**
     * 获取超值商品列表
     */
    private void getProduct(int page, int rows) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        final String firstStr = firstCB.getText() + "";
        String secondStr = secondCB.getText() + "";
        String thirdStr = thirdCB.getText() + "";
        if (!firstStr.equals("分类")) {
            params.put("stype", firstStr);//店铺类别
        }
        if (radioBtn_goodOrderBy.isChecked()) {
            params.put("isHot", true);
            params.put("isPriceSort", false);
        }
        else if (radioBtn_priceOrderBy.isChecked()) {
            params.put("isHot", false);
            params.put("isPriceSort", true);
        }
        //默认排序
        else  {
            params.put("isHot", false);
            params.put("isPriceSort", false);
        }
        if (!secondStr.equals("全城")) {
            params.put("area", secondStr);
        }
        if (!MyApplication.token.equals("")) {
            params.put("token", MyApplication.token);
        }
        AsyncHttp.posts(HttpConstant.GET_HOT_PRODUCT, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                avi_loading.setVisibility(View.GONE);
                ToastUtils.ToastShowShort(mContext, "连接超时，请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

//                super.onSuccess(statusCode, headers, response);
                avi_loading.setVisibility(View.GONE);
//                Log.i(TAG, response.toString());
                String status = response.optString("status");
                String msg = response.optString("msg");
                String rows = response.optString("rows");
                if (status.equals("0")) {
                    tmepInfos = (ArrayList<ProductInfo>) JSON.parseArray(rows, ProductInfo.class);
                    if (tmepInfos.size() > 0) {
                        if (isRefresh) {
                            dataAdapter.clear();
                            mCurrentCounter = 0;
                            allInfos.clear();
                        }
                        dataAdapter.addAll(tmepInfos);
                        mCurrentCounter += tmepInfos.size();
                        if (isRefresh) {
                            isRefresh = false;
                            mRecyclerView.refreshComplete();
                            mRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                        }
                    } else {
                        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
                        ToastUtils.ToastShowShort(mContext, msg);
                    }
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb_titleBack:
                finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.firstCB_classifyOf_hotProduct:
                if (firstCB.isChecked()) {
                    thirdCB.setChecked(false);
                    secondCB.setChecked(false);
                    if (productCategoryList.size() == 0) {
                        getProductCategory();
                    } else {
                        setStypeList();
                        showSelectList();
                    }
                }
                break;
            case R.id.secondCB_classifyOf_hotProduct:
                if (secondCB.isChecked()) {
                    thirdCB.setChecked(false);
                    firstCB.setChecked(false);
                    if (rangeList.size() == 0) {
                        getProductRange();
                    }
                }
                showSelectList();
                break;
            case R.id.thirdCB_classifyOf_hotProduct:
                if (thirdCB.isChecked()) {
                    firstCB.setChecked(false);
                    secondCB.setChecked(false);
                }
                showSelectList();
                break;
        }
    }

    /**
     * 筛选列表
     */
    private void showSelectList() {
        if (firstCB.isChecked()) {
            ll_beforeTwo_select.setVisibility(View.VISIBLE);
            ll_orderBy.setVisibility(View.GONE);
            ll_recyclerView.setVisibility(View.GONE);
        } else {
            ll_beforeTwo_select.setVisibility(View.GONE);
        }
        if (secondCB.isChecked()) {
            ll_recyclerView.setVisibility(View.VISIBLE);
            ll_beforeTwo_select.setVisibility(View.GONE);
            ll_orderBy.setVisibility(View.GONE);
        } else {
            ll_recyclerView.setVisibility(View.GONE);
        }
        if (thirdCB.isChecked()) {
            ll_recyclerView.setVisibility(View.GONE);
            ll_beforeTwo_select.setVisibility(View.GONE);
            ll_orderBy.setVisibility(View.VISIBLE);
        } else {
            ll_orderBy.setVisibility(View.GONE);
        }
        if (!firstCB.isChecked() && !secondCB.isChecked() && !thirdCB.isChecked()) {

        }

    }

    /**
     * 获取行业类型
     */
    private void getProductCategory() {
        RequestParams parmas = new RequestParams();
        AsyncHttp.posts(HttpConstant.GET_PRODUCT_CATEGORY, parmas, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "获取数据失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String msg = response.optString("msg");
                String status = response.optString("status");
                if (status.equals("0")) {
                    String json = response.optString("rows");
                    //解析数据
                    productCategoryList = JSON.parseArray(json, ProductCategory.class);
                    setSelectListAdapter();
                } else {
                    if (!msg.equals("")) {
                        ToastUtils.ToastShowShort(mContext, msg);

                    }
                }
            }
        });

    }

    /**
     * 设置行业类型数据
     */
    private void setSelectListAdapter() {
        if (productCategoryList != null) {
            if (productCategoryList.size() > 0) {
                for (int i = 0; i < productCategoryList.size(); i++) {
                    String pName = productCategoryList.get(i).pName;
                    pNameList.add(pName);
                }
                lv_ptype_classify.setAdapter(new PtypeCategoryAdapter(mContext, pNameList));
                //设置二级菜单高度跟一级菜单高度一致,每个item为1/12 displayHeight(已在adapter设置)
                SetSizeUtils.setSizeOnlyHeightOf(mContext, lv_stype_classify, 12, pNameList.size());
                //默认选择第一项
//                lv_ptype_classify.setSelection(0);
                subCategoryList = productCategoryList.get(0).sName;
                setStypeList();
            }
        }
    }

    /**
     * 设置二级类别数据
     */
    public void setStypeList() {
        StypeCategoryAdapter stypeCategoryAdapter = new StypeCategoryAdapter(mContext, subCategoryList, firstCheckStr);
        lv_stype_classify.setAdapter(stypeCategoryAdapter);
        showSelectList();
        //二级菜单item点击事件
        stypeCategoryAdapter.setOnItemCheckListener(new StypeCategoryAdapter.onItemCheckListener() {
            @Override
            public void onItemChecked(int position) {
                firstCB.setText(subCategoryList.get(position).name);
                firstCB.setChecked(false);
                ll_beforeTwo_select.setVisibility(View.GONE);
                firstCheckStr = subCategoryList.get(position).name;
                //请求网络筛选
                //之前的数据需要清空
                tmepInfos.clear();
                page = 1;
                isRefresh = true;
                getProduct(page, rows);
            }
        });
    }

    /**
     * 获取商圈
     */
    private void getProductRange() {
        RequestParams params = new RequestParams();
        AsyncHttp.posts(HttpConstant.GET_CITY_RANGE, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                String msg = response.optString("msg");
                String status = response.optString("status");
                JSONArray array = response.optJSONArray("rows");
//                Log.i("RANGE", response.toString());
                if (status.equals("0")) {
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            rangeList.add(array.optString(i) + "");
                        }
                        RecyclerAdapter_CityRange adapter_cityRange = new RecyclerAdapter_CityRange(mContext, rangeList);
                        recyclerView_cityRange.setAdapter(adapter_cityRange);
                        showSelectList();
                        adapter_cityRange.setOnItemClickListener(new RecyclerAdapter_CityRange.onItemClickListener() {
                            @Override
                            public void selectCityRange(int position) {
                                secondCB.setText(rangeList.get(position));
                                secondCB.setChecked(false);
                                page = 1;
                                isRefresh = true;
                                ll_recyclerView.setVisibility(View.GONE);
                                //请求数据前清空原数据
                                tmepInfos.clear();
                                getProduct(page, rows);
                            }
                        });

                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //筛选子类
            case R.id.radioBtn_priceOrderBy:
                thirdCB.setText(radioBtn_priceOrderBy.getText() + "");
                thirdCB.setChecked(true);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                isRefresh = true;
                tmepInfos.clear();
                getProduct(page, rows);
                //
                break;
            case R.id.radioBtn_goodOrderBy:
                thirdCB.setText(radioBtn_goodOrderBy.getText() + "");
                thirdCB.setChecked(true);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                isRefresh = true;
                tmepInfos.clear();
                getProduct(page, rows);
                break;
            case R.id.radioBtn_nearByOrderBy:
                thirdCB.setText(radioBtn_nearByOrderBy.getText() + "");
                thirdCB.setChecked(true);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                isRefresh = true;
                tmepInfos.clear();
                getProduct(page, rows);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.view_shadow_category:
                ll_beforeTwo_select.setVisibility(View.GONE);
                firstCB.setChecked(false);
                break;
            case R.id.view_shadow_cityRange:
                ll_recyclerView.setVisibility(View.GONE);
                secondCB.setChecked(false);
                break;
            case R.id.view_shadow_orderBy:
                ll_orderBy.setVisibility(View.GONE);
                thirdCB.setChecked(false);
                break;
        }
        return false;
    }
}
