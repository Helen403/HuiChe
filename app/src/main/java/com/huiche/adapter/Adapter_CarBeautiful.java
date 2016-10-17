package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.CarBeautifulBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/29.
 */
public class Adapter_CarBeautiful extends MyBaseAdapter<CarBeautifulBean> {
    public Adapter_CarBeautiful(ArrayList<CarBeautifulBean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_car_beautiful;
    }

    @Override
    public void onInitView(View view, CarBeautifulBean carBeautifulBean, int position) {

    }
}
