package com.huiche.activity.mine;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.Adapter_discount;
import com.huiche.base.BaseActivity;
import com.huiche.bean.DiscountBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class DiscountActivity extends BaseActivity {

    ListView listView;
    Adapter_discount adapter_discount;

    ImageButton imageLeft_titil_all;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_discount);
        listView = (ListView) findViewById(R.id.listview);

        imageLeft_titil_all= (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {
        ArrayList<DiscountBean> data = new ArrayList();
        for (int i = 0; i < 5; i++) {
            DiscountBean dis = new DiscountBean();
            data.add(dis);
        }
        adapter_discount = new Adapter_discount(data);
        listView.setAdapter(adapter_discount);
    }

    @Override
    public void setListeners() {
        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
