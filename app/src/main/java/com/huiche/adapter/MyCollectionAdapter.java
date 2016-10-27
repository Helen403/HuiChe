package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.huiche.R;
import com.huiche.bean.MyCollectionBean;
import com.huiche.lib.lib.base.BaseApplication;
import com.huiche.lib.lib.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MyCollectionAdapter extends MyBaseAdapter<MyCollectionBean.DataBean> {


    private int mRightWidth = 0;
    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }


    public MyCollectionAdapter(List<MyCollectionBean.DataBean> data, int width, IOnItemRightClickListener mListener) {
        super(data);
        this.mListener = mListener;
        this.mRightWidth = width;
    }

    @Override
    public int getContentView() {
        return R.layout.item_my_collection;
    }

    @Override
    public void onInitView(View view, MyCollectionBean.DataBean dataBean, final int position) {
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        getViewById(R.id.aditem_right).setLayoutParams(lp2);
        getViewById(R.id.aditem_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, position);
                }
            }
        });

        //设置数据
        setText(dataBean.goods_name, R.id.tv_1);
        setImageByUrl(dataBean.goods_img, R.id.iv_1);
        setText("距" + dataBean.distance, R.id.tv_3);

        String str = dataBean.goods_mark.substring(0, 1);
        int mark = Integer.parseInt(str);

        int drawable = getFieldValue("drawable", "comments_star_" + mark + "_0", BaseApplication.context);
        getViewById(R.id.iv_1).setBackgroundResource(drawable);
    }


    /**
     * 通过反射获取资源 R.id
     * 根据给定的类型名和字段名，返回R文件中的字段的值
     *
     * @param typeName  属于哪个类别的属性 （id,layout,drawable,string,color,attr......）
     * @param fieldName 字段名
     * @return 字段的值
     */
    public int getFieldValue(String typeName, String fieldName, Context context) {
        int i;
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".R$" + typeName);
            i = clazz.getField(fieldName).getInt(null);
        } catch (Exception e) {
            return -1;
        }
        return i;
    }


}
