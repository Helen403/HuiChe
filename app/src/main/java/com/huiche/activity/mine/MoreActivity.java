package com.huiche.activity.mine;

import android.view.View;
import android.widget.ImageButton;

import com.huiche.R;
import com.huiche.adapter.Adapter_more;
import com.huiche.base.BaseActivity;
import com.huiche.bean.MoreBean;
import com.huiche.customer_view.MyGridView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MoreActivity extends BaseActivity {
    ImageButton imageLeft_titil_all;

    MyGridView gv1, gv2, gv3, gv4, gv5, gv6;

    Adapter_more adapter_more;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_more);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
        gv1 = (MyGridView) findViewById(R.id.gv1);
        gv2 = (MyGridView) findViewById(R.id.gv2);
        gv3 = (MyGridView) findViewById(R.id.gv3);
        gv4 = (MyGridView) findViewById(R.id.gv4);
        gv5 = (MyGridView) findViewById(R.id.gv5);
        gv6 = (MyGridView) findViewById(R.id.gv6);
    }

    @Override
    public void initData() {
        ArrayList<MoreBean> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            data.add(new MoreBean());
        }
        adapter_more = new Adapter_more(data);
        gv1.setAdapter(adapter_more);
        gv2.setAdapter(adapter_more);
        gv3.setAdapter(adapter_more);
        gv4.setAdapter(adapter_more);
        gv5.setAdapter(adapter_more);
        gv6.setAdapter(adapter_more);
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
