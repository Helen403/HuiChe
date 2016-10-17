package com.huiche.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huiche.R;
import com.huiche.adapter.GuideViewPagerAdapter;
import com.huiche.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public  class GuideActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<View> list=new ArrayList<View>();
//    private int []pic=new int[]{R.drawable.guide1,R.drawable.guide2,R.drawable.guide3,R.drawable.guide4};
    private ImageView image;
    private GuideViewPagerAdapter adapter;
    @Override
    public void dealLogicBeforeFindView() {

    }
    @Override
    public void findViews() {
        setContentView(R.layout.activity_guide);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
    }
    @Override
    public void initData() {
        initRes();
    }

    //填充数据
    private void initRes() {
        // 定义一个布局
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
//        // 初始化引导图片
//        for (int i = 0; i < pic.length; i++) {
//            image = new ImageView(this);
//            image.setLayoutParams(mParams);
//            // 防止图片不能填充屏幕
//            image.setScaleType(ImageView.ScaleType.FIT_XY);
//            // 加载图片资源
//            image.setImageResource(pic[i]);
//            list.add(image);
//        }
//        adapter=new GuideViewPagerAdapter(this,list);
//        viewPager.setAdapter(adapter);
//        viewPager.setPageTransformer(true,new DepthPageTransformer());

    }

    @Override
    public void setListeners() {

    }
}
