package com.huiche.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiche.R;


/**
 * Created by Administrator on 2016/8/18.
 */
public class DeleteDialog {
    //删除弹窗
    public Dialog mDialog;
    public TextView dialog_content;
    public TextView tv_cancel;
    public TextView tv_confirm;

    public DeleteDialog(Context mContext, String content) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete_card, null);
        dialog_content = (TextView) view.findViewById(R.id.tv_dialogNormal_content);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancelNormal);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirmNormal);
        mDialog = new Dialog(mContext, R.style.customDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        dialog_content.setText(content);
//		tv_cancel.setOnClickListener(mContext);
//		tv_confirm.setOnClickListener(mContext);
        Window diawindow = mDialog.getWindow();
        WindowManager m = mDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
//        p.height = (int) (d.getWidth() * 0.3); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);

    }


    public void showDialog() {
        mDialog.show();
    }

    public void dimissDialog() {
        mDialog.dismiss();
    }
}
