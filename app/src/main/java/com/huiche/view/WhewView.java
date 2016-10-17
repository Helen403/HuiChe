package com.huiche.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * U_U
 *
 * @author LGL
 */
public class WhewView extends View {

    private Paint paint;
    private int maxWidth = 255;
    // 是否运行
    private boolean isStarting = false;
    private List<Integer> alphaList = new ArrayList<Integer>();
    private List<Integer> startWidthList = new ArrayList<Integer>();
    private int speed = 1;// 默认为1
    private int rippleWidthScale = 4;// 波纹宽度，要换算为maxWidth的几分之几
    private int centerRadius = 200;// 中心图片半径
    private int rippleColor = 0x0020AFE7;// 波纹中心初始颜色
    private int rippleAlpha = 180;// 开始透明度，255为完全不透明，0为完全透明
    private int alphaSpeed = 1;// 透明度变化速度，默认为1

    public WhewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init();
    }

    public WhewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public WhewView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        paint = new Paint();
        // 设置博文的颜色
        paint.setColor(rippleColor);// 0x0059ccf5
        alphaList.add(rippleAlpha);// 圆心的不透明度
        startWidthList.add(0);
    }

    /**
     * 设置波纹开始透明度,默认为255完成不透明，0为完全透明
     */
    public void setRippleAlpha(int rippleAlpha) {
        this.rippleAlpha = rippleAlpha;
    }

    /**
     * 设置波纹初始颜色，默认蓝色0x0020AFE7
     */
    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    /**
     * 设置开始扩散中心点半径（默认200）应该设置为中心图片宽度一半，效果为从中心图片边缘开始扩散
     */
    public void setCenterRadius(int centerRadius) {
        this.centerRadius = centerRadius;
    }

    /**
     * 设置波纹宽度比例，默认为屏幕宽度的1/4
     */
    public void setRippleWidthScale(int rippleWidthScale) {
        this.rippleWidthScale = rippleWidthScale;

    }

    /**
     * 设置扩散透明度变化速度，默认1
     *
     * @param alphaSpeed
     */
    public void setAlphaSpeed(int alphaSpeed) {
        this.alphaSpeed = alphaSpeed;
    }

    /**
     * 设置扩散速度，默认1
     *
     * @param speed 扩散速度
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 设置扩散最大半径宽度(不包括中心图片的半径),默认255,若speed！=alphaSpeed，该值应与屏幕高度一致（超出屏幕消失）
     *
     * @param maxWidth
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(rippleColor);
        setBackgroundColor(Color.TRANSPARENT);// 颜色：完全透明
        // 依次绘制 同心圆
        for (int i = 0; i < alphaList.size(); i++) {

            int alpha = alphaList.get(i);
            // 圆半径
            int startWidth = startWidthList.get(i);
            paint.setAlpha(alpha);
            // 同心圆扩散&& startWidth < maxWidth
            // 这个半径决定你想要多大的扩散面积
            if (isStarting && alpha > 0 && startWidth < maxWidth) {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, startWidth
                        + centerRadius, paint);
                alpha = alpha - alphaSpeed;
                startWidth = startWidth + speed;
                alphaList.set(i, alpha);
                startWidthList.set(i, startWidth);
            } else if (isStarting && startWidth >= maxWidth) {
                alphaList.set(i, 0);
                startWidthList.set(i, 0);
            }
        }
        int times = maxWidth / speed;// 倍数
        int lastPosition = startWidthList.size() - 1;
        // 计算占屏幕几分之几对应占最大扩散宽度的几分之几
        int scale = maxWidth * rippleWidthScale / getWidth();
        if (isStarting
                && startWidthList.get(lastPosition) == times / scale * speed) {
            // 添加一个新的波纹
            alphaList.add(rippleAlpha);
            startWidthList.add(0);
        }
        // // 同心圆数量达到10个，删除最外层圆
        if (isStarting && startWidthList.size() == 10) {
            startWidthList.remove(0);
            alphaList.remove(0);
        }
        // 刷新界面
        invalidate();

    }

    // 执行动画
    public void start() {
        isStarting = true;
    }

    // 停止动画
    public void stop() {
        isStarting = false;
    }

    // 判断是都在不在执行
    public boolean isStarting() {
        return isStarting;
    }

}
