package com.huiche.activity;

import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.Adapter_discount;
import com.huiche.bean.DiscountBean;
import com.huiche.lib.lib.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class DiscountActivity extends BaseActivity {

    ListView listView;
    Adapter_discount adapter_discount;


    @Override
    public int getContentView() {
        return R.layout.activity_discount;
    }

    @Override
    public void findViews() {
        setTitle("礼品卡");
        listView = (ListView) findViewById(R.id.listview);

    }

    @Override
    public void initData() {
        ArrayList<DiscountBean> data = new ArrayList();
        for (int i = 0; i < 5; i++) {
            DiscountBean dis = new DiscountBean();
            data.add(dis);
        }
        adapter_discount = new Adapter_discount(DiscountActivity.this);
        listView.setAdapter(adapter_discount);
        adapter_discount.setData(data);
    }

    @Override
    public void setListeners() {
    }
}
