package com.huiche.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.ExchangeOrderActivity;
import com.huiche.activity.LoginActivity;
import com.huiche.activity.ShoppingCartActivity;
import com.huiche.activity.mine.ChangePhoneActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.ProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.LoadImageUtil;
import com.huiche.utils.SetSizeUtils;
import com.huiche.utils.ToastUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 超值兑换弹窗
 */
public class AddProductPopwindow implements View.OnClickListener {
    private LinearLayout ll_addTo_shoppingCart;
    private TextView tv_addToCart_productName;
    private TextView tv_shoppingCar_account;
    private TextView tv_addToCart_integral;
    //    private TextView et_addCount;
    private ImageView iv_addToCart_productIcon;
    private ImageButton imb_addToCart_deleteAdd_grey;
    private ImageButton imb_reduceProduct;
    private ImageButton imb_increaseProduct;
    private TextView tv_addProduct_toCart;
    private TextView tv_settle_accounts;
    private ImageView iv_shoppingCar_icon;
    private TextView tv_addTo_next;//下一步
    private LoadImageUtil loadImageUtil;
    private PopupWindow addProductPop;
    private ProductInfo product;
    private Context mContext;
    private int addCount;
    private EditText et_addCount;
    private RelativeLayout rl_addTo_shoppingCart;

    public AddProductPopwindow(Context context) {
        loadImageUtil = new LoadImageUtil();
        mContext = context;
    }

    private void initPopWindow(ProductInfo product) {
        this.product = product;
        if (addProductPop != null) {
            // 设置数据
            tv_addToCart_productName.setText(product.name);
            tv_addToCart_integral.setText(product.integral + "积分");
            loadImageUtil.imageLoader.displayImage(product.goodsImgs.get(0), iv_addToCart_productIcon);
            et_addCount.setText("1");
        } else {
            View popContentView = View.inflate(mContext,
                    R.layout.item_popwindow_addtocart, null);
            addProductPop = new PopupWindow(popContentView,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            // find控件
            ll_addTo_shoppingCart = (LinearLayout) popContentView
                    .findViewById(R.id.ll_addTo_shoppingCart);
            tv_addToCart_productName = (TextView) popContentView
                    .findViewById(R.id.tv_addToCart_productName);
            iv_addToCart_productIcon = (ImageView) popContentView
                    .findViewById(R.id.iv_addToCart_productIcon);
            et_addCount = (EditText) popContentView
                    .findViewById(R.id.et_addCount);
            imb_addToCart_deleteAdd_grey = (ImageButton) popContentView
                    .findViewById(R.id.imb_addToCart_deleteAdd_grey);
            imb_reduceProduct = (ImageButton) popContentView
                    .findViewById(R.id.imb_reduceProduct);
            imb_increaseProduct = (ImageButton) popContentView
                    .findViewById(R.id.imb_increaseProduct);
            tv_addProduct_toCart = (TextView) popContentView
                    .findViewById(R.id.tv_addProduct_toCart);
            tv_addToCart_integral = (TextView) popContentView
                    .findViewById(R.id.tv_addToCart_integral);
            tv_settle_accounts = (TextView) popContentView
                    .findViewById(R.id.tv_settle_accounts);
            tv_addTo_next = (TextView) popContentView
                    .findViewById(R.id.tv_addTo_next);
            tv_shoppingCar_account = (TextView) popContentView
                    .findViewById(R.id.tv_shoppingCar_account);
            iv_shoppingCar_icon = (ImageView) popContentView
                    .findViewById(R.id.iv_shoppingCar_icon);
            rl_addTo_shoppingCart=(RelativeLayout)popContentView.findViewById(R.id.rl_addTo_shoppingCart);
            // 监听器
            imb_reduceProduct.setOnClickListener(this);
            imb_increaseProduct.setOnClickListener(this);
            tv_settle_accounts.setOnClickListener(this);
            tv_addProduct_toCart.setOnClickListener(this);
            imb_addToCart_deleteAdd_grey.setOnClickListener(this);
            tv_addTo_next.setOnClickListener(this);
            rl_addTo_shoppingCart.setOnClickListener(this);
            // 设置icon显示比例7:5
            SetSizeUtils.setSizeOnlyWidthOf(mContext, iv_addToCart_productIcon, 5, 1, 7, 5);
//            // 设置数据
            tv_addToCart_productName.setText(product.name);
            tv_addToCart_integral.setText(product.integral + "积分");
            loadImageUtil.imageLoader.displayImage(product.goodsImgs.get(0), iv_addToCart_productIcon);
            // 设置popupwindow可按返回键取消
            addProductPop.setBackgroundDrawable(new BitmapDrawable());
            // 设置popupwindow点击其他区域能dismiss
            addProductPop.setOutsideTouchable(true);
            addProductPop.setAnimationStyle(R.style.popwindow_anim_down);
            // 设置关闭pop时复原Activity背景
            addProductPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    // 默认显示的内容
                    WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
                    params.alpha = 1f;
                    ((Activity) mContext).getWindow().setAttributes(params);
                }
            });

