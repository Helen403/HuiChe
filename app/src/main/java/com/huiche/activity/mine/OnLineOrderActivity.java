package com.huiche.activity.mine;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.fragment.AllOrderFragment;
import com.huiche.fragment.HasCancelFragment;
import com.huiche.fragment.HasCloseFragment;
import com.huiche.fragment.HasFragment;
import com.huiche.fragment.UnPayFragment;
import com.huiche.fragment.UnSendFragment;
import com.huiche.fragment.WaitFragment;
import com.huiche.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;

public class OnLineOrderActivity extends BaseActivity implements OnClickListener {
//    private FrameLayout my_frame;
    private LinearLayout ll_all_order, ll_not_pay, ll_not_send_goods,
            ll_wait_goods, ll_has_goods;
    private TextView tv_all_order, tv_un_pay, tv_un_send, tv_wait_goods, tv_has_goods, tv_canceled, tv_closed;
    private View view_quan, view_second, view_third, view_fourth, view_five, view_close, view_calcel;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HorizontalScrollView mHorizontalScrollView;
    private int screenHalf;
    private int scrollx;
    private LinearLayout ll_cancel_goods, ll_close_goods;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private int indexFragment = 0;
//    private String ssss = "";

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_online_order);
        TitleUtils.setInfo(OnLineOrderActivity.this, "线上订单");
