package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyBaseAdapter;
import com.huiche.bean.BusinessStoreList;
import com.huiche.utils.SetSizeUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ShoppingCartListViewAdapter extends MyBaseAdapter<BusinessStoreList> {
	// mContext;
	// mInflater;
	// datalist;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private boolean isSelectAll;
	public ShoppingCartListViewAdapter(Context mContext,List<BusinessStoreList> datalist,ImageLoader imageLoader,DisplayImageOptions options,boolean isSelectAll) {
		super(mContext, datalist);
		this.imageLoader = imageLoader;
		this.options = options;
		this.isSelectAll=isSelectAll;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//暂时不考虑复用的情况
		BusinessStoreList info = datalist.get(position);
		if(info.cartMessages!=null){
			if(info.cartMessages.size()>1){
				//动态加载布局子项
				convertView = mInflater.inflate(R.layout.item_listview_shoppingcart_sub, null);
				TextView storeNameSub = (TextView) convertView.findViewById(R.id.tv_storeName_shoppingCartItem_sub);
				final CheckBox checkBox_shoppingCartSub = (CheckBox) convertView.findViewById(R.id.checkBox_shoppingCartSub);
				LinearLayout ll_shoppingCart_parent = (LinearLayout) convertView.findViewById(R.id.ll_shoppingCart_parent);
				//设置数据
				storeNameSub.setText(info.getBusinessName());
				//是否全选
				if(isSelectAll){
					checkBox_shoppingCartSub.setChecked(true);
				}else{
					checkBox_shoppingCartSub.setChecked(false);
				}
				for(int i=0;i<info.cartMessages.size();i++){
					View subView = mInflater.inflate(R.layout.item_listview_shoppingcart_chird, null);
					ImageView productIcon = (ImageView) subView.findViewById(R.id.iv_productIcon_shoppingCartItem_chird);
					TextView productName = (TextView) subView.findViewById(R.id.tv_productName_shoppingCartItem_chird);
					TextView exchangeIntegral = (TextView) subView.findViewById(R.id.tv_exchangeIntegral_shoppingCartItem_chird);
					TextView doorPrice = (TextView) subView.findViewById(R.id.tv_doorPrice_shoppingCartItem_chird);
					TextView addCount = (TextView) subView.findViewById(R.id.tv_addCount_shoppingCartItem_chird);
					View divideLastSub = subView.findViewById(R.id.view_divideLastSub);
					View divideLast = subView.findViewById(R.id.view_divideLast);
					final CheckBox select_product=(CheckBox)subView.findViewById(R.id.checkBox_checkSub_shoppingCartItem_chird);
					productName.setText(info.cartMessages.get(i).getProductP().getName());
					exchangeIntegral.setText(info.cartMessages.get(i).getProductP().getIntegral()+"积分");
					doorPrice.setText("门店价"+info.cartMessages.get(i).getProductP().marketPrice+"元");
					addCount.setText(info.cartMessages.get(i).getCount()+"");
					SetSizeUtils.setSizeOnlyWidthOf(mContext, productIcon, 4, 1, 4, 3);
					imageLoader.displayImage(info.cartMessages.get(i).getProductP().getGoodsImgs()[0],productIcon,options);
					if(i==info.cartMessages.size()-1){
						//最后一项改变分隔线样式
						divideLast.setVisibility(View.VISIBLE);
						divideLastSub.setVisibility(View.GONE);
					}
					//是否全选
					if(isSelectAll){
						select_product.setChecked(true);
					}else{
						select_product.setChecked(false);
					}

                  //初始化商品选中状态
					//boolean isSelect=info.isSelect;
//					if(isSelect){
//						select_product.setChecked(true);
//					}else{
//						select_product.setChecked(false);
//					}


					checkBox_shoppingCartSub.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if(checkBox_shoppingCartSub.isChecked()){
								select_product.setChecked(true);
							}else{
								select_product.setChecked(false);
							}
						}
					});
					ll_shoppingCart_parent.addView(subView);
					//监听器
					
				}
				
			}else if(info.cartMessages.size()==1){
				//只有一种商品
				convertView = mInflater.inflate(R.layout.item_listview_shoppingcart, null);
				TextView storeName=(TextView) convertView.findViewById(R.id.tv_storeName_shoppingCartItem);
				ImageView productIcon=(ImageView) convertView.findViewById(R.id.iv_productIcon_shoppingCartItem);
				TextView productName=(TextView) convertView.findViewById(R.id.tv_productName_shoppingCartItem);
				TextView exchangeIntegral=(TextView) convertView.findViewById(R.id.tv_exchangeIntegral_shoppingCartItem);
				TextView doorPrice=(TextView) convertView.findViewById(R.id.tv_doorPrice_shoppingCartItem);
				TextView addCount=(TextView) convertView.findViewById(R.id.tv_addCount_shoppingCartItem);
				final CheckBox select_all=(CheckBox)convertView.findViewById(R.id.checkBox_checkAll_shoppingCartItem);
				final CheckBox select_child=(CheckBox)convertView.findViewById(R.id.checkBox_checkSub_shoppingCartItem);
				if(isSelectAll){
					select_all.setChecked(true);
					select_child.setChecked(true);
				}else{
					select_all.setChecked(false);
					select_child.setChecked(false);
				}
				select_all.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(select_all.isChecked()){
							select_child.setChecked(true);
						}else{
							select_child.setChecked(false);
						}
					}
				});
				//设置数据
				storeName.setText(info.getBusinessName()+"");
				productName.setText(info.cartMessages.get(0).getProductP().getName());
				exchangeIntegral.setText(info.cartMessages.get(0).getProductP().getIntegral()+"");
				doorPrice.setText("门店价"+info.cartMessages.get(0).getProductP().marketPrice+"元");
				addCount.setText(info.cartMessages.get(0).getCount() + "");
				//设置icon显示比例4:3
				SetSizeUtils.setSizeOnlyWidthOf(mContext, productIcon, 4, 1, 4, 3);
				imageLoader.displayImage(info.cartMessages.get(0).getProductP().getGoodsImgs()[0],productIcon,options);
				if(isSelectAll){

				}
			}
		}
		
		return convertView;
	}
	
	public static class ViewHolder{
//		ImageView productIcon;
//		ImageButton reduceProduct,increaseProduct;
//		TextView storeName,productName,addCount,doorPrice,exchangeIntegral;
	}

	
}
