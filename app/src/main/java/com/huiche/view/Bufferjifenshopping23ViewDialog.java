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
public class Bufferjifenshopping23ViewDialog extends RelativeLayout {
    public View view;
    public TextView close;
    public TextView tv_4;


    public Bufferjifenshopping23ViewDialog(Context context) {
        super(context);
        view = (Bufferjifenshopping23ViewDialog) View.inflate(getContext(), R.layout.view_jifenshopping_1, this);

        tv_4 = (TextView) findViewById(R.id.tv_4);
    }

}
