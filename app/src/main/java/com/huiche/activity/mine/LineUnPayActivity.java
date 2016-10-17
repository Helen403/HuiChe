package com.huiche.activity.mine;
/***
 * 线下订单--未消费
 */


import android.content.SharedPreferences;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.LineUnPayInfo;
import com.huiche.R;
import com.huiche.adapter.LineUnPayAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineUnPayActivity extends BaseActivity {
    private PullToRefreshListView pull_listview;
    private ListView mListview;
    private SharedPreferences share;
    private int page = 1;
    private int rows = 10;
    private int status = 0;
    private List<LineUnPayInfo> orderlist = new ArrayList<LineUnPayInfo>();
    private List<LineUnPayInfo> alldata = new ArrayList<LineUnPayInfo>();
    private LineUnPayAdapter adapter;
//    private int count = 0;
    private TextView tv_none;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_line_unpay);
        tv_none = (TextView) findViewById(R.id.tv_none);
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        TitleUtils.setInfo(this, "未消费");
        pull_listview = (PullToRefreshListView) findViewById(R.id.lind_unpay_listview);
        pull_listview.setMode(Mode.BOTH);
        mListview = pull_listview.getRefreshableView();


    }

    @Override
    public void initData() {
        adapter = new LineUnPayAdapter(this, alldata);
        mListview.setAdapter(adapter);
        getUnPayGoodsList(page);

    }

    //获取线下订单--未消费列表
    private void getUnPayGoodsList(int page) {
        RequestParams params = new RequestParams();
        params.put("id", share.getString("id", ""));
//        String idd = share.getString("id", "");
        params.put("page", page);
        params.put("rows", rows);
        params.put("status", status);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(LineUnPayActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.LINE_UNPAY, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//				super.onFailure(statusCode, headers, throwable, errorResponse);
                pull_listview.onRefreshComplete();
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(LineUnPayActivity.this, "请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

//				super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    String success = response.getString("msg");
                    if (status.equals("0")) {
                        pull_listview.onRefreshComplete();
                        if (success.equals("success")) {
                            orderlist.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            if (list.length() == 0) {
                                ToastUtils.ToastShowShort(LineUnPayActivity.this, "没有更多数据");
                            }
                            String jsonStr = list.toString();
                            orderlist = JSON.parseArray(jsonStr, LineUnPayInfo.class);
                            alldata.addAll(orderlist);
                            adapter.notifyDataSetChanged();
                        }
                        if (alldata.size() < 1) {
                            tv_none.setVisibility(View.VISIBLE);
                            pull_listview.setVisibility(View.GONE);
                        } else {
                            tv_none.setVisibility(View.GONE);
                            pull_listview.setVisibility(View.VISIBLE);
                        }
                    } else {
                        pull_listview.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    pull_listview.onRefreshComplete();
                }
            }
        });


    }

    @Override
    public void setListeners() {
        pull_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    alldata.clear();
                    page = 1;
                    getUnPayGoodsList(page);
                } else {
                    page++;
                    getUnPayGoodsList(page);


                }


            }
        });

    }

}
