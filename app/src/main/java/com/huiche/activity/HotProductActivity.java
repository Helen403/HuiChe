package com.huiche.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.huiche.adapter.ProductGridViewAdapter;
import com.huiche.adapter.PtypeCategoryAdapter;
import com.huiche.adapter.RecyclerAdapter_CityRange;
import com.huiche.adapter.RecyclerAdapter_HotProduct;
import com.huiche.adapter.StypeCategoryAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.bean.ProductCategory;
import com.huiche.bean.ProductInfo;
import com.huiche.bean.SubCategory;
import com.huiche.constant.HttpConstant;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.LoadImageUtil;
import com.huiche.utils.SetSizeUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
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
 * 没在使用
 */
public  class HotProductActivity extends BaseActivity implements
        OnClickListener, AddToShoppingCartListener, SwipeRefreshLayout.OnRefreshListener, CheckBox.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {
    private Context mContext;
    private TextView tv_titleText;
    private ImageButton imb_titleBack;
    private LinearLayout ll_hotProduct;
    private GridView mGridView;
    private ProductGridViewAdapter mAdapter;
    private final int LOAD_MORE = 0;
    private int page = 1;
    private int rows = 10;
    private final String TAG = "HotProductActivity";
    private List<ProductInfo> allInfos = new ArrayList<ProductInfo>();
    private List<ProductInfo> tmepInfos = new ArrayList<ProductInfo>();
    private RecyclerView mRecyclerView;
    private LoadImageUtil loadImageUtil;
    private RecyclerAdapter_HotProduct recyclerViewAdapter;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshWidget;
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

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_hot_product);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        firstCB = (CheckBox) findViewById(R.id.firstCB_classifyOf_hotProduct);
        secondCB = (CheckBox) findViewById(R.id.secondCB_classifyOf_hotProduct);
        thirdCB = (CheckBox) findViewById(R.id.thirdCB_classifyOf_hotProduct);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_hotProduct);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        radio_orderBy = (RadioGroup) findViewById(R.id.radioGroup_orderBy);
        radioBtn_priceOrderBy = (RadioButton) findViewById(R.id.radioBtn_priceOrderBy);
        radioBtn_goodOrderBy = (RadioButton) findViewById(R.id.radioBtn_goodOrderBy);
        radioBtn_nearByOrderBy = (RadioButton) findViewById(R.id.radioBtn_nearByOrderBy);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_hotProduct);
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
        //
        recyclerView_cityRange.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView_cityRange.setItemAnimator(new DefaultItemAnimator());
        //进度条的颜色变化，最多可以设置4种颜色
        mSwipeRefreshWidget.setColorSchemeResources(R.color.text_color_sky_blue, R.color.white);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