            //防止PopupWindow被软件盘挡住
            addProductPop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            addProductPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        SharedPreferences share=mContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token=share.getString("token","");
        if(token.equals("")){
            ToastUtils.ToastShowShort(mContext, "请先登录");
            intent.setClass(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            addProductPop.dismiss();
        }
        switch (view.getId()) {
            case R.id.imb_addToCart_deleteAdd_grey:
                addProductPop.dismiss();
                break;
            //减少
            case R.id.imb_reduceProduct:
                int count = Integer.parseInt(et_addCount.getText() + "") - 1;
                if (count >= 0)
                    et_addCount.setText(count + "");
                break;
            //增加
            case R.id.imb_increaseProduct:
                int count_ = Integer.parseInt(et_addCount.getText() + "") + 1;
                et_addCount.setText(count_ + "");
                break;
            //结算
            case R.id.tv_settle_accounts:

                settleOrder();
                break;
            //添加到购物车
            case R.id.tv_addProduct_toCart:
                addToShoppingCar();
                break;
            //跳转到购物车
            case R.id.rl_addTo_shoppingCart:
                intent.setClass(mContext, ShoppingCartActivity.class);
                mContext.startActivity(intent);
                addProductPop.dismiss();
                break;

        }
    }

    /**
     * 添加到购物车
     */
    private void addToShoppingCar() {
        if (MyApplication.token.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请先登录");
            return;
        }
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        final int count = Integer.parseInt(et_addCount.getText() + "");
        if(count<1){
            ToastUtils.ToastShowShort(mContext,"请选择商品数量");
            return;
        }
        dialog.show("", false, null);
        RequestParams params = new RequestParams();
        params.put("productId", product.id);
        params.put("count", count);
        AsyncHttp.post(HttpConstant.ADD_TO_SHORCART, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtils.ToastShowShort(mContext, "请检查网络");
                dialog.dialogcancel();
                addProductPop.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.i("sss", response.toString());
                addProductPop.dismiss();
                dialog.dialogcancel();
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    ToastUtils.ToastShowShort(mContext, "添加成功");
                    int oldCount = Integer.parseInt(tv_shoppingCar_account.getText() + "");
                    tv_shoppingCar_account.setText(oldCount + count + "");
                }
            }
        });

    }

    /**
     * 下单
     */
    private void settleOrder() {
        RequestParams params = new RequestParams();
        String cou = et_addCount.getText() + "";

        if (cou.equals("0")) {
            ToastUtils.ToastShowShort(mContext, "请选择商品数量");
            return;
        }
        addProductPop.dismiss();
        params.put("productId", product.id);
        params.put("count", cou);
        if (MyApplication.token.equals("")) {
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(loginIntent);
            return;
        }
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.COMMIT_ORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                dialog.dialogcancel();
//                Log.i(TAG,response.toString());
                int status = response.optInt("status");
                int errorCode = response.optInt("errorCode");
                String msg = response.optString("msg");
//                - status=0时，下单成功，进入订单页面；
//                - status=10时，用户未登录，进入登录页面登录后重走流程；
//                - status=1，errorCode=5时，用户未绑手机，进入绑定手机页面绑定后重走流程；
//                - status=1，errorCode!=5时，报错返回；
                if (status == 0) {//下单成功
                    JSONObject obj = response.optJSONObject("rows");
                    JSONObject obj2 = obj.optJSONObject("value");
                    int offlineOrderId = obj2.optInt("id");
                    Intent intent = new Intent(mContext, ExchangeOrderActivity.class);
                    intent.putExtra("offlineOrderId", offlineOrderId);
                    mContext.startActivity(intent);
                } else if (status == 10) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    //用户没登录
                } else if (status == 1) {
                    if (errorCode == 5) {
                        //用户未绑手机
                        Intent intent = new Intent(mContext, ChangePhoneActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        ToastUtils.ToastShowShort(mContext, msg);
                    }
                }
            }
        });
    }

    /**
     * 之前需调用initPopWindow()
     *
     * @return
     */
    public PopupWindow getPopwindow() {
        return addProductPop;
    }

    /**
     * 弹窗
     *
     * @param parentView 要依附的父布局
     * @param product    要显示的商品信息
     */
    public void show(View parentView, ProductInfo product) {
        initPopWindow(product);
        addProductPop.showAtLocation(parentView, Gravity.BOTTOM,
                0, 0);
        // 修改Activity背景为半透明
        WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
        params.alpha = 0.6f;
        ((Activity) mContext).getWindow().setAttributes(params);
    }
}
