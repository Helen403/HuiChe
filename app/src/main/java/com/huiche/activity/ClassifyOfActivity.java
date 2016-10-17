package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.adapter.AdapterListview_NearByFirstPage;
import com.huiche.adapter.PtypeCategoryAdapter;
import com.huiche.adapter.RecyclerAdapter_CityRange;
import com.huiche.adapter.StypeCategoryAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.bean.BusinessInfoAll;
import com.huiche.bean.ProductCategory;
import com.huiche.bean.SubCategory;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
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

/***
 * 用来显示 附近中 显示 餐饮美食 休闲娱乐 酒店宾馆居家生活 购物超市运动健身，统一Activity
 *
 * @author zane
 */
public  class ClassifyOfActivity extends BaseActivity implements OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, View.OnTouchListener {
    /***
     * 存放顶部三个 radiobuton
     */
    private LinearLayout radioGroup;
    /***
     * 顶部第一个radiobutton
     */
    private CheckBox firstRB;
    /***
     * 顶部第二个radiobutton
     */
    private CheckBox secondRB;
    /***
     * 顶部第三个radiobutton
     */
    private CheckBox thirdRB;
    /***
     * 存放两个listview的布局，用来设置高度
     */
//    private LinearLayout containerLV;
//    /***
//     * 左边联动的listveiw
//     */
//    private ListView leftListView;
//    /***
//     * 右边联动的listveiw
//     */
//    private ListView rightListView;
    /***
     * 设置标题信息
     */
    private TextView text_titil_all;
    /***
     * 左边返回按键
     */
    private ImageView imageLeft_titil_all;
    /***
     * 左边listView的数据集合
//     */
//    private List<String> lefList = new ArrayList<String>();
//    /***
//     * 空集合
//     */
//    private List<String> emptyList = new ArrayList<String>();
//    /***
//     * 右边listView的数据集合
//     */
//    private List<String> rightList = new ArrayList<String>();
    /***
     * 用来判断显示隐藏 listView的标志位,1为显示，2为隐藏
     */
//    private int showHideFlag = -1;
//    /**
//     * 行业信息结合
//     */
//    private List<String> groupSector = new ArrayList<>();
    /**
     * 加载完数据可以点击
     */
    private boolean canClick = false;
    private TextView tv_right_title;
    private PullToRefreshListView pullRefreList;
    private ListView listView;
    private Context mContext;
    private AdapterListview_NearByFirstPage contentAdapter;
    private List<BusinessInfoAll> contentList = new ArrayList<BusinessInfoAll>();
    private List<BusinessInfoAll> contentTempList = new ArrayList<BusinessInfoAll>();
    private int page = 1;
    private int rows = 10;
    private String titleName;
    private PopupWindow popupWindow;
    private LinearLayout ll_shadow;
    private RadioGroup radio_orderBy;
    private LinearLayout ll_beforeTwo_select;
    private RadioButton radioBtn_smartOrderBy;
    private RadioButton radioBtn_goodOrderBy;
    private RadioButton radioBtn_nearByOrderBy;
    //    private RadioButton radioBtn_lowByOrderBy;
    private ListView lv_ptype_classify;//父分类
    private ListView lv_stype_classify;//子分类
    private LinearLayout ll_orderBy;
    public List<ProductCategory> productCategoryList = new ArrayList<>();
    public List<String> pNameList = new ArrayList<>();
    public List<String> sNameList = new ArrayList<>();
    private LinearLayout ll_selectTwoList;
    private boolean firstClickFirstRb = true;
    private boolean firstClickSecondRb = true;
    private StypeCategoryAdapter stypeCategoryAdapter;
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private SharedPreferences sp;
    private String firstCheckStr = "";//第一个筛选
    private RecyclerView recyclerView_cityRange;
    private List<String> rangeList = new ArrayList<>();
    private LinearLayout ll_recyclerView;
    //阴影背景
    private View view_shadow_category;
    private View view_shadow_cityRange;
    private View view_shadow_orderBy;
    private TextView tv_none;
    private String subName;//（更多页面）选择的子类
    private RelativeLayout rell_view;
//    private String secondCheckStr = "";//第二个筛选
//    private String threeCheckStr = "";//第三个筛选

    @Override
    public void dealLogicBeforeFindView() {

        mContext = this;
        sp = getSharedPreferences("user_data", MODE_PRIVATE);
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_classifyof);

