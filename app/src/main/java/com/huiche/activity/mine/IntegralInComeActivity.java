package com.huiche.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.IntegralInComeInfo;
import com.huiche.R;
import com.huiche.adapter.IntegralInComeAdapter;
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

public class IntegralInComeActivity extends BaseActivity {
    private PullToRefreshListView pull_list;
    private ListView mListView;
    private int page = 1;
    private int rows = 10;
    private SharedPreferences share;
    private IntegralInComeAdapter adapter;
    private List<IntegralInComeInfo> orderlist = new ArrayList<IntegralInComeInfo>();
    private List<IntegralInComeInfo> alldata = new ArrayList<IntegralInComeInfo>();

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_integral_income);
        TitleUtils.setInfo(this, "积分收入");
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        Intent intent = getIntent();
        String in_integral = intent.getStringExtra("integral_in");


        TextView tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_integral.setText("+" + in_integral);
        pull_list = (PullToRefreshListView) findViewById(R.id.lv_intergral);
        pull_list.setMode(Mode.BOTH);
        mListView = pull_list.getRefreshableView();
    }

    @Override
    public void initData() {
        adapter = new IntegralInComeAdapter(this, alldata);
        mListView.setAdapter(adapter);
        getIntegralInCome();
    }

    //积分收入
    private void getIntegralInCome() {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        boolean type = true;
        params.put("type", type);
        params.put("id", share.getString("id", ""));
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(IntegralInComeActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.INTEGRAL_INCOME, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                pull_list.onRefreshComplete();
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(IntegralInComeActivity.this, "请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    String success = response.getString("msg");
                    if (status.equals("0")) {
                        pull_list.onRefreshComplete();
                        if (success.equals("success")) {
                            //orderlist.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            String jsonStr = list.toString();
                            orderlist = JSON.parseArray(jsonStr, IntegralInComeInfo.class);
                            alldata.addAll(orderlist);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        pull_list.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pull_list.onRefreshComplete();
                }
            }
        });

    }

    @Override
    public void setListeners() {
        pull_list.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    alldata.clear();
                    page = 1;
                    getIntegralInCome();
                } else {
                    page++;
                    getIntegralInCome();
                }
            }
        });

    }

}
