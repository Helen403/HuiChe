package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.PostResult.LineOrderDetail;
import com.huiche.PostResult.LineOrderInfo;
import com.huiche.R;
import com.huiche.activity.ExchangeOrderActivity;
import com.huiche.activity.PayDetailActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.DeleteDialog;
import com.huiche.view.MyListView;
import com.huiche.view.SystemDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineOrderAdapter extends BaseAdapter{
	private List<LineOrderInfo> dataList=new ArrayList<LineOrderInfo>();
	private Context mContext;
	private LayoutInflater inflater;
	private SharedPreferences share;
	private SystemDialog mDialog;
	private String kfNumber;
	private DeleteDialog dialog;
	private int location;
	
	public LineOrderAdapter(Context context,List<LineOrderInfo>list){
		this.mContext=context;
		this.dataList=list;
		inflater=LayoutInflater.from(context);
		share=context.getSharedPreferences("user_data", context.MODE_PRIVATE);
		dialog=new DeleteDialog(context,"是否删除该订单");

	}
	
	public void nitify(List<LineOrderInfo> dataLists){
		dataList.clear();
		dataList.addAll(dataLists);
		this.notifyDataSetChanged();
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
			convertView=inflater.inflate(R.layout.item_line_order, null);
			holder=new Holder();
			holder.myListview=(MyListView)convertView.findViewById(R.id.myListview);
			holder.tv_order=(TextView)convertView.findViewById(R.id.tv_orderID);
			holder.tv_shop_name=(TextView)convertView.findViewById(R.id.tv_shop_name);
			holder.tv_pay_type=(TextView)convertView.findViewById(R.id.tv_pay_type);
			holder.tv_date=(TextView)convertView.findViewById(R.id.tv_date);
			holder.tv_intenal=(TextView)convertView.findViewById(R.id.tv_intenal);
			holder.tv_wait_pay=(TextView)convertView.findViewById(R.id.tv_wait_pay);
			holder.tv_cancel_grey=(TextView)convertView.findViewById(R.id.tv_cancel_grey);
			holder.tv_cancel_org=(TextView)convertView.findViewById(R.id.tv_cancel_org);
			holder.ll_shop=(LinearLayout)convertView.findViewById(R.id.ll_shop);
			holder.ll_tip=(LinearLayout)convertView.findViewById(R.id.ll_tip);
			holder.line_view=(View)convertView.findViewById(R.id.line_view);
			holder.ll_paytype=(LinearLayout)convertView.findViewById(R.id.ll_allpaytype);
			holder.tv_call_blue=(TextView)convertView.findViewById(R.id.tv_call_blue);
			holder.view_code=(View)convertView.findViewById(R.id.view_code);
			holder.ll_code=(LinearLayout)convertView.findViewById(R.id.ll_code);
			holder.tv_pay_code=(TextView)convertView.findViewById(R.id.tv_pay_code);
			holder.delete_order=(ImageView)convertView.findViewById(R.id.delete_order);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		holder.tv_cancel_org.setVisibility(View.GONE);
		holder.tv_cancel_grey.setVisibility(View.GONE);
		holder.tv_wait_pay.setVisibility(View.GONE);
		holder.ll_tip.setVisibility(View.GONE);
		holder.line_view.setVisibility(View.GONE);
		holder.delete_order.setVisibility(View.GONE);
		
		LineOrderInfo lineOrderInfo=dataList.get(position);
		int length=dataList.size();
		String ss=lineOrderInfo.id;
		holder.tv_order.setText(lineOrderInfo.no);
		holder.tv_shop_name.setText(lineOrderInfo.businessName);
		holder.tv_pay_type.setText(lineOrderInfo.getPayType());
		holder.tv_date.setText(lineOrderInfo.createDate);
		holder.tv_intenal.setText(lineOrderInfo.allIntegral);

		
		String status=lineOrderInfo.status;
		boolean canCancel=dataList.get(position).canCancel;
		String phone=dataList.get(position).phone;

		//如果“status”字段都不是上面的状态，那就再判断一下当前的几个值是否符合这个要求：canCancel==true&&phone!=""&&phone!=undefined，如果为true，那么你就显示“联系商家”的按钮。
		//status为订单的状态,0为未验证(未消费),1为已完成，2为待付款,
		//如果“status”字段都不是上面的状态，那就再判断一下当前的几个值是否符合这个要求：canCancel==true&&phone!=""&&phone!=undefined，如果为true，那么你就显示“联系商家”的按钮。
		if(status.equals("0")){
			holder.ll_paytype.setVisibility(View.VISIBLE);
			holder.view_code.setVisibility(View.GONE);
			holder.ll_code.setVisibility(View.GONE);
			holder.delete_order.setVisibility(View.GONE);
			if(canCancel){
				holder.tv_cancel_org.setVisibility(View.VISIBLE);
				holder.tv_cancel_grey.setVisibility(View.GONE);
				holder.tv_wait_pay.setVisibility(View.GONE);
				holder.tv_call_blue.setVisibility(View.GONE);

			}else{
				holder.tv_cancel_org.setVisibility(View.GONE);
				holder.tv_cancel_grey.setVisibility(View.VISIBLE);
				holder.tv_wait_pay.setVisibility(View.GONE);
				holder.ll_tip.setVisibility(View.VISIBLE);
				holder.tv_call_blue.setVisibility(View.GONE);
			}
		}
		else if(status.equals("1")){
			holder.tv_cancel_org.setVisibility(View.GONE);
			holder.tv_cancel_grey.setVisibility(View.GONE);
			holder.tv_wait_pay.setVisibility(View.GONE);
			holder.ll_tip.setVisibility(View.GONE);
			holder.ll_paytype.setVisibility(View.VISIBLE);
			holder.tv_call_blue.setVisibility(View.GONE);
			holder.view_code.setVisibility(View.GONE);
			holder.ll_code.setVisibility(View.GONE);
			holder.delete_order.setVisibility(View.GONE);
			//显示联系商家
			if(canCancel && !phone.equals("")){
				holder.tv_call_blue.setVisibility(View.VISIBLE);
				holder.tv_cancel_org.setVisibility(View.GONE);
				holder.tv_cancel_grey.setVisibility(View.GONE);
				holder.tv_wait_pay.setVisibility(View.GONE);
				//显示商品验证码
//				holder.view_code.setVisibility(View.VISIBLE);
//				holder.ll_code.setVisibility(View.VISIBLE);
//				String code=dataList.get(position).checkNo;
//				holder.tv_pay_code.setText(code);
				holder.delete_order.setVisibility(View.GONE);
			}

			
		}else if(status.equals("2")){
			holder.tv_cancel_org.setVisibility(View.GONE);
			holder.tv_cancel_grey.setVisibility(View.GONE);
			holder.tv_wait_pay.setVisibility(View.VISIBLE);
			holder.ll_tip.setVisibility(View.GONE);
			holder.ll_paytype.setVisibility(View.GONE);
			holder.tv_call_blue.setVisibility(View.GONE);
			holder.view_code.setVisibility(View.GONE);
			holder.ll_code.setVisibility(View.GONE);
			holder.delete_order.setVisibility(View.VISIBLE);

		}



		//商品列表
		List<LineOrderDetail>list=new ArrayList<LineOrderDetail>();
		list=lineOrderInfo.productLists;
		if(list.size()>0){
			holder.ll_shop.setVisibility(View.VISIBLE);
			holder.line_view.setVisibility(View.VISIBLE);
			LineOrderShopAdapter adapter=new LineOrderShopAdapter(mContext, list);
			holder.myListview.setAdapter(adapter);
			
		}else{
			holder.ll_shop.setVisibility(View.GONE);
		}

		holder.tv_call_blue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//           获取系统客服电话
				getPhone();
			}
		});
		//付款
		holder.tv_wait_pay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent();
				String orderType=dataList.get(position).orderType;
				if(orderType.equals("4")){
					//进入买单模块,要对应传数据
					intent.setClass(mContext, PayDetailActivity.class);
					//这个id是订单id
					intent.putExtra("rows", dataList.get(position).getId());
					intent.putExtra("BusinessName",dataList.get(position).getBusinessName());
					intent.putExtra("businessID",dataList.get(position).getId());



					//allintegral是积分，需要转化为元,除以10
					String allin=dataList.get(position).getAllIntegral();
					if(!allin.equals("")){
						double dall=Double.parseDouble(allin)*0.1;
						intent.putExtra("businessMoney",dall+"");
					}

					intent.putExtra("freeMoney",dataList.get(position).getFreeServiceQuota());
					mContext.startActivity(intent);
				}else{
					//进入兑换模块
					String orderId=dataList.get(position).getId();

					if(!orderId.equals("")){
						int offlineOrderId=Integer.parseInt(orderId);
						intent = new Intent(mContext, ExchangeOrderActivity.class);
						intent.putExtra("offlineOrderId", offlineOrderId);
						mContext.startActivity(intent);
					}



				}



			}
		});




		//删除订单
		holder.delete_order.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//删除弹窗
				location=position;
				dialog.showDialog();
			}
		});
		dialog.tv_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dimissDialog();
			}
		});

		dialog.tv_confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dimissDialog();
				deleteOrder(dataList.get(location).no, dataList.get(location).getId(), location);
			}
		});

		
		
		
		return convertView;
	}
	//删除商品
	public void deleteOrder(String no,String id,final int location){
		RequestParams params=new RequestParams();
		params.put("no", no);
		params.put("id", id);
		//缓冲小菊花
		final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(mContext);
		bufferCircleDialog.show("正在加载",true,null);
		AsyncHttp.post(HttpConstantChc.DELETE_LINE_ORDER, params, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {

				super.onFailure(statusCode, headers, throwable, errorResponse);
				bufferCircleDialog.dialogcancel();
				ToastUtils.ToastShowShort(mContext, "请检查网络");
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
						dataList.remove(location);
						notifyDataSetChanged();
						ToastUtils.ToastShowShort(mContext, success);
					} else {
						ToastUtils.ToastShowShort(mContext, success);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
	}






	//获取服务器客服电话
	private void getPhone() {
		RequestParams params = new RequestParams();
		params.put("token", share.getString("token", ""));
		AsyncHttp.post(HttpConstantChc.GET_SYSTEM_PHONE, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
										  JSONObject response) {
						try {
							String msg = response.getString("msg");
							String status = response.getString("status");
							 kfNumber = response.getString("rows");
							if (status.equals("0")) {
								mDialog=new SystemDialog(mContext,kfNumber,"取消","呼叫");
								mDialog.showSystemDialog();
								mDialog.tv_cancel.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										mDialog.dimissSystemDialog();

									}
								});
								mDialog.tv_confirm.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent();
										mDialog.dimissSystemDialog();
										intent.setAction(Intent.ACTION_CALL);
										intent.setData(Uri.parse("tel:" + kfNumber));
										// 开启系统拨号器
										mContext.startActivity(intent);

									}
								});

							} else {
								Toast.makeText(mContext, msg, Toast.LENGTH_SHORT)
										.show();

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						super.onSuccess(statusCode, headers, response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
										  Throwable throwable, JSONObject errorResponse) {

						super.onFailure(statusCode, headers, throwable,
								errorResponse);
						Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT)
								.show();
					}

				});
	}
	public class Holder{
		public TextView tv_order,tv_shop_name,tv_pay_type,tv_date,tv_intenal,tv_button;
		public TextView tv_wait_pay,tv_cancel_grey,tv_cancel_org,tv_call_blue,tv_pay_code;
		public MyListView myListview;
		public LinearLayout ll_shop,ll_tip,ll_paytype,ll_code;
		public View line_view,view_code;
		public ImageView delete_order;
		
	}

}
