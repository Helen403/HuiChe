package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.activity.ExchangeOrderActivity;
import com.huiche.activity.LoginActivity;
import com.huiche.activity.mine.ChangePhoneActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.ProductInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.lib.lib.base.MyBaseAdapter;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.ObliqueLineTextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class BusinessDetailHelenListViewAdapter extends MyBaseAdapter<ProductInfo> {

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    Context mContext;

    public BusinessDetailHelenListViewAdapter(Context context, List<ProductInfo> data) {
        super(context);
        // 设置加载网络图片
        initImageLoader();
        this.mContext = context;
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


    @Override
    public int getContentView() {
        return R.layout.item_bussiness_detail_new_helen;
    }

    @Override
    public void onInitView(View view, final ProductInfo productInfo, int position) {
        setText(productInfo.name, R.id.tv_productName);
        String url = productInfo.goodsImgs.get(0);
        ImageView imageView = getViewById(R.id.iv_exchangeIcon);
        imageLoader.displayImage(url, imageView, options);
        double saveMoney = productInfo.marketPrice - productInfo.integral / 10;
        DecimalFormat formater2 = new DecimalFormat("#0.00");
        setText("省" + formater2.format(saveMoney) + "元", R.id.tv_saveMoney);
        setText("已兑换" + productInfo.exchangePeople + "件", R.id.tv_hasExchange);
        setText(productInfo.integral + "积分", R.id.tv_exchangeIntegral);
       ObliqueLineTextView textView = getViewById(R.id.tv_doorPrice);
        textView.setText("￥" + productInfo.marketPrice + "元");


//        getViewById(R.id.gtv_discount).setVisibility(View.VISIBLE);
//        setText(productInfo.getDiscount() + "折", R.id.gtv_discount);
        setText("门店价" + productInfo.marketPrice + "元", R.id.tv_doorPrice);
        //设置显示比例
//        SetSizeUtils.setSizeOnlyWidthOf(mContext, getViewById(R.id.rl_gradientText), 3, 1, 16, 10);
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = wm.getDefaultDisplay().getWidth();
        int width = displayWidth / 3;
        //设置内容宽度和图片宽度一致
        //设置折扣显示比例16:10
        int discountWidth = width / 2;
        int discountHeight = (width / 2) * 10 / 16;
        RelativeLayout.LayoutParams discountParams = new RelativeLayout.LayoutParams(discountWidth, discountHeight);
//        getViewById(R.id.gtv_discount).setLayoutParams(discountParams);
        //距离底部7/16height
//        getViewById(R.id.gtv_discount).setPadding(0, 0, 0, discountHeight * 7 / 16);


        getViewById(R.id.tv_exchangeNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settleOrder(productInfo.id);

            }
        });

//        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//
//        layoutParams.height = 700;
//
//
//        imageView.setLayoutParams(layoutParams);


    }


    /**
     * 下单
     *
     * @param id
     */
    private void settleOrder(int id) {
        RequestParams params = new RequestParams();
        String cou = 1 + "";
        if (cou.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请选择商品数量");
            return;
        }
        params.put("productId", id);
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


}

