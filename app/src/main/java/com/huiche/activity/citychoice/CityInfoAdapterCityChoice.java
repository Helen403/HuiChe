package com.huiche.activity.citychoice;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseImageAdapter;
import com.huiche.utils.SetSizeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CityInfoAdapterCityChoice extends BaseImageAdapter<City> {
	private Context context;
	private List<City> list = new ArrayList<>();
	private ViewHolder holder;
	private String[] letterAll = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	public CityInfoAdapterCityChoice(Context mContext, List<City> datalist) {
		super(mContext, datalist);
		// TODO Auto-generated constructor stub
		this.context = mContext;
		this.list = datalist;
		
		

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int bbb=position;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.itemlistview_cityinfo, null);
			holder.cityName = (TextView) convertView
					.findViewById(R.id.cityInfoShow_listView);
			holder.cityNamePinyin = (TextView) convertView
					.findViewById(R.id.cityInfoShow_pinyin_listView);
			SetSizeUtils.setSizeOnlyHeightOf(context, holder.cityName, 14);
			SetSizeUtils.setSizeOnlyHeightOf(context, holder.cityNamePinyin, 14);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position >= 1) {
			//hideOrShow(true);
			String currentStr = getAlpha(list.get(position).getPinyi());
			String previewStr = (position - 1) >= 0 ? getAlpha(list
					.get(position - 1).getPinyi()) : " ";
			if (!previewStr.equals(currentStr)) {
				hideOrShow(true);
			} else {
				hideOrShow(false);
			}
		}
		holder.cityName.setText(list.get(position).getName());
		holder.cityName.setTextColor(Color.RED);
		holder.cityNamePinyin.setText(getAlpha(list.get(position).getPinyi()));

		return convertView;
	}

	class ViewHolder {
		private TextView cityName;
		private TextView cityNamePinyin;
		private View filamentShow;

	}

	public void refreshData(List<City> lists) {
		list.clear();
		list.addAll(lists);
		notifyDataSetChanged();
	}
	/**
	 * 是否显示拼音布局
	 * @param flag
	 */
	public void hideOrShow(boolean flag){
		if(flag){
			holder.cityNamePinyin.setVisibility(View.VISIBLE);
		}else{
			holder.cityNamePinyin.setVisibility(View.GONE);
		}
	}
	// 获得汉语拼音首字母
		private String getAlpha(String str) {
			if (str == null) {
				return "#";
			}
			if (str.trim().length() == 0) {
				return "#";
			}
			char c = str.trim().substring(0, 1).charAt(0);
			// 正则表达式，判断首字母是否是英文字母
			Pattern pattern = Pattern.compile("^[A-Za-z]+$");
			if (pattern.matcher(c + "").matches()) {
				return (c + "").toUpperCase();
			} else if (str.equals("0")) {
				return "定位";
			} else if (str.equals("1")) {
				return "最近";
			} else if (str.equals("2")) {
				return "热门";
			} else if (str.equals("3")) {
				return "全部";
			} else {
				return "#";
			}
		}

}
