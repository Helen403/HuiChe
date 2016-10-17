package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.huiche.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnLineMyEvaluateAdapter extends BaseAdapter {
    private List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
    private Context mContext;

    public OnLineMyEvaluateAdapter(Context context, List<Map<String, String>> dataList) {
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate, null);
            holder = new ViewHolder();
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_shop_name.setText(dataList.get(position).get("businessName"));
        holder.tv_date.setText(dataList.get(position).get("time"));
        holder.tv_content.setText(dataList.get(position).get("content"));
        String level = dataList.get(position).get("level");
        if (!level.equals("")) {
            float value = Float.parseFloat(level);
            holder.ratingbar.setRating(value);
        }


        return convertView;
    }

    class ViewHolder {
        public TextView tv_shop_name, tv_date, tv_content;
        public RatingBar ratingbar;


    }

}
