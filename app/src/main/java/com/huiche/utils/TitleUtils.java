package com.huiche.utils;


import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;

/***
 * 设置回退键 和设置标题信息的
 * @author Administrator
 *
 *
 *
 */

public  class TitleUtils {
	
	public static void setInfo(final Activity activity,String str){
		TextView	text_titil_all = (TextView)activity.findViewById(R.id.text_titil_all);
		ImageView	imageLeft_titil_all = (ImageView)activity.findViewById(R.id.imageLeft_titil_all);
		ImageButton imageRigth_titil_all=(ImageButton)activity.findViewById(R.id.imageRigth_titil_all);
		text_titil_all.setText(str);
		imageRigth_titil_all.setVisibility(View.INVISIBLE);
		TextView tv_right_title=(TextView)activity.findViewById(R.id.tv_right_title);
		tv_right_title.setVisibility(View.INVISIBLE);
	    SetSizeUtils.setSizeOnlyHeightOf(activity, activity.findViewById(R.id.rl_title_mainActivity), 15);
		imageLeft_titil_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				activity.finish();
			}
		});
	}
	/***
	 * 显示标题栏右边图片
	 * @param activity
	 * @param str
	 * @param imgId  需要显示右边imageButton的图片
	 * 
	 * 
	 */
	public static void setInfoImg(final Activity activity,String str,int imgId){
		TextView	text_titil_all = (TextView)activity.findViewById(R.id.text_titil_all);
		ImageView	imageLeft_titil_all = (ImageView)activity.findViewById(R.id.imageLeft_titil_all);
		ImageButton imageRigth_titil_all=(ImageButton)activity.findViewById(R.id.imageRigth_titil_all);
		TextView tv_right_title=(TextView)activity.findViewById(R.id.tv_right_title);
		tv_right_title.setVisibility(View.GONE);
		text_titil_all.setText(str);
		imageRigth_titil_all.setVisibility(View.VISIBLE);
		imageRigth_titil_all.setImageResource(imgId);
	   SetSizeUtils.setSizeOnlyHeightOf(activity, activity.findViewById(R.id.rl_title_mainActivity), 11);
		imageLeft_titil_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				activity.finish();
			}
		});
	}
	
	/**
	 * 
	 * @param activity
	 * @param str 中间标题
	 * @param rightStr 右侧文字
	 */
	
	public static void setInfoText(final Activity activity,String str,String rightStr){
		TextView	text_titil_all = (TextView)activity.findViewById(R.id.text_titil_all);
		ImageView	imageLeft_titil_all = (ImageView)activity.findViewById(R.id.imageLeft_titil_all);
		ImageButton imageRigth_titil_all=(ImageButton)activity.findViewById(R.id.imageRigth_titil_all);
		imageRigth_titil_all.setVisibility(View.GONE);
		//中间标题
		text_titil_all.setText(str);
		TextView tv_right_title=(TextView)activity.findViewById(R.id.tv_right_title);
		//右边文字
		tv_right_title.setText(rightStr);
		
	   SetSizeUtils.setSizeOnlyHeightOf(activity, activity.findViewById(R.id.rl_title_mainActivity), 11);
		imageLeft_titil_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				activity.finish();
			}
		});
	}
	
	
	
}
