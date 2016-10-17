package com.huiche.adapter;

import android.view.View;

import com.huiche.R;
import com.huiche.bean.MyPartnerBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/10/8.
 */
public class Adapter_MyPartner extends MyBaseAdapter<MyPartnerBean> {
    public Adapter_MyPartner(ArrayList<MyPartnerBean> data) {
        super(data);
    }

    @Override
    public int getContentView() {
        return R.layout.item_my_partner;
    }

    @Override
    public void onInitView(View view, MyPartnerBean myPartnerBean, int position) {

    }
}
