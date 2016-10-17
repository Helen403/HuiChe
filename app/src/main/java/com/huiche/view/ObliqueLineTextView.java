package com.huiche.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.huiche.R;


/**
 * 斜线,从左上角到右下角\
 *
 * @author Administrator
 */
public class ObliqueLineTextView extends TextView {
    private Paint paint;

    public ObliqueLineTextView(Context context) {
        super(context);

    }

    public ObliqueLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ObliqueLineTextView);
        int color = array.getColor(R.styleable.ObliqueLineTextView_line_color, Color.BLACK);
        float lineWidth = array.getDimensionPixelSize(R.styleable.ObliqueLineTextView_line_width, 1);
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);//设置strokewidth,画笔大小，控制线的宽度
        paint.setAntiAlias(true);//抗锯齿，不设置画出来的线会出现锯齿
        array.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, 5, getMeasuredWidth(), getMeasuredHeight() - 5, paint);
        super.onDraw(canvas);
    }

}
