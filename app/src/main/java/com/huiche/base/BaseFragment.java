package com.huiche.base;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment  {

	public Activity context;

	/**
	 * fragment Initialization monitoring interface
	 */
	public View layout_morefragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		findViews();
		initData();
		setListeners();
		initViews();
	}
	
	/**
	 * 填充布局 order:----1----
	 * Initialization debris control, add layout debris Execution
	 */
	public abstract View inflateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	/**
	 *Initialization debris control, add layout debris
	 * Execution order:----1----
	 */
	public abstract void findViews();

	/**
	 * Execution order:----2----
	 */
	public abstract void initData();

	/**
	 * Execution order:----3----
	 */
	public abstract void setListeners();

	/**
	 *
	 * Execution order:----4----
	 */
	public abstract void initViews();

}
