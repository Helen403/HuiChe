package com.huiche.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.ListBaseAdapter;
import com.huiche.bean.ProductInfo;
import com.huiche.listener.AddToShoppingCartListener;
import com.huiche.utils.LoadImageUtil;
import com.huiche.view.GradientTextView;


public class ProductDetailGridViewAdapter extends ListBaseAdapter {
	private LoadImageUtil loadImageUtil;
	private AddToShoppingCartListener addProductListener;
	private  LayoutInflater mInflater;
//	private  List<ProductInfo> list;

	public ProductDetailGridViewAdapter (Context context,LoadImageUtil loadImageUtil) {
		this.mContext=context;
		this.addProductListener=(AddToShoppingCartListener) context;
//		this.list=list;
		this.loadImageUtil=loadImageUtil;
		mInflater=LayoutInflater.from(context);
	}
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		MyHolder holder = (MyHolder) viewHolder;
		//设置icon显示比例为屏幕1/2
		WindowManager windowManager=(WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		int displayWidth=windowManager.getDefaultDisplay().getWidth();
		int width=displayWidth/2-50;
		LayoutParams iconParams=new LayoutParams(width,width);
		holder.icon.setLayoutParams(iconParams);
		//设置折扣显示比例16:10
		int discountWidth=width/2;
		int discountHeight=(width/2)*10/16;
		LayoutParams discountParams=new LayoutParams(discountWidth,discountHeight);
		holder.discountText.setLayoutParams(discountParams);
		//距离底部7/16height
		holder.discountText.setPadding(0, 0, 0, discountHeight*7/16);
		//设置数据
		final ProductInfo info= (ProductInfo) mDataList.get(position);
		loadImageUtil.imageLoader.displayImage(info.goodsImgs.get(0), holder.icon);
		holder.productName.setText(info.name);
		holder.memberPrice.setText("￥"+info.marketPrice);
		holder.exchangeIntegral.setText(info.integral+"积分兑换");
		//点击事件
		holder.addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addProductListener.addProduct(info);
			}
		});
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = mInflater.inflate(R.layout.item_gridview_recommend,null);
		return new MyHolder(itemView);
	}
	class MyHolder extends RecyclerView.ViewHolder{
		GradientTextView discountText;
		ImageButton addButton;
		ImageView icon;
		TextView productName,memberPrice,exchangeIntegral;
		public MyHolder(View itemView) {
			super(itemView);
			discountText=(GradientTextView)itemView .findViewById(R.id.gradientTextView_discount);
			addButton=(ImageButton) itemView.findViewById(R.id.imb_add_recommend);
			icon=(ImageView) itemView.findViewById(R.id.iv_productIcon_recommend);
			productName=(TextView) itemView.findViewById(R.id.tv_productName_recommend);
			memberPrice=(TextView) itemView.findViewById(R.id.tv_memberPrice_recommend);
			exchangeIntegral=(TextView) itemView.findViewById(R.id.tv_exchangeIntegral_recommend);
		}

	}

}
