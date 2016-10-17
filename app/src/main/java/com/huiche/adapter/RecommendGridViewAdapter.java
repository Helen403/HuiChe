package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.RecommendInfo;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.view.GradientTextView;

import java.util.List;


/**
 * 使用该适配器，该activity需要实现AddToShoppingCartListener
 *
 * @author Administrator
 */
public class RecommendGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<RecommendInfo> list;
    private LayoutInflater mInflater;
    private AddToShoppingCartListener addProductListener;

    public RecommendGridViewAdapter(Context context, List<RecommendInfo> list) {
        this.mContext = context;
        this.addProductListener = (AddToShoppingCartListener) context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_gridview_recommend, null);
            holder.discountText = (GradientTextView) convertView.findViewById(R.id.gradientTextView_discount);
            holder.addButton = (ImageButton) convertView.findViewById(R.id.imb_add_recommend);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_productIcon_recommend);
            holder.productName = (TextView) convertView.findViewById(R.id.tv_productName_recommend);
            holder.memberPrice = (TextView) convertView.findViewById(R.id.tv_memberPrice_recommend);
            holder.exchangeIntegral = (TextView) convertView.findViewById(R.id.tv_exchangeIntegral_recommend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置icon显示比例为屏幕1/2
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = windowManager.getDefaultDisplay().getWidth();
        int width = displayWidth / 2 - 50;
        LayoutParams iconParams = new LayoutParams(width, width);
        holder.icon.setLayoutParams(iconParams);
        //设置折扣显示比例16:10
        int discountWidth = width / 2;
        int discountHeight = (width / 2) * 10 / 16;
        LayoutParams discountParams = new LayoutParams(discountWidth, discountHeight);
        holder.discountText.setLayoutParams(discountParams);
        //距离底部7/16height
        holder.discountText.setPadding(0, 0, 0, discountHeight * 7 / 16);
        //设置数据
        final RecommendInfo info = list.get(position);
        holder.icon.setImageDrawable(info.icon);
        holder.productName.setText(info.productName);
        holder.memberPrice.setText("￥" + info.menberPrice);
        holder.exchangeIntegral.setText(info.integralExchange + "积分兑换");
        //点击事件
        holder.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addProductListener.addProduct(info);
            }
        });

        return convertView;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    public static class ViewHolder {
        GradientTextView discountText;
        ImageButton addButton;
        ImageView icon;
        TextView productName, memberPrice, exchangeIntegral;
    }

}
