package com.huiche.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.PostResult.LineUnPayDetail;
import com.huiche.PostResult.LineUnPayInfo;
import com.huiche.R;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ToastUtils;
import com.huiche.view.MyListView;
import com.huiche.view.SystemDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineUnPayAdapter extends BaseAdapter{
	private Context mContext;
	private List<LineUnPayInfo>dataList=new ArrayList<LineUnPayInfo>();
	private SystemDialog systemDialog;
	private SharedPreferences share;
	private int cpo;

	public LineUnPayAdapter(Context context,List<LineUnPayInfo>dataList){
		this.mContext=context;
		this.dataList=dataList;
		share=context.getSharedPreferences("user_data",context.MODE_PRIVATE);
		systemDialog=new SystemDialog(mContext,"是否取消订单","取消","确定");
	}
	
	@Override
	public int getCount() {

		return dataList.size();
	}

	@Override
	public Object getItem(int position) {

		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	Holder holder;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_line_unpay, null);
			holder=new Holder();
			holder.tv_order=(TextView)convertView.findViewById(R.id.tv_orderID);
			holder.tv_shop_name=(TextView)convertView.findViewById(R.id.tv_shop_name);
			holder.tv_pay_type=(TextView)convertView.findViewById(R.id.tv_pay_type);
			holder.tv_date=(TextView)convertView.findViewById(R.id.tv_date);
			holder.tv_intenal=(TextView)convertView.findViewById(R.id.tv_intenal);
			
			//取消订单按钮
			holder.tv_cancel_org=(TextView)convertView.findViewById(R.id.tv_cancel_org);
			holder.tv_cancel_grey=(TextView)convertView.findViewById(R.id.tv_cancel_grey);
			holder.myListview=(MyListView)convertView.findViewById(R.id.myListview);
			holder.ll_shop=(LinearLayout)convertView.findViewById(R.id.ll_shop);
			holder.line_view=(View)convertView.findViewById(R.id.line_view);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		LineUnPayInfo info=dataList.get(position);
		holder.tv_order.setText(info.getNo());
		holder.tv_shop_name.setText(info.getBusinessName());
		holder.tv_pay_type.setText(info.getPayType());
		holder.tv_date.setText(info.getCreateDate());
		holder.tv_intenal.setText(info.getAllIntegral());
		
		holder.tv_cancel_org.setVisibility(View.GONE);
		holder.tv_cancel_grey.setVisibility(View.GONE);
		
		//商品列表
		String status=info.getStatus();
		boolean canCancel=info.isCanCancel();
		if(canCancel){
			holder.tv_cancel_org.setVisibility(View.VISIBLE);
			holder.tv_cancel_grey.setVisibility(View.GONE);
		}else {
			holder.tv_cancel_org.setVisibility(View.GONE);
			holder.tv_cancel_grey.setVisibility(View.VISIBLE);
		}
		List<LineUnPayDetail>data=new ArrayList<LineUnPayDetail>();
		data=dataList.get(position).getProductLists();
		if(data.size()>0){
			holder.ll_shop.setVisibility(View.VISIBLE);
			holder.line_view.setVisibility(View.VISIBLE);
			LineUnPayADetaildapter adapter=new LineUnPayADetaildapter(mContext, data);
			holder.myListview.setAdapter(adapter);
			
		}else{
			holder.ll_shop.setVisibility(View.GONE);
		}
		//取消订单
		holder.tv_cancel_org.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				systemDialog.showSystemDialog();
				cpo=position;
			}
		});


		systemDialog.tv_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				systemDialog.dimissSystemDialog();
			}
		});

		systemDialog.tv_confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				systemDialog.dimissSystemDialog();
				//取消订单
				String id=dataList.get(cpo).getId();
				String no=dataList.get(cpo).getNo();
				cancelOrder(id,no,cpo);
			}
		});

		
		
		
		
		
		return convertView;
	}

	private void cancelOrder(String id,String no, final int position) {
		//取消订单
		RequestParams params=new RequestParams();
		params.put("token",share.getString("token",""));
		params.put("id",id);
		params.put("no",no);
		AsyncHttp.posts(HttpConstantChc.CANCEL_LINE_ORDER, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					String status = response.getString("status");
					String msg = response.getString("msg");
					if (status.equals("0")) {
						ToastUtils.ToastShowShort(mContext, msg);
						dataList.remove(position);
						notifyDataSetChanged();
					} else {
						ToastUtils.ToastShowShort(mContext, msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				ToastUtils.ToastShowShort(mContext, "请检查网络");
			}
		});

	}

	public class Holder{
		public TextView tv_order,tv_shop_name,tv_pay_type,tv_date,tv_intenal,tv_button;
		public TextView tv_wait_pay,tv_cancel_grey,tv_cancel_org;
		public MyListView myListview;
		public LinearLayout ll_shop,ll_tip;
		public View line_view;
		
	}

}
