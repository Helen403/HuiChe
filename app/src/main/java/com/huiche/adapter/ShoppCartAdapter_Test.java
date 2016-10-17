package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.BusinessStoreList;
import com.huiche.bean.ShoppingCartProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ImagLoadUtils;
import com.huiche.utils.SetSizeUtils;
import com.huiche.utils.ToastUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ShoppCartAdapter_Test extends BaseExpandableListAdapter {
    private Context context;
    private List<BusinessStoreList> groupList;
    private Map<Integer, List<ShoppingCartProductInfo>> childList;
    private LayoutInflater inflater;
//    private boolean isOnClickSellerBox = false;//标志位，true:表示商家的checkBox被点击，false:表示商品的CheckBox没被点击
//    private SharedPreferences share;
//    private int location = 0;
    private ModifyCountInterface modifyCountInterface;
//    private List<ViewHolderGroup> viewHolderGroupList = new ArrayList<>();
//    private List<ViewHolderChild> viewHolderChildList = new ArrayList<>();
//    private Map<Integer, List<ViewHolderChild>> viewHolderChildMap = new HashMap();
//    private CheckInterface checkInterface;
//    private OnGroupChangeListener listener;
//    private Map<Integer, OnGroupChangeListener> listenerMap = new HashMap<>();
    private List<OnGroupChangeListener> listenerList = new ArrayList<>();
    private Map<Integer, List<OnGroupChangeListener>> listenerListMap = new HashMap<>();
    private int index = 0;

    public ShoppCartAdapter_Test(Context context, List<BusinessStoreList> groupList, Map<Integer, List<ShoppingCartProductInfo>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        share = context.getSharedPreferences("user_data", context.MODE_PRIVATE);
    }

    //修改数量接口
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
//        this.checkInterface = checkInterface;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupList.get(groupPosition).getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolderGroup viewHolderGroup;
        if (convertView == null) {
            viewHolderGroup = new ViewHolderGroup();
            convertView = inflater.inflate(R.layout.item_listview_shoppingcart_sub, null);
            viewHolderGroup.sellerCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_shoppingCartSub);
            viewHolderGroup.sellerText = (TextView) convertView.findViewById(R.id.tv_storeName_shoppingCartItem_sub);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        viewHolderGroup.sellerText.setText(groupList.get(groupPosition).getBusinessName());
//        viewHolderGroup.sellerCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                groupList.get(groupPosition).setIsSelect(((CheckBox) v).isChecked());
//                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());//暴露组选接口
//            }
//        });
        viewHolderGroup.sellerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                groupList.get(groupPosition).setIsSelect(b);
//                if (b) {
//                    List<ViewHolderChild> child = viewHolderChildMap.get(groupPosition);
//                    for (int i = 0; i < child.size(); i++) {
//                        child.get(i).select_product.setChecked(b);
//                    }
//                }
//               checkInterface.checkGroup(groupPosition, b);//暴露组选接口
                if (!b) return;
                List<OnGroupChangeListener> listenerList = listenerListMap.get(groupPosition);
                for (OnGroupChangeListener listener : listenerList) {
                    listener.onGroupChange(groupPosition);
                }
            }
        });
//        viewHolderGroup.sellerCheckBox.setChecked(groupList.get(groupPosition).isSelect());
//        viewHolderGroupList.add(viewHolderGroup);
        return convertView;

    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolderChild holderChild;
        if (convertView == null) {
            holderChild = new ViewHolderChild();
            convertView = inflater.inflate(R.layout.item_listview_shoppingcart_chird, null);
            holderChild.productIcon = (ImageView) convertView.findViewById(R.id.iv_productIcon_shoppingCartItem_chird);
            holderChild.productName = (TextView) convertView.findViewById(R.id.tv_productName_shoppingCartItem_chird);
            holderChild.exchangeIntegral = (TextView) convertView.findViewById(R.id.tv_exchangeIntegral_shoppingCartItem_chird);
            holderChild.doorPrice = (TextView) convertView.findViewById(R.id.tv_doorPrice_shoppingCartItem_chird);
            holderChild.addCount = (TextView) convertView.findViewById(R.id.tv_addCount_shoppingCartItem_chird);
            holderChild.divideLastSub = convertView.findViewById(R.id.view_divideLastSub);
            holderChild.divideLast = convertView.findViewById(R.id.view_divideLast);
            holderChild.select_product = (CheckBox) convertView.findViewById(R.id.checkBox_checkSub_shoppingCartItem_chird);
            holderChild.img_reduce = (ImageButton) convertView.findViewById(R.id.imb_reduceProduct_shoppingCartItem);
            holderChild.img_add = (ImageButton) convertView.findViewById(R.id.imb_increaseProduct_shoppingCartItem);
            convertView.setTag(holderChild);
        } else {
            holderChild = (ViewHolderChild) convertView.getTag();
        }
        holderChild.productName.setText(childList.get(groupList.get(groupPosition).getId()).get(childPosition).getProductP().getName());
        holderChild.exchangeIntegral.setText(childList.get(groupList.get(groupPosition).getId()).get(childPosition).getProductP().getIntegral() + "积分");
        holderChild.addCount.setText(childList.get(groupList.get(groupPosition).getId()).get(childPosition).getCount() + "");
        SetSizeUtils.setSizeOnlyWidthOf(context, holderChild.productIcon, 4, 1, 4, 3);
        ImagLoadUtils.setImage(childList.get(groupList.get(groupPosition).getId()).get(childPosition).getProductP().getGoodsImgs()[0], holderChild.productIcon, context);
        ImagLoadUtils.setImage(childList.get(groupList.get(groupPosition).getId()).get(childPosition).getProductP().getGoodsImgs()[0], holderChild.productIcon, context);
