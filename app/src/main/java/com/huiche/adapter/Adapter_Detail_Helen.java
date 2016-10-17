package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.DetailHelenBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/28.
 */
public class Adapter_Detail_Helen extends MyBaseAdapter<DetailHelenBean> {
    public Adapter_Detail_Helen(ArrayList<DetailHelenBean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_detail_helen;
    }

    @Override
    public void onInitView(View view, DetailHelenBean detailHelenBean, int position) {

    }
}
