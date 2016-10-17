package com.huiche.adapter;

import android.view.View;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.OilBaen;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/26.
 */
public class Adapter_oil extends MyBaseAdapter<OilBaen> {


    public Adapter_oil(ArrayList<OilBaen> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_oil;
    }

    @Override
    public void onInitView(View view, OilBaen oilBaen, int position) {
        TextView tv1 = getViewById(R.id.tv_1);
        TextView tv2 = getViewById(R.id.tv_2);
        tv1.setText(oilBaen.money);
        tv2.setText("售价:49.76" + oilBaen.price + "元");

    }
}
