package com.huiche.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.huiche.R;
import com.huiche.bean.CarManagerFisterBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class Adapter_CarManagerFister extends MyBaseAdapter<CarManagerFisterBean.DataBean.CarBean> {


    private int mRightWidth = 0;
    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }


    public Adapter_CarManagerFister(List<CarManagerFisterBean.DataBean.CarBean> data, int width, IOnItemRightClickListener mListener) {
        super(data);
        this.mListener = mListener;
        this.mRightWidth = width;
    }

    @Override
    public int getContentView() {
        return R.layout.item_car_manager_fister;
    }


    @Override
    public void onInitView(View view, CarManagerFisterBean.DataBean.CarBean carBean, final int position) {
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

        //设置数据
        //设置车的品牌
        setText(carBean.ca_brand, R.id.tv_1);
        //设置车的型号
        setText(carBean.ca_model, R.id.tv_2);
        //设置车购买时间
        setText(carBean.ca_buy, R.id.tv_3);
        //设置车的年检时间
        setText(carBean.ca_inspect, R.id.tv_4);
        //设置车更换润滑油日期
        setText(carBean.ca_lube, R.id.tv_5);

    }

}
