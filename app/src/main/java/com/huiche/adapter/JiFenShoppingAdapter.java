package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.huiche.R;
import com.huiche.activity.jiFenShopping22Activity;
import com.huiche.bean.jiFenBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class JiFenShoppingAdapter extends MyBaseAdapter<jiFenBean> {
    Context context;

    public JiFenShoppingAdapter(Context context, ArrayList<jiFenBean> data) {
        super(data);
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_jifne;
    }

    @Override
    public void onInitView(View view, jiFenBean jiFenBean, int position) {
        getViewById(R.id.tv_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, jiFenShopping22Activity.class);
                context.startActivity(intent);
            }
        });
    }
}
