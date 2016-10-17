package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseImageAdapter;
import com.huiche.bean.CityBean;
import com.huiche.utils.SetSizeUtils;

import java.util.ArrayList;
import java.util.List;

public class CityInfoAdapterListView extends BaseImageAdapter<CityBean> {
	private Context context;
	private List<CityBean> list=new ArrayList<>();
	private ViewHolder holder;
	public CityInfoAdapterListView(Context mContext, List<CityBean> datalist) {
		super(mContext, datalist);
		this.context=mContext;
		this.list=datalist;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			 holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.itemlistview_cityinfo, null);
			holder.cityName=(TextView)convertView.findViewById(R.id.cityInfoShow_listView);
			SetSizeUtils.setSizeOnlyHeightOf(context, holder.cityName, 14);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
			holder.cityName.setText(list.get(position).getName());
			
		return convertView;
	}

	class ViewHolder {
		private TextView cityName;
	
	}
//	public void refreshData(List<CityBean> lists){
//		list.clear();
//		list.addAll(lists);
//		notifyDataSetChanged();
//	}

}
