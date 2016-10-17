package com.huiche.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
/***
 * 	 首页 nearByFragment 中listview 的headview中的viewpage的是适配器
 * @author zane
 *
 */
public class ViewPageAdaper extends PagerAdapter {
	private List<View> viewLists;  
	public ViewPageAdaper(List<View> lists) {
		// TODO Auto-generated constructor stub
		this.viewLists=lists;
	}
	@Override
	public int getCount() {

		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0==arg1;
	}
	@Override
	public Object instantiateItem(View container, int position) {

		 ((ViewPager) container).addView(viewLists.get(position%viewLists.size()), 0);  
		 return viewLists.get(position%viewLists.size());
	}
	@Override
	public void destroyItem(View container, int position, Object object) {

		 ((ViewPager) container).removeView(viewLists.get(position%viewLists.size()));  
	}

}
