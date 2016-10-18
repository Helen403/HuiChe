package com.huiche.fragment;
/***
 * 我的收藏---商家
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.PostResult.CollectionShop;
import com.huiche.R;
import com.huiche.activity.BusinessDetailActivity;
import com.huiche.activity.mine.MyCollectionsActivity;
import com.huiche.adapter.MyShopCollectionAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionShopFragment extends BaseFragment {
    private View view;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView mSwipeListView;
    private int page = 1;
    private int rows = 10;
    private SharedPreferences share;
    private String menberId;
    private LinearLayout ll_tip;
    private MyShopCollectionAdapter adapter;
    private List<CollectionShop> dataList = new ArrayList<CollectionShop>();
    private List<CollectionShop> alllist = new ArrayList<CollectionShop>();
    //    private final String ACTION_NAME = "UPDATE_UI";
    MyCollectionsActivity collectionsActivity;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_collection, container, false);
        return view;
    }

    @Override
    public void findViews() {
        collectionsActivity = new MyCollectionsActivity();
        share = this.getActivity().getSharedPreferences("user_data", this.getActivity().MODE_PRIVATE);
        menberId = share.getString("id", "0");
        pull_listview = (PullToRefreshSwipeListView) view.findViewById(R.id.pull_listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        mSwipeListView = pull_listview.getRefreshableView();
        ll_tip = (LinearLayout) view.findViewById(R.id.ll_tip);
        ll_tip.setVisibility(View.GONE);
        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<SwipeListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<SwipeListView> refreshView) {
                // 刷新数据的操作
                if (refreshView.isHeaderShown()) {

                    page = 1;
                    alllist.clear();
                    getUserCollection(page, menberId);

                } else {
                    // Toast.makeText(getApplicationContext(), "上拉加载更多",
                    // Toast.LENGTH_SHORT).show();
                    page++;
                    getUserCollection(page, menberId);

                }

            }
        });


//		pull_listview.setOnRefreshListener(new OnRefreshListener2<SwipeListView>() {
//			
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
//				Log.v("aa-im","onPullDownToRefresh");
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
//				Log.v("aa-im","onPullUpToRefresh");
//			}
//		});


    }

    @Override
    public void initData() {
        adapter = new MyShopCollectionAdapter(getActivity(), alllist, mSwipeListView.getRightViewWidth(), new MyShopCollectionAdapter.IOnItemRightClickListener() {

            @Override
            public void onRightClick(View v, int position) {
                //滑动删除
                String id = alllist.get(position).id;
                cancelCollectionShop(id, position);


            }
        });
        mSwipeListView.setAdapter(adapter);

        //请求收藏店铺信息
        getUserCollection(page, menberId);

    }

    private void getUserCollection(int page, String menberId) {
        RequestParams params = new RequestParams();
        params.put("menberId", menberId);
        params.put("page", page);
        params.put("rows", rows);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(getActivity());
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.COLLECTION_SHOP, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                bufferCircleDialog.dialogcancel();
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    dataList.clear();
                    JSONArray list = response.optJSONArray("rows");
                    if (list.length() < 1) {
                        ToastUtils.ToastShowShort(getActivity(), "没有更多数据");
                    }
                    String data = list.toString();
                    dataList = JSON.parseArray(data, CollectionShop.class);
                    alllist.addAll(dataList);
//						Log.i("222525", alllist.toString());
                    adapter.notifyDataSetChanged();
                    pull_listview.onRefreshComplete();
                    String shopNum = response.optString("total");
                    if (!shopNum.equals("0")) {
//                        MyCollectionsActivity.upDateShop(shopNum);
                    }


                    if (alllist.size() < 1) {
                        ll_tip.setVisibility(View.VISIBLE);
                        pull_listview.setVisibility(View.GONE);
                    } else {
                        ll_tip.setVisibility(View.GONE);
                        pull_listview.setVisibility(View.VISIBLE);
                    }
                } else {
                    pull_listview.onRefreshComplete();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    ll_tip.setVisibility(View.VISIBLE);
                    pull_listview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                bufferCircleDialog.dialogcancel();
                pull_listview.onRefreshComplete();
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                ll_tip.setVisibility(View.VISIBLE);
                pull_listview.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void setListeners() {

        mSwipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int positionTmp = position - 1;
                if (positionTmp >= 0) {
                    CollectionShop businessInfoAll = alllist.get(positionTmp);


                    String businessStoreId = businessInfoAll.getBusinessStoreId();
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
        });


    }

    @Override
    public void initViews() {


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (adapter == null) {
            adapter = new MyShopCollectionAdapter(getActivity(), alllist, mSwipeListView.getRightViewWidth(), new MyShopCollectionAdapter.IOnItemRightClickListener() {

                @Override
                public void onRightClick(View v, int position) {


                }
            });
        }
        mSwipeListView.setAdapter(adapter);


    }

    //取消收藏商家

    public void cancelCollectionShop(String id, final int position) {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("idList", id);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(getActivity());
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.CANCEL_COLLECTION_GOODS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                bufferCircleDialog.dialogcancel();
                String status = response.optString("status");
//                String msg = response.optString("msg");
                boolean success = response.optBoolean("success");
                if (status.equals("0")) {
                    if (success) {
                        alllist.remove(position);
//						adapter.notifyDataSetChanged();
                        adapter = new MyShopCollectionAdapter(getActivity(), alllist, mSwipeListView.getRightViewWidth(), new MyShopCollectionAdapter.IOnItemRightClickListener() {

                            @Override
                            public void onRightClick(View v, int position) {
                                //滑动删除
                                String id = alllist.get(position).id;
                                cancelCollectionShop(id, position);

                            }
                        });
                        mSwipeListView.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                bufferCircleDialog.dialogcancel();
            }
        });


    }
}
