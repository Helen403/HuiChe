package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.PostResult.ShoppCartBuyOrder;
import com.huiche.R;
import com.huiche.activity.mine.ChangePhoneActivity;
import com.huiche.adapter.ShoppCartAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.BusinessStoreList;
import com.huiche.bean.ShoppingCartProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.constant.MyRequestCode;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.DeleteDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 购物车 fragment
 *
 * @author Administrator
 */
public class ShoppingCartActivity extends BaseActivity implements
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
    private Map<Integer, Integer> deletePosition = new HashMap<>();
    //结算商品的数组id
    private List<String> productCartList = new ArrayList<String>();
    private List<String> shopingCartIdList = new ArrayList<String>();
    private boolean flag = true;
    private List<ShoppCartBuyOrder> shoppCartBuyOrderList;
    private ArrayList<String> offerIdList = new ArrayList<>();//生成的订单id数组
    private BufferCircleDialog mdialog;


    View dialogVV;
    TextView tv_confirmNormalasd;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {

        /******************************************************************/
        //总布局
        RelativeLayout content = new RelativeLayout(this);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_shopping_cart, content, false);
        dialogVV = inflater.inflate(R.layout.dialog_delete_card_helen, content, false);
        dialogVV.setVisibility(View.GONE);
        dialogVV.findViewById(R.id.tv_cancelNormal).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVV.setVisibility(View.GONE);
            }
        });
        tv_confirmNormalasd = (TextView) dialogVV.findViewById(R.id.tv_confirmNormal);
        tv_confirmNormalasd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVV.setVisibility(View.GONE);
                clearAll();
            }
        });
        TextView tv_dialogNormal_content = (TextView) dialogVV.findViewById(R.id.tv_dialogNormal_content);
        tv_dialogNormal_content.setText("是否清空购物车");
        dialogVV.setLayoutParams(rl);
        content.addView(view);
        content.addView(dialogVV);
        setContentView(content);


        /******************************************************************/


//        setContentView(R.layout.activity_shopping_cart);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        tv_selectAll_shoppingCart = (TextView) findViewById(R.id.tv_selectAll_shoppingCart);
        tv_deleteProduct_shoppingCart = (TextView) findViewById(R.id.tv_deleteProduct_shoppingCart);
        tv_cleanAll_shoppingCart = (TextView) findViewById(R.id.tv_cleanAll_shoppingCart);
        ll_noProductTips_shoppingCart = (LinearLayout) findViewById(R.id.ll_noProductTips_shoppingCart);
        tv_buy = (TextView) findViewById(R.id.tv_totalIntegral_shoppingCart);
        //购物车list
        mListView = (ExpandableListView) findViewById(R.id.my_listview);
        tv_confirm = (TextView) findViewById(R.id.tv_settleAccounts_shoppingCart);
        tv_confirm.setOnClickListener(this);
        ll_select = (LinearLayout) findViewById(R.id.ll_selectTpye_shoppingCart);
    }

    @Override
    public void initData() {
        initImageLoader();
        tv_titleText.setText("购物车");
        if (MyApplication.token.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请登录查看");
            return;
        }
        getProductList();
    }

    private int count = 0;

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
        tv_cleanAll_shoppingCart.setOnClickListener(this);
        tv_selectAll_shoppingCart.setOnClickListener(this);
        tv_deleteProduct_shoppingCart.setOnClickListener(this);
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true，屏蔽组的点击事件
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回首页
            case R.id.imb_titleBack:
                setResult(MyRequestCode.SHOPPING_CART_RESULT);
                finish();
                break;
            // 清空购物车
            case R.id.tv_cleanAll_shoppingCart:
                dialogVV.setVisibility(View.VISIBLE);


//                clearAll();
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
                    ToastUtils.ToastShowShort(mContext, "请选择需要删除的商品");
                    return;
                }
                //初始化弹窗
                dialog = new DeleteDialog(mContext, "确定要删除购物车商品?");
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
                productCartId.clear();
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
                    ToastUtils.ToastShowShort(mContext, "请选择商品");
                    return;
                }
                deleShoppCartProduct(productCartId);
                break;


            //结算按钮
            case R.id.tv_settleAccounts_shoppingCart:
                shopingCartIdList.clear();
                offerIdList.clear();
                for (int i = 0; i < businessStoreList.size(); i++) {
                    List<ShoppingCartProductInfo> child = new ArrayList<ShoppingCartProductInfo>();
                    child = businessStoreList.get(i).cartMessages;
                    for (int j = 0; j < child.size(); j++) {
                        if (child.get(j).isChoseProduct()) {
//                            shopingCartIdList.add(child.get(j).getProductP().getId());
                            shopingCartIdList.add(child.get(j).getId());

                        }
                    }
                }
                if (shopingCartIdList.size() < 1) {
                    ToastUtils.ToastShowShort(mContext, "请选择商品");
                    return;
                }
                //结算
                buyOverShoppCartProduct(shopingCartIdList);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(MyRequestCode.SHOPPING_CART_RESULT);
        }
        return super.onKeyDown(keyCode, event);
    }

    //结算
    private void buyOverShoppCartProduct(final List<String> shopingCartIdList) {
        RequestParams params = new RequestParams();
        params.put("shopingCartId", shopingCartIdList);
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.BUYOVER_SHOPPCART, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "数据加载失败，请检查网络！");
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                dialog.dialogcancel();
//                Log.i("shopCart", response.toString());
                String msg = response.optString("msg");
                boolean success = response.optBoolean("success");
                int errorCode = response.optInt("errorCode");
                int status = response.optInt("status");
