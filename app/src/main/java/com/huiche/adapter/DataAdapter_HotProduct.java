package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.ProductDetailActivity;
import com.huiche.base.ListBaseAdapter;
import com.huiche.bean.ProductInfo;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.utils.LoadImageUtil;
import com.huiche.view.GradientTextView;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/8/14 0014.
 */
public class DataAdapter_HotProduct extends ListBaseAdapter {
    private boolean isProductDetail;//产品详情内部不再响应onclick
    private WindowManager windowManager;
    private Context mContext;
    //    private LayoutInflater inflater;
    private LoadImageUtil loadImageUtil;
    private AddToShoppingCartListener addListener;

    //    private List<ProductInfo> dataList;
    public DataAdapter_HotProduct(Context context, LoadImageUtil loadImageUtil, boolean isProductDetail) {
        this.mContext = context;
//        inflater = LayoutInflater.from(context);
        this.loadImageUtil = loadImageUtil;
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.isProductDetail = isProductDetail;
//        dataList=list;
    }

    public void setAddProductListener(AddToShoppingCartListener addListener) {
        this.addListener = addListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyHolder mHolder = (MyHolder) holder;
        //设置数据
        final ProductInfo info = (ProductInfo) mDataList.get(position);
        loadImageUtil.imageLoader.displayImage(info.goodsImgs.get(0), mHolder.icon, loadImageUtil.options);
        mHolder.productName.setText(info.name);
        DecimalFormat df = new DecimalFormat("######0.00");
        String userin = df.format(info.integral * 0.1);
        mHolder.memberPrice.setText("￥" + userin);
        mHolder.exchangeIntegral.setText(info.integral + "积分兑换");
//            holder.addButton.setImageResource(R.drawable.add_button_blue);
        mHolder.discountText.setText(info.discount + "折");
        //点击事件
        //添加商品
        mHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addListener != null)
                    addListener.addProduct(info);
            }
        });
        //跳转到商品详情
        if (isProductDetail)
            return;
        mHolder.icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", info.id);
                intent.putExtra("businessStoreId", info.businessStoreId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_hotproduct, parent, false);
        return new MyHolder(itemView);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView exchangeIntegral;
        TextView memberPrice;
        TextView productName;
        ImageView icon;
        ImageButton addButton;
        GradientTextView discountText;
        LinearLayout ll_content_hotProduct;
        LinearLayout ll_item_all;

        public MyHolder(View itemView) {
            super(itemView);
            discountText = (GradientTextView) itemView.findViewById(R.id.gradientTextView_discount);
            ll_content_hotProduct = (LinearLayout) itemView.findViewById(R.id.ll_content_hotProduct);
            ll_item_all = (LinearLayout) itemView.findViewById(R.id.ll_item_all);
            addButton = (ImageButton) itemView.findViewById(R.id.imb_add_recommend);
            icon = (ImageView) itemView.findViewById(R.id.iv_productIcon_recommend);
            productName = (TextView) itemView.findViewById(R.id.tv_productName_recommend);
            memberPrice = (TextView) itemView.findViewById(R.id.tv_memberPrice_recommend);
            exchangeIntegral = (TextView) itemView.findViewById(R.id.tv_exchangeIntegral_recommend);

            int displayWidth = windowManager.getDefaultDisplay().getWidth();
            int width = displayWidth / 2 - 50;
            RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(width, width);
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置内容宽度和图片宽度一致
            icon.setLayoutParams(iconParams);
            ll_content_hotProduct.setLayoutParams(llParams);
            //若为产品详情中的店长推荐需要加上margin
            if (isProductDetail) {
//                int margin = TransformUtil.dip2px(mContext, 5);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(margin, margin, margin, margin);
//                ll_item_all.setLayoutParams(layoutParams);
                exchangeIntegral.setTextColor(mContext.getResources().getColor(R.color.yellow));
                addButton.setImageResource(R.drawable.add_button_green);
            }
//            //设置折扣显示比例16:10
            int discountWidth = width / 2;
            int discountHeight = (width / 2) * 10 / 16;
            RelativeLayout.LayoutParams discountParams = new RelativeLayout.LayoutParams(discountWidth, discountHeight);
            discountText.setLayoutParams(discountParams);
            //距离底部7/16height
            discountText.setPadding(0, 0, 0, discountHeight * 7 / 16);
        }
    }
}
