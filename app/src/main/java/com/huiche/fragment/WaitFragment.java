package com.huiche.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.OnLineUnSendInfo;
import com.huiche.R;
import com.huiche.adapter.WaitFragmentAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * 线上订单-待收货
 */

/***
 * @author Administrator
 *         线上订单-待收货
 */
public class WaitFragment extends BaseFragment {

    private View view;
    private PullToRefreshListView order_listview;
    private TextView tv_none;
    private ListView myListview;
    private SharedPreferences share;
    //状态筛选（不传：全部，0：未付款，1：待发货， 2：已取消， 3：已关闭，4：已完成，5：待确认 ）
    private int indexPage = 1, page = 1, rows = 10, status = 5;
    private List<OnLineUnSendInfo> orderlist = new ArrayList<OnLineUnSendInfo>();
    private List<OnLineUnSendInfo> alldata = new ArrayList<OnLineUnSendInfo>();
    private WaitFragmentAdapter adapter;
//	private int count=0;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_wait_fragment, container, false);
        return view;
    }

    @Override
    public void findViews() {
        share = getActivity().getSharedPreferences("user_data", getActivity().MODE_PRIVATE);
        order_listview = (PullToRefreshListView) view.findViewById(R.id.order_listview);
        order_listview.setMode(PullToRefreshBase.Mode.BOTH);
        myListview = order_listview.getRefreshableView();
        tv_none = (TextView) view.findViewById(R.id.tv_none);
    }

    @Override
    public void initData() {
        adapter = new WaitFragmentAdapter(getActivity(), alldata);
        myListview.setAdapter(adapter);
        getWaitData();


    }

    //获取待收货数据
    private void getWaitData() {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        params.put("status", status);
        String token = share.getString("token", "");
        params.put("token", share.getString("token", ""));
        AsyncHttp.posts(HttpConstantChc.LINE_ALL_ORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//				super.onFailure(statusCode, headers, throwable, errorResponse);
                tv_none.setVisibility(View.VISIBLE);
                order_listview.onRefreshComplete();
                order_listview.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//				super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    String success = response.getString("msg");
                    if (status.equals("0")) {
                        tv_none.setVisibility(View.GONE);
                        order_listview.setVisibility(View.VISIBLE);
                        if (success.equals("success")) {
                            JSONArray list = response.getJSONArray("rows");
                            if (list.length() == 0) {
                                toast("没有更多数据");
                            }
                            String jsonStr = list.toString();
                            orderlist = JSON.parseArray(jsonStr, OnLineUnSendInfo.class);
                            alldata.addAll(orderlist);
                            adapter.notifyDataSetChanged();
                            order_listview.onRefreshComplete();

                        }
                        //判断有无数据
                        if (alldata.size() < 1) {
                            tv_none.setVisibility(View.VISIBLE);
                            order_listview.setVisibility(View.GONE);
                        } else {
                            tv_none.setVisibility(View.GONE);
                            order_listview.setVisibility(View.VISIBLE);
                        }
                    } else {
                        toast(success);
                        order_listview.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    order_listview.onRefreshComplete();
                }
            }
        });
    }

    @Override
    public void setListeners() {
        order_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    alldata.clear();
                    page = 1;
                    getWaitData();
                } else {
                    page++;
                    getWaitData();
                }

            }
        });

    }

    @Override
    public void initViews() {


    }

    protected void toast(String success) {
        Toast.makeText(getActivity(), success, Toast.LENGTH_SHORT).show();

    }

}
