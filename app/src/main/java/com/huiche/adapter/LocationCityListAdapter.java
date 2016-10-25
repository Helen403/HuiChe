package com.huiche.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.LocationBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

/**
 * Created by Administrator on 2016/10/25.
 */
public class LocationCityListAdapter extends MyBaseAdapter<LocationBean.DataBean.CitylistBean> {
    public LocationCityListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentView() {
        return R.layout.item_location_city_list;
    }

    @Override
    public void onInitView(View view, LocationBean.DataBean.CitylistBean citylistBean, int position) {
        TextView tv = getViewById(R.id.tv_1);
        if (citylistBean.ci_id.equals("10086")) {
            tv.setBackgroundColor(Color.parseColor("#f5f5f5"));
        } else {
            tv.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        setText(citylistBean.ci_name, R.id.tv_1);
    }
}
