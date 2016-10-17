package com.huiche.activity.mine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.CardQuanPagerAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.fragment.CardHasUsedFragment;
import com.huiche.fragment.CardOverdueFragment;
import com.huiche.fragment.CardWaitUseFragment;
import com.huiche.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;




public class MyCardQuanActivity extends BaseActivity implements OnClickListener, ViewPager.OnPageChangeListener {

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
//    private FrameLayout my_fragment;
    private TextView tv_wait;
    private TextView tv_overdue;
    private TextView tv_used;
//    private Context mContext;
    private FragmentManager fm;
    private FragmentTransaction ft;
//    private LinearLayout view_left, view_right, view_last;
    private int indexFragment = 0;
    private ViewPager viewPager;
    private ImageView mTabLineIv;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    @Override
    public void dealLogicBeforeFindView() {


    }

    @Override
    public void findViews() {
        //其item布局为 item_my_collection
        setContentView(R.layout.activity_mycardquan);
//        mContext = this;
        TitleUtils.setInfo(MyCardQuanActivity.this, "我的卡券");
        tv_wait = (TextView) findViewById(R.id.tv_wait);
        tv_wait.setOnClickListener(this);
        tv_overdue = (TextView) findViewById(R.id.tv_overdue);
        tv_overdue.setOnClickListener(this);
//        my_fragment = (FrameLayout) findViewById(R.id.my_fragment);
//        view_left = (LinearLayout) findViewById(R.id.view_left);
//        view_right = (LinearLayout) findViewById(R.id.view_right);
//        view_last = (LinearLayout) findViewById(R.id.view_last);
        tv_used = (TextView) findViewById(R.id.tv_used);
        viewPager = (ViewPager) findViewById(R.id.my_pager);
        mTabLineIv = (ImageView) findViewById(R.id.id_tab_line_iv);
        tv_used.setOnClickListener(this);
        fm = getSupportFragmentManager();

    }

    @Override
    public void initData() {
        CardWaitUseFragment cardWaitUseFragment = new CardWaitUseFragment();
        CardOverdueFragment cardOverdueFragment = new CardOverdueFragment();
        CardHasUsedFragment cardHasUsedFragment = new CardHasUsedFragment();
        fragmentList.add(cardWaitUseFragment);
        fragmentList.add(cardOverdueFragment);
        fragmentList.add(cardHasUsedFragment);
        viewPager.setAdapter(new CardQuanPagerAdapter(fm, fragmentList));
        viewPager.setCurrentItem(0);
        initTabLineWidth();
        //ChangeShopFragment();

    }

    @Override
    public void setListeners() {
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wait:
                //切换到待使用
                viewPager.setCurrentItem(0);
                // ChangeShopFragment();

                break;

            case R.id.tv_overdue:
                viewPager.setCurrentItem(1);
                //  ChangeGoodsFragment();
                //切换到已过期
                break;
            case R.id.tv_used:
                viewPager.setCurrentItem(2);
                // ChangeUsedFragment();
                break;
        }
    }

    //已使用
    private void ChangeUsedFragment() {
//        view_left.setVisibility(View.INVISIBLE);
//        view_right.setVisibility(View.INVISIBLE);
//        view_last.setVisibility(View.VISIBLE);
        tv_wait.setTextColor(getResources().getColor(R.color.text_color_black));
        tv_overdue.setTextColor(getResources().getColor(R.color.text_color_black));
        tv_used.setTextColor(getResources().getColor(R.color.bule_title));
//        ft=fm.beginTransaction();
//        CardHasUsedFragment goods=new CardHasUsedFragment();
//        ft.replace(R.id.my_fragment, goods);
//        ft.commit();
        // switchFragment(2);

    }

    //已过期
    private void ChangeGoodsFragment() {
//        view_left.setVisibility(View.INVISIBLE);
//        view_right.setVisibility(View.VISIBLE);
//        view_last.setVisibility(View.INVISIBLE);
        tv_wait.setTextColor(getResources().getColor(R.color.text_color_black));
        tv_overdue.setTextColor(getResources().getColor(R.color.bule_title));
        tv_used.setTextColor(getResources().getColor(R.color.text_color_black));
//        ft=fm.beginTransaction();
//        CardOverdueFragment goods=new CardOverdueFragment();
//        ft.replace(R.id.my_fragment, goods);
//        ft.commit();
        // switchFragment(1);
    }


    //待使用
    private void ChangeShopFragment() {
//        view_left.setVisibility(View.VISIBLE);
//        view_right.setVisibility(View.INVISIBLE);
//        view_last.setVisibility(View.INVISIBLE);
        tv_wait.setTextColor(getResources().getColor(R.color.bule_title));
        tv_overdue.setTextColor(getResources().getColor(R.color.text_color_black));
        tv_used.setTextColor(getResources().getColor(R.color.text_color_black));
//        ft=fm.beginTransaction();
//        CardWaitUseFragment shop=new CardWaitUseFragment();
//        ft.replace(R.id.my_fragment, shop);
//        ft.commit();
        //switchFragment(0);

    }

    public void switchFragment(int checkIndex) {
        ft = fm.beginTransaction();
        Fragment currentFragment = fragmentList.get(indexFragment);
        Fragment targetFragment = fragmentList.get(checkIndex);
        if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {
                ft.hide(currentFragment).add(
                        R.id.my_fragment, targetFragment);
            } else {
                ft.hide(currentFragment).show(targetFragment);
            }
        } else {
            if (!targetFragment.isAdded()) {
                ft.add(R.id.my_fragment, targetFragment)
                        .show(targetFragment);
            }
        }
        ft.commit();
        indexFragment = checkIndex;


    }


    //页面滑动监听
    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();

        /**
         * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
         * 设置mTabLineIv的左边距 滑动场景：
         * 记3个页面,
         * 从左到右分别为0,1,2
         * 0->1; 1->2; 2->1; 1->0
         */

        if (currentIndex == 0 && position == 0)// 0->1
        {
            lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 0) // 1->0
        {
            lp.leftMargin = (int) (-(1 - offset)
                    * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 1) // 1->2
        {
            lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));
        } else if (currentIndex == 2 && position == 1) // 2->1
        {
            lp.leftMargin = (int) (-(1 - offset)
                    * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));
        }
        mTabLineIv.setLayoutParams(lp);

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                ChangeShopFragment();
                break;
            case 1:
                ChangeGoodsFragment();
                break;
            case 2:
                ChangeUsedFragment();
                break;
        }
        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }
}
