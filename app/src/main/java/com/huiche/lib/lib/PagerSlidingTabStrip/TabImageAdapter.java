package com.huiche.lib.lib.PagerSlidingTabStrip;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiche.R;


public class TabImageAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

	private final int[] mImages = {R.mipmap.ic_perm_group_system_tools,
			R.mipmap.ic_perm_group_personal_info, R.mipmap.ic_perm_group_social_info};
	private Context mContext;
	
	public TabImageAdapter(Context context) {
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return mImages.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// looks a little bit messy here
		TextView v = new TextView(mContext);
		v.setBackgroundResource(R.color.background_window);
		v.setText("PAGE " + (position + 1));
		final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, 
				mContext.getResources().getDisplayMetrics());
		v.setPadding(padding, padding, padding, padding);
		v.setGravity(Gravity.CENTER);
		container.addView(v, 0);
		return v;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		container.removeView((View) view);
	}

	@Override
	public int getPageIconResId(int position) {
		return mImages[position];
	}
}
