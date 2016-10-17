package com.huiche.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.PostResult.IntegralInComeInfo;
import com.huiche.R;
import com.huiche.adapter.IntegralOutComeAdapter;
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



/***
 * 
 * @author Administrator
 * 个人中心--积分支出
 *
 */

public class IntegralOutComeActivity extends BaseActivity {
	private PullToRefreshListView pull_list;
	private ListView mListView;
	private int page=1;
	private int rows=10;
	private SharedPreferences share;
    private IntegralOutComeAdapter adapter;
    private List<IntegralInComeInfo> orderlist=new ArrayList<IntegralInComeInfo>();
	private List<IntegralInComeInfo> alldata=new ArrayList<IntegralInComeInfo>();

	@Override
	public void dealLogicBeforeFindView() {

		
	}
	@Override
	public void findViews() {
		setContentView(R.layout.activity_integral_outcome);
		TitleUtils.setInfo(this, "积分支出");
		share=getSharedPreferences("user_data", MODE_PRIVATE);
		Intent intent=getIntent();
		String out_integral = intent.getStringExtra("integral_out");
		pull_list=(PullToRefreshListView)findViewById(R.id.lv_intergral);
		TextView tv_out = (TextView) findViewById(R.id.tv_out);
		tv_out.setText("-" + out_integral);
		pull_list.setMode(Mode.BOTH);
		mListView=pull_list.getRefreshableView();
	}

	@Override
	public void initData() {
		getIntegraloutCome();
		adapter=new IntegralOutComeAdapter(this, alldata);
		mListView.setAdapter(adapter);
		
	}
	private void getIntegraloutCome() {
		RequestParams params=new RequestParams();
		params.put("page", page);
		params.put("rows", rows);
		boolean type = false;
		params.put("type", type);
		params.put("token", share.getString("token", ""));
		params.put("id", share.getString("id", ""));
		//缓冲小菊花
		final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(IntegralOutComeActivity.this);
		bufferCircleDialog.show("正在加载",false,null);
		AsyncHttp.posts(HttpConstantChc.INTEGRAL_INCOME, params, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {
//				super.onFailure(statusCode, headers, throwable, errorResponse);
				pull_list.onRefreshComplete();
				bufferCircleDialog.dialogcancel();
				ToastUtils.ToastShowShort(IntegralOutComeActivity.this, "请检查网络");
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
							//orderlist.clear();
							JSONArray list = response
									.getJSONArray("rows");
							String jsonStr = list.toString();
							orderlist = JSON.parseArray(jsonStr, IntegralInComeInfo.class);
							alldata.addAll(orderlist);
							adapter.notifyDataSetChanged();
						}
					} else {
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
		pull_list.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if(refreshView.isHeaderShown()){
					alldata.clear();
					page=1;
					getIntegraloutCome();
				}else{
					page++;
					getIntegraloutCome();
				}
			}
		});
		
	}

}
