package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.CityCar2Bean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/30.
 */
public class Adapter_CityCar_2 extends MyBaseAdapter<CityCar2Bean> {
    public Adapter_CityCar_2(ArrayList<CityCar2Bean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_city_car_2;
    }

    @Override
    public void onInitView(View view, CityCar2Bean cityCar2Bean, int position) {

    }
}
