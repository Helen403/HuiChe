package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;

import java.util.List;
import java.util.Map;

public class IntegralAdapter extends BaseAdapter {
    private List<Map<String, String>> dataList;
    private Context context;
    private LayoutInflater inflater;

    public IntegralAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.dataList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_intergral, null);
            holder.money_integral = (TextView) convertView.findViewById(R.id.money_integral);
            holder.money_integral = (TextView) convertView.findViewById(R.id.money_integral);
            holder.money_integral = (TextView) convertView.findViewById(R.id.money_integral);
            holder.money_integral = (TextView) convertView.findViewById(R.id.money_integral);
            holder.money_integral = (TextView) convertView.findViewById(R.id.money_integral);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        public TextView money_integral;
        public TextView tv_why;
        public TextView tv_why_integral;
        public TextView tv_date;
        public TextView tv_time;
        private TextView tv_phone;


    }

}
