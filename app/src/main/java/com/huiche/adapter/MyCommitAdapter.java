package com.huiche.adapter;

import android.content.Context;
import android.graphics.Color;

import com.huiche.R;
import com.huiche.bean.MyCommitBean;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;


/**
 * Created by Administrator on 2016/9/27.
 */
public class MyCommitAdapter extends MyBaseRecycleAdapter<MyCommitBean.DataBean.EvaluateBean> {


    public MyCommitAdapter(Context context, MyRecycleView mRecyclerView) {
        super(context, mRecyclerView);
    }

    @Override
    public int getContentView() {
        return R.layout.item_my_commit;
    }

    @Override
    public void onInitView(RecycleViewHolder holder, MyCommitBean.DataBean.EvaluateBean evaluateBean, int position) {
        com.huiche.view.CircleImageView icon = holder.getViewById(R.id.cir);
        icon.setBorderWidth(0);
        icon.setBorderColor(Color.parseColor("#00000000"));
        holder.setImageByUrl(evaluateBean.headerimg, icon);
        //评论名字
        holder.setText(evaluateBean.co_name, R.id.tv_1);
        //评论的内容
        holder.setText(evaluateBean.ev_text, R.id.tv_6);
        //评论的时间
        holder.setText(evaluateBean.ev_date, R.id.tv_7);

        //设置评分
        holder.setText(evaluateBean.ev_mark, R.id.tv_2);
        String str = evaluateBean.ev_mark.substring(0, 1);
        int mark = Integer.parseInt(str);

        int drawable = getFieldValue("drawable", "comments_star_" + mark + "_0", context);
        holder.getViewById(R.id.iv_1).setBackgroundResource(drawable);
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
