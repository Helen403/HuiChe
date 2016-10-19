package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;

public class MyPersionalAdapter extends BaseAdapter {
    private String[] strType;
    private int[] imgData;
    private Context context;
    private LayoutInflater flater;

    public MyPersionalAdapter(Context context, String[] strType, int[] imgData) {
        this.context = context;
        this.imgData = imgData;
        this.strType = strType;
        flater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgData.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Holder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new Holder();
            convertView = flater.inflate(R.layout.item_mypersional, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_name.setText(strType[position]);
        holder.img_pic.setImageResource((imgData[position]));
        return convertView;
    }

    class Holder {
        public TextView tv_name;
        public ImageView img_pic;

    }

}
