package com.huiche.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.adapter.ShoppCartAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.base.MyApplication;
import com.huiche.bean.BusinessStoreList;
import com.huiche.bean.ShoppingCartProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.huiche.view.DeleteDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车 fragment
 *
 * @author Administrator
 */
public class ShoppingCartFragment extends BaseFragment implements
        ShoppCartAdapter.CheckInterface, ShoppCartAdapter.ModifyCountInterface, OnClickListener {

    private View convertView;
    private TextView tv_selectAll_shoppingCart;// 全选
    private TextView tv_deleteProduct_shoppingCart;// 删除
    private TextView tv_cleanAll_shoppingCart;// 清空购物车
    private TextView tv_buy;
    private LinearLayout ll_noProductTips_shoppingCart;// 暂无商品
    //private ListView mListView;// 购物车列表
    private ExpandableListView mListView;
    private List<BusinessStoreList> businessStoreList = new ArrayList<BusinessStoreList>();
    private Map<Integer, List<ShoppingCartProductInfo>> childList = new HashMap<Integer, List<ShoppingCartProductInfo>>();//商品集合，key:对应商家的id
    //private ShoppingCartListViewAdapter adapter;
    private ShoppCartAdapter adapter;
    private Context mContext;
    private ImageButton imb_titleBack;
    private TextView tv_titleText;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private LinearLayout ll_select;
    private int firstCount = 0;
    private TextView tv_select_all;
    private boolean isSelectAll = false;
    private DeleteDialog dialog;
    private TextView tv_confirm;
    //删除商品的数组id
    private List<String> productCartId = new ArrayList<String>();
    //结算商品的数组id
    private List<String> productCartList = new ArrayList<String>();

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        //原布局 fragment_shopping_cart
        convertView = inflater.inflate(R.layout.my_fragment_shoppingcart,
                container, false);
        mContext = getActivity();
        return convertView;
    }

    @Override
    public void findViews() {
        imb_titleBack = (ImageButton) convertView.findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) convertView.findViewById(R.id.tv_titleText);
        tv_selectAll_shoppingCart = (TextView) convertView
                .findViewById(R.id.tv_selectAll_shoppingCart);
        tv_deleteProduct_shoppingCart = (TextView) convertView
                .findViewById(R.id.tv_deleteProduct_shoppingCart);
        tv_cleanAll_shoppingCart = (TextView) convertView
                .findViewById(R.id.tv_cleanAll_shoppingCart);
        ll_noProductTips_shoppingCart = (LinearLayout) convertView
                .findViewById(R.id.ll_noProductTips_shoppingCart);

        tv_buy = (TextView) convertView.findViewById(R.id.tv_totalIntegral_shoppingCart);
        //购物车list
        mListView = (ExpandableListView) convertView.findViewById(R.id.my_listview);
        tv_confirm = (TextView) convertView.findViewById(R.id.tv_settleAccounts_shoppingCart);
        tv_confirm.setOnClickListener(this);
//		mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//				return false;//返回true，屏蔽组的点击事件
//			}
//		});
        ll_select = (LinearLayout) convertView.findViewById(R.id.ll_selectTpye_shoppingCart);
    }

    @Override
    public void initData() {
        initImageLoader();
        tv_titleText.setText("购物车");
        imb_titleBack.setVisibility(View.GONE);
        if (MyApplication.token.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请登录查看");
            return;
        }
        getProductList();
    }

    private int count = 0;

    @Override
    public void setListeners() {
        tv_cleanAll_shoppingCart.setOnClickListener(this);
        tv_selectAll_shoppingCart.setOnClickListener(this);
        tv_deleteProduct_shoppingCart.setOnClickListener(this);
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true，屏蔽组的点击事件
            }
        });