//                - status=0时，下单成功，进入订单页面；
//                - status=10时，用户未登录，进入登录页面登录后重走流程；
//                - status=1，errorCode=5时，用户未绑手机，进入绑定手机页面绑定后重走流程；
//                - status=1，errorCode!=5时，报错返回；
                if (status == 0) {//下单成功
                    JSONObject obj = response.optJSONObject("rows");
                    String result = obj.optJSONArray("value").toString();
                    shoppCartBuyOrderList = JSON.parseArray(result, ShoppCartBuyOrder.class);
                    for (ShoppCartBuyOrder order : shoppCartBuyOrderList) {
                        offerIdList.add(order.getId() + "");
                    }

                    /***********************************************************/

//                    getProductList();

                    /***************************************************************/
                    //当结算成功后，应该从新请求商品数据
                    businessStoreList.clear();
                    tv_buy.setText("0.0积分");
                    getProductList();


                    Intent intent = new Intent(mContext, ShoppingCartOrderActicity.class);
                    intent.putStringArrayListExtra("offerIdList", offerIdList);
                    startActivity(intent);
                } else if (status == 10) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    //用户没登录
                } else if (status == 1) {
                    if (errorCode == 5) {
                        //用户未绑手机
                        Intent intent = new Intent(mContext, ChangePhoneActivity.class);
                        startActivity(intent);
                    } else {
                        ToastUtils.ToastShowShort(mContext, msg);
                    }
                }

            }
        });
    }

    //删除商品接口
    private void deleShoppCartProduct(final List<String> productCartId) {
        RequestParams params = new RequestParams();
        params.put("productCartId", productCartId);
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.DELETE_SHOPPCART_PRODUCT, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dialogcancel();
                ToastUtils.ToastShowShort(mContext, "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.i("shopCart", response.toString());
                dialog.dialogcancel();
                String msg = response.optString("msg");
                if (response.optString("status").equals("0")) {
                    //删除成功移除数据并刷新界面
//                    refreshData();
                    tv_buy.setText("0.0积分");
                    getProductList();
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                }

            }
        });
    }

    /**
     * 删除成功或结算成功刷新界面
     */
    private void refreshData() {
        for (int i = 0; i < businessStoreList.size(); i++) {
            //利用迭代器遍历删除
            Iterator<ShoppingCartProductInfo> iterator = childList.get(businessStoreList.get(i).getId()).iterator();
            //删除子项
            while (iterator.hasNext()) {
                ShoppingCartProductInfo info = iterator.next();
                if (info.isChoseProduct())
                    iterator.remove();
            }
        }
        //若子项size为0，删除父项（商家名称）
//        Iterator<BusinessStoreList> iteratorPar = businessStoreList.iterator();
//        while (iteratorPar.hasNext()) {
//            BusinessStoreList store = iteratorPar.next();
//            if (store.getCartMessages().size() == 0)
//                iteratorPar.remove();
//        }
        adapter.notifyDataSetChanged();
        tv_buy.setText("0.0积分");
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
        mdialog = new BufferCircleDialog(mContext);
        mdialog.show("", true, null);
        RequestParams params = new RequestParams();
        AsyncHttp.post(HttpConstant.GET_SHOPPINGCART_LIST, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                mdialog.dialogcancel();
                ToastUtils.ToastShowShort(mContext, "数据加载失败，请检查网络！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                mdialog.dialogcancel();
//                Log.i("shopCart", response.toString());
                String msg = response.optString("msg");
                if (response.optString("status").equals("0")) {
                    JSONObject rows = response.optJSONObject("rows");
                    if (rows != null) {
                        String jsonString = rows.optString("value");
                        if (!jsonString.equals("")) {
                            businessStoreList = JSON.parseArray(jsonString, BusinessStoreList.class);
                            setProductData();
                        }
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
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
            adapter = new ShoppCartAdapter(mContext, businessStoreList, childList);
            adapter.setCheckInterface(this);
            adapter.setModifyCountInterface(this);
            mListView.setAdapter(adapter);
            for (int i = 0; i < businessStoreList.size(); i++) {
                //默认数据全部展开
                mListView.expandGroup(i);
            }
        } else {
            mListView.setVisibility(View.GONE);
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
                ToastUtils.ToastShowShort(mContext, "操作失败，请检查网络");
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
                        businessStoreList.clear();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        ll_noProductTips_shoppingCart.setVisibility(View.VISIBLE);
                        ll_select.setVisibility(View.GONE);
                        mListView.setVisibility(View.GONE);
                        tv_buy.setText("0.0积分");
                    }
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
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
            ToastUtils.ToastShowShort(mContext, "请选择一种商品");
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
            ToastUtils.ToastShowShort(mContext, "请选择一种商品");
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

        DecimalFormat df = new DecimalFormat("######0.0");

        tv_buy.setText(df.format(totalPrice) + "积分");
    }


    /**
     * 全选与反选
     */
    private void doCheckAll(boolean sellAll) {
        for (int i = 0; i < businessStoreList.size(); i++) {
            businessStoreList.get(i).setIsSelect(sellAll);
            checkGroup(i, sellAll);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mdialog.isShowDialog()) {
            mdialog.dialogcancel();
        }
    }
}
