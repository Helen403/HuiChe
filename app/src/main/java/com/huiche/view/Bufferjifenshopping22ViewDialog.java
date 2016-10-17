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
public class Bufferjifenshopping22ViewDialog extends RelativeLayout {
    public View view;
    public TextView close;
    public TextView tv_4;


    public Bufferjifenshopping22ViewDialog(Context context) {
        super(context);
        view = (Bufferjifenshopping22ViewDialog) View.inflate(getContext(), R.layout.view_jifenshopping, this);
        close = (TextView) findViewById(R.id.tv_3);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(GONE);
            }
        });
        tv_4 = (TextView) findViewById(R.id.tv_4);
    }


}