        radioGroup = (LinearLayout) findViewById(R.id.ll_classifyOf_activity);
        radio_orderBy = (RadioGroup) findViewById(R.id.radioGroup_orderBy);
        firstRB = (CheckBox) findViewById(R.id.firstRB_classifyOf_activity);
        secondRB = (CheckBox) findViewById(R.id.secondRB_classifyOf_activity);
        thirdRB = (CheckBox) findViewById(R.id.thirdRB_classifyOf_activity);
        radioBtn_smartOrderBy = (RadioButton) findViewById(R.id.radioBtn_smartOrderBy);
        radioBtn_goodOrderBy = (RadioButton) findViewById(R.id.radioBtn_goodOrderBy);
        radioBtn_nearByOrderBy = (RadioButton) findViewById(R.id.radioBtn_nearByOrderBy);
//        radioBtn_lowByOrderBy = (RadioButton) findViewById(R.id.radioBtn_lowByOrderBy);
        text_titil_all = (TextView) findViewById(R.id.text_titil_all);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        imageLeft_titil_all = (ImageView) findViewById(R.id.imageLeft_titil_all);
        view_shadow_cityRange = findViewById(R.id.view_shadow_cityRange);
        view_shadow_category = findViewById(R.id.view_shadow_category);
        view_shadow_orderBy = findViewById(R.id.view_shadow_orderBy);
        lv_ptype_classify = (ListView) findViewById(R.id.lv_ptype_classify);
        lv_stype_classify = (ListView) findViewById(R.id.lv_stype_classify);
        ll_beforeTwo_select = (LinearLayout) findViewById(R.id.ll_beforeTwo_select);
        ll_selectTwoList = (LinearLayout) findViewById(R.id.ll_selectTwoList);
        ll_orderBy = (LinearLayout) findViewById(R.id.ll_orderBy);
        ll_recyclerView = (LinearLayout) findViewById(R.id.ll_recyclerView);
        pullRefreList = (PullToRefreshListView) findViewById(R.id.lv_content_classifyOf);
        recyclerView_cityRange = (RecyclerView) findViewById(R.id.recyclerView_cityRange);
        tv_none=(TextView)findViewById(R.id.tv_none);
        rell_view=(RelativeLayout)findViewById(R.id.rell_view);
        //
        recyclerView_cityRange.setLayoutManager(new GridLayoutManager(mContext, 4));
//        recyclerView_cityRange.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.popwin_enter4top));
        recyclerView_cityRange.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initData() {
        tv_right_title.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        titleName = intent.getStringExtra("title");
        subName = intent.getStringExtra("more");
        if (titleName != null) {
            text_titil_all.setText(titleName);
            firstRB.setText(titleName);
        } else if (subName != null) {
            text_titil_all.setText(subName);
            firstRB.setText(subName);
        }
        //屏幕高度1/11
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioGroup, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_smartOrderBy, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_goodOrderBy, 11);
        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_nearByOrderBy, 11);
