package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.PostResult.OnLineUnSendDetail;
import com.huiche.R;
import com.huiche.utils.ImagLoadUtils;

import java.util.ArrayList;
import java.util.List;


public class OnLineUnSendProductAdapter extends BaseAdapter{
	private List<OnLineUnSendDetail>data=new ArrayList<OnLineUnSendDetail>();
	private Context mContext;
	public OnLineUnSendProductAdapter(Context mContext,List<OnLineUnSendDetail>data){
		this.mContext=mContext;
		this.data=data;
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Object getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	ViewHolder holder;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_line_order_shop_detail, null);
			holder=new ViewHolder();
			holder.img_shop=(ImageView)convertView.findViewById(R.id.img_shop);
			holder.tv_intergal_price=(TextView)convertView.findViewById(R.id.tv_intergal_price);
			holder.tv_markey_price=(TextView)convertView.findViewById(R.id.tv_markey_price);
			holder.tv_shop_name=(TextView)convertView.findViewById(R.id.tv_shop_name);
			holder.tv_shop_num=(TextView)convertView.findViewById(R.id.tv_shop_num);
			holder.tv_sum_intergal=(TextView)convertView.findViewById(R.id.tv_sum_intergal);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		String count=data.get(position).num;
		String intergal=data.get(position).integral;
		holder.tv_shop_name.setText(data.get(position).name);
		holder.tv_markey_price.setText(data.get(position).price+"元");
		holder.tv_intergal_price.setText(intergal);
		holder.tv_shop_num.setText("X "+count);
		if(!count.equals("") && !intergal.equals("")){
			int s=Integer.parseInt(count);
			double jifen=Double.parseDouble(intergal);
			double sum=s*jifen;
			holder.tv_sum_intergal.setText(sum+"");
		}
		
		String path=data.get(position).images[0];
		ImagLoadUtils.setImage(path, holder.img_shop, mContext);
		//imageLoader.displayImage(path, holder.img_shop, options);
		return convertView;
	}
	public class ViewHolder{
		public ImageView img_shop;
		public TextView tv_shop_name,tv_shop_num,tv_markey_price,tv_intergal_price,tv_sum_intergal;
	}

}
