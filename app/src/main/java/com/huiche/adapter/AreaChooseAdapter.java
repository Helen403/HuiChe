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

public class AreaChooseAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> dataList = new ArrayList<String>();

    public AreaChooseAdapter(Context mContext, List<String> dataList) {
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

    ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_province_list, null);
            holder = new ViewHolder();
            holder.tv_area = (TextView) convertView.findViewById(R.id.tv_province);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_area.setText(dataList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv_area;
    }

}
