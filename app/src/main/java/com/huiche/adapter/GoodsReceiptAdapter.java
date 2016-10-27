package com.huiche.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.huiche.R;
import com.huiche.bean.GoodsReceiptBean;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */
public class GoodsReceiptAdapter extends MyBaseAdapter<GoodsReceiptBean.DataBean> {

    private int mRightWidth = 0;
    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }


    public GoodsReceiptAdapter(List<GoodsReceiptBean.DataBean> data, int width, IOnItemRightClickListener mListener) {
        super(data);
        this.mListener = mListener;
        this.mRightWidth = width;
    }

    @Override
    public int getContentView() {
        return R.layout.item_goods_receipt;
    }

    @Override
    public void onInitView(View view, GoodsReceiptBean.DataBean dataBean, final int position) {
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


//        setText(dataBean.);



    }

}
