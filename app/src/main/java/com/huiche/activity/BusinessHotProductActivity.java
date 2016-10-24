package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.adapter.BusinessHotProductAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.bean.ProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.lib.lib.LRecyclerView.recyclerview.HeaderSpanSizeLookup;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.LRecyclerView.recyclerview.ProgressStyle;
import com.huiche.lib.lib.LRecyclerView.util.RecyclerViewStateUtils;
import com.huiche.lib.lib.LRecyclerView.view.LoadingFooter;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.utils.AsyncHttp;
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
 * Created by Administrator on 2016/8/31.
 *  商家详情进入的商家超值商品页面
 */
public  class BusinessHotProductActivity extends BaseActivity implements View.OnClickListener{
    private LRecyclerView my_recyclerView;
//    private SharedPreferences share;
    private BusinessHotProductAdapter dataAdapter;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isRefresh;
    //商家Id
    private String businessStoreId;
    private int page=1;
//    private int rows = 20;//10
    private Context mContext;
    private List<ProductInfo> exchangeProductList = new ArrayList<ProductInfo>();//超值商品列表

//    private List<ProductInfo> allInfos = new ArrayList<ProductInfo>();
//    private int mCurrentCounter = 0;
//    private final String TAG = "HotProductActivityTest";
    private TextView tv_titleText;
    private ImageButton imb_titleBack;
    private TextView tv_none;
    private ProductInfo productInfo;//要添加的商品
    private AddProductPopwindow addProductPopwindow;
    private LinearLayout ll_business;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
     setContentView(R.layout.activity_business_hot);
        mContext=this;
        my_recyclerView=(LRecyclerView)findViewById(R.id.recyclerView_hotProduct);
        tv_titleText=(TextView)findViewById(R.id.tv_titleText);
        imb_titleBack=(ImageButton)findViewById(R.id.imb_titleBack);
        tv_none=(TextView)findViewById(R.id.tv_none);
        ll_business=(LinearLayout)findViewById(R.id.ll_business);
        tv_none.setVisibility(View.GONE);
       initRecyclerView();

    }

    //初始化recyclerview
    private void initRecyclerView() {
        dataAdapter=new BusinessHotProductAdapter(this,false);
//        mRecyclerViewAdapter=new LRecyclerViewAdapter(this,dataAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BusinessHotProductActivity.this, 2);
        gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((LRecyclerViewAdapter) my_recyclerView.getAdapter(), gridLayoutManager.getSpanCount()));
        my_recyclerView.setLayoutManager(gridLayoutManager);
        my_recyclerView.setItemAnimator(new DefaultItemAnimator());
        my_recyclerView.setAdapter(mRecyclerViewAdapter);
        //设置样式必须在调用setAdapter后调用否则不生效
        my_recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        my_recyclerView.setRefreshing(true);
//        my_recyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
//            @Override
//            public void onRefresh() {
//                isRefresh = true;
//                exchangeProductList.clear();
//                page = 1;
//                getBusinessData(page);
//            }
//            @Override
//            public void onScrollUp() {
//
//            }
//            @Override
//            public void onScrollDown() {
//
//            }
//            @Override
//            public void onBottom() {
//                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(my_recyclerView);
//                if (state == LoadingFooter.State.Loading) {
////                    Log.d(TAG, "the state is Loading, just wait..");
//                    return;
//                }
//                RecyclerViewStateUtils.setFooterViewState(BusinessHotProductActivity.this, my_recyclerView, 10, LoadingFooter.State.Loading, null);
//
//                page++;
//                getBusinessData(page);
//            }
//
//            @Override
//            public void onScrolled(int distanceX, int distanceY) {
//
//            }
//        });

    }

    @Override
    public void initData() {
        tv_titleText.setText("超值");
        Intent intent=getIntent();
        //商家id
        businessStoreId=intent.getStringExtra("businessStoreId");
        //根据商家ID获取超值商品
        getBusinessData(page);
    }


    private void getBusinessData(int page) {
        //先加载1000rows
        RequestParams params = new RequestParams();
        params.put("businessStoreId", businessStoreId);
        params.put("page", page);
        params.put("rows", 1000);
        AsyncHttp.posts(HttpConstant.GET_STORE_PRODUCT_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "网络异常");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    JSONArray array = response.optJSONArray("rows");
                    if (array.length() > 0) {
                        List<ProductInfo> tempProductList = new ArrayList<ProductInfo>();//超值商品列表
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            String discount = obj.optString("discount");
                            if (!discount.equals("")) {//有参数为超值商品
                                ProductInfo exchangeProduct = JSON.parseObject(obj.toString(), ProductInfo.class);
                                tempProductList.add(exchangeProduct);
                            }

                        }
                        if (tempProductList.size() < 0) {
                            ToastUtils.ToastShowShort(mContext, "没有更多数据");
                        }


                        exchangeProductList.addAll(tempProductList);
                        if (exchangeProductList.size() > 0) {
                            if (isRefresh) {
                                dataAdapter.clear();
//                                mCurrentCounter = 0;
                                // exchangeProductList.clear();
                            }
                            dataAdapter.addAll(exchangeProductList);
//                            mCurrentCounter += exchangeProductList.size();
                            if (isRefresh) {
                                isRefresh = false;
                                my_recyclerView.refreshComplete();
                                mRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                RecyclerViewStateUtils.setFooterViewState(my_recyclerView, LoadingFooter.State.Normal);
                            }
                        } else {
                            //当无超值商品时，显示
                            tv_none.setVisibility(View.VISIBLE);
                            my_recyclerView.setVisibility(View.GONE);
                            RecyclerViewStateUtils.setFooterViewState(my_recyclerView, LoadingFooter.State.TheEnd);
                            ToastUtils.ToastShowShort(mContext, msg);
                        }
                    } else {
                        if (exchangeProductList.size() < 1) {
                            //当无超值商品时，显示
                            tv_none.setVisibility(View.VISIBLE);
                            my_recyclerView.setVisibility(View.GONE);
                        }
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                    //当无超值商品时，显示
                    tv_none.setVisibility(View.VISIBLE);
                    my_recyclerView.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);

        //点击添加按钮
        dataAdapter.setAddProductListener(new AddToShoppingCartListener() {
            @Override
            public void addProduct(Object product) {
                if (product instanceof ProductInfo) {
                    productInfo = ((ProductInfo) product);
                    if (addProductPopwindow == null)
                        addProductPopwindow = new AddProductPopwindow(mContext);
                    addProductPopwindow.show(ll_business, productInfo);
                }

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imb_titleBack:
                finish();
                break;
        }
    }
}
