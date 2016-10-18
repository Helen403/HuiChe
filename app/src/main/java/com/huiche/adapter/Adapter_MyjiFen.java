package com.huiche.adapter;

import android.content.Context;

import com.huiche.R;
import com.huiche.bean.MyjiFenBean;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;

/**
 * Created by Administrator on 2016/9/27.
 */
public class Adapter_MyjiFen extends MyBaseRecycleAdapter<MyjiFenBean.DataBean> {

    public Adapter_MyjiFen(Context context, MyRecycleView mRecyclerView) {
        super(context, mRecyclerView);
    }

    @Override
    public int getContentView() {
        return R.layout.item_my_jifen;
    }

    @Override
    public void onInitView(RecycleViewHolder holder, MyjiFenBean.DataBean dataBean, int position) {
        holder.setText(dataBean.up_itme,R.id.tv_1);
        holder.setText(dataBean.up_int,R.id.tv_3);
        holder.setText(dataBean.up_date,R.id.tv_2);
    }


}
