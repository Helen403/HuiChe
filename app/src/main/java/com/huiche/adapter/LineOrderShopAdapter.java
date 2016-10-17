package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.PostResult.LineOrderDetail;
import com.huiche.R;
import com.huiche.base.BaseImageAdapter;
import com.huiche.utils.ImagLoadUtils;

import java.util.ArrayList;
import java.util.List;


public class LineOrderShopAdapter extends BaseImageAdapter<LineOrderDetail> {

    private List<LineOrderDetail> datalist = new ArrayList<LineOrderDetail>();
    private LayoutInflater flater;


    public LineOrderShopAdapter(Context context, List<LineOrderDetail> list) {
        super(context, list);
        this.dataList = list;
        this.datalist = list;
        this.context = context;
        flater = LayoutInflater.from(context);
    }

    ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = flater.inflate(R.layout.item_line_order_shop_detail, null);
            holder = new ViewHolder();
            holder.img_shop = (ImageView) convertView.findViewById(R.id.img_shop);
            holder.tv_intergal_price = (TextView) convertView.findViewById(R.id.tv_intergal_price);
            holder.tv_markey_price = (TextView) convertView.findViewById(R.id.tv_markey_price);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_shop_num = (TextView) convertView.findViewById(R.id.tv_shop_num);
            holder.tv_sum_intergal = (TextView) convertView.findViewById(R.id.tv_sum_intergal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String count = datalist.get(position).count;
        String intergal = datalist.get(position).integral;
        holder.tv_shop_name.setText(datalist.get(position).name);
        holder.tv_markey_price.setText(datalist.get(position).marketPrice + "元");
        holder.tv_intergal_price.setText(intergal);
        holder.tv_shop_num.setText("X " + count);
        if (!count.equals("") && !intergal.equals("")) {
            int s = Integer.parseInt(count);
            double jifen = Double.parseDouble(intergal);
            double sum = s * jifen;
            holder.tv_sum_intergal.setText(sum + "");
        }

        String path = datalist.get(position).goodsThumbs[0];
        ImagLoadUtils.setImage(path, holder.img_shop, context);
        //imageLoader.displayImage(path, holder.img_shop, options);
        return convertView;
    }


    public class ViewHolder {
        public ImageView img_shop;
        public TextView tv_shop_name, tv_shop_num, tv_markey_price, tv_intergal_price, tv_sum_intergal;
    }

}
