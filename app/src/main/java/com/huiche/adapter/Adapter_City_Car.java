package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.CityCarBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/29.
 */
public class Adapter_City_Car extends MyBaseAdapter<CityCarBean> {
    public Adapter_City_Car(ArrayList<CityCarBean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_car_city;
    }

    @Override
    public void onInitView(View view, CityCarBean cityCarBean, int position) {

    }
}
