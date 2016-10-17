package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.OrderData;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class ShoppingSubOrderAdapter extends BaseAdapter {
    private LayoutInflater inflater;
//    private Context context;
    private List<OrderData.DataBean.ProductListsBean> dataList;

    public ShoppingSubOrderAdapter(Context context, List<OrderData.DataBean.ProductListsBean> dataList) {
//        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        SubViewHolder holder;
        if (convertView == null) {
            holder = new SubViewHolder();
            convertView = inflater.inflate(R.layout.item_shopping_sub_order, null);
            holder.tv_productName = (TextView) convertView.findViewById(R.id.tv_productName_Order);
            holder.tv_productCount = (TextView) convertView.findViewById(R.id.tv_productCount_Order);
            convertView.setTag(holder);
        } else {
            holder = (SubViewHolder) convertView.getTag();
        }
        OrderData.DataBean.ProductListsBean bean =dataList.get(i);
        holder.tv_productCount.setText("X"+bean.getCount());
        holder.tv_productName.setText(bean.getName()+"");
        return convertView;
    }

    public class SubViewHolder {
        TextView tv_productName;
        TextView tv_productCount;
    }


}
