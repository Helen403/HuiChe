package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends BaseAdapter {
//    private Context context;
    private List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
    private LayoutInflater inflater;


    public MessageAdapter(Context context, List<Map<String, String>> dataList) {
//        this.context = context;
        this.dataList = dataList;
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
            convertView = inflater.inflate(R.layout.item_message, null);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(dataList.get(position).get("content"));
        holder.tv_time.setText(dataList.get(position).get("time"));


        return convertView;
    }

    class ViewHolder {
        public TextView tv_content;
        public TextView tv_date;
        public TextView tv_time;
    }

}
