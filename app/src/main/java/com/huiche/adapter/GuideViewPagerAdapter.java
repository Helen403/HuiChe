package com.huiche.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.huiche.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> dataList = new ArrayList<View>();

    public GuideViewPagerAdapter(Context context, List<View> list) {
        this.mContext = context;
        this.dataList = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(dataList.get(position), 0);

        dataList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == dataList.size() - 1) {
                    Intent intent = new Intent();
//                    intent.setClass(mContext, LoginActivity.class);
                    intent.setClass(mContext, MainActivity.class);
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_data", mContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstLogin", false);
                    editor.commit();
                    Activity activity = (Activity) mContext;
                    mContext.startActivity(intent);
                    activity.finish();

                }
            }
        });

        return dataList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(dataList.get(position));
    }

    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
