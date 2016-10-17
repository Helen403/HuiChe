package com.huiche.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.activity.BusinessDetailActivity;
import com.huiche.adapter.OnLineMyEvaluateAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author 个人中心--我的 评价
 */

public class EvaluateActivity extends BaseActivity {
    private PullToRefreshListView pull_list;
    private ListView mListView;
    private int page = 1;
    private int rows = 10;
    private SharedPreferences share;
    private OnLineMyEvaluateAdapter adapter;
    private TextView tv_no;

    private List<Map<String, String>> allList = new ArrayList<Map<String, String>>();

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {

        setContentView(R.layout.activity_evaluate);
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        TitleUtils.setInfo(this, "我的评价");
        pull_list = (PullToRefreshListView) findViewById(R.id.lind_evaluate_listview);
        pull_list.setMode(Mode.BOTH);
        mListView = pull_list.getRefreshableView();
        tv_no = (TextView) findViewById(R.id.tv_no);
    }

    @Override
    public void initData() {
        adapter = new OnLineMyEvaluateAdapter(this, allList);
        mListView.setAdapter(adapter);
        getLineEvaluateList(page);
    }

    //获取我的评价列表
    private void getLineEvaluateList(int page) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("rows", rows);
        params.put("menberId", share.getString("id", ""));
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(EvaluateActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.ONLINE_MUEVALUATE, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                tv_no.setVisibility(View.VISIBLE);
                pull_list.onRefreshComplete();
                pull_list.setVisibility(View.GONE);
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(EvaluateActivity.this, "请检查网络");
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
//							orderlist.clear();
                            JSONArray list = response
                                    .getJSONArray("rows");
                            List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
                            if (list.length() > 0) {
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject obj = list.getJSONObject(i);
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("businessStoreId", obj.optString("businessStoreId"));
                                    map.put("businessName", obj.optString("businessName"));
                                    map.put("level", obj.optString("level"));
                                    map.put("content", obj.optString("content"));
                                    map.put("photo", obj.optString("photo"));
                                    map.put("id", obj.optString("id"));
                                    map.put("time", obj.optString("time"));
                                    dataList.add(map);
                                }
                                allList.addAll(dataList);
                                adapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.ToastShowShort(EvaluateActivity.this, "没有更多数据");
                            }
                        }


                        if (allList.size() < 1) {
                            tv_no.setVisibility(View.VISIBLE);
                            pull_list.setVisibility(View.GONE);
                        }
                    } else {
                        toast(success);
                        pull_list.onRefreshComplete();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pull_list.onRefreshComplete();
                }
            }
        });
    }

    protected void toast(String success) {
        Toast.makeText(this, success, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setListeners() {
        pull_list.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    allList.clear();
                    page = 1;
                    getLineEvaluateList(page);
                } else {
                    page++;
                    getLineEvaluateList(page);
                }
            }
        });
        //点击评价跳转到商家详情页面
        pull_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.setClass(EvaluateActivity.this, BusinessDetailActivity.class);
                intent.putExtra("businessStoreId", allList.get(position - 1).get("businessStoreId"));
                intent.putExtra("empty", "0");
                startActivity(intent);

            }
        });
    }

}