//        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));

    }

    @Override
    public void initData() {
        tv_titleText.setText("超值");
        SetSizeUtils.setSizeOnlyHeightOf(mContext, ll_hotProduct, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_priceOrderBy, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_goodOrderBy, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_nearByOrderBy, 11);
        loadImageUtil = new LoadImageUtil();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAdapter = new RecyclerAdapter_HotProduct(mContext, allInfos, loadImageUtil, mRecyclerView);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        getProduct(page, rows, false, true);
        getProductCategory(false);
        getProductRange(false);
    }

    @Override
    public void setListeners() {
        mSwipeRefreshWidget.setOnRefreshListener(this);
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

        if (recyclerViewAdapter != null) {
            //点击事件
            recyclerViewAdapter.setOnItemClickListener(new RecyclerAdapter_HotProduct.onItemClickListener() {
                @Override
                public void addProduct(int position) {
                    ToastUtils.ToastShowShort(mContext, position + "");
//                    recyclerViewAdapter.addOne(position);
                }

                @Override
                public void removeProduct(int position) {
                    ToastUtils.ToastShowShort(mContext, position + "");
//                    recyclerViewAdapter.removeOne(position);
                }
            });
        }
        //滑动监听,不能用setOnScorllListener（）
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if (lastPosition >= gridLayoutManager.getItemCount() - 1) {
                        //加载更多
                        page++;
                        getProduct(page, rows, true, false);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.i("dx***", dx + "");
//                Log.i("dy***", dy + "");
                firstVisiableItem = gridLayoutManager.findFirstVisibleItemPosition();
            }
        });

    }

    /**
     * 获取超值商品列表
     */
    private void getProduct(final int page, int rows, final boolean loadMore, boolean showDialog) {
        //缓冲小菊花
        final BufferCircleDialog cbufferCircleDialog=new BufferCircleDialog(HotProductActivity.this);

        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        final String firstStr = firstCB.getText() + "";
        String secondStr = secondCB.getText() + "";
        String thirdStr = thirdCB.getText() + "";
        if (!firstStr.equals("分类")) {
            params.put("stype", firstStr);//店铺类别
        }
        if (radioBtn_nearByOrderBy.isChecked()) {

        }
        if (radioBtn_goodOrderBy.isChecked()) {
            params.put("isHot", true);
        }
        if (radioBtn_priceOrderBy.isChecked()) {
            params.put("isPriceSort", true);
        }
        if (!secondStr.equals("全城")) {
            params.put("area", secondStr);
        }
//		params.put("area", area);//商圈
//		params.put("isHot", isHot);//是否按热门排序
//		params.put("isPriceSort", isPriceSort);//是否按价格排序
//		params.put("ptype", ptype);//商品类别
//		params.put("stype", stype);//店铺类别
        if (showDialog) {
            cbufferCircleDialog.show( "正在加载", true, null);
        }
        AsyncHttp.posts(HttpConstant.GET_HOT_PRODUCT, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "数据加载失败，请检查网络");
                if (cbufferCircleDialog.isShowDialog()) {
                    cbufferCircleDialog.dialogcancel();
                }
                mSwipeRefreshWidget.setRefreshing(false);
                recyclerViewAdapter.finishLoadMore();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                Log.i("sb", response.toString());
                if (cbufferCircleDialog.isShowDialog()) {
                    cbufferCircleDialog.dialogcancel();
                }
//				Log.i(TAG,response.toString());
                String status = response.optString("status");
                String msg = response.optString("msg");
                String rows = response.optString("rows");
//                tmepInfos.clear();
                if (status.equals("0")) {
                    if (!rows.equals("")) {
                        tmepInfos = (ArrayList<ProductInfo>) JSON.parseArray(rows, ProductInfo.class);
                        if (tmepInfos.size() > 0 && page == 1) {
                            allInfos.clear();
                        }
                        allInfos.addAll(tmepInfos);
                    }
                    if (loadMore) {
                        if (tmepInfos.size() > 0) {
                            recyclerViewAdapter.addMore(tmepInfos);
                        } else {
//                            recyclerViewAdapter.showLoadMore(false);
                        }
                    } else {
//                        recyclerViewAdapter.notifyDataSetChanged();
                        if (tmepInfos.size() > 0) {
                            recyclerViewAdapter = new RecyclerAdapter_HotProduct(mContext, allInfos, loadImageUtil, mRecyclerView);
                            mRecyclerView.setAdapter(recyclerViewAdapter);
                            recyclerViewAdapter.finishLoadMore();
                            mSwipeRefreshWidget.setRefreshing(false);
                        }
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                    mSwipeRefreshWidget.setRefreshing(false);
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


    /**
     * 添加商品
     */
    @Override
    public void addProduct(Object product) {

    }

    /**
     * swipeRefreshLayout下拉刷新回调
     */
    @Override
    public void onRefresh() {
        if (firstVisiableItem == 0) {
            page = 1;
            allInfos.clear();
            getProduct(page, rows, false, false);
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
                        getProductCategory(true);
                    }else{
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
                        getProductRange(true);
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
            mSwipeRefreshWidget.setEnabled(false);
        } else {
            ll_beforeTwo_select.setVisibility(View.GONE);
        }
        if (secondCB.isChecked()) {
            ll_recyclerView.setVisibility(View.VISIBLE);
            ll_beforeTwo_select.setVisibility(View.GONE);
            ll_orderBy.setVisibility(View.GONE);
            mSwipeRefreshWidget.setEnabled(false);
        } else {
            ll_recyclerView.setVisibility(View.GONE);
        }
        if (thirdCB.isChecked()) {
            ll_recyclerView.setVisibility(View.GONE);
            ll_beforeTwo_select.setVisibility(View.GONE);
            ll_orderBy.setVisibility(View.VISIBLE);
            mSwipeRefreshWidget.setEnabled(false);
        } else {
            ll_orderBy.setVisibility(View.GONE);
        }
        if (!firstCB.isChecked() && !secondCB.isChecked() && !thirdCB.isChecked()) {
            mSwipeRefreshWidget.setEnabled(true);
        }

    }

    /**
     * 获取行业类型
     */
    private void getProductCategory(boolean showDialog) {
        RequestParams parmas = new RequestParams();
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(HotProductActivity.this);
        if (showDialog) {
            bufferCircleDialog.show( "正在加载", true, null);
        }
        AsyncHttp.posts(HttpConstant.GET_PRODUCT_CATEGORY, parmas, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "获取数据失败");
                if (bufferCircleDialog.isShowDialog()) {
                    bufferCircleDialog.dialogcancel();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (bufferCircleDialog.isShowDialog()) {
                    bufferCircleDialog.dialogcancel();
                }
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
                page = 1;
                getProduct(page, rows, false, true);
            }
        });
    }

    /**
     * 获取商圈
     */
    private void getProductRange(boolean showDialog) {
        RequestParams params = new RequestParams();
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialogs=new BufferCircleDialog(HotProductActivity.this);
        if (showDialog) {
            bufferCircleDialogs.show( "正在加载", true, null);
        }
        AsyncHttp.posts(HttpConstant.GET_CITY_RANGE, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (bufferCircleDialogs.isShowDialog()) {
                    bufferCircleDialogs.dialogcancel();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (bufferCircleDialogs.isShowDialog()) {
                    bufferCircleDialogs.dialogcancel();
                }
                String msg = response.optString("msg");
                String status = response.optString("status");
                JSONArray array = response.optJSONArray("rows");
                Log.i("RANGE", response.toString());
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
                                ll_recyclerView.setVisibility(View.GONE);
                                getProduct(page, rows, false, true);
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
                thirdCB.setChecked(false);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                getProduct(page, rows, false, true);
                //
                break;
            case R.id.radioBtn_goodOrderBy:
                thirdCB.setText(radioBtn_goodOrderBy.getText() + "");
                thirdCB.setChecked(false);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                getProduct(page, rows, false, true);
                break;
            case R.id.radioBtn_nearByOrderBy:
                thirdCB.setText(radioBtn_nearByOrderBy.getText() + "");
                thirdCB.setChecked(false);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                getProduct(page, rows, false, true);
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
