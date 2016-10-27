package com.huiche.activity;

import android.widget.ImageButton;

import com.huiche.R;
import com.huiche.adapter.MoreAdapter;
import com.huiche.bean.MoreBean;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.custemview.MyGridView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MoreActivity extends BaseActivity {

    MyGridView gv1, gv2, gv3, gv4, gv5, gv6;

    MoreAdapter moreAdapter_;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_more;
    }

    @Override
    public void findViews() {
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
        moreAdapter_ = new MoreAdapter(data);
        gv1.setAdapter(moreAdapter_);
        gv2.setAdapter(moreAdapter_);
        gv3.setAdapter(moreAdapter_);
        gv4.setAdapter(moreAdapter_);
        gv5.setAdapter(moreAdapter_);
        gv6.setAdapter(moreAdapter_);
    }

    @Override
    public void setListeners() {
    }
}
