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
public class BufferpayViewDialog extends RelativeLayout {
    public View view;
    public TextView close;
    public TextView tv_2;
    public TextView tv_3;




    public BufferpayViewDialog(Context context) {
        super(context);
        view = (BufferpayViewDialog) View.inflate(getContext(), R.layout.view_pay, this);
        close = (TextView) findViewById(R.id.close);

        tv_2= (TextView) findViewById(R.id.tv_2);
        tv_3= (TextView) findViewById(R.id.tv_3);


        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(GONE);
            }
        });
    }


}
