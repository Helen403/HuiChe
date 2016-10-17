package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.utils.SetSizeUtils;

public class MyPersionalAdapter extends BaseAdapter{
	private String []strType;
	private int []imgData;
	private Context context;
	private LayoutInflater flater;
	public MyPersionalAdapter(Context context,String []strType,int []imgData){
		this.context=context;
		this.imgData=imgData;
		this.strType=strType;
		flater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return imgData.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
  Holder holder;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			holder=new Holder();
			convertView=flater.inflate(R.layout.item_mypersional, null);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.ll_quan=(LinearLayout)convertView.findViewById(R.id.ll_quan);
			holder.img_que=(ImageView)convertView.findViewById(R.id.img_que);
			holder.img_pic=(ImageView)convertView.findViewById(R.id.img_pic);
			SetSizeUtils.setSizeOnlyHeightOf(context, holder.tv_name, 20);
			SetSizeUtils.setSizeOnlyHeightOf(context, holder.img_pic, 20);
			SetSizeUtils.setSizeOnlyHeightOf(context, holder.img_que, 10);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
//		if(position==imgData.length-1){
//			holder.ll_quan.setVisibility(View.GONE);
//			holder.img_que.setVisibility(View.VISIBLE);
//			holder.img_que.setImageResource(imgData[position]);
//
//		}else{
			holder.ll_quan.setVisibility(View.VISIBLE);
			holder.tv_name.setText(strType[position]);
			holder.img_pic.setImageResource((imgData[position]));
		//}
		return convertView;
	}
	class Holder{
		public TextView tv_name;
		public ImageView img_pic;
		public LinearLayout ll_quan;
//		public RelativeLayout ll_que;
		public ImageView img_que;
		public LinearLayout li;
		
	}

}