//        my_frame = (FrameLayout) findViewById(id.my_frame);
        ll_all_order = (LinearLayout) findViewById(R.id.ll_all_order);
        ll_not_pay = (LinearLayout) findViewById(R.id.ll_not_pay);
        ll_not_send_goods = (LinearLayout) findViewById(R.id.ll_not_send_goods);
        ll_wait_goods = (LinearLayout) findViewById(R.id.ll_wait_goods);
        ll_has_goods = (LinearLayout) findViewById(R.id.ll_has_goods);
        tv_all_order = (TextView) findViewById(R.id.tv_all_order);
        tv_un_pay = (TextView) findViewById(R.id.tv_un_pay);
        tv_un_send = (TextView) findViewById(R.id.tv_un_send);
        tv_wait_goods = (TextView) findViewById(R.id.tv_wait_goods);
        tv_has_goods = (TextView) findViewById(R.id.tv_has_goods);
        view_quan = findViewById(R.id.view_quan);
        view_second = findViewById(R.id.view_second);
        view_third = findViewById(R.id.view_third);
        view_fourth = findViewById(R.id.view_fourth);
        view_five = findViewById(R.id.view_five);
        ll_cancel_goods = (LinearLayout) findViewById(R.id.ll_cancel_goods);
        ll_close_goods = (LinearLayout) findViewById(R.id.ll_close_goods);
        tv_canceled = (TextView) findViewById(R.id.tv_canceled);
        tv_closed = (TextView) findViewById(R.id.tv_closed);
        view_close = findViewById(R.id.view_close);
        view_calcel = findViewById(R.id.view_calcel);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizon_scroll);
        AllOrderFragment allOrderFragment = new AllOrderFragment();
        UnPayFragment unPayFragment = new UnPayFragment();
        UnSendFragment unSendFragment = new UnSendFragment();
        HasCancelFragment hasCancelFragment = new HasCancelFragment();
        HasCloseFragment hasCloseFragment = new HasCloseFragment();
        WaitFragment waitFragment = new WaitFragment();
        HasFragment hasFragment = new HasFragment();
        fragmentList.add(allOrderFragment);
        fragmentList.add(unPayFragment);
        fragmentList.add(unSendFragment);
        fragmentList.add(hasCancelFragment);
        fragmentList.add(hasCloseFragment);
        fragmentList.add(hasFragment);
        fragmentList.add(waitFragment);
        fm = getSupportFragmentManager();
        //默认为全部订单
        allOrderFragment();
    }

    @Override
    public void initData() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        screenHalf = display.getWidth() / 2;
        scrollx = mHorizontalScrollView.getScrollX();
    }

    @Override
    public void setListeners() {
        ll_all_order.setOnClickListener(this);
        ll_not_pay.setOnClickListener(this);
        ll_not_send_goods.setOnClickListener(this);
        ll_wait_goods.setOnClickListener(this);
        ll_has_goods.setOnClickListener(this);
        ll_cancel_goods.setOnClickListener(this);
        ll_close_goods.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全部订单
            case R.id.ll_all_order:
                selectCheck();
                int left = ll_all_order.getLeft();
                int leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                allOrderFragment();
                break;
            //未支付
            case R.id.ll_not_pay:
                selectCheck();
                left = ll_not_pay.getLeft();
                leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                unPayFragment();
                break;
            //待发货
            case R.id.ll_not_send_goods:
                selectCheck();
                left = ll_not_send_goods.getLeft();
                leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                unSendGragment();

                break;
            //待收货
            case R.id.ll_wait_goods:
                selectCheck();
                left = ll_wait_goods.getLeft();
                leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                waitFragment();

                break;
            //已收货
            case R.id.ll_has_goods:
                selectCheck();
                left = ll_has_goods.getLeft();
                leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                hasFragment();

                break;

            //已取消
            case R.id.ll_cancel_goods:
                selectCheck();
                left = ll_cancel_goods.getLeft();
                leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                cancelFragment();
                break;

            //已关闭

            case R.id.ll_close_goods:
                selectCheck();
                left = ll_close_goods.getLeft();
                leftscreen = left - scrollx;
                mHorizontalScrollView.smoothScrollBy((leftscreen - screenHalf),
                        0);
                closeFragment();
                break;

            default:
                break;
        }

    }


    private void selectCheck() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        screenHalf = display.getWidth() / 2;
        scrollx = mHorizontalScrollView.getScrollX();

    }

    //已关闭
    private void closeFragment() {
//		ft = fm.beginTransaction();
//		HasCloseFragment tab1 = new HasCloseFragment();
//		ft.replace(R.id.my_frame, tab1);
//		ft.commit();
        tv_all_order.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_pay.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_send.setTextColor(getResources().getColor(R.color.grey_text));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_has_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.status_color));
        tv_canceled.setTextColor(getResources().getColor(R.color.grey_text));
        view_quan.setVisibility(View.INVISIBLE);
        view_second.setVisibility(View.INVISIBLE);
        view_third.setVisibility(View.INVISIBLE);
        view_fourth.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.INVISIBLE);
        view_close.setVisibility(View.VISIBLE);
        view_calcel.setVisibility(View.INVISIBLE);
        switchFragment(4);
    }

    //已取消
    private void cancelFragment() {
//	ft = fm.beginTransaction();
//	HasCancelFragment tab1 = new HasCancelFragment();
//	ft.replace(R.id.my_frame, tab1);
//	ft.commit();
        tv_all_order.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_pay.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_send.setTextColor(getResources().getColor(R.color.grey_text));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_has_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.grey_text));
        tv_canceled.setTextColor(getResources().getColor(R.color.status_color));
        view_quan.setVisibility(View.INVISIBLE);
        view_second.setVisibility(View.INVISIBLE);
        view_third.setVisibility(View.INVISIBLE);
        view_fourth.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.INVISIBLE);
        view_close.setVisibility(View.INVISIBLE);
        view_calcel.setVisibility(View.VISIBLE);
        switchFragment(3);
    }

    //已完成
    private void hasFragment() {
//		ft = fm.beginTransaction();
//		HasFragment tab1 = new HasFragment();
//		ft.replace(R.id.my_frame, tab1);
//		ft.commit();
        tv_all_order.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_pay.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_send.setTextColor(getResources().getColor(R.color.grey_text));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.grey_text));
        tv_canceled.setTextColor(getResources().getColor(R.color.grey_text));
        tv_has_goods.setTextColor(getResources().getColor(R.color.status_color));
        view_quan.setVisibility(View.INVISIBLE);
        view_second.setVisibility(View.INVISIBLE);
        view_third.setVisibility(View.INVISIBLE);
        view_fourth.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.VISIBLE);
        view_close.setVisibility(View.INVISIBLE);
        view_calcel.setVisibility(View.INVISIBLE);
        switchFragment(6);
    }

    //待收货
    private void waitFragment() {
        tv_all_order.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_pay.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_send.setTextColor(getResources().getColor(R.color.grey_text));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.status_color));
        tv_has_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.grey_text));
        tv_canceled.setTextColor(getResources().getColor(R.color.grey_text));
        view_quan.setVisibility(View.INVISIBLE);
        view_second.setVisibility(View.INVISIBLE);
        view_third.setVisibility(View.INVISIBLE);
        view_fourth.setVisibility(View.VISIBLE);
        view_close.setVisibility(View.INVISIBLE);
        view_calcel.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.INVISIBLE);
