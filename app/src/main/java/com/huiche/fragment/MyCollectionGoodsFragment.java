package com.huiche.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.PostResult.CollectionGoods;
import com.huiche.R;
import com.huiche.activity.mine.MyCollectionsActivity;
import com.huiche.adapter.MyGoodsCollectionAdapter;
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

public class MyCollectionGoodsFragment extends BaseFragment {
    private View view;
    private LinearLayout ll_tip;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView mListView;
    private List<CollectionGoods> dataList = new ArrayList<CollectionGoods>();
    private List<CollectionGoods> allList = new ArrayList<CollectionGoods>();
    private MyGoodsCollectionAdapter adapter;
    private int page = 1;
    private int rows = 10;
    private SharedPreferences share;
    private String menberId;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_collection, container, false);
        return view;
    }

    @Override
    public void findViews() {
        ll_tip = (LinearLayout) view.findViewById(R.id.ll_goods_tip);
        pull_listview = (PullToRefreshSwipeListView) view.findViewById(R.id.pull_listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        mListView = pull_listview.getRefreshableView();
    }

    @Override
    public void initData() {
        share = this.getActivity().getSharedPreferences("user_data", this.getActivity().MODE_PRIVATE);
        menberId = share.getString("id", "0");
        //获取会员收藏商品数据
        getUserGoods();
        adapter = new MyGoodsCollectionAdapter(getActivity(), allList, mListView.getRightViewWidth(), new MyGoodsCollectionAdapter.IOnItemRightClickListener() {

            @Override
            public void onRightClick(View v, int position) {
                //滑动删除数据
                String id = allList.get(position).id;
                cancelCollectionShop(id, position);


            }
        });
        mListView.setAdapter(adapter);
    }

    //获取商品数据
    private void getUserGoods() {
        RequestParams params = new RequestParams();
        params.put("menberId", menberId);
        params.put("page", page);
        params.put("rows", rows);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(getActivity());
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.COLLECTION_GOODS, params, new JsonHttpResponseHandler() {
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
                    dataList = JSON.parseArray(data, CollectionGoods.class);
                    allList.addAll(dataList);

                    adapter.notifyDataSetChanged();
                    pull_listview.onRefreshComplete();
                    String number = response.optString("total");
                    if (!number.equals("0")) {
                        MyCollectionsActivity.upDateGoods(number);
                    }
                    if (allList.size() < 1) {
                        ll_tip.setVisibility(View.VISIBLE);
                        pull_listview.setVisibility(View.GONE);
                    } else {
                        ll_tip.setVisibility(View.GONE);
                        pull_listview.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    ll_tip.setVisibility(View.VISIBLE);
                    pull_listview.setVisibility(View.GONE);
                    pull_listview.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                ll_tip.setVisibility(View.VISIBLE);
                pull_listview.setVisibility(View.GONE);
                pull_listview.onRefreshComplete();
                bufferCircleDialog.dialogcancel();
            }
        });

    }

    @Override
    public void setListeners() {
        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<SwipeListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<SwipeListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    allList.clear();
                    page = 1;
                    getUserGoods();
                } else {
                    page++;
                    getUserGoods();
                }
            }
        });

    }

    @Override
    public void initViews() {


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
                super.onSuccess(statusCode, headers, response);
                bufferCircleDialog.dialogcancel();
                String status = response.optString("status");
//                String msg = response.optString("msg");
                boolean success = response.optBoolean("success");
                if (status.equals("0")) {
                    if (success) {
                        allList.remove(position);
//						adapter.notifyDataSetChanged();
                        adapter = new MyGoodsCollectionAdapter(getActivity(), allList, mListView.getRightViewWidth(), new MyGoodsCollectionAdapter.IOnItemRightClickListener() {

                            @Override
                            public void onRightClick(View v, int position) {
                                //滑动删除数据
                                String id = allList.get(position).id;
                                cancelCollectionShop(id, position);


                            }
                        });
                        mListView.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                bufferCircleDialog.dialogcancel();
            }
        });


    }


}
