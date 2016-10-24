package com.huiche.adapter;

import android.content.Context;
import android.view.View;

import com.huiche.R;
import com.huiche.bean.DiscountBean;
import com.huiche.lib.lib.base.MyBaseAdapter;


/**
 * Created by Administrator on 2016/9/27.
 */
public class Adapter_discount extends MyBaseAdapter<DiscountBean> {
    public Adapter_discount(Context context) {
        super(context);
    }

    @Override
    public int getContentView() {
        return R.layout.item_discount;
    }

    @Override
    public void onInitView(View view, DiscountBean discountBean, int position) {

    }
}
