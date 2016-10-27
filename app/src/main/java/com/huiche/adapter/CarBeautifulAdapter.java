package com.huiche.adapter;

import android.content.Context;
import android.view.View;

import com.huiche.R;
import com.huiche.bean.CarBeautifulBean;
import com.huiche.lib.lib.base.MyBaseAdapter;


/**
 * Created by Administrator on 2016/9/29.
 */
public class CarBeautifulAdapter extends MyBaseAdapter<CarBeautifulBean> {
    public CarBeautifulAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentView() {
        return R.layout.item_car_beautiful;
    }

    @Override
    public void onInitView(View view, CarBeautifulBean carBeautifulBean, int position) {

    }
}
