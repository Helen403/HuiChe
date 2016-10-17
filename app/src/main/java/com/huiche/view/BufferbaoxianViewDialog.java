package com.huiche.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;


/**
 * @author Administrator
 *         卡卷领取提示
 */
public class BufferbaoxianViewDialog extends RelativeLayout {
    public View view;
    public ImageView close;
    TextView tv1;


    public BufferbaoxianViewDialog(Context context) {
        super(context);
        view = (BufferbaoxianViewDialog) View.inflate(getContext(), R.layout.view_jihuo, this);
        close = (ImageView) findViewById(R.id.close);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv1.setText("等待审核中...");
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(GONE);
            }
        });

    }


}