//		mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
//				final TextView tv_sum=(TextView)v.findViewById(R.id.tv_addCount_shoppingCartItem_chird);
//				ImageButton img_add=(ImageButton)v.findViewById(R.id.imb_increaseProduct_shoppingCartItem);
//				count=0;
//				img_add.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						count = childList.get(businessStoreList.get(groupPosition).getId()).get(childPosition).getCount();
//						count++;
//						tv_sum.setText(count + "");
//					}
//				});
//
//
//
//				return false;
//			}
//		});
    }

    @Override
    public void initViews() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 清空购物车
            case R.id.tv_cleanAll_shoppingCart:
                clearAll();
                break;
            //全选与反选
            case R.id.tv_selectAll_shoppingCart:
                if (!isSelectAll) {
                    isSelectAll = true;
                    tv_selectAll_shoppingCart.setText("取消全选");

                } else {
                    isSelectAll = false;
                    tv_selectAll_shoppingCart.setText("全选");
                }
                doCheckAll(isSelectAll);

                break;
            //删除商品弹窗
            case R.id.tv_deleteProduct_shoppingCart:
                if (totalCount == 0) {
                    ToastUtils.ToastShowShort(getActivity(), "请选择需要删除的商品");
                    return;
                }
                //初始化弹窗
                dialog = new DeleteDialog(getActivity(), "确定要删除购物车商品?");
                dialog.tv_cancel.setOnClickListener(this);
                dialog.tv_confirm.setOnClickListener(this);
                dialog.showDialog();
                break;
            //取消
            case R.id.tv_cancelNormal:
                if (dialog != null) {
                    dialog.dimissDialog();
                }

                break;
            //确认删除
            case R.id.tv_confirmNormal:
                dialog.dimissDialog();
                for (int i = 0; i < businessStoreList.size(); i++) {
                    List<ShoppingCartProductInfo> child = new ArrayList<ShoppingCartProductInfo>();
                    child = businessStoreList.get(i).cartMessages;
                    for (int j = 0; j < child.size(); j++) {
                        if (child.get(j).isChoseProduct()) {
                            productCartId.add(child.get(j).getId());
                        }
                    }
                }
                if (productCartId.size() < 1) {
                    ToastUtils.ToastShowShort(getActivity(), "请选择商品");
                    return;
                }

                deleShoppCartProduct(productCartId);
                break;
            //结算按钮
            case R.id.tv_settleAccounts_shoppingCart:
                for (int i = 0; i < businessStoreList.size(); i++) {
                    List<ShoppingCartProductInfo> child = new ArrayList<ShoppingCartProductInfo>();
                    child = businessStoreList.get(i).cartMessages;
                    for (int j = 0; j < child.size(); j++) {
                        if (child.get(j).isChoseProduct()) {
                            productCartList.add(child.get(j).getId());
                        }
                    }
                }
                if (productCartList.size() < 1) {
                    ToastUtils.ToastShowShort(getActivity(), "请选择商品");
                    return;
                }
                //结算
                buyOverShoppCartProduct(productCartList);
                break;

            default:
                break;
        }
    }

    //结算
    private void buyOverShoppCartProduct(List<String> productCartList) {
        RequestParams params = new RequestParams();
        params.put("productCartId", productCartList);
        AsyncHttp.post(HttpConstant.BUYOVER_SHOPPCART, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(getActivity(), "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("shopCart", response.toString());
                String msg = response.optString("msg");
                if (response.optString("status").equals("0")) {

                } else {
                    ToastUtils.ToastShowShort(getActivity(), msg);
                }
            }
        });
    }

    //删除商品接口
    private void deleShoppCartProduct(List<String> productCartId) {
        RequestParams params = new RequestParams();
        params.put("productCartId", productCartId);
        AsyncHttp.post(HttpConstant.DELETE_SHOPPCART_PRODUCT, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(getActivity(), "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("shopCart", response.toString());
                String msg = response.optString("msg");
                if (response.optString("status").equals("0")) {

                } else {
                    ToastUtils.ToastShowShort(getActivity(), msg);
                }

            }
        });
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.shangj_beij11)
                        // 加载中要显示图片
                .showImageForEmptyUri(R.drawable.shangj_beij11)
                        // url为空要显示图片
                .showImageOnFail(R.drawable.shangj_beij11)
                .cacheInMemory(true)
                        // 加载失败要显示图片
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
    }

    /**
     * 获取购物车列表
     */
    public void getProductList() {
        RequestParams params = new RequestParams();
        AsyncHttp.post(HttpConstant.GET_SHOPPINGCART_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(getActivity(), "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("shopCart", response.toString());
                String msg = response.optString("msg");
                if (response.optString("status").equals("0")) {
                    JSONObject rows = response.optJSONObject("rows");
                    if (rows != null) {
                        String jsonString = rows.optString("value");
                        if (!jsonString.equals("")) {
                            businessStoreList = JSON.parseArray(jsonString, BusinessStoreList.class);
                            setProductData();
                        } else {
                            ll_select.setVisibility(View.GONE);
                        }
                    } else {
                    }
                } else {
                    ll_select.setVisibility(View.GONE);
                    ll_noProductTips_shoppingCart.setVisibility(View.VISIBLE);
                    ToastUtils.ToastShowShort(getActivity(), msg);
                }

            }
        });

    }

    /**
     * 显示数据
     */
    private void setProductData() {
        if (businessStoreList.size() > 0) {
            ll_noProductTips_shoppingCart.setVisibility(View.GONE);
            ll_select.setVisibility(View.VISIBLE);
            //添加商家的商品数据
            for (int i = 0; i < businessStoreList.size(); i++) {
                childList.put(businessStoreList.get(i).getId(), businessStoreList.get(i).cartMessages);
            }
            adapter = new ShoppCartAdapter(getActivity(), businessStoreList, childList);
            adapter.setCheckInterface(this);
            adapter.setModifyCountInterface(this);
            mListView.setAdapter(adapter);
            for (int i = 0; i < businessStoreList.size(); i++) {
                //默认数据全部展开
                mListView.expandGroup(i);
            }


        } else {
            ll_select.setVisibility(View.GONE);
            ll_noProductTips_shoppingCart.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 清空购物车
     */
    private void clearAll() {
        RequestParams params = new RequestParams();
        AsyncHttp.post(HttpConstant.CLEAN_SHOPCART, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(getActivity(), "操作失败，请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
//				Log.i("shopCart", response.toString());
                String msg = response.optString("msg");
                String success = response.optString("success");
                String status = response.optString("status");
                if (status.equals("0")) {
                    if (success.equals("true")) {
                        ToastUtils.ToastShowShort(getActivity(), msg);
                        businessStoreList.clear();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        ll_noProductTips_shoppingCart.setVisibility(View.VISIBLE);
                        ll_select.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.ToastShowShort(getActivity(), msg);
                }
            }
        });

    }

    //购物车全选反选
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        List<ShoppingCartProductInfo> child = new ArrayList<ShoppingCartProductInfo>();
        child = businessStoreList.get(groupPosition).cartMessages;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setIsChoseProduct(isChecked);
        }
        adapter.notifyDataSetChanged();
        calculate();

    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;//判断改组下面的所有子元素是否是同一种状态
        List<ShoppingCartProductInfo> child = new ArrayList<ShoppingCartProductInfo>();
        child = businessStoreList.get(groupPosition).cartMessages;
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).isChoseProduct() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            businessStoreList.get(groupPosition).setIsSelect(isChecked);//如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            businessStoreList.get(groupPosition).setIsSelect(false);//否则，组元素一律设置为未选中状态
        }
        adapter.notifyDataSetChanged();
        calculate();
    }


    //数量
    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        if (isChecked) {
            int count = childList.get(businessStoreList.get(groupPosition).getId()).get(childPosition).getCount();
            count++;
            ((TextView) showCountView).setText(count + "");
            if (businessStoreList != null) {
                childList.get(businessStoreList.get(groupPosition).getId()).get(childPosition).setCount(count);
                adapter.notifyDataSetChanged();
                calculate();
            }
        } else {
            ToastUtils.ToastShowShort(getActivity(), "请选择一种商品");
        }
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        if (isChecked) {
            int count = childList.get(businessStoreList.get(groupPosition).getId()).get(childPosition).getCount();
            count--;
            ((TextView) showCountView).setText(count + "");
            childList.get(businessStoreList.get(groupPosition).getId()).get(childPosition).setCount(count);
            adapter.notifyDataSetChanged();
            calculate();
        } else {
            ToastUtils.ToastShowShort(getActivity(), "请选择一种商品");
        }

    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private double totalPrice = 0.00;//购买的商品总价
    private int totalCount = 0;//购买的商品总数量

    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < businessStoreList.size(); i++) {
            List<ShoppingCartProductInfo> child = new ArrayList<ShoppingCartProductInfo>();
            child = businessStoreList.get(i).cartMessages;
            for (int j = 0; j < child.size(); j++) {
                ShoppingCartProductInfo info = child.get(j);
                if (info.isChoseProduct()) {
                    totalCount++;
                    totalPrice += info.getProductP().getIntegral() * info.getCount();
                }
            }


        }

        tv_buy.setText(totalPrice + "积分");
    }


    /**
     * 全选与反选
     */
//    private void doCheckAll(boolean sellAll) {
//        for (int i = 0; i < businessStoreList.size(); i++) {
//            businessStoreList.get(i).setIsSelect(sellAll);
//            checkGroup(i, sellAll);
//        }
//        adapter.notifyDataSetChanged();
//    }


    /**
     * 全选与反选
     */
    private void doCheckAll(boolean sellAll) {
        for (int i = 0; i < businessStoreList.size(); i++) {
            businessStoreList.get(i).setIsSelect(sellAll);
//            checkGroup(i, sellAll);
            View view = mListView.getChildAt(i);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox_shoppingCartSub);
            checkBox.setChecked(sellAll);
        }

    }
}
