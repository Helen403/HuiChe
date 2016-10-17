package com.huiche.activity.mine;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.adapter.RedPacketAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ToastUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19.
 */
public class RedPacketActivity extends BaseActivity implements View.OnClickListener {
    public ImageButton imb_titleBack;
    public TextView tv_titleText;
    public PullToRefreshListView pull_listview;
    public ListView mListview;
    public int page = 1;
    public int rows = 10;
    public RedPacketAdapter adapter;
    public SharedPreferences share;
    public List<Map<String, String>> dataList = new ArrayList<>();
    private TextView tv_none;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_redpacket);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        tv_titleText.setText("我的红包");
        pull_listview = (PullToRefreshListView) findViewById(R.id.pull_listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        mListview = pull_listview.getRefreshableView();
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        tv_none = (TextView) findViewById(R.id.tv_none);
        if (tv_none != null)
            tv_none.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        adapter = new RedPacketAdapter(this, dataList);
        mListview.setAdapter(adapter);
        //获取红包记录
        getMyRedPacketData();
    }

    private void getMyRedPacketData() {
        String token = share.getString("token", "");
        String memberId = share.getString("id", "");
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("menberId", memberId);
        params.put("page", page);
        params.put("rows", 10);
        AsyncHttp.post(HttpConstantChc.MY_REDPACKET_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                String status = response.optString("status");
                String msg = response.optString("msg");
                pull_listview.onRefreshComplete();
//                int total = response.optInt("total");
                if (status.equals("0")) {
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    JSONArray row = response.optJSONArray("rows");
                    for (int i = 0; i < row.length(); i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        JSONObject obj = row.optJSONObject(i);
                        map.put("businessStoreName", obj.optString("businessStoreName"));
                        map.put("date", obj.optString("date"));
                        map.put("image", obj.optString("image"));
                        map.put("integral", obj.optString("integral"));
                        list.add(map);
                    }
                    if (row.length() < 1) {
                        ToastUtils.ToastShowShort(RedPacketActivity.this, "没有更多数据");
                    }
                    dataList.addAll(list);
                    adapter.notifyDataSetChanged();
                    //无数据
                    if (dataList.size() < 1) {
                        tv_none.setVisibility(View.VISIBLE);
                        pull_listview.setVisibility(View.GONE);
                    } else {
                        tv_none.setVisibility(View.GONE);
                        pull_listview.setVisibility(View.VISIBLE);

                    }

                } else {
                    ToastUtils.ToastShowShort(RedPacketActivity.this, msg);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(RedPacketActivity.this, "请检查网络");
                pull_listview.onRefreshComplete();
            }
        });

    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    page = 1;
                    dataList.clear();
                    getMyRedPacketData();
                } else {
                    page++;
                    getMyRedPacketData();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb_titleBack:
                finish();
                break;
        }
    }
}