//        SetSizeUtils.setSizeOnlyHeightOf(mContext, radioBtn_lowByOrderBy, 11);
//        SetSizeUtils.setSizeOnlyHeightOf(mContext, ll_selectTwoList, 2);

        //获取listview设置适配器
        pullRefreList.setMode(PullToRefreshBase.Mode.BOTH);
        listView = pullRefreList.getRefreshableView();
        contentAdapter = new AdapterListview_NearByFirstPage(mContext, contentList);
        listView.setAdapter(contentAdapter);
        getContentList(page, rows, false, true);
        getProductCategory(false);
        getProductRange(false);

    }

    /**
     * 获取商圈
     */
    private void getProductRange(boolean show) {
        //缓冲小菊花
        final BufferCircleDialog sbufferCircleDialog = new BufferCircleDialog(ClassifyOfActivity.this);
        RequestParams params = new RequestParams();
        if (show) {
            sbufferCircleDialog.show("正在加载", false, null);
        }
        AsyncHttp.posts(HttpConstant.GET_CITY_RANGE, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (sbufferCircleDialog.isShowDialog()) {
                    sbufferCircleDialog.dialogcancel();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                if (sbufferCircleDialog != null && sbufferCircleDialog.isShowDialog()) {
                    sbufferCircleDialog.dialogcancel();
                }
                String msg = response.optString("msg");
                String status = response.optString("status");
                JSONArray array = response.optJSONArray("rows");
//                Log.i("RANGE", response.toString());
                if (status.equals("0")) {
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            rangeList.add(array.optString(i) + "");
                        }
                        rangeList.add(0, "全城");
                        RecyclerAdapter_CityRange adapter_cityRange = new RecyclerAdapter_CityRange(mContext, rangeList);
                        recyclerView_cityRange.setAdapter(adapter_cityRange);
                        showSelectList();
                        adapter_cityRange.setOnItemClickListener(new RecyclerAdapter_CityRange.onItemClickListener() {
                            @Override
                            public void selectCityRange(int position) {
                                secondRB.setText(rangeList.get(position));
                                secondRB.setChecked(false);
                                page = 1;
                                ll_recyclerView.setVisibility(View.GONE);
                                getContentList(page, rows, true, true);
                            }
                        });

                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                }

            }
        });
    }

    /**
     * 获取行业类型
     */


    private void getProductCategory(boolean show) {
        //缓冲小菊花
        final BufferCircleDialog jobbufferCircleDialog = new BufferCircleDialog(ClassifyOfActivity.this);
        RequestParams parmas = new RequestParams();
        if (show) {
            jobbufferCircleDialog.show("正在加载", true, null);
        }
        AsyncHttp.posts(HttpConstant.GET_PRODUCT_CATEGORY, parmas, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (jobbufferCircleDialog.isShowDialog()) {
                    jobbufferCircleDialog.dialogcancel();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (jobbufferCircleDialog.isShowDialog()) {
                    jobbufferCircleDialog.dialogcancel();
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
                lv_ptype_classify.setSelection(0);
                subCategoryList = productCategoryList.get(0).sName;
                setStypeList();
            }
        }
    }

    /**
     * 设置二级类别数据
     */
    public void setStypeList() {
        //增加全部item
        SubCategory all = new SubCategory();
        StypeCategoryAdapter stypeCategoryAdapter = new StypeCategoryAdapter(mContext, subCategoryList, firstCheckStr);
        lv_stype_classify.setAdapter(stypeCategoryAdapter);
        showSelectList();
        //二级菜单item点击事件
        stypeCategoryAdapter.setOnItemCheckListener(new StypeCategoryAdapter.onItemCheckListener() {
            @Override
            public void onItemChecked(int position) {
                firstRB.setText(subCategoryList.get(position).name);
                firstRB.setChecked(false);
                ll_beforeTwo_select.setVisibility(View.GONE);
                firstCheckStr = subCategoryList.get(position).name;
                //请求网络筛选
                page = 1;
                getContentList(page, rows, true, true);
            }
        });
    }

    @Override
    public void setListeners() {

        firstRB.setOnClickListener(this);
        secondRB.setOnClickListener(this);
        thirdRB.setOnClickListener(this);
        view_shadow_category.setOnTouchListener(this);
        view_shadow_cityRange.setOnTouchListener(this);
        view_shadow_orderBy.setOnTouchListener(this);
        radio_orderBy.setOnCheckedChangeListener(this);
        imageLeft_titil_all.setOnClickListener(this);
        pullRefreList.setOnItemClickListener(this);
        //一级菜单item点击事件
        lv_ptype_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PtypeCategoryAdapter adapter = (PtypeCategoryAdapter) adapterView.getAdapter();
                adapter.setSelection(position);
                adapter.notifyDataSetInvalidated();
                subCategoryList = productCategoryList.get(position).sName;
                String pName = productCategoryList.get(position).pName;
                setStypeList();
            }
        });

        //刷新数据
        pullRefreList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
