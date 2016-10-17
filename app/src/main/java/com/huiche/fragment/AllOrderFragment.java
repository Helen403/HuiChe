package com.huiche.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.OnLineAllOrderInfo;
import com.huiche.R;
import com.huiche.adapter.OnLineAllOrderAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
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
 * 
 * @author Administrator
 * 线上订单-全部订单
 *
 */

public class AllOrderFragment extends BaseFragment {
	private View view;
	private PullToRefreshListView order_listview;
	private TextView tv_none;
	private ListView myListview;
	private SharedPreferences share;
	private int page=1,rows=10;
	private List<OnLineAllOrderInfo>orderlist=new ArrayList<OnLineAllOrderInfo>();
	private List<OnLineAllOrderInfo>alldata=new ArrayList<OnLineAllOrderInfo>();
	private OnLineAllOrderAdapter adapter;


	@Override
	public View inflateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_all_order, container,false);
		return view;
	}
	@Override
	public void findViews() {
		order_listview=(PullToRefreshListView)view.findViewById(R.id.order_listview);
		order_listview.setMode(PullToRefreshBase.Mode.BOTH);
		share=getActivity().getSharedPreferences("user_data", getActivity().MODE_PRIVATE);
		myListview=order_listview.getRefreshableView();
		tv_none=(TextView)view.findViewById(R.id.tv_none);
		
	}

	@Override
	public void initData() {
		adapter=new OnLineAllOrderAdapter(getActivity(),alldata);
		myListview.setAdapter(adapter);
		getLineAllOrder();
		
	}
	//获取全部订单
	private void getLineAllOrder() {
		RequestParams params=new RequestParams();
		params.put("page", page);
		params.put("rows", rows);
		String token=share.getString("token", "");
		params.put("token", share.getString("token", ""));
		//缓冲小菊花
		final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(getActivity());
		bufferCircleDialog.show("正在加载", false, null);
		AsyncHttp.posts(HttpConstantChc.LINE_ALL_ORDER, params, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {

				super.onFailure(statusCode, headers, throwable, errorResponse);
				tv_none.setVisibility(View.VISIBLE);
				order_listview.onRefreshComplete();
				order_listview.setVisibility(View.GONE);
				bufferCircleDialog.dialogcancel();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {

				super.onSuccess(statusCode, headers, response);
				try {
					bufferCircleDialog.dialogcancel();
					String status = response.getString("status");
					String success = response.getString("msg");
					if (status.equals("0")) {
						orderlist.clear();
						tv_none.setVisibility(View.GONE);
						order_listview.onRefreshComplete();
						order_listview.setVisibility(View.VISIBLE);
						if (success.equals("success")) {
							JSONArray list = response.getJSONArray("rows");
							if (list.length() == 0) {
								toast("没有更多数据");
							}
							String jsonStr = list.toString();
							orderlist = JSON.parseArray(jsonStr, OnLineAllOrderInfo.class);
							alldata.addAll(orderlist);
							adapter.notifyDataSetChanged();
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
					getLineAllOrder();
				} else {
					page++;
					getLineAllOrder();
					order_listview.onRefreshComplete();
				}


			}
		});

		order_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String resu = alldata.get(position).allPrice;


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
