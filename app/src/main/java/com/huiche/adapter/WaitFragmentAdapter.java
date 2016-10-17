package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.PostResult.OnLineUnSendDetail;
import com.huiche.PostResult.OnLineUnSendInfo;
import com.huiche.R;
import com.huiche.view.MyListView;

import java.util.ArrayList;
import java.util.List;


public class WaitFragmentAdapter extends BaseAdapter {
    private List<OnLineUnSendInfo> dataList = new ArrayList<>();
    private Context mContext;

    public WaitFragmentAdapter(Context mContext, List<OnLineUnSendInfo> dataList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_online_waitfragment, null);
            holder = new Holder();
            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_orderID);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.myListview = (MyListView) convertView.findViewById(R.id.myListview);
            holder.tv_intenal = (TextView) convertView.findViewById(R.id.tv_intenal);
            holder.ll_shop = (LinearLayout) convertView.findViewById(R.id.ll_shop);
            holder.ll_tip = (LinearLayout) convertView.findViewById(R.id.ll_tip);
            holder.line_view = convertView.findViewById(R.id.line_view);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_cancel_org = (TextView) convertView.findViewById(R.id.tv_cancel_org);
            holder.tv_pay_blue = (TextView) convertView.findViewById(R.id.tv_pay_blue);
            holder.tv_confirm_red = (TextView) convertView.findViewById(R.id.tv_confirm_red);
            holder.ll_wuliu = (LinearLayout) convertView.findViewById(R.id.ll_wuliu);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        OnLineUnSendInfo info = dataList.get(position);
        holder.tv_order.setText(info.no);
        holder.tv_shop_name.setText(info.businessName);
        holder.tv_intenal.setText(info.allIntegral);
        //商品列表
        List<OnLineUnSendDetail> data;
        data = info.onlineProducts;
        if (data.size() > 0) {
            holder.ll_shop.setVisibility(View.VISIBLE);
            holder.line_view.setVisibility(View.VISIBLE);
            OnLineWaitProductAdapter adapter = new OnLineWaitProductAdapter(mContext, data);
            holder.myListview.setAdapter(adapter);

        } else {
            holder.ll_shop.setVisibility(View.GONE);
        }


        return convertView;
    }

    public class Holder {
        public TextView tv_order, tv_shop_name, tv_pay_type, tv_date, tv_intenal, tv_button, tv_status;
        public TextView tv_pay_blue, tv_confirm_red, tv_cancel_org;
        public MyListView myListview;
        public LinearLayout ll_shop, ll_tip, ll_wuliu;
        public View line_view;

    }


}
