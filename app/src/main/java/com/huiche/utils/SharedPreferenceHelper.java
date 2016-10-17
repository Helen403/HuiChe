package com.huiche.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 
 * @version 1.0
 * 
 */
public  class SharedPreferenceHelper {

	private static String cityRecent = "cityInfoRecent";
	private static Editor cityInfoEditor;

	// public SharedPreferenceHelper(Context context) {
	// userCacheconfiure = context.getSharedPreferences("cityInfoRecent",
	// Context.MODE_PRIVATE);
	// sb=new StringBuffer();
	// userEditor = userCacheconfiure.edit();
	// }
	/**
	 * 添加 使用过得城市信息
	 * 
	 * @param context
	 *            上下文
	 * @param cityName
	 *            添加的城市名称,带着"市"的字样,用来区分几个
	 */
	public static void setCityRecentInfo(Context context, String cityName) {
		SharedPreferences cityInfoShared = context.getSharedPreferences(
				cityRecent, Context.MODE_PRIVATE);
		cityInfoEditor = cityInfoShared.edit();
		// StringBuffer sb=new StringBuffer();
		String oldStr = cityInfoShared.getString("cityInfoAdd", "");
		if (!oldStr.contains(cityName)) {
			cityInfoEditor.putString("cityInfoAdd", oldStr + cityName);
			cityInfoEditor.commit();
		}
		//cityInfoEditor.putString("cityInfo", "");
		String src=cityInfoShared.getString("cityInfoAdd", "");
		String arrs[]=src.split("市");
		if(arrs.length>8){
			cityInfoEditor.clear();
			cityInfoEditor.commit();
			StringBuffer sb=new StringBuffer();
			sb.append(arrs[1]+"市").append(arrs[2]+"市").append(arrs[3]+"市").append(arrs[4]+"市").append(arrs[5]+"市")
			.append(arrs[6]+"市").append(arrs[7]+"市").append(arrs[8]+"市");
			cityInfoEditor.putString("cityInfoAdd",sb.toString());
			cityInfoEditor.commit();
		}
	}

	/**
	 * 获取使用过的城市信息。返回一个集合。
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getCityRecentInfo(Context context) {
		SharedPreferences cityInfoShared = context.getSharedPreferences(
				cityRecent, Context.MODE_PRIVATE);
		String str = cityInfoShared.getString("cityInfoAdd", "");
		String arr[] = str.split("市");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		Collections.reverse(list);
		return list;
	}

}
