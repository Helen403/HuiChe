package com.huiche.utils;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

public  class SetGridViewHeightUtils {
	/***
	 * 
	 * @param mcontext 界面
	 * @param v 要传入的gridview
	 * @param coutItem gridview 集合的大小
	 * @param numColums 一行显示几个
	 * @param heights 每个item的高度占屏幕高度几分之几;
	 */
	public static void setGridViewSize(Context mcontext, View v, int coutItem,
			int numColums,int heights) {
		int height = 0;
		WindowManager wm = (WindowManager) mcontext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int h1 = display.getHeight() / heights;
		if (coutItem % numColums == 0) {
			height = (coutItem / numColums) * h1 + h1 / 2;
		} else if(coutItem>=21){
			height = (coutItem / numColums + 4) * h1 + h1 / 2;
		}
		else {
			height = (coutItem / numColums + 1) * h1 + h1 / 2;
		}
		LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.height = height;
		v.setLayoutParams(params);
	}
}
