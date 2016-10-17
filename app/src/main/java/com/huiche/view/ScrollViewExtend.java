package com.huiche.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 能够兼容ViewPager的ScrollView
 *
 * @Description: 解决了ViewPager在ScrollView中的滑动反弹问题
 * @File: ScrollViewExtend.java
 * @Package com.image.indicator.control
 * @Author Hanyonglu
 * @Date 2012-6-18 下午01:34:50
 * @Version V1.0
 */
public class ScrollViewExtend extends ScrollView {
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    private OnScrollToBottomListener onScrollToBottom;

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 添加监听滚动底部listener
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {

        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY != 0 && null != onScrollToBottom) {
            onScrollToBottom.onScrollBottomListener(clampedY);
        }

    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener {
        public void onScrollBottomListener(boolean isBottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}