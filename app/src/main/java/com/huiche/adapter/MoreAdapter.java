package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.MoreBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/27.
 */
public class MoreAdapter extends MyBaseAdapter<MoreBean> {
    public MoreAdapter(ArrayList<MoreBean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_more;
    }

    @Override
    public void onInitView(View view, MoreBean moreBean, int position) {

    }
}
