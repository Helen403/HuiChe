package com.huiche.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 根据屏幕比例显示图片大小
 * 
 * @author Administrator
 * 
 */
public final class WindowUtils {
	public WindowManager windowManager;
	public LayoutParams params;

	public WindowUtils(Context context) {
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		params = ((Activity) context).getWindow().getAttributes();
	}

	public int getdisplayWidth() {
		return windowManager.getDefaultDisplay().getWidth();
	}

	public int getdisplayHeight() {
		return windowManager.getDefaultDisplay().getHeight();

	}

	public LayoutParams getwindowParams() {
		return params;
	}

	public WindowManager getwindowManager() {
		return windowManager;
	}

}
