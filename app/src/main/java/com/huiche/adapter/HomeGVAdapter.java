package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.huiche.R;
import com.huiche.activity.CarBeautifulActivity;
import com.huiche.activity.InsuranceActivity;
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
    public void onInitView(View view, HomeGVBean homeGVBean, final int position) {
        ImageView iv = getViewById(R.id.iv_1);
        iv.setImageResource(homeGVBean.drawable);
        setText(homeGVBean.title, R.id.tv_1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    //汽车美容
                    case 3:
                        goToActivityByClass(CarBeautifulActivity.class);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    //保险
                    case 7:
                        goToActivityByClass(InsuranceActivity.class);
                        break;
                }
            }
        });
    }
}
