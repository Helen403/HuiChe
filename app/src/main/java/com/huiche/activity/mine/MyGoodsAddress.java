package com.huiche.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.adapter.MyAddressAdapter;
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

public class MyGoodsAddress extends BaseActivity implements OnClickListener{
    private TextView tv_right_title;
    private PullToRefreshListView pull_list;
    private ListView mListView;
    private LinearLayout ll_no;
	private SharedPreferences share;
//	private int page=1;
//	private int rows=10;
	private List<Map<String,String>>allList=new ArrayList<Map<String,String>>();
	private List<Map<String,String>>dataList=new ArrayList<Map<String,String>>();
	private MyAddressAdapter adapter;
	
	@Override
	public void dealLogicBeforeFindView() {
	}

	@Override
	public void findViews() {
		setContentView(R.layout.activity_my_goods_address);
		TitleUtils.setInfoText(MyGoodsAddress.this, "收货地址", "添加");
		share=getSharedPreferences("user_data", MODE_PRIVATE);
		tv_right_title=(TextView)findViewById(R.id.tv_right_title);
		pull_list=(PullToRefreshListView)findViewById(R.id.lv_address);
		ll_no=(LinearLayout)findViewById(R.id.ll_no);
		pull_list.setMode(Mode.DISABLED);
		mListView=pull_list.getRefreshableView();
		
		
	}

	@Override
	public void initData() {
		adapter=new MyAddressAdapter(MyGoodsAddress.this, allList,MyGoodsAddress.this);
		mListView.setAdapter(adapter);
		getAddressList();
		
	}
	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		//getAddressList();
	}
	@Override
	protected void onRestart() {

		super.onRestart();
		//getAddressList();
	}

	private void getAddressList() {
		RequestParams params=new RequestParams();
//		params.put("page", page);
//		params.put("rows", rows);
		params.put("token", share.getString("token", ""));
		//缓冲小菊花
		final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(MyGoodsAddress.this);
		bufferCircleDialog.show("正在加载", false, null);
		AsyncHttp.posts(HttpConstantChc.ADDRESS_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {

//				super.onFailure(statusCode, headers, throwable, errorResponse);
				ll_no.setVisibility(View.VISIBLE);
				pull_list.onRefreshComplete();
				pull_list.setVisibility(View.GONE);
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
						pull_list.onRefreshComplete();
						if (success.equals("success")) {
							JSONArray list = response
									.getJSONArray("rows");
							if (list.length() > 0) {
								allList.clear();
								dataList.clear();
								pull_list.setVisibility(View.VISIBLE);
								ll_no.setVisibility(View.GONE);
								for (int i = 0; i < list.length(); i++) {
									Map<String, String> map = new HashMap<String, String>();
									JSONObject obj = list.getJSONObject(i);
									map.put("id", obj.optString("id"));
									map.put("area", obj.optString("area"));
									map.put("isDefault", obj.optString("isDefault"));
									map.put("address", obj.optString("address"));
									map.put("zipcode", obj.optString("zipcode"));
									map.put("tel", obj.optString("tel"));
									map.put("name", obj.optString("name"));
									map.put("province", obj.optString("province"));
									map.put("city", obj.optString("city"));
									dataList.add(map);
								}
								allList.addAll(dataList);
								adapter.notifyDataSetChanged();
							} else {
								ll_no.setVisibility(View.VISIBLE);
								pull_list.setVisibility(View.GONE);
							}


//							String jsonStr=list.toString();
//							orderlist=JSON.parseArray(jsonStr, LineOrderInfo.class);
//							alldata.addAll(orderlist);
//							adapter.notifyDataSetChanged();
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
	@Override
	public void setListeners() {
		tv_right_title.setOnClickListener(this);
		//pulltorefresh上下滑动加载数据
//		pull_list.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				if(refreshView.isHeaderShown()){
//					allList.clear();
//					page=1;
//					getAddressList();
//				}else{
//					page++;
//					getAddressList();
//				}
//			}
//		});
		
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch(v.getId()){
		
		//添加地址
		case R.id.tv_right_title:
			intent.setClass(MyGoodsAddress.this, AddAddressActivity.class);
			startActivityForResult(intent, 1100);
			break;
		}
		
	}
	protected void toast(String success) {
		Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1100&& resultCode==1101){
			getAddressList();
		}
		//修改地址后，刷新数据
		else if(requestCode==1369 && resultCode==1132){
			getAddressList();
		}
	}


}
