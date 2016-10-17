package com.huiche.activity.mine;

import android.content.SharedPreferences;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.LineWaitEvaluate;
import com.huiche.R;
import com.huiche.adapter.LineWaitEvaluateAdapter;
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

public class LineWaitEvaluateActivity extends BaseActivity {
    private PullToRefreshListView pull_listview;
    private ListView mListView;
    private SharedPreferences share;
    private int page = 1;
    private int rows = 10;
    private List<LineWaitEvaluate> dataList = new ArrayList<LineWaitEvaluate>();
    private List<LineWaitEvaluate> allList = new ArrayList<LineWaitEvaluate>();
    private LineWaitEvaluateAdapter adapter;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.acitivty_line_wait_evaluate);
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        TitleUtils.setInfo(this, "待评价");
        pull_listview = (PullToRefreshListView) findViewById(R.id.lind_evaluate_listview);
        pull_listview.setMode(Mode.BOTH);
        mListView = pull_listview.getRefreshableView();
    }

    @Override
    public void initData() {
        //获取评价列表
        getEvaluateList(page);
        adapter = new LineWaitEvaluateAdapter(this, allList);
        mListView.setAdapter(adapter);

    }


    private void getEvaluateList(int page) {
        RequestParams params = new RequestParams();
        params.put("id", share.getString("id", ""));
//        String idd = share.getString("id", "");
        params.put("page", page);
        params.put("rows", rows);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(LineWaitEvaluateActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.LINE_EVALUATE, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//				super.onFailure(statusCode, headers, throwable, errorResponse);
                pull_listview.onRefreshComplete();
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(LineWaitEvaluateActivity.this, "请检查网络");
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
                            //orderlist.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            if (list.length() == 0) {
                                ToastUtils.ToastShowShort(LineWaitEvaluateActivity.this, "没有更多数据");
                            }
                            String jsonStr = list.toString();
                            dataList = JSON.parseArray(jsonStr, LineWaitEvaluate.class);
                            allList.addAll(dataList);
                            adapter.notifyDataSetChanged();
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
                    allList.clear();
                    page = 1;
                    getEvaluateList(page);
                } else {
                    page++;
                    getEvaluateList(page);
                }

            }
        });
    }

}
