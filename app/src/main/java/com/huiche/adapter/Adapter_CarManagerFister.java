package com.huiche.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.huiche.R;
import com.huiche.base.CarManagerFisterBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class Adapter_CarManagerFister extends MyBaseAdapter<CarManagerFisterBean> {


    private int mRightWidth = 0;
    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }


    public Adapter_CarManagerFister(ArrayList<CarManagerFisterBean> data, int width, IOnItemRightClickListener mListener) {
        super(data);
        this.mListener = mListener;
        this.mRightWidth = width;
    }

    @Override
    public int getContentView() {
        return R.layout.item_car_manager_fister;
    }

    @Override
    public void onInitView(View view, CarManagerFisterBean carManagerFisterBean, final int position) {
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        getViewById(R.id.aditem_right).setLayoutParams(lp2);
        getViewById(R.id.aditem_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, position);
                }
            }
        });

    }
}