//        holderChild.select_product.setChecked(childList.get(groupList.get(groupPosition).getId()).get(childPosition).isChoseProduct());
//        holderChild.select_product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                childList.get(groupList.get(groupPosition).getId()).get(childPosition).setIsChoseProduct(((CheckBox) v).isChecked());
//                checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());//暴露子选接口
//            }
//        });
        holderChild.select_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                childList.get(groupList.get(groupPosition).getId()).get(childPosition).setIsChoseProduct(b);
//                checkInterface.checkChild(groupPosition, childPosition, b);//暴露子选接口
            }
        });

        holderChild.img_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int count=childList.get(groupList.get(groupPosition).getId()).get(childPosition).getCount();
                int count = childList.get(groupList.get(groupPosition).getId()).get(childPosition).getCount();
                count--;
//                location = groupPosition;
                String productId = childList.get(groupList.get(groupPosition).getId()).get(childPosition).getId();
                //当数量改变提交到服务器才改变UI
                reduceProduct(productId, count, groupPosition, childPosition, holderChild.addCount, childList.get(groupList.get(groupPosition).getId()).get(childPosition).isChoseProduct());

            }
        });
        //增加购物车数量
        holderChild.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int count=childList.get(groupList.get(groupPosition).getId()).get(childPosition).getCount();
//                String num=holderChild.addCount.getText().toString().trim();
                int count = childList.get(groupList.get(groupPosition).getId()).get(childPosition).getCount();
                count++;
//                location = groupPosition;
                String productId = childList.get(groupList.get(groupPosition).getId()).get(childPosition).getId();
                //当数量改变提交到服务器才改变UI
                addProduct(productId, count, groupPosition, childPosition, holderChild.addCount, childList.get(groupList.get(groupPosition).getId()).get(childPosition).isChoseProduct());
            }
        });
        //最后一项不显示分割线
        if (isLastChild) holderChild.divideLastSub.setVisibility(View.GONE);
        else holderChild.divideLastSub.setVisibility(View.VISIBLE);
        OnGroupChangeListener listener = new OnGroupChangeListener() {
            @Override
            public void onGroupChange(int changePosition) {
//                if(groupPosition==changePosition){
                holderChild.select_product.setChecked(true);
//                }
            }
        };
        if (index == groupPosition) {
            listenerList.add(listener);
        } else {
            listenerList.clear();
            listenerList.add(listener);
            index++;
        }
        if (isLastChild) {
            setOnGroupChangeListener(groupPosition, listenerList);
        }
        return convertView;
    }

    public void reduceProduct(String productId, final int productCount, final int groupPosition, final int childPosition, final View show_view, final boolean isselect) {
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        params.put("count", productCount);
        AsyncHttp.post(HttpConstant.UPDATE_PRODUCT_COUNT, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(context, "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.i("shopCart", response.toString());
                if (response.optString("status").equals("0")) {
                    // holderChild.addCount.setText(productCount + "");
                    modifyCountInterface.doDecrease(groupPosition, childPosition, show_view,
                            isselect);//暴露增加接口
                }
            }
        });
    }

    public void addProduct(String productId, final int productCount, final int groupPosition, final int childPosition, final View show_view, final boolean isselect) {
        ToastUtils.ToastShowShort(context, productId + "");
        RequestParams params = new RequestParams();
        params.put("productId", productId);
        params.put("count", productCount);
        AsyncHttp.post(HttpConstant.UPDATE_PRODUCT_COUNT, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(context, "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.i("shopCart", response.toString());
                if (response.optString("status").equals("0")) {
                    // holderChild.addCount.setText(productCount + "");
                    modifyCountInterface.doIncrease(groupPosition, childPosition, show_view,
                            isselect);//暴露增加接口
                }
            }
        });


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static class ViewHolderGroup {
        public CheckBox sellerCheckBox;
        public TextView sellerText;
    }

    public static class ViewHolderChild {
//        private static OnGroupChangeListener lss;
        public ImageView productIcon;
        public TextView productName, exchangeIntegral, doorPrice, addCount;
        public View divideLastSub, divideLast;
        public CheckBox select_product;
        public ImageButton img_reduce, img_add;
    }

    public interface OnGroupChangeListener {
        void onGroupChange(int groupPosition);
    }

    public void setOnGroupChangeListener(int groupPosition, List<OnGroupChangeListener> listenerList) {
//        listenerMap.put(clickPosition,listener);
        listenerListMap.put(groupPosition, listenerList);

    }

    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }

    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */

        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

}


