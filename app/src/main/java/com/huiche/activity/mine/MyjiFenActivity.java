package com.huiche.activity.mine;

import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.Adapter_MyjiFen;
import com.huiche.base.BaseActivity;
import com.huiche.bean.MyjiFenBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MyjiFenActivity extends BaseActivity {

    ListView listview;
    Adapter_MyjiFen adapter_myjiFen;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_my_jifen);
        listview = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void initData() {

        ArrayList<MyjiFenBean> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new MyjiFenBean());
        }
        adapter_myjiFen = new Adapter_MyjiFen(data);
        listview.setAdapter(adapter_myjiFen);
    }

    @Override
    public void setListeners() {

    }
}
