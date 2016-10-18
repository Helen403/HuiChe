package com.huiche.adapter;

import android.content.Context;

import com.huiche.R;
import com.huiche.bean.DetailHelenBean;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;


/**
 * Created by Administrator on 2016/9/28.
 */
public class Adapter_Detail_Helen extends MyBaseRecycleAdapter<DetailHelenBean.DataBean.CommissionBean> {

    public Adapter_Detail_Helen(Context context, MyRecycleView mRecyclerView) {
        super(context, mRecyclerView);
    }

    @Override
    public int getContentView() {
        return R.layout.item_detail_helen;
    }

    @Override
    public void onInitView(RecycleViewHolder holder, DetailHelenBean.DataBean.CommissionBean commissionBean, int position) {
        holder.setText(commissionBean.com_company, R.id.tv_1);
        holder.setText("余额:" + commissionBean.com_balance, R.id.tv_3);
        //设置佣金数
        if ("0".equals(commissionBean.com_type)) {
            holder.setText("+" + commissionBean.com_money, R.id.tv_2);

        } else {
            holder.setText("-" + commissionBean.com_money, R.id.tv_2);
        }
        holder.setText(commissionBean.com_time, R.id.tv_4);


    }
}
