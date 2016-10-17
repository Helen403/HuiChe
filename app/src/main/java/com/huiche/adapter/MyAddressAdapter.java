package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.activity.mine.EditTextUserAddress;
import com.huiche.activity.mine.MyGoodsAddress;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.view.DeleteDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAddressAdapter extends BaseAdapter{
	private Context mContext;
	private List<Map<String,String>>allList=new ArrayList<Map<String,String>>();
	private SharedPreferences share;
	private int location,deleteP;
	private MyGoodsAddress activity;
	private DeleteDialog mDialog;

	public MyAddressAdapter(Context context,List<Map<String,String>>allList,MyGoodsAddress activity){
		this.mContext=context;
		this.allList=allList;
		share=context.getSharedPreferences("user_data", context.MODE_PRIVATE);
		this.activity=activity;
		mDialog=new DeleteDialog(context,"确定删除该地址?");


	}
	@Override
	public int getCount() {

		return allList.size();
	}

	@Override
	public Object getItem(int position) {

		return allList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
  ViewHolder holder;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_my_goods_address, null);
			holder=new ViewHolder();
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
			holder.tv_phone=(TextView)convertView.findViewById(R.id.tv_phone);
			holder.tv_moren=(TextView)convertView.findViewById(R.id.tv_moren);
			holder.tv_edit=(TextView)convertView.findViewById(R.id.tv_edit);
			holder.tv_delete=(TextView)convertView.findViewById(R.id.tv_delete);
			holder.check_mr=(CheckBox)convertView.findViewById(R.id.check_mr);
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(allList.get(position).get("name"));
		holder.tv_phone.setText(allList.get(position).get("tel"));
		final String province=allList.get(position).get("province");
		final String city=allList.get(position).get("city");
		final String address=allList.get(position).get("address");
		final String AddressId=allList.get(position).get("id");
		final String area=allList.get(position).get("area");
		holder.tv_address.setText(province+city+area+address);
		//判断是否默认
		final String isDefault=allList.get(position).get("isDefault");
		if(isDefault.equals("true")){
			holder.check_mr.setChecked(true);
			
		}else{
			holder.check_mr.setChecked(false);
		}
		//删除地址
		holder.tv_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteP=position;
				//显示删除弹窗
                  mDialog.showDialog();

				
			}
		});

		mDialog.tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dimissDialog();

			}
		});

		mDialog.tv_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String id=allList.get(deleteP).get("id");
				deleAddress(id);
				mDialog.dimissDialog();

			}
		});
		
		//设置成默认地址
		holder.tv_moren.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id=allList.get(position).get("id");
				location=position;
				saveMorenAddress(id);
				
			}
		});

		//编辑地址
		holder.tv_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
           Intent intent=new Intent(mContext, EditTextUserAddress.class);
				intent.putExtra("name",allList.get(position).get("name"));
				intent.putExtra("phone",allList.get(position).get("tel"));
				intent.putExtra("province",province);
				intent.putExtra("city",city);
				intent.putExtra("address",address);
				intent.putExtra("area", area);
				intent.putExtra("isDefault", isDefault);
				intent.putExtra("id", AddressId);
				activity.startActivityForResult(intent,1369);

			}
		});
		
		return convertView;
	}
	
	
	protected void saveMorenAddress(String id) {
		RequestParams params=new RequestParams();
		params.put("token", share.getString("token", ""));
		params.put("id", id);
		AsyncHttp.posts(HttpConstantChc.SAVE_MOREN_ADDRESS, params, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {

				super.onFailure(statusCode, headers, throwable, errorResponse);
				toast("请检查您的网络");
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {

				super.onSuccess(statusCode, headers, response);
				try {
					String status = response.getString("status");
					boolean success = response.getBoolean("success");
					String msg = response.getString("msg");
					if (status.equals("0")) {
						if (success) {

							for (int i = 0; i < allList.size(); i++) {
								Map<String, String> map = new HashMap<String, String>();
								map = allList.get(i);
								if (i == location) {
									map.put("isDefault", "true");
								} else {
									map.put("isDefault", "false");
								}

								allList.set(i, map);
							}
							notifyDataSetChanged();
							toast(msg);
						} else {
							toast(msg);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
		
		
		
		
	}
	//删除地址
	protected void deleAddress(String position) {
		RequestParams params=new RequestParams();
		params.put("token", share.getString("token", ""));
		params.put("id", position);
		AsyncHttp.posts(HttpConstantChc.DELETE_ADDRESS, params, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {

				super.onFailure(statusCode, headers, throwable, errorResponse);
				toast("请检查您的网络");

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {

				super.onSuccess(statusCode, headers, response);
				try {
					String status = response.getString("status");
					boolean success = response.getBoolean("success");
					String msg = response.getString("msg");
					if (status.equals("0")) {
						if (success) {
							allList.remove(deleteP);
							notifyDataSetChanged();
							toast(msg);
						} else {
							toast(msg);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		});
		
		
		
	}


	class ViewHolder{
		TextView tv_name,tv_address,tv_phone,tv_moren,tv_edit,tv_delete;
		CheckBox check_mr;
		
	}
	protected void toast(String success) {
		Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
		
	}








}
