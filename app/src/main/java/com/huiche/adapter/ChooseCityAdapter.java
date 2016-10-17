package com.huiche.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChooseCityAdapter extends BaseAdapter{
	public Context mContext;
	public List<Map<String,String>>dataList=new ArrayList<Map<String,String>>();

	public ChooseCityAdapter(Context mContext,List<Map<String,String>>dataList){
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return convertView;
	}

}
