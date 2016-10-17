package com.huiche.activity.mine;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.adapter.MessageAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.TitleUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends BaseActivity {
    private PullToRefreshListView pull_listview;
    private ListView mListview;
    private SharedPreferences share;
    private int page = 1;
    private int rows = 10;
    private MessageAdapter adapter;
    private List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> allList = new ArrayList<Map<String, String>>();
    private TextView tv_none;

    @Override
    public void dealLogicBeforeFindView() {


    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_message);
        TitleUtils.setInfo(MessageActivity.this, "系统消息");
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        pull_listview = (PullToRefreshListView) findViewById(R.id.lv_message);
        pull_listview.setMode(Mode.BOTH);
        mListview = pull_listview.getRefreshableView();
        tv_none = (TextView) findViewById(R.id.tv_none);
    }

    @Override
    public void initData() {
        adapter = new MessageAdapter(this, allList);
        mListview.setAdapter(adapter);
        getSystemMessages();

    }

    //获取系统消息
    private void getSystemMessages() {
        RequestParams params = new RequestParams();
        params.put("menberId", share.getString("id", ""));
        params.put("page", page);
        params.put("rows", rows);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(MessageActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.SYSTEM_MESSAGE, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//				super.onFailure(statusCode, headers, throwable, errorResponse);
                pull_listview.onRefreshComplete();
                tv_none.setVisibility(View.VISIBLE);
                pull_listview.setVisibility(View.GONE);
                toast("请检查网络");
                bufferCircleDialog.dialogcancel();
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
                            tv_none.setVisibility(View.GONE);
                            pull_listview.setVisibility(View.VISIBLE);
                            dataList.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            for (int i = 0; i < list.length(); i++) {
                                Map<String, String> map = new HashMap<String, String>();
                                JSONObject obj = list.getJSONObject(i);
                                map.put("id", obj.optString("id", ""));
                                map.put("content", obj.optString("content", ""));
                                map.put("status", obj.optString("status", ""));
                                map.put("time", obj.optString("time", ""));
                                dataList.add(map);
                            }
                            allList.addAll(dataList);
                            pull_listview.onRefreshComplete();
                            adapter.notifyDataSetChanged();

                        }
                    } else {
                        pull_listview.onRefreshComplete();
                        tv_none.setVisibility(View.VISIBLE);
                        pull_listview.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    pull_listview.onRefreshComplete();
                }
            }
        });


    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setListeners() {
        pull_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    allList.clear();
                    page = 1;
                    getSystemMessages();
                } else {
                    page++;
                    getSystemMessages();
                }
            }
        });

    }

}
