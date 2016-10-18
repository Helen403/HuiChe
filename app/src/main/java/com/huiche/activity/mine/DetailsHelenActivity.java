package com.huiche.activity.mine;

import com.huiche.R;
import com.huiche.adapter.Adapter_Detail_Helen;
import com.huiche.lib.lib.custemview.MyRecycleView;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DetailsHelenActivity extends com.huiche.lib.lib.base.BaseActivity {
    Adapter_Detail_Helen adapter;

    MyRecycleView myRecycleView;


    @Override
    public int getContentView() {
        return R.layout.activity_detail_helen;
    }

    @Override
    public void findViews() {
        setTitle("明细");
        myRecycleView = (MyRecycleView) findViewById(R.id.my_recycleview);
    }

    @Override
    public void initData() {
//        ArrayList<DetailHelenBean> data = new ArrayList();
//        for (int i = 0; i < 10; i++) {
//            data.add(new DetailHelenBean());
//        }
//        adapter = new Adapter_Detail_Helen(data);
//        listview.setAdapter(adapter);
    }

    @Override
    public void setListeners() {
    }
}
