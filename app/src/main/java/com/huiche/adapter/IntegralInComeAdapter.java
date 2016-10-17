package com.huiche.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.PostResult.IntegralInComeInfo;
import com.huiche.R;

import java.util.ArrayList;
import java.util.List;

public class IntegralInComeAdapter extends BaseAdapter{
	private Context mContext;
	private List<IntegralInComeInfo> dataList=new ArrayList<IntegralInComeInfo>();
	public IntegralInComeAdapter(Context mContext,List<IntegralInComeInfo>dataList){
		this.mContext=mContext;
		this.dataList=dataList;
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

	ViewHolder holder;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_integral_income, null);
			holder=new ViewHolder();
			holder.tv_integral=(TextView)convertView.findViewById(R.id.money_integral);
			holder.tv_content=(TextView)convertView.findViewById(R.id.tv_why);
			holder.tv_date=(TextView)convertView.findViewById(R.id.tv_date);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		IntegralInComeInfo info=dataList.get(position);
		
		String integral=info.quota;
		holder.tv_integral.setText("+"+integral);
		
		String reason=info.reason;
		holder.tv_content.setText(reason);
		
		String date=info.time;
		holder.tv_date.setText(date);
		
		
		return convertView;
	}
	class ViewHolder{
		public TextView tv_integral,tv_content,tv_date;
		
		
		
	}

}
