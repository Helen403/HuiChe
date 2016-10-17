package com.huiche.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;


/**
 * @author Administrator
 *         卡卷领取提示
 */
public class BufferCardDetailDialog extends RelativeLayout {
    View view;

    //点击确定按钮
    public TextView tv_confirmNormal;

    public BufferCardDetailDialog(Context context) {
        super(context);
    }


    /**
     * 弹出自定义ProgressDialog
     * <p/>
     * <p/>
     * 上下文
     */
    public void show() {
        view = View.inflate(getContext(), R.layout.dialog_card_detail_helen, this);

    }


}
