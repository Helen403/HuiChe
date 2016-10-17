package com.huiche.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类。
 * 
 */
public final class ToastUtils {
	private static Toast myToast;

	
	public static void ToastShowShort(Context context, String content) {
		if (myToast != null) {
			myToast.cancel();
		}
		myToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
		myToast.show();
	}

	
	public static void ToastShowLong(Context context, String content) {
		if (myToast != null) {
			myToast.cancel();
		}
		myToast = Toast.makeText(context, content, Toast.LENGTH_LONG);
		myToast.show();
	}
}
