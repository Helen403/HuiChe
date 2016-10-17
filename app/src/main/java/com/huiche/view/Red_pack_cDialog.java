package com.huiche.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;


/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class Red_pack_cDialog {
    //删除弹窗
    public Dialog mDialog;
    public TextView tv_red_type, tv_inter_red;
    public ImageView img_finish;

    public Red_pack_cDialog(Context mContext, String content, String money) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_red, null);
        mDialog = new Dialog(mContext, R.style.customDialog);
        img_finish = (ImageView) view.findViewById(R.id.img_finish);
        tv_red_type = (TextView) view.findViewById(R.id.tv_red_type);
        tv_inter_red = (TextView) view.findViewById(R.id.tv_inter_red);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        Window diawindow = mDialog.getWindow();
        WindowManager m = mDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.2
//        p.height = (int) (d.getWidth() * 0.3); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);

    }


    public void showDialog() {
        mDialog.show();
    }

    public void dimissDialog() {
        mDialog.dismiss();
    }
}
