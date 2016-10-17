package com.huiche.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.huiche.activity.ProductDetailActivity;
import com.huiche.bean.ProductInfo;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.utils.LoadImageUtil;
import com.huiche.view.GradientTextView;

import java.util.List;


/**
 * 使用该适配器，该activity需要实现AddToShoppingCartListener
 *
 * @author Administrator
 */
public class ProductGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProductInfo> list;
    private LayoutInflater mInflater;
    private AddToShoppingCartListener addProductListener;
    private LoadImageUtil loadImageUtil;

    public ProductGridViewAdapter(Context context, List<ProductInfo> list, LoadImageUtil loadImageUtil) {
        this.mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.loadImageUtil = loadImageUtil;
    }

    /**
     * 需要实现AddToShoppingCartListener
     *
     * @param context
     * @param list
     * @param loadImageUtil
     * @param listener
     */
    public ProductGridViewAdapter(Context context, List<ProductInfo> list, LoadImageUtil loadImageUtil, Activity listener) {
        this.mContext = context;
        this.addProductListener = (AddToShoppingCartListener) listener;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.loadImageUtil = loadImageUtil;
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
        final ProductInfo info = list.get(position);
        loadImageUtil.imageLoader.displayImage(info.goodsImgs.get(0), holder.icon, loadImageUtil.options);
        holder.productName.setText(info.name);
        holder.memberPrice.setText("￥" + info.realPrice);
        holder.exchangeIntegral.setText(info.integral + "积分兑换");
        holder.addButton.setImageResource(R.drawable.add_button_blue);
        //点击事件
        //添加商品
        holder.addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (addProductListener != null) {
                    addProductListener.addProduct(info);
                }
            }
        });
        //跳转到商品详情
        holder.icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", info.id);
                mContext.startActivity(intent);
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
