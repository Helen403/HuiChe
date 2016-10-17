package com.huiche.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiche.R;


/**
 * @author Administrator
 *         卡卷领取提示
 */
public class BufferViewDialog extends Dialog {
    public BufferViewDialog dialog;
    public Context context;

    public BufferViewDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BufferViewDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
//	public void onWindowFocusChanged(boolean hasFocus) {
//		ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
//		// 获取ImageView上的动画背景
//		AnimationDrawable spinner = (AnimationDrawable) imageView
//				.getBackground();
//		// 开始动画
//		spinner.start();
//	}

    /**
     * 给Dialog设置提示信息
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     * <p/>
     * <p/>
     * 上下文
     *
     * @param bufferRedBaoViewDialog
     * @param message                提示
     * @param cancelable             是否按返回键取消
     * @param cancelListener         按下返回键监听
     * @return
     */
    public void show( final BufferRedBaoViewDialog bufferRedBaoViewDialog, CharSequence message,
                     boolean cancelable, OnCancelListener cancelListener) {
        dialog = new BufferViewDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_progress_custom_show);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {

            TextView txt = (TextView) dialog.findViewById(R.id.message);
            TextView textView = (TextView) dialog.findViewById(R.id.tv_2);
            textView.setText(message);

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                    if (bufferRedBaoViewDialog != null)
                        bufferRedBaoViewDialog.setVisibility(View.GONE);

                }
            });
        }
        // 按返回键是否取消
        dialog.setCancelable(cancelable);

        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
//		lp.dimAmount = 0.2f;

        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();

    }

    //可控制缓冲菊花是否可关闭
    public void setCancelableListener(boolean flag) {
        dialog.setCancelable(flag);
    }

    public void dialogcancel() {
        dialog.cancel();
    }

    //有无弹出菊花
    public boolean isShowDialog() {
        return dialog.isShowing();
    }


}
