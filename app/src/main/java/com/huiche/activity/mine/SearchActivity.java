package com.huiche.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.BusinessDetailActivity;
import com.huiche.adapter.AdapterListview_NearByFirstPage;
import com.huiche.adapter.RecyclerAdapter_HotProduct;
import com.huiche.adapter.SearchAdapter;
import com.huiche.bean.BusinessInfoAll;
import com.huiche.bean.ProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.KeyBoardUtils;
import com.huiche.utils.LoadImageUtil;
import com.huiche.utils.SystemBarUtil;
import com.huiche.utils.ToastUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class SearchActivity extends Activity {

    //商家
    private TextView shopping;
    //搜索
    public EditText search;
    //取消
    private TextView cancel;
    //搜索热词
    private GridView hotgv;

    //搜索热词
    private LinearLayout linearLayout;
    //显示搜索列表
    private RelativeLayout relativeLayout;
    //显示搜索列表
    public TextView searchDeatil;

    private Context context;

    //修复bug用的成员变量
    private int count = 0;

    /**
     * 存放商家 列表的结合
     */
    private ArrayList<BusinessInfoAll> bussinessStrore_List = new ArrayList<BusinessInfoAll>();
    private ArrayList<BusinessInfoAll> bussinessStroreTmp = new ArrayList<BusinessInfoAll>();

    //商家显示的列表
    private ListView lv;
    //商品显示的列表
    private RecyclerView mRecyclerView;
    private List<ProductInfo> allInfos = new ArrayList<ProductInfo>();
    private List<ProductInfo> allInfosTemp = new ArrayList<ProductInfo>();

    /**
     * 首页 listview中每个Item中的数据的Adapter
     */
    private AdapterListview_NearByFirstPage listAdapter;


    private LoadImageUtil loadImageUtil;

    private GridLayoutManager gridLayoutManager;

    private RecyclerAdapter_HotProduct recyclerViewAdapter;


    private int pageR = 1;
    private int rowsR = 10;

    private int pageL = 1;
    private int rowsL = 10;

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private SwipeRefreshLayout mSwipeRefreslv;

    private FootView footView;


    private int firstVisiableItemR = -1;

    public boolean isAllowFullScreen;
    ArrayList<String> data;

    public void dealLogicBeforeFindView() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dealLogicBeforeFindView();
        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }

        // 沉浸式状态栏
        SystemBarUtil.initSystemBarWhite(this);
        findViews();
        initData();
        setListeners();
    }


    public void findViews() {
        setContentView(R.layout.activity_search_detail);
        shopping = (TextView) findViewById(R.id.shopping);
        search = (EditText) findViewById(R.id.text_titil_all);
        cancel = (TextView) findViewById(R.id.cancel);
        hotgv = (GridView) findViewById(R.id.search_gv);
        linearLayout = (LinearLayout) findViewById(R.id.ll);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        searchDeatil = (TextView) findViewById(R.id.tv_search);
        lv = (ListView) findViewById(R.id.lv);
        mSwipeRefreslv = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_lv);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_hotProduct);
    }

    public void initData() {
        data = new ArrayList<String>();

        data.add("申容汽车");
        data.add("平安修车");
        data.add("嘟嘟加油");


        hotgv.setAdapter(new SearchAdapter(this, data));
        loadImageUtil = new LoadImageUtil();

        //进度条的颜色变化，最多可以设置4种颜色
        mSwipeRefreshWidget.setColorSchemeResources(R.color.text_color_sky_blue, R.color.white);
        recyclerViewAdapter = new RecyclerAdapter_HotProduct(context, allInfos, loadImageUtil, mRecyclerView);
        gridLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        mSwipeRefreshWidget.setEnabled(false);
        footView = new FootView(this);
        listAdapter = new AdapterListview_NearByFirstPage(context, bussinessStrore_List);
        lv.setAdapter(listAdapter);
        lv.addFooterView(footView);
        mSwipeRefreslv.setEnabled(false);
        search.requestFocus();
        search.setFocusable(true);

    }

    public void setListeners() {
        //点击取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听键盘
        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    count++;
                    if (0 == count % 2) {
                        String str = search.getText().toString();
                        if (str.isEmpty()) {
                            ToastUtils.ToastShowShort(context, "请输入要搜索的内容");
                        } else {
                            getData(1, 10, false);
                        }
                    }
                }
                return false;
            }
        });

        //1、给EditText追加ChangedListener
        search.addTextChangedListener(watcher);


        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                    //先隐藏
                    linearLayout.setVisibility(View.GONE);
                    if (View.VISIBLE == mRecyclerView.getVisibility() || View.VISIBLE == lv.getVisibility()) {
                        return;
                    }
                    //搜索详情显示
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    //先隐藏
                    //  linearLayout.setVisibility(View.VISIBLE);
                    //搜索详情显示
                    relativeLayout.setVisibility(View.GONE);

                }
            }
        });

        //搜索详情条
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = search.getText().toString();
                if (str.isEmpty()) {
                    ToastUtils.ToastShowShort(context, "请输入要搜索的内容");
                } else {
                    getData(1, 10, false);
                }
            }
        });


        //商家进行监听  进行切换
        shopping.setTag(1);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer tag = (Integer) shopping.getTag();
                if (1 == tag) {
                    shopping.setText("商品");
                    shopping.setTag(2);
                    lv.setVisibility(View.GONE);
                    //搜索详情显示
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    //去除对应的数据
                    allInfos.clear();
                    mSwipeRefreshWidget.setEnabled(false);
                    mSwipeRefreslv.setEnabled(false);
                } else {
                    shopping.setText("商家");
                    shopping.setTag(1);
                    //搜索详情显示
                    mRecyclerView.setVisibility(View.GONE);
                    //搜索详情显示
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    //去除对应的数据
                    bussinessStrore_List.clear();
                    mSwipeRefreshWidget.setEnabled(false);
                    mSwipeRefreslv.setEnabled(false);
                }
            }
        });


        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * RecycleView
             * 上拉刷新
             */
            @Override
            public void onRefresh() {
                if (firstVisiableItemR == 0) {
                    pageR = 1;
                    getData(pageR, rowsR, false);
                }
            }
        });

        mSwipeRefreslv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * listView
             * 上拉刷新
             */
            @Override
            public void onRefresh() {
                pageL = 1;
                getData(pageL, rowsL, false);
            }
        });


        //滑动监听,不能用setOnScorllListener（）
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if (lastPosition >= gridLayoutManager.getItemCount() - 1) {
                        //加载更多
                        ++pageR;
                        getData(pageR, rowsR, true);
                    } else {
                        recyclerViewAdapter.showLoadMore(false);
                    }
                } else {
                    recyclerViewAdapter.showLoadMore(false);
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }

                //表示正在滚动，用户手指在屏幕上
                if (newState == 1)
                    KeyBoardUtils.closeKeybord(search);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisiableItemR = gridLayoutManager.findFirstVisibleItemPosition();


            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0) {
                    BusinessInfoAll businessInfoAll = bussinessStrore_List.get(position);
                    String businessStoreId = businessInfoAll.getId();
                    Intent intent = new Intent(context, BusinessDetailActivity.class);
                    intent.putExtra("businessStoreId", businessStoreId);
                    if (businessInfoAll.isHavePay()) {
                        intent.putExtra("empty", "1");
                    } else {
                        intent.putExtra("empty", "2");
                    }
                    startActivity(intent);
                }
            }
        });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //表示正在滚动，用户手指在屏幕上
                if (scrollState == 1)
                    KeyBoardUtils.closeKeybord(search);

                // 滑动停止的时候
                if (AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
                    if (lv.getLastVisiblePosition() == lv.getCount() - 1) {// 底部
                        footView.setVisibility(View.VISIBLE);// 加载数据的时候，显示出来

                        ++pageL;
                        getData(pageL, rowsL, true);

                    } else {
                        // 假如是静止状态的时候并且没有滑动到底部，取消所有的任务
                        footView.setVisibility(View.GONE);
                    }
                } else {// 不是静止状态的时候，隐藏底部进度条
                    footView.setVisibility(View.GONE);
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    //2、描述监听
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            String str = search.getText().toString();
            if (str.isEmpty()) {
                ToastUtils.ToastShowShort(context, "请输入要搜索的内容");
            } else {
                Integer tag = (Integer) shopping.getTag();
                if (1 == tag) {
                    pageL = 1;
                    getData(pageL, rowsL, false);
                } else {
                    pageR = 1;
                    getData(pageR, rowsR, false);
                }


            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * 这里进行网络请求 返回结果进行显示
     */
    public void getData(final int page, int rows, final boolean loadMore) {
        String str = search.getText().toString();
        //获取待使用卡券数据
        RequestParams params = new RequestParams();
        //搜索关键字（不传返回全部）
        params.put("keyWord", str);
        //返回页数
        params.put("page", page);
        //返回行数
        params.put("rows", rows);

        String url = "";
        Integer tag = (Integer) shopping.getTag();
        //商家
        if (1 == tag) {
            url = HttpConstant.GET_SHOPPING_BUSINNESS_LIST;
        } else {
            //商品
            url = HttpConstant.GET_SHOPPING_GOODS_LIST;
        }

        final String finalUrl = url;
        AsyncHttp.posts(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                //商家
                if (finalUrl == HttpConstant.GET_SHOPPING_BUSINNESS_LIST) {

                    StringBuffer sb = new StringBuffer(response.toString());
                    try {
                        String str = response.getString("total");
                        int count = Integer.parseInt(str);
                        if (response.getString("status").equals("0") && count > 0) {
                            JSONArray rows = response.getJSONArray("rows");
                            bussinessStroreTmp.clear();
                            for (int i = 0; i < rows.length(); i++) {
                                BusinessInfoAll all = null;
                                JSONObject obj = rows.getJSONObject(i);

                                all = JSON.parseObject(obj.toString(),
                                        BusinessInfoAll.class);
                                bussinessStroreTmp.add(all);
                            }

                            if (bussinessStroreTmp.size() > 0 && 1 == page) {

                            }

                            if (1 == page) {
                                if (bussinessStroreTmp.size() > 0) {
                                    bussinessStrore_List.clear();
                                    bussinessStrore_List.addAll(bussinessStroreTmp);
                                    listAdapter.setData(bussinessStrore_List);
                                } else {
                                    ToastUtils.ToastShowShort(SearchActivity.this, "没有更多数据");
                                    footView.setVisibility(View.GONE);
                                }
                            }


                            if (loadMore) {
                                if (bussinessStroreTmp.size() > 0) {
                                    bussinessStrore_List.addAll(bussinessStroreTmp);
                                    listAdapter.setData(bussinessStrore_List);
                                    if (bussinessStrore_List.size() < 5) {
                                        footView.setVisibility(View.GONE);
                                    }
                                } else {
                                    ToastUtils.ToastShowShort(SearchActivity.this, "没有更多数据");
                                    footView.setVisibility(View.GONE);
                                }
                            }


                            bussinessStroreTmp.clear();

                            mSwipeRefreslv.setEnabled(true);
                            mSwipeRefreshWidget.setEnabled(false);
                            mSwipeRefreslv.setRefreshing(false);
                            linearLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            //搜索详情显示
                            relativeLayout.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);


                        } else {
                            //商家不存在
                            ToastUtils.ToastShowShort(context, "商家不存在");
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    try {
                        String str = response.getString("total");
                        int count = Integer.parseInt(str);
                        String msg = response.optString("msg");
                        String rows = response.optString("rows");
                        if (response.getString("status").equals("0") && count > 0) {

                            lv.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

                            //搜索详情显示

                            allInfosTemp = (ArrayList<ProductInfo>) JSON.parseArray(rows, ProductInfo.class);


                            if (1 == page) {
                                if (allInfosTemp.size() > 0) {
                                    allInfos.clear();
                                    allInfos.addAll(allInfosTemp);
                                    recyclerViewAdapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.ToastShowShort(SearchActivity.this, "没有更多数据");
                                    footView.setVisibility(View.GONE);
                                }
                            }


                            if (loadMore) {
                                if (allInfosTemp.size() > 0) {
                                    recyclerViewAdapter.addMore(allInfosTemp);
                                } else {
                                    ToastUtils.ToastShowShort(SearchActivity.this, "没有更多数据");
                                    footView.setVisibility(View.GONE);
                                }
                            }


                            mSwipeRefreshWidget.setEnabled(true);
                            mSwipeRefreshWidget.setRefreshing(false);
                            mSwipeRefreslv.setEnabled(false);
                            //回收资源
                            allInfosTemp.clear();
                        } else {
                            //商品不存在
                            ToastUtils.ToastShowShort(context, "商品不存在");
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(context, "请检查网络");
            }
        });


        hotgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search.setText(data.get(position));
                getData(1, 10, false);
            }
        });
    }


    /**
     * @author robin
     *         刷新底部的View
     */
    public class FootView extends RelativeLayout {
        private Context context;

        public FootView(Context context) {
            super(context);
            this.context = context;
            initView();
        }

        private void initView() {
            View.inflate(context, R.layout.item_foot_loadingmore, this);
        }

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


    @Override
    protected void onPause() {
        super.onPause();
        KeyBoardUtils.closeKeybord(search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyBoardUtils.openKeybord(search);
    }


}
