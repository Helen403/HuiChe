package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.CarBeautiful2Bean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/30.
 */
public class Adapter_CarBeautiful2 extends MyBaseAdapter<CarBeautiful2Bean> {
    public Adapter_CarBeautiful2(ArrayList<CarBeautiful2Bean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_car_beautiful_2;
    }

    @Override
    public void onInitView(View view, CarBeautiful2Bean carBeautiful2Bean, int position) {

    }
}
