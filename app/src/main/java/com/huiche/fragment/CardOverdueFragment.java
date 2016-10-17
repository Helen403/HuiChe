package com.huiche.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.MyCardResultData;
import com.huiche.R;
import com.huiche.activity.mine.MyCardDetaiActivity;
import com.huiche.adapter.CardWaitUseAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
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
 * @author chc
 *         已过期卡券Fragment
 */
public class CardOverdueFragment extends BaseFragment {

    private View contentView;
    private PullToRefreshListView pull_listview;
    private ListView mListView;
    private int page = 1;
    private int rows = 10;
    private int status = 2;
    private SharedPreferences share;
    private List<MyCardResultData> dataList = new ArrayList<MyCardResultData>();
    private List<MyCardResultData> allData = new ArrayList<MyCardResultData>();
    private CardWaitUseAdapter adapter;
    private TextView tv_none;
//    private int count = 0;

    //status为1时为待使用,status为2时为已过期,	status为3时为已使用
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_card_overdue, container, false);
        return contentView;
    }

    @Override
    public void findViews() {
        pull_listview = (PullToRefreshListView) contentView.findViewById(R.id.lv_card);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        mListView = pull_listview.getRefreshableView();
        share = getActivity().getSharedPreferences("user_data", getActivity().MODE_PRIVATE);
        tv_none = (TextView) contentView.findViewById(R.id.tv_none);

    }

    @Override
    public void initData() {
        adapter = new CardWaitUseAdapter(getActivity(), allData, status);
        mListView.setAdapter(adapter);
        getOverdueCardData();

    }

    private void getOverdueCardData() {
        //获取卡券数据
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("page", page);
        params.put("rows", rows);
        params.put("status", status);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(getActivity());
        bufferCircleDialog.show("正在加载", true, null);
        AsyncHttp.posts(HttpConstantChc.WAIT_USE_CARD_DATA, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        pull_listview.onRefreshComplete();
                        JSONArray obj = response.getJSONArray("rows");
                        if (obj.length() > 0) {
                            String result = obj.toString();
                            dataList = JSON.parseArray(result, MyCardResultData.class);
                            allData.addAll(dataList);
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.ToastShowShort(getActivity(), "没有更多数据");

                        }

                        if (allData.size() < 1) {
                            tv_none.setVisibility(View.VISIBLE);
                            pull_listview.setVisibility(View.GONE);

                        } else {
                            tv_none.setVisibility(View.GONE);
                            pull_listview.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtils.ToastShowShort(getActivity(), msg);
                        pull_listview.onRefreshComplete();
                    }

                } catch (JSONException e) {
                    pull_listview.onRefreshComplete();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(getActivity(), "请检查网络");
                pull_listview.onRefreshComplete();
                pull_listview.onRefreshComplete();
                tv_none.setVisibility(View.GONE);
                bufferCircleDialog.dialogcancel();
            }
        });
    }

    @Override
    public void setListeners() {
        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    allData.clear();
                    page = 1;
                    getOverdueCardData();
                } else {
                    page++;
                    getOverdueCardData();
                }
            }
        });

        pull_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Log.i("ssssssss", position + " : " + parent.getAdapter().getItem(position));
                String cardId = allData.get(position - 1).getId();
                String couponsTypeId = allData.get(position - 1).getCouponsTypeId();
                String businessStoreImage = allData.get(position - 1).getBusinessStoreImage();
                Intent intent = new Intent(getActivity(), MyCardDetaiActivity.class);
                String detai = allData.get(position - 1).getDetails();
                String businessStoreName = allData.get(position - 1).getBusinessStoreName();
                intent.putExtra("businessStoreName", businessStoreName);
                intent.putExtra("userule", detai);
                intent.putExtra("id", cardId);
                intent.putExtra("status", status);
                intent.putExtra("couponsTypeId", couponsTypeId);
                intent.putExtra("businessStoreImage", businessStoreImage);
                intent.putExtra("businessStoreId", allData.get(position - 1).getBusinessStoreId());
                //	startActivity(intent);
                intent.putExtra("enterType", 2);
                startActivityForResult(intent, 5689);

            }
        });

    }

    @Override
    public void initViews() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5689 && resultCode == 1542) {
            allData.clear();
            getOverdueCardData();
        }


    }

}
