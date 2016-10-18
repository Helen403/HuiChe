package com.huiche.adapter;

import android.content.Context;

import com.huiche.R;
import com.huiche.bean.MyPartnerBean;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;


/**
 * Created by Administrator on 2016/10/8.
 */
public class Adapter_MyPartner extends MyBaseRecycleAdapter<MyPartnerBean.DataBean.PartnerBean> {

    public Adapter_MyPartner(Context context, MyRecycleView mRecyclerView) {
        super(context, mRecyclerView);
    }

    @Override
    public int getContentView() {
        return R.layout.item_my_partner;
    }

    @Override
    public void onInitView(RecycleViewHolder holder, MyPartnerBean.DataBean.PartnerBean partnerBean, int position) {
        holder.setImageByUrl(partnerBean.co_img, R.id.iv_1);
        holder.setText(partnerBean.co_name, R.id.tv_1);
        holder.setText(partnerBean.co_area, R.id.tv_2);
        holder.setText("距" + partnerBean.distance, R.id.tv_3);

        String str = partnerBean.co_score.substring(0, 1);
        int mark = Integer.parseInt(str);

        int drawable = getFieldValue("drawable", "comments_star_" + mark + "_0", context);
        holder.getViewById(R.id.iv_2).setBackgroundResource(drawable);
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
