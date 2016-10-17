package com.huiche.utils;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;


public  class SetSizeUtils {
	private static WindowManager wm;
	private static Display display;

	/**
	 * 设置 图形按照屏幕的宽度设置大小，高和宽都根据屏幕宽度设置
	 * 
	 * @param context
	 * @param context
	 *            控件
	 * @param x
	 *            屏幕宽度的几分之几
	 * @param y
	 *            屏幕宽度的几分之几
	 */
	public static void setSizeWidth(Context context, View view, int x, int y) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		int h1 = display.getHeight();
		int w1 = display.getWidth();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		params.width = h1 / x;
		params.height = h1 / y;
		view.setLayoutParams(params);
	}

	/***
	 * 设置 图形按照屏幕的宽度设置大小，高和宽都根据屏幕高度设置
	 * 
	 * @param context
	 * @param context
	 *            控件
	 * @param x
	 *            屏幕高度的几分之几
	 * @param y
	 * 屏幕高度的几分之几
	 */
	public static void setSizeHeight(Context context, View view, int x, int y) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		int h1 = display.getHeight();
		int w1 = display.getWidth();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		params.width = w1 / x;
		params.height = w1 / y;
		view.setLayoutParams(params);
	}

	/***
	 * 进设仅根据屏幕的宽度，置控件的宽度 ，高度 自适应
	 * 
	 * @param context
	 * @param view
	 * @param x
	 */
	public static void setSizeOnlyWidth(Context context, View view, int x) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		float h1 = display.getHeight();
		float w1 = display.getWidth();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		params.width = (int) (w1 / x);
		view.setLayoutParams(params);
	}

	/***
	 * 
	 * @param context
	 * @param view
	 * @param y
	 *            占屏幕高度的几分之几
	 * @param bottomMargen
	 *            与底部的距离
	 */
	public static void setSizeOnlyHeight(Context context, View view, int y,
			int bottomMargen) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		float h1 = display.getHeight();
		float w1 = display.getWidth();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		params.height = (int) (h1 / y);
		params.bottomMargin = bottomMargen;
		view.setLayoutParams(params);
	}

	/***
	 * 占屏幕高度的几分之几,根据屏幕高度
	 * 
	 * @param context
	 * @param view
	 * @param y
	 * 
	 */
	public static void setSizeOnlyHeightOf(Context context, View view, int y) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		float h1 = display.getHeight();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		params.height = (int) (h1 / y);
		view.setLayoutParams(params);
	}

	/**
	 * 占屏幕宽度的几分之几,根据屏幕宽度,控件的width:height = scaleX:scaleY
	 * 
	 * @param context
	 * @param view
	 * @param px
	 *            ,sx/px,px分之sx
	 * @param sx
	 *            ,sx/px,px分之sx
	 * @param scaleX
	 * @param scaleY
	 */
	public static void setSizeOnlyWidthOf(Context context, View view,int px, int sx,
			 int scaleX, int scaleY) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		int displayWidth = display.getWidth();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		int width = displayWidth * sx / px;
		int height = width * scaleY / scaleX;
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}
	/**
	 * 占屏幕宽度的几分之几,根据屏幕宽度,控件的width:height = scaleX:scaleY
	 *
	 * @param context
	 * @param view
	 * @param px
	 *            ,sx/px,px分之sx
	 * @param sx
	 *            ,sx/px,px分之sx
	 */
	public static void setSizeOnlyWidthOf(Context context, View view,int px, int sx) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		int displayWidth = display.getWidth();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		int width = displayWidth * sx / px;
		params.width = width;
		view.setLayoutParams(params);
	}

	/**
	 * 占屏幕宽度的几分之几,根据屏幕宽度,控件的width:height = scaleX:scaleY
	 * 
	 * @param context
	 * @param view
	 * @param py
	 *            ,sy/py,py分之sy
	 * @param sy
	 *            ,sy/py,py分之sy
	 * @param scaleX
	 * @param scaleY
	 */
	public static void setSizeOnlyHeightOf(Context context, View view, int py,int sy,
			 int scaleX, int scaleY) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		int displayHeight = display.getHeight();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		int height = displayHeight * sy / py;
		int width = height * scaleX / scaleY;
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}

	/**
	 * 占屏幕宽度的几分之几,根据屏幕宽度,控件的width:height = scaleX:scaleY
	 *
	 * @param context
	 * @param view
	 * @param py
	 *            ,sy/py,py分之sy
	 * @param sy
	 *            ,sy/py,py分之sy
	 *
	 */
	public static void setSizeOnlyHeightOf(Context context, View view, int py,int sy) {
		if (null == wm) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

		}
		if (null == display) {
			display = wm.getDefaultDisplay();
		}
		int displayHeight = display.getHeight();
		MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
		int height = displayHeight * sy / py;
		params.height = height;
		view.setLayoutParams(params);
	}

}
