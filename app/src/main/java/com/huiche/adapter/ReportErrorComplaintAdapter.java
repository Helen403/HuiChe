package com.huiche.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ReportErrorComplaintAdapter extends FragmentPagerAdapter {
	public List<Fragment> fragmentList;
	public ReportErrorComplaintAdapter(FragmentManager fm,List<Fragment> fragmentList) {
		super(fm);
		this.fragmentList=fragmentList;
	}

	@Override
	public Fragment getItem(int position) {

		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

}
