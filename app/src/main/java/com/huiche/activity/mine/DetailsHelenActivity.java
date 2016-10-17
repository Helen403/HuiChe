package com.huiche.activity.mine;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.Adapter_Detail_Helen;
import com.huiche.base.BaseActivity;
import com.huiche.bean.DetailHelenBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DetailsHelenActivity extends BaseActivity {
    ListView listview;
    Adapter_Detail_Helen adapter;
    ImageButton imageLeft_titil_all;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_detail_helen);
        listview = (ListView) findViewById(R.id.listview);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {
        ArrayList<DetailHelenBean> data = new ArrayList();
        for (int i = 0; i < 10; i++) {
            data.add(new DetailHelenBean());
        }
        adapter = new Adapter_Detail_Helen(data);
        listview.setAdapter(adapter);
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
