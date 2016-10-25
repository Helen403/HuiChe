package com.huiche.adapter;

import android.content.Context;
import android.view.View;

import com.huiche.R;
import com.huiche.bean.LocationBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

/**
 * Created by Administrator on 2016/10/25.
 */
public class LocationHotcityAdapter extends MyBaseAdapter<LocationBean.DataBean.HotcityBean> {
    public LocationHotcityAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentView() {
        return R.layout.item_location_city;
    }

    @Override
    public void onInitView(View view, LocationBean.DataBean.HotcityBean hotcityBean, int position) {
        setText(hotcityBean.ci_name, R.id.tv_4);
    }
}
