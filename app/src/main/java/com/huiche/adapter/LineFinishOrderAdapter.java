package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.PostResult.LineFinishOrderInfo;
import com.huiche.R;
import com.huiche.view.MyListView;

import java.util.ArrayList;
import java.util.List;


public class LineFinishOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<LineFinishOrderInfo> dataList = new ArrayList<LineFinishOrderInfo>();

    public LineFinishOrderAdapter(Context mContext, List<LineFinishOrderInfo> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;

    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int position) {

        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    Holder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_line_finish_order, null);
            holder = new Holder();
            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_orderID);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_pay_type = (TextView) convertView.findViewById(R.id.tv_pay_type);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_intenal = (TextView) convertView.findViewById(R.id.tv_intenal);
            holder.ll_shop = (LinearLayout) convertView.findViewById(R.id.ll_shop);
            holder.line_view = (View) convertView.findViewById(R.id.line_view);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        LineFinishOrderInfo info = dataList.get(position);
        holder.tv_order.setText(info.getNo());
        holder.tv_shop_name.setText(info.getBusinessName());
        holder.tv_pay_type.setText(info.getPayType());
        holder.tv_date.setText(info.getCreateDate());
        holder.tv_intenal.setText(info.getAllIntegral());


        return convertView;
    }

    public class Holder {
        public TextView tv_order, tv_shop_name, tv_pay_type, tv_date, tv_intenal, tv_button;

        public MyListView myListview;
        public LinearLayout ll_shop, ll_tip;
        public View line_view;

    }

}
