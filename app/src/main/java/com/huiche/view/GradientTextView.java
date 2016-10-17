package com.huiche.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 左上角倾斜45度,设置有红色背景的折扣或卡巻类型
 *
 * @author Administrator
 */
public class GradientTextView extends TextView {

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        double tangent = Math.atan2(getMeasuredHeight(), getMeasuredWidth());
        float rotateAngle = (float) (tangent / Math.PI) * 180;
        canvas.rotate(-rotateAngle, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        super.onDraw(canvas);
    }

}
