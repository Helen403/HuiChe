package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.adapter.AdapterListView_MoreProductActivity;
import com.huiche.base.BaseActivity;
import com.huiche.bean.BusinessInfo;
import com.huiche.bean.BusinessInfo_of;
import com.huiche.constant.HttpConstantHao;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.TitleUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/***
 * 首页附近中  griView的 更多页面
 * @author Administrator
 *
 */
public  class MoreProductActivity extends BaseActivity implements OnClickListener{
	/***
	 * 显示商品的数据ListView
	 */
	private ListView listView;
	/***
	 * 存放信息的集合
	 */
	private List<BusinessInfo> listData=new ArrayList<>();
	/***
	 * 存放businessinfo中list的数据
	 */
	private List<BusinessInfo_of> listData_of=new ArrayList<>();
	/***
	 * 空集合
	 */
	private List<BusinessInfo> emptyList=new ArrayList<>();
	/***
	 * 本界面lsitView的适配器
	 */
	private AdapterListView_MoreProductActivity adapter;
	private PullToRefreshListView pullTolistView;
	private Context mContext;

	@Override
	public void dealLogicBeforeFindView() {

		mContext = this;
	}
	@Override
	public void findViews() {

		setContentView(R.layout.activity_moreproduct);
		pullTolistView=(PullToRefreshListView)findViewById(R.id.listView_moreProduct_Activity);
		pullTolistView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		listView = pullTolistView.getRefreshableView();
	}

	@Override
	public void initData() {
		 adapter=new AdapterListView_MoreProductActivity(this, listData);
		 listView.setAdapter(adapter);

		Intent intent=getIntent();
		String titleName=intent.getStringExtra("title");
		TitleUtils.setInfo(this, titleName);
		getDataFromWeb(true);
	}

	private void getDataFromWeb(boolean show) {

		RequestParams params=new RequestParams();
		//缓冲小菊花
		final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(MoreProductActivity.this);
		if(show){
			bufferCircleDialog.show("正在加载",true,null);
		}
		AsyncHttp.posts(HttpConstantHao.GET_BUSINESS_INFO, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {

				super.onSuccess(statusCode, headers, response);
				if (bufferCircleDialog.isShowDialog()) {
					bufferCircleDialog.dialogcancel();
				}
				try {
					if (response.getString("msg").equals("success")) {
						JSONArray rows = response.getJSONArray("rows");
						if (rows.length() > 0) {
							emptyList.clear();
							for (int i = 0; i < rows.length(); i++) {
								BusinessInfo info = new BusinessInfo();
								JSONObject obj = rows.getJSONObject(i);
								String pName = obj.getString("pName");
								info.setpName(pName);
								//listData_of.clear();
								JSONArray sName = obj.getJSONArray("sName");
//								Log.d("Zane", sName.toString());
								listData_of = new ArrayList<>();
								for (int j = 0; j < sName.length(); j++) {
									int a = sName.length();
									BusinessInfo_of info_of = new BusinessInfo_of();
									String name = sName.getJSONObject(j).getString("name");
									info_of.setName(name);
									listData_of.add(info_of);
								}
								info.setsName(listData_of);
								emptyList.add(info);
//							listData.add(info);
							}
							listData.addAll(emptyList);
							adapter.notifyDataSetChanged();
						} else {
//							ToastUtils.ToastShowShort(mContext,"没有更多数据了");
						}
						pullTolistView.onRefreshComplete();
//						adapter.refreshData(listData);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				if (bufferCircleDialog.isShowDialog()) {
					bufferCircleDialog.dialogcancel();
				}
				pullTolistView.onRefreshComplete();
			}
		});
	}
	@Override
	public void setListeners() {
		pullTolistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if(refreshView.isHeaderShown()){
					//下拉刷新
					listData.clear();
					getDataFromWeb(false);
				}else{
					//上拉加载

				}
			}
		});

	}

	@Override
	public void onClick(View v) {

		
	}

}
