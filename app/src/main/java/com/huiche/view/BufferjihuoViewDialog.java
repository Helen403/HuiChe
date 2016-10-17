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
public class BufferjihuoViewDialog extends RelativeLayout {
    public View view;
    public ImageView close;
    public TextView tv_1;




    public BufferjihuoViewDialog(Context context) {
        super(context);
        view = (BufferjihuoViewDialog) View.inflate(getContext(), R.layout.view_jihuo, this);
        close = (ImageView) findViewById(R.id.close);
        tv_1= (TextView) findViewById(R.id.tv_1);

    }


}
