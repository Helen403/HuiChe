package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.huiche.R;
import com.huiche.bean.HomeGVBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

/**
 * Created by Administrator on 2016/10/21.
 */
public class HomeGVAdapter extends MyBaseAdapter<HomeGVBean> {

    public HomeGVAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentView() {
        return R.layout.item_home_gv;
    }

    @Override
    public void onInitView(View view, HomeGVBean homeGVBean, int position) {
        ImageView iv = getViewById(R.id.iv_1);
        iv.setImageResource(homeGVBean.drawable);
        setText(homeGVBean.title, R.id.tv_1);
    }
}