//                    contentList.clear();
                    page = 1;
                    contentList.clear();
                    getContentList(page, rows, false, false);
                } else {
                    //上拉加载更多
                    page++;
                    getContentList(page, rows, false, false);
                }
            }
        });
    }

    /**
     * 获取指定类型商品信息
     */
    private void getContentList(int page, int rows, final boolean isSelect, boolean show) {
        //缓冲小菊花
        final BufferCircleDialog goodbufferCircleDialog = new BufferCircleDialog(ClassifyOfActivity.this);
        RequestParams params = new RequestParams();
        String firstStr = firstRB.getText() + "";
        String secondStr = secondRB.getText() + "";
        String thirdStr = thirdRB.getText() + "";
        if (subName != null) {
            params.put("stype", firstStr);
        } else {
            if (!firstStr.equals(titleName)) {
                params.put("stype", firstStr);
            } else {
                params.put("ptype", titleName);
            }
        }
        if (firstStr.equals("全部分类")) {
            params.put("stype", "");
            params.put("ptype", "");
        }
        if (!secondStr.equals("全城")) {
            params.put("area", secondStr);
        }
        if (radioBtn_nearByOrderBy.isChecked()) {

        }
        if (radioBtn_goodOrderBy.isChecked()) {
            params.put("isHot", true);
        }
        if (radioBtn_smartOrderBy.isChecked()) {
            params.put("messageHot", true);
        }
        params.put("page", page);
        params.put("rows", rows);
        if (show) {
            goodbufferCircleDialog.show("正在加载", true, null);
        }
        AsyncHttp.posts(HttpConstant.GET_SEARCH_DO, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (goodbufferCircleDialog.isShowDialog()) {
                    goodbufferCircleDialog.dialogcancel();
                }
                String msg = response.optString("msg");
                String status = response.optString("status");
                if (msg.equals("SUCCESS")) {
                    if (isSelect) {
                        contentList.clear();
                    }
                    String json = response.optString("rows");
                    contentTempList = JSON.parseArray(json, BusinessInfoAll.class);
                    if (contentTempList.size() > 0) {
                        contentList.addAll(contentTempList);
                        contentAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, "暂无数据");
                    if (contentList.size() < 1) {
                        tv_none.setVisibility(View.VISIBLE);
                        rell_view.setVisibility(View.GONE);
                    }
                }
                pullRefreList.onRefreshComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "加载失败，请检查网络");
                pullRefreList.onRefreshComplete();
                if (goodbufferCircleDialog.isShowDialog()) {
                    goodbufferCircleDialog.dialogcancel();
                }
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position - 1 >= 0) {
            BusinessInfoAll businessInfoAll = contentList.get(position - 1);
            String businessStoreId = businessInfoAll.getId();
            Intent intent = new Intent(mContext, BusinessDetailActivity.class);
            intent.putExtra("businessStoreId", businessStoreId);
            /**************************************************/

            if (!businessInfoAll.isHavePay()) {
                intent.putExtra("empty", "1");
            } else {
                intent.putExtra("empty", "2");
            }
        /*******************************************************/
            startActivity(intent);
        }

    }


    /**
     * 筛选列表
     */
    private void showSelectList() {
        if (firstRB.isChecked()) {
            ll_beforeTwo_select.setVisibility(View.VISIBLE);
            ll_orderBy.setVisibility(View.GONE);
            ll_recyclerView.setVisibility(View.GONE);
            pullRefreList.setEnabled(false);
        } else {
            ll_beforeTwo_select.setVisibility(View.GONE);
        }
        if (secondRB.isChecked()) {
            ll_recyclerView.setVisibility(View.VISIBLE);
            ll_beforeTwo_select.setVisibility(View.GONE);
            ll_orderBy.setVisibility(View.GONE);
            pullRefreList.setEnabled(false);
        } else {
            ll_recyclerView.setVisibility(View.GONE);
        }
        if (thirdRB.isChecked()) {
            ll_recyclerView.setVisibility(View.GONE);
            ll_beforeTwo_select.setVisibility(View.GONE);
            ll_orderBy.setVisibility(View.VISIBLE);
            pullRefreList.setEnabled(false);
        } else {
            ll_orderBy.setVisibility(View.GONE);
        }
        if (!firstRB.isChecked() && !secondRB.isChecked() && !thirdRB.isChecked()) {
            pullRefreList.setEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageLeft_titil_all:
                finish();
                break;
            // 三个筛选
            case R.id.firstRB_classifyOf_activity:
                if (firstRB.isChecked()) {
                    secondRB.setChecked(false);
                    thirdRB.setChecked(false);
                }
                if (productCategoryList.size() == 0) {
                    getProductCategory(true);
                } else {
                    setStypeList();
                    showSelectList();
                }
                break;
            case R.id.secondRB_classifyOf_activity:
                if (secondRB.isChecked()) {
                    firstRB.setChecked(false);
                    thirdRB.setChecked(false);
                }
                if (rangeList.size() == 0) {
                    getProductRange(true);
                }
//                if (firstClickSecondRb) {
//                    getProductRange();
//                    firstClickSecondRb = false;
//                }
                else {
                    showSelectList();
                }
                break;
            case R.id.thirdRB_classifyOf_activity:
                if (thirdRB.isChecked()) {
                    firstRB.setChecked(false);
                    secondRB.setChecked(false);
                }
                showSelectList();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            //智能筛选子类
            case R.id.radioBtn_smartOrderBy:
                thirdRB.setText(radioBtn_smartOrderBy.getText() + "");
                thirdRB.setChecked(false);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                getContentList(page, rows, true, true);
                //
                break;
            case R.id.radioBtn_goodOrderBy:
                thirdRB.setText(radioBtn_goodOrderBy.getText() + "");
                thirdRB.setChecked(false);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                getContentList(page, rows, true, true);
                break;
            case R.id.radioBtn_nearByOrderBy:
                thirdRB.setText(radioBtn_nearByOrderBy.getText() + "");
                thirdRB.setChecked(false);
                ll_orderBy.setVisibility(View.GONE);
                page = 1;
                getContentList(page, rows, true, true);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.view_shadow_category:
                ll_beforeTwo_select.setVisibility(View.GONE);
                firstRB.setChecked(false);
                break;
            case R.id.view_shadow_cityRange:
                ll_recyclerView.setVisibility(View.GONE);
                secondRB.setChecked(false);
                break;
            case R.id.view_shadow_orderBy:
                ll_orderBy.setVisibility(View.GONE);
                thirdRB.setChecked(false);
                break;
        }
        return false;
    }
}
