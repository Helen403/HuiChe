package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.PostResult.CollectionShop;
import com.huiche.R;
import com.huiche.activity.PayActivity;
import com.huiche.utils.ImagLoadUtils;

import java.util.ArrayList;
import java.util.List;


public class MyShopCollectionAdapter extends BaseAdapter {
    private int mRightWidth = 0;
    private Context mContext;
    private List<CollectionShop> data = new ArrayList<CollectionShop>();

    /**
     * 单击事件监听器
     */
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }

    public MyShopCollectionAdapter(Context context, List<CollectionShop> data, int width, IOnItemRightClickListener l) {
        this.mContext = context;
        this.data = data;
        mListener = l;
        this.mRightWidth = width;
//        int six = data.size();
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    ViewHolder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_my_shop_collection, parent, false);
            holder = new ViewHolder();
            holder.img_shop = (ImageView) convertView.findViewById(R.id.img_shop);
            holder.img_v = (ImageView) convertView.findViewById(R.id.img_v);
            holder.img_dui = (ImageView) convertView.findViewById(R.id.img_dui);
            holder.img_ji = (ImageView) convertView.findViewById(R.id.img_ji);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_shop_address = (TextView) convertView.findViewById(R.id.tv_shop_address);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            holder.ll_image = (LinearLayout) convertView.findViewById(R.id.ll_image);
            holder.aditem_right = (RelativeLayout) convertView.findViewById(R.id.aditem_right);
            holder.ll_reft = (LinearLayout) convertView.findViewById(R.id.ll_reft);
            holder.tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        holder.aditem_right.setLayoutParams(lp2);

        holder.img_v.setVisibility(View.GONE);
        holder.img_dui.setVisibility(View.GONE);
        holder.img_ji.setVisibility(View.GONE);
        String isExchange = data.get(position).isExchange;
        if (isExchange.equals("兑")) {
            holder.img_dui.setVisibility(View.VISIBLE);
        } else {
            holder.img_dui.setVisibility(View.GONE);
        }
        String isIntegra = data.get(position).isIntegeral;
        if (isIntegra.equals("积")) {
            holder.img_ji.setVisibility(View.VISIBLE);
        } else {
            holder.img_ji.setVisibility(View.GONE);
        }
        String vip = data.get(position).vip;
        if (vip.equals("vip")) {
            holder.img_v.setVisibility(View.VISIBLE);
        } else {
            holder.img_v.setVisibility(View.GONE);
        }

        String address = data.get(position).businessAddress;
        holder.tv_shop_address.setText(address);
        holder.tv_shop_name.setText(data.get(position).businessName);
        String level = data.get(position).level;
        if (!level.equals("")) {

            //float value=Float.parseFloat(level);
            holder.ratingbar.setRating(Integer.parseInt(level));
        }
        String path = data.get(position).photo;
        ImagLoadUtils.setImage(path, holder.img_shop, mContext);
        //imageLoader.displayImage(path, holder.img_shop, options);

        holder.aditem_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, position);
                }
            }
        });


        if (!data.get(position).isHavePay()) {
            holder.tv_confirm.setBackgroundResource(R.drawable.textview_background_stoke_helen);
            holder.tv_confirm.setTextColor(Color.parseColor("#AFAFAF"));
            holder.tv_confirm.setEnabled(false);
//			holder.tv_confirm.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_confirm.setBackgroundResource(R.drawable.textview_background_stoke);
            holder.tv_confirm.setTextColor(Color.parseColor("#20AFE7"));
            holder.tv_confirm.setEnabled(true);

        }

        //买单按钮
        holder.tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String shopId = data.get(position).businessStoreId;
                intent.putExtra("businessID", shopId);
                intent.putExtra("businessName", data.get(position).getBusinessName());
                intent.setClass(mContext, PayActivity.class);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public ImageView img_shop, img_v, img_dui, img_ji;
        public TextView tv_shop_name, tv_shop_address, tv_confirm;
        public RatingBar ratingbar;
        public LinearLayout ll_image, ll_reft;
        public RelativeLayout aditem_right;


    }


}
