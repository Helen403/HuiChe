package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.DiscountBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/27.
 */
public class Adapter_discount extends MyBaseAdapter<DiscountBean> {
    public Adapter_discount(ArrayList<DiscountBean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_discount;
    }

    @Override
    public void onInitView(View view, DiscountBean discountBean, int position) {

    }
}
