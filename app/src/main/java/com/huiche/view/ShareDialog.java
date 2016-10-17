package com.huiche.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiche.R;


/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class ShareDialog {
    private Dialog mDialog;
    public TextView tv_share_cancel;
    public TextView img_qq, img_weixin, img_pyq,
            img_shouchang;

    public ShareDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_setshare, null);
        mDialog = new Dialog(context, R.style.customDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        img_qq = (TextView) view.findViewById(R.id.tv_qq);
        img_weixin = (TextView) view.findViewById(R.id.tv_weixin);
        img_pyq = (TextView) view.findViewById(R.id.tv_pyq);
        tv_share_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);
        WindowManager manager = mDialog.getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.3f;
        lp.width = (int) (display.getWidth());
        lp.y = 0;
        mDialog.getWindow().setAttributes(lp);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
