package com.huiche.adapter;


import com.huiche.R;
import com.huiche.bean.HomeBean;
import com.huiche.lib.lib.base.MyLBaseRecycleAdapter;

/**
 * Created by Administrator on 2016/10/21.
 */
public class HomeAdapter extends MyLBaseRecycleAdapter<HomeBean> {

    @Override
    public int getContentView() {
        return R.layout.item_home;
    }

    @Override
    public void onInitView(RecycleViewHolder holder, HomeBean homeBean, int position) {

    }
}
