package com.huiche.activity.mine;

import android.content.SharedPreferences;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.LineFinishOrderInfo;
import com.huiche.R;
import com.huiche.adapter.LineFinishOrderAdapter;
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

public class LineFinishOrderActivity extends BaseActivity {
    private PullToRefreshListView pull_list;
    private ListView mListView;
    private SharedPreferences share;
    private List<LineFinishOrderInfo> orderlist = new ArrayList<LineFinishOrderInfo>();
    private List<LineFinishOrderInfo> alldata = new ArrayList<LineFinishOrderInfo>();
    private int page = 1;
    private int rows = 10;
    private LineFinishOrderAdapter adapter;

    @Override
    public void dealLogicBeforeFindView() {

    }

    /***
     * 商家的商品列表还没做
     */
    @Override
    public void findViews() {
        setContentView(R.layout.activity_line_finish_order);
        TitleUtils.setInfo(this, "已完成");
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        pull_list = (PullToRefreshListView) findViewById(R.id.lind_finish_listview);
        pull_list.setMode(Mode.BOTH);
        mListView = pull_list.getRefreshableView();
    }

    @Override
    public void initData() {
        adapter = new LineFinishOrderAdapter(this, alldata);
        mListView.setAdapter(adapter);
        getFinishOrder(page);

    }

    private void getFinishOrder(int page) {
        RequestParams params = new RequestParams();
        params.put("id", share.getString("id", ""));
        String idd = share.getString("id", "");
        params.put("page", page);
        params.put("rows", rows);
        int status = 1;
        params.put("status", status);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(LineFinishOrderActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.LINE_FINISH_ORDER, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//				super.onFailure(statusCode, headers, throwable, errorResponse);
                pull_list.onRefreshComplete();
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(LineFinishOrderActivity.this, "请检查网络");
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
                        pull_list.onRefreshComplete();
                        if (success.equals("success")) {
                            orderlist.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            if (list.length() == 0) {
                                ToastUtils.ToastShowShort(LineFinishOrderActivity.this, "没有更多数据");
                            }
                            String jsonStr = list.toString();
                            orderlist = JSON.parseArray(jsonStr, LineFinishOrderInfo.class);
                            alldata.addAll(orderlist);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        pull_list.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
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
                    getFinishOrder(page);
                } else {
                    page++;
                    getFinishOrder(page);
                }

            }
        });
    }

}
