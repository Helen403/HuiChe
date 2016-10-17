package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.PostResult.Province;
import com.huiche.R;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author Administrator
 * 省份列表
 *
 */

public class ProvinceListAdapter extends BaseAdapter{
	private Context mContext;
	private List<Province>dataList=new ArrayList<Province>();
	public ProvinceListAdapter(Context context,List<Province>dataList){
		this.mContext=context;
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_province_list, null);
			holder=new ViewHolder();
			holder.tv_province=(TextView)convertView.findViewById(R.id.tv_province);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		Province pro=dataList.get(position);
		String name=pro.name;
		holder.tv_province.setText(name);
		return convertView;
	}
	class ViewHolder{
		TextView tv_province;
	}

}
