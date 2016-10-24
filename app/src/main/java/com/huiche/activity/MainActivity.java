package com.huiche.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.huiche.R;
import com.huiche.activity.mine.JiFenShopping;
import com.huiche.fragment.HomeFragment;
import com.huiche.fragment.IntegralShopFragment;
import com.huiche.fragment.MineFragment;
import com.huiche.fragment.U_CardFragment;
import com.huiche.listener.GridViewFromNearByListener;
import com.huiche.two_dimensioncode.CaptureActivity;

public class MainActivity extends com.huiche.lib.lib.base.BaseActivity implements OnClickListener,
        OnCheckedChangeListener, GridViewFromNearByListener {
    private RadioButton nearBy;
    private RadioGroup radioGroup;
    private ImageView iv_uu_Fragment;


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void findViews() {
        head_view.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        nearBy = (RadioButton) findViewById(R.id.radioButton_nearBy_MainActivity);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_MainActivity);
        iv_uu_Fragment = (ImageView) findViewById(R.id.iv_uu_Fragment);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListeners() {
        radioGroup.setOnCheckedChangeListener(this);
        iv_uu_Fragment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_uu_Fragment:
                radioGroup.clearCheck();
                switchFragment(2);
                break;
        }
    }


    @Override
    protected void onFragmentChange(int fragmentId) {
        super.onFragmentChange(R.id.frameLayout_MainActivity);
        HomeFragment homeFragment = new HomeFragment();
        IntegralShopFragment integralShopFragment = new IntegralShopFragment();
        U_CardFragment cardFragment = new U_CardFragment();
        MineFragment mineFragment = new MineFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(homeFragment);
        fragmentList.add(homeFragment);
        fragmentList.add(mineFragment);
        switchFragment(0);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            // 附近
            case R.id.radioButton_nearBy_MainActivity:
                switchFragment(0);
                break;
            // 积分商城
            case R.id.radioButton_scoreMall_MainActivity:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, JiFenShopping.class);
                startActivity(intent);

                break;
            // 购物车
            case R.id.radioButton_shoppingCart_MainActivity:
                Intent intents = new Intent();
                intents.setClass(this, CaptureActivity.class);
                startActivity(intents);
                break;
            // 我的
            case R.id.radioButton_mine_MainActivity:
                switchFragment(3);
                break;
            default:
                break;
        }
    }


    @Override
    public void changColorSetData(String str, int position) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
