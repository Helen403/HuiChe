package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.OrderData;
import com.huiche.view.MyListView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class ShoppingSuperOrderAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<OrderData.DataBean> dataList;

    public ShoppingSuperOrderAdapter(Context context, List<OrderData.DataBean> dataList) {
        this.context = context;
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
        SuperViewHolder holder;
        if (convertView == null) {
            holder = new SuperViewHolder();
            convertView = inflater.inflate(R.layout.item_shopping_super_order, null);
            holder.tv_storeName = (TextView) convertView.findViewById(R.id.tv_storeName_Order);
            holder.lv_orderSubMsg = (MyListView) convertView.findViewById(R.id.lv_orderSubMsg);
            convertView.setTag(holder);
        } else {
            holder = (SuperViewHolder) convertView.getTag();
        }
        OrderData.DataBean bean = dataList.get(i);
        holder.tv_storeName.setText("店铺：" + bean.getBusinessName() + "");
        holder.lv_orderSubMsg.setAdapter(new ShoppingSubOrderAdapter(context, bean.getProductLists()));
        return convertView;
    }

    public class SuperViewHolder {
        TextView tv_storeName;
        MyListView lv_orderSubMsg;
    }

}
