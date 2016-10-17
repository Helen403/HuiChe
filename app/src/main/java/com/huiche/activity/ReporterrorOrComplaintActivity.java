package com.huiche.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.ReportErrorComplaintAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.fragment.ComplaintFragment;
import com.huiche.fragment.ReportErrorFragment;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.WindowUtils;

import java.util.ArrayList;


/**
 * 报错/投诉
 * 
 * @author Administrator
 * 
 */
public  class ReporterrorOrComplaintActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {
	private Context mContext;
	private ImageButton imb_titleBack;
	private TextView tv_titleText;
	private TextView tv_titleButton;
	private TextView tv_reportError;
	private TextView tv_complaint;
	private ViewPager mViewPager;
	private View view_selectDivider;// 选中的下划线
	private WindowUtils windowUtils;
	private int displayWidth;
	private ArrayList<Fragment> fragmentList;
	private int currentItemIndex=0;
	private LayoutParams selectDividerParams;
	private ReportErrorFragment reportErrorFragment;
	private ComplaintFragment complaintFragment;

	@Override
	public void dealLogicBeforeFindView() {
		mContext = this;
	}

	@Override
	public void findViews() {
		setContentView(R.layout.activity_reporterror_complaint);
		imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
		tv_titleText = (TextView) findViewById(R.id.tv_titleText);
		tv_titleButton = (TextView) findViewById(R.id.tv_titleButton);
		tv_reportError = (TextView) findViewById(R.id.tv_reportError);
		tv_complaint = (TextView) findViewById(R.id.tv_complaint);
		mViewPager = (ViewPager) findViewById(R.id.vp_reportError_complaint);
		view_selectDivider = (View) findViewById(R.id.view_selectDivider);

	}

	@Override
	public void initData() {
		tv_titleText.setText("报错/投诉");
		tv_titleButton.setVisibility(View.VISIBLE);
		// 比例为屏幕宽度的1/6
		windowUtils = new WindowUtils(mContext);
		displayWidth = windowUtils.getdisplayWidth();
		selectDividerParams=new LayoutParams(
				displayWidth / 6, LayoutParams.WRAP_CONTENT);
		selectDividerParams.setMargins(displayWidth/6,0,0,0);
		view_selectDivider.setLayoutParams(selectDividerParams);
		// 初始化fragment集合
		fragmentList = new ArrayList<Fragment>();
		reportErrorFragment = new ReportErrorFragment();
		complaintFragment = new ComplaintFragment();
		fragmentList.add(reportErrorFragment);
		fragmentList.add(complaintFragment);
		// 设置数据适配器
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new ReportErrorComplaintAdapter(fm, fragmentList));
		mViewPager.setCurrentItem(currentItemIndex);
		//拦截事件，防止嵌套了listview后不能左右滑动
		mViewPager.getParent().requestDisallowInterceptTouchEvent(true);

	}

	@Override
	public void setListeners() {
		imb_titleBack.setOnClickListener(this);
		tv_titleButton.setOnClickListener(this);
		tv_reportError.setOnClickListener(this);
		tv_complaint.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.imb_titleBack:
			finish();
			break;
		// 提交
		case R.id.tv_titleButton:
			String errorTpye=reportErrorFragment.getProblemType();
			String errorText=reportErrorFragment.getProblemText();
//			String complaintType=complaintFragment.getProblemType();
//			String complaintText=complaintFragment.getProblemText();
			ToastUtils.ToastShowLong(mContext, "type=" + errorTpye + "content=" + errorText);

			break;
			// 报错
		case R.id.tv_reportError:
			mViewPager.setCurrentItem(0);
			
			break;
			// 投诉
		case R.id.tv_complaint:
			mViewPager.setCurrentItem(1);
			
			break;

		default:
			break;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}

	@Override
	public void onPageSelected(int arg0) {
		TranslateAnimation translateAnim = null;
		switch (arg0) {
		case 0:
			// 下滑线动画，投诉-->报错
			if (currentItemIndex == 1) {
				translateAnim = new TranslateAnimation(displayWidth*4/6, displayWidth/6, 0, 0);
			}
			tv_reportError.setTextColor(mContext.getResources().getColor(
					R.color.bule_title));
			tv_complaint.setTextColor(mContext.getResources().getColor(
					R.color.text_color_black_title));
//			reportErrorFragment.showPop();
			break;

		case 1:
			// 下滑线动画，报错-->投诉
			if (currentItemIndex == 0) {
				translateAnim = new TranslateAnimation(displayWidth/6, displayWidth*4/6, 0, 0);
			}
			// 选中项标题颜色为蓝色，未选中项为灰色
			tv_complaint.setTextColor(mContext.getResources().getColor(
					R.color.bule_title));
			tv_reportError.setTextColor(mContext.getResources().getColor(
					R.color.text_color_black_title));
//			reportErrorFragment.dismissPop();
			
			break;

		default:
			break;
		}
		selectDividerParams.setMargins(0, 0, 0, 0);
		view_selectDivider.setLayoutParams(selectDividerParams);
		currentItemIndex = arg0;
		translateAnim.setFillAfter(true);// True:图片停在动画结束位置
		translateAnim.setDuration(300);
		view_selectDivider.startAnimation(translateAnim);

	}

		

}
