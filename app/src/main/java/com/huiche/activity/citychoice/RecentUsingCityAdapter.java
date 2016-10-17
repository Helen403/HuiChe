package com.huiche.activity.citychoice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseImageAdapter;
import com.huiche.listener.GridViewFromNearByListener;
import com.huiche.utils.SetSizeUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * 给 在nearbyfragment中城市选择的gridview的适配器
 * 
 * @author zane
 * 
 */
public class RecentUsingCityAdapter extends BaseImageAdapter<String> {
	private ViewHolder holder;
	private List<String> mList = new ArrayList<>();
	private TextView cityName;
	private Context mContext;
	private LinearLayout cityNmaeGroup;
	private GridViewFromNearByListener listener;
	/***
	 * 选中的那个positiong,默认值为 一次都没选中
	 */
	private int selectPositon = -1;

	public RecentUsingCityAdapter(Context context, List<String> list,
			GridViewFromNearByListener activity) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.mList = list;
		this.mContext = context;
		this.listener = activity;

	}

	public void changeBackGroud(int positions) {
		this.selectPositon = positions;
		notifyDataSetChanged();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {


		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_gridview_city_nearby,
					null);
			cityName = (TextView) convertView
					.findViewById(R.id.cityName_itemGridview_nearByFragment);
			cityNmaeGroup = (LinearLayout) convertView
					.findViewById(R.id.cityNmaeGroup_itemGridview_nearByFragment);
			SetSizeUtils.setSizeOnlyHeightOf(mContext, cityNmaeGroup, 12);
			holder.cityNames = cityName;
			holder.cityNmaeGroups = cityNmaeGroup;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == selectPositon) {
			holder.cityNames.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.color.selected));
		} else {
			holder.cityNames.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.color.white));
		}
		holder.cityNames.setText(mList.get(position));
		holder.cityNames.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				listener.changColorSetData(mList.get(position), position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		private TextView cityNames;
		private LinearLayout cityNmaeGroups;

	}

	public void reflishData(List<String> lists) {
		mList.clear();
		mList.addAll(lists);
		notifyDataSetChanged();
	}
}
