package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
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
import com.huiche.base.MyApplication;
import com.huiche.bean.ProductInfo;
import com.huiche.utils.LoadImageUtil;
import com.huiche.view.GradientTextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class RecyclerAdapter_HotProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private WindowManager windowManager;
    private LoadImageUtil loadImageUtil;
    private Context mContext;
    private List<ProductInfo> dataList;
    private onItemClickListener onItemClickListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean visiable = true;
    //    private static final int TYPE_HEADER=2;
    private RecyclerView recyclerView;

    /**
     * 没有尾部加载更多使用该构造方法
     *
     * @param context
     * @param list
     * @param util
     */
    public RecyclerAdapter_HotProduct(Context context, List<ProductInfo> list, LoadImageUtil util) {
        mContext = context;
        dataList = list;
        loadImageUtil = util;
        //设置icon显示比例为屏幕1/2
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

    }

    /**
     * 有尾布局，传进recyclerView用于解决加载item不能填充满布局只显示正常item宽高
     *
     * @param context
     * @param list
     * @param util
     * @param recyclerView
     */
    public RecyclerAdapter_HotProduct(Context context, List<ProductInfo> list, LoadImageUtil util, RecyclerView recyclerView) {
        mContext = context;
        dataList = list;
        loadImageUtil = util;
        //设置icon显示比例为屏幕1/2
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.recyclerView = recyclerView;
        setSpanCount(recyclerView);
    }

    /**
     * 设置每个条目占用的列数
     */
    private void setSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == TYPE_ITEM) {
                        return 1;
                    } else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            });
        }
    }

    /**
     * 设置监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据viewType添加不同viewHolder
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_hotproduct, parent, false);
            return new MyHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_foot_loadingmore, parent, false);
            return new FootViewHolder(itemView);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //最后一项item为footer
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyHolder) {
            MyHolder holder = (MyHolder) viewHolder;
            //设置数据
            DecimalFormat df = new DecimalFormat("######0.0");
            final ProductInfo info = dataList.get(position);
            loadImageUtil.imageLoader.displayImage(info.goodsImgs.get(0), holder.icon, loadImageUtil.options);
            holder.productName.setText(info.name);
            holder.memberPrice.setText("￥" + df.format(info.integral * 0.1));
            holder.exchangeIntegral.setText(info.integral + "积分兑换");
//            holder.addButton.setImageResource(R.drawable.add_button_blue);
            holder.discountText.setText(info.discount + "折");
            //点击事件
            if (MyApplication.token.equals("")) {
                holder.addButton.setVisibility(View.GONE);
            } else {
                holder.addButton.setVisibility(View.VISIBLE);
            }
            //添加商品
            holder.addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.addProduct(position);

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
            //长按删除
            holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //test remove
                    if (onItemClickListener != null) {
                        onItemClickListener.removeProduct(position);
                    }
                    return false;
                }
            });
        } else if (viewHolder instanceof FootViewHolder) {
            if (!visiable) {
                ((FootViewHolder) viewHolder).ll_loadingMore.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (dataList.size() > 0)
            return dataList.size() + 1;//+1添加尾部
        else
            return 0;

    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView exchangeIntegral;
        TextView memberPrice;
        TextView productName;
        ImageView icon;
        ImageButton addButton;
        GradientTextView discountText;
        LinearLayout ll_content_hotProduct;

        public MyHolder(View itemView) {
            super(itemView);
            discountText = (GradientTextView) itemView.findViewById(R.id.gradientTextView_discount);
            ll_content_hotProduct = (LinearLayout) itemView.findViewById(R.id.ll_content_hotProduct);
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
//            //设置折扣显示比例16:10
            int discountWidth = width / 2;
            int discountHeight = (width / 2) * 10 / 16;
            RelativeLayout.LayoutParams discountParams = new RelativeLayout.LayoutParams(discountWidth, discountHeight);
            discountText.setLayoutParams(discountParams);
            //距离底部7/16height
            discountText.setPadding(0, 0, 0, discountHeight * 7 / 16);
        }
    }

    /**
     * footView,上拉加载更多
     */
    class FootViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_loadingMore;

        public FootViewHolder(View itemView) {
            super(itemView);
            ll_loadingMore = (LinearLayout) itemView.findViewById(R.id.ll_loadingMore);
        }
    }


    /**
     * 回调接口
     */
    public interface onItemClickListener {
        void addProduct(int position);

        void removeProduct(int position);
    }

    public void addOne(int pos) {
        dataList.add(pos, dataList.get(0));
        notifyItemInserted(pos);
        //最后一项增加或删除position正常改变
        // 加如下代码保证position的位置正确
        notifyItemInserted(pos);
        if (pos != dataList.size() - 1) {
            notifyItemRangeChanged(pos, dataList.size() - pos);
        }
    }

    public void removeOne(int pos) {
        dataList.remove(pos);
        notifyItemRemoved(pos);
        // 加如下代码保证position的位置正确
        if (pos != dataList.size() - 1) {
            notifyItemRangeChanged(pos, dataList.size() - pos);
        }
    }

    /**
     * 加载结束，去掉
     */
    public void finishLoadMore() {
        notifyItemRemoved(getItemCount() - 1);
    }

    /**
     * 加载更多
     */
    public void addMore(List<ProductInfo> moreData) {
        //插入数据
        notifyItemRangeInserted(getItemCount(), moreData.size());
        dataList.addAll(moreData);
        //remove loadMoreTips
        finishLoadMore();
    }

    public void showLoadMore(boolean visiable) {
        this.visiable = visiable;
    }
}
