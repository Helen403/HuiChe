package com.huiche.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.huiche.PostResult.LineWaitEvaluate;
import com.huiche.R;
import com.huiche.utils.ImagLoadUtils;

import java.util.ArrayList;
import java.util.List;

public class LineWaitEvaluateAdapter extends BaseAdapter{
	private Context mContext;
	private List<LineWaitEvaluate> dataList=new ArrayList<LineWaitEvaluate>();

	
	public LineWaitEvaluateAdapter(Context context,List<LineWaitEvaluate>dataList){
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
		//item_line_evaluate
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_line_evaluate, null);
			holder=new ViewHolder();
			holder.img_user=(ImageView)convertView.findViewById(R.id.img_user);
			holder.tv_shop_address=(TextView)convertView.findViewById(R.id.tv_shop_address);
			holder.tv_shop_name=(TextView)convertView.findViewById(R.id.tv_shop_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		String path=dataList.get(position).photo;
		ImagLoadUtils.setImage(path, holder.img_user, mContext);
		holder.tv_shop_name.setText(dataList.get(position).businessName);
		holder.tv_shop_address.setText(dataList.get(position).businessArea);
		
		
		return convertView;
	}
	class ViewHolder{
		ImageView img_user;
		TextView tv_shop_name,tv_shop_address;
		RatingBar ratingbar;
		
	}
	
	

}
