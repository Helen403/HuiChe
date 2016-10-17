package com.huiche.activity.mine;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.CardQuanAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.fragment.CardOverdueFragment;
import com.huiche.fragment.CardWaitUseFragment;
import com.huiche.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;


public class CardQuanActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
    private ViewPager mViewPager;
    private int currentItemIndex = 0;// 当前pager页
    private View scrollTabDivide;// 选择下滑线
    private int screenWidth;// 屏幕宽度
    private TextView tv_wait;
    private TextView tv_overdue;
    private Context mContext;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_card_quan);
        mContext = this;
        TitleUtils.setInfo(CardQuanActivity.this, "卡券");
        tv_wait = (TextView) findViewById(R.id.tv_wait);
        tv_overdue = (TextView) findViewById(R.id.tv_overdue);
        mViewPager = (ViewPager) findViewById(R.id.vp_integral_add);
        scrollTabDivide = findViewById(R.id.view_divide_select);
    }

    @Override
    public void initData() {
        // 设置选择下滑线宽度，为屏幕宽度的一半
        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        scrollTabDivide.setLayoutParams(new LayoutParams(screenWidth / 2,
                LayoutParams.WRAP_CONTENT));
        // 初始化fragment集合
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        Fragment cardWaitFragment = new CardWaitUseFragment();
        Fragment cardOverdueFragment = new CardOverdueFragment();
        fragmentList.add(cardWaitFragment);
        fragmentList.add(cardOverdueFragment);
        // 设置数据适配器
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new CardQuanAdapter(fm, fragmentList));
        mViewPager.setCurrentItem(currentItemIndex);
    }

    @Override
    public void setListeners() {
        mViewPager.setOnPageChangeListener(this);
        tv_wait.setOnClickListener(this);
        tv_overdue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wait:
                //切换到待使用界面
                mViewPager.setCurrentItem(0);
                break;

            case R.id.tv_overdue:
                //切换到已过期界面
                mViewPager.setCurrentItem(1);
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

                if (currentItemIndex == 1) {
                    translateAnim = new TranslateAnimation(screenWidth / 2, 0, 0, 0);
                }
                tv_wait.setTextColor(mContext.getResources().getColor(
                        R.color.status_color));
                tv_overdue.setTextColor(mContext.getResources().getColor(
                        R.color.light_black));
                break;

            case 1:
                // 下滑线动画，待使用-->已过期
                if (currentItemIndex == 0) {
                    translateAnim = new TranslateAnimation(0, screenWidth / 2, 0, 0);
                }
                // 选中项标题颜色为蓝色，未选中项为灰色
                tv_overdue.setTextColor(mContext.getResources().getColor(
                        R.color.status_color));
                tv_wait.setTextColor(mContext.getResources().getColor(
                        R.color.light_black));
                break;

            default:
                break;
        }
        currentItemIndex = arg0;
        translateAnim.setFillAfter(true);// True:图片停在动画结束位置
        translateAnim.setDuration(300);
        scrollTabDivide.startAnimation(translateAnim);
    }

}