//		ft = fm.beginTransaction();
//		WaitFragment tab1 = new WaitFragment();
//
//		ft.replace(R.id.my_frame, tab1);
//		ft.commit();
        switchFragment(5);
    }

    //待发货
    private void unSendGragment() {
        tv_all_order.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_pay.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_send.setTextColor(getResources().getColor(R.color.status_color));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_has_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.grey_text));
        tv_canceled.setTextColor(getResources().getColor(R.color.grey_text));
        view_quan.setVisibility(View.INVISIBLE);
        view_second.setVisibility(View.INVISIBLE);
        view_third.setVisibility(View.VISIBLE);
        view_fourth.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.INVISIBLE);
        view_close.setVisibility(View.INVISIBLE);
        view_calcel.setVisibility(View.INVISIBLE);
        switchFragment(2);
//		ft = fm.beginTransaction();
//		UnSendFragment tab1 = new UnSendFragment();
//
//		ft.replace(R.id.my_frame, tab1);
//		ft.commit();
    }

    //待付款
    private void unPayFragment() {
        tv_all_order.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_pay.setTextColor(getResources().getColor(R.color.status_color));
        tv_un_send.setTextColor(getResources().getColor(R.color.grey_text));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_has_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.grey_text));
        tv_canceled.setTextColor(getResources().getColor(R.color.grey_text));
        view_quan.setVisibility(View.INVISIBLE);
        view_second.setVisibility(View.VISIBLE);
        view_third.setVisibility(View.INVISIBLE);
        view_fourth.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.INVISIBLE);
        view_close.setVisibility(View.INVISIBLE);
        view_calcel.setVisibility(View.INVISIBLE);
        switchFragment(1);
//		ft = fm.beginTransaction();
//		UnPayFragment tab1 = new UnPayFragment();
//
//		ft.replace(R.id.my_frame, tab1);
//		ft.commit();

    }

    //全部订单
    private void allOrderFragment() {
        //改变选中字体颜色
        tv_all_order.setTextColor(getResources().getColor(R.color.status_color));
        tv_un_pay.setTextColor(getResources().getColor(R.color.grey_text));
        tv_un_send.setTextColor(getResources().getColor(R.color.grey_text));
        tv_wait_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_has_goods.setTextColor(getResources().getColor(R.color.grey_text));
        tv_closed.setTextColor(getResources().getColor(R.color.grey_text));
        tv_canceled.setTextColor(getResources().getColor(R.color.grey_text));
        view_quan.setVisibility(View.VISIBLE);
        view_second.setVisibility(View.INVISIBLE);
        view_third.setVisibility(View.INVISIBLE);
        view_fourth.setVisibility(View.INVISIBLE);
        view_five.setVisibility(View.INVISIBLE);
        view_close.setVisibility(View.INVISIBLE);
        view_calcel.setVisibility(View.INVISIBLE);
        switchFragment(0);

    }

    public void switchFragment(int checkIndex) {
        ft = fm.beginTransaction();
        Fragment currentFragment = fragmentList.get(indexFragment);
        Fragment targetFragment = fragmentList.get(checkIndex);
        if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {
                ft.hide(currentFragment).add(
                        R.id.my_frame, targetFragment);
            } else {
                ft.hide(currentFragment).show(targetFragment);
            }
        } else {
            if (!targetFragment.isAdded()) {
                ft.add(R.id.my_frame, targetFragment)
                        .show(targetFragment);
            }
        }
        ft.commit();
        indexFragment = checkIndex;


    }


}
