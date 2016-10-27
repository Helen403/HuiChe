package com.huiche.activity;

import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.DiscountAdapter;
import com.huiche.bean.DiscountBean;
import com.huiche.lib.lib.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class DiscountActivity extends BaseActivity {

    ListView listView;
    DiscountAdapter discountAdapter_;


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
        discountAdapter_ = new DiscountAdapter(DiscountActivity.this);
        listView.setAdapter(discountAdapter_);
        discountAdapter_.setData(data);
    }

    @Override
    public void setListeners() {
    }
}
