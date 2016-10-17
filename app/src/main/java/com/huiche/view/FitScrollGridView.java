package com.huiche.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 适配scrollview，解决滑动冲突，需手动把scrollview滚动至最顶端
 * sv.smoothScrollTo(0, 0);
 *
 * @author Administrator
 */
public class FitScrollGridView extends GridView {

    public FitScrollGridView(Context context) {
        super(context);
    }

    public FitScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitScrollGridView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //关键代码
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
