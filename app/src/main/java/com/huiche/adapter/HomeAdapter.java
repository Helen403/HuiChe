package com.huiche.adapter;


import com.huiche.R;
import com.huiche.bean.HomeBean;
import com.huiche.lib.lib.base.MyLBaseRecycleAdapter;

/**
 * Created by Administrator on 2016/10/21.
 */
public class HomeAdapter extends MyLBaseRecycleAdapter<HomeBean.DataBean> {

    @Override
    public int getContentView() {
        return R.layout.item_home;
    }

    @Override
    public void onInitView(RecycleViewHolder holder, HomeBean.DataBean dataBean, int position) {
        holder.setText(dataBean.goods_name, R.id.tv_1);
        holder.setImageByUrl(dataBean.goods_img, R.id.iv_1);
        holder.setText(dataBean.area, R.id.tv_2);
        holder.setText("距" + dataBean.distance, R.id.tv_4);
    }


}
