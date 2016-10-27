package com.huiche.activity;


import android.widget.ImageButton;
import android.widget.ScrollView;

import com.huiche.R;
import com.huiche.adapter.Adapter_jiFenShopping;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.custemview.MyGridView;

/**
 * Created by Administrator on 2016/9/27.
 */
public class JiFenShopping extends BaseActivity {

    MyGridView gv;
    Adapter_jiFenShopping adapter;
    ScrollView scrollviewf;
    ImageButton imageLeft_titil_all;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_jifenshopping;
    }

    @Override
    public void findViews() {
//        gv = (MyGridView) findViewById(R.id.gv);
//        scrollviewf= (ScrollView) findViewById(R.id.scrollview);
//        imageLeft_titil_all= (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {
//
//        ArrayList<jiFenBean> data = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            data.add(new jiFenBean());
//
//        }
//        adapter = new Adapter_jiFenShopping(this, data);
//        gv.setAdapter(adapter);
//
//
//        // 滚动到顶部：
//        scrollviewf.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public void setListeners() {

//        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
}
