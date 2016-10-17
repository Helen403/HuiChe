package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.utils.ImagLoadUtils;
import com.huiche.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/19.
 */
public class RedPacketAdapter extends BaseAdapter {
    public List<Map<String, String>> dataList = new ArrayList<>();
    public Context mContext;

    public RedPacketAdapter(Context context, List<Map<String, String>> dataList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_redpacket, null);
            holder = new ViewHolder();
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);
            holder.user_img = (CircleImageView) convertView.findViewById(R.id.img_user);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String path = dataList.get(position).get("image");
        ImagLoadUtils.setImage(path, holder.user_img, mContext);
        holder.tv_name.setText(dataList.get(position).get("businessStoreName"));
        holder.tv_date.setText(dataList.get(position).get("date"));
        holder.tv_integral.setText("+" + dataList.get(position).get("integral") + "积分");
        return convertView;
    }

    class ViewHolder {
        CircleImageView user_img;
        TextView tv_name, tv_date, tv_integral;
    }
}
