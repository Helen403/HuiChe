package com.huiche.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.LineOrderInfo;
import com.huiche.R;
import com.huiche.adapter.LineOrderAdapter;
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


/**
 * @author Administrator
 *         线下订单--全部订单
 */
public class LineOrderActivity extends BaseActivity {
    private PullToRefreshListView lind_order_listview;
    private int page = 1;
    private int rows = 10;
    private int status = 99;
    private SharedPreferences share;
    private List<LineOrderInfo> orderlist = new ArrayList<LineOrderInfo>();
    private List<LineOrderInfo> alldata = new ArrayList<LineOrderInfo>();
    private LineOrderAdapter adapter;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        //item布局为 item_line_order
        setContentView(R.layout.activity_line_order);
        Context mContext = this;
        TitleUtils.setInfo(this, "全部订单");
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        lind_order_listview = (PullToRefreshListView) findViewById(R.id.lind_order_listview);
        lind_order_listview.setMode(Mode.BOTH);
        ListView myListView = lind_order_listview.getRefreshableView();
        adapter = new LineOrderAdapter(mContext, alldata);
        myListView.setAdapter(adapter);
        lind_order_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    alldata.clear();
                    page = 1;
                    getAllLineOrder(page, rows);

                } else {
                    page++;
                    getAllLineOrder(page, rows);
                }


            }
        });
    }

    @Override
    public void initData() {
        getAllLineOrder(page, rows);

    }

    @Override
    public void setListeners() {

    }

    //获取线下全部订单
    public void getAllLineOrder(int page, int rows) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        params.put("status", status);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(LineOrderActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.ALL_LINE_ORDER, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                lind_order_listview.onRefreshComplete();
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(LineOrderActivity.this, "请检查网络");
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
                        lind_order_listview.onRefreshComplete();
                        if (success.equals("success")) {
                            orderlist.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            if (list.length() == 0) {
                                ToastUtils.ToastShowShort(LineOrderActivity.this, "没有更多数据");
                            }
                            String jsonStr = list.toString();
                            orderlist = JSON.parseArray(jsonStr, LineOrderInfo.class);
                            alldata.addAll(orderlist);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        lind_order_listview.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    lind_order_listview.onRefreshComplete();
                }
            }
        });

    }

}
