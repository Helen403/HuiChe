package com.huiche.activity.mine;

import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.Adapter_MyPartner;
import com.huiche.base.BaseActivity;
import com.huiche.bean.MyPartnerBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/8.
 */
public class MyPartnerActivity extends BaseActivity {

    ListView listView;

    Adapter_MyPartner adapter_myPartner;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_my_partner);
        listView = (ListView) findViewById(R.id.listview);

        ArrayList<MyPartnerBean> data = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            data.add(new MyPartnerBean());
        }
        adapter_myPartner = new Adapter_MyPartner(data);
        listView.setAdapter(adapter_myPartner);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }
}
