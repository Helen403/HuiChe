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
public class BufferDelectCardViewDialog extends RelativeLayout {
    View view;

    //点击确定按钮
    public TextView tv_confirmNormal;

    public BufferDelectCardViewDialog(Context context) {
        super(context);
    }


    /**
     * 弹出自定义ProgressDialog
     * <p/>
     * <p/>
     * 上下文
     */
    public void show() {
        view = View.inflate(getContext(), R.layout.dialog_delete_card_helen, this);

        TextView tv_cancelNormal = (TextView) view.findViewById(R.id.tv_cancelNormal);
//        tv_cancelNormal.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.setVisibility(View.GONE);
//            }
//        });
        tv_confirmNormal = (TextView) view.findViewById(R.id.tv_confirmNormal);

    }


}
