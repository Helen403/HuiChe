package com.huiche.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.huiche.R;
import com.huiche.bean.GoodsReceiptBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/30.
 */
public class Adapter_GoodsReceipt extends MyBaseAdapter<GoodsReceiptBean> {

    private int mRightWidth = 0;
    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }


    public Adapter_GoodsReceipt(ArrayList<GoodsReceiptBean> data,int width, IOnItemRightClickListener mListener) {
        super(data);
        this.mListener = mListener;
        this.mRightWidth = width;
    }

    @Override
    public int getContentView() {
        return R.layout.item_goods_receipt;
    }

    @Override
    public void onInitView(View view, GoodsReceiptBean goodsReceiptBean, final int position) {
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
