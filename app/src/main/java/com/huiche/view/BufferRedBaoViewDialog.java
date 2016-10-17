package com.huiche.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.MainActivity;
import com.huiche.activity.mine.WebViewActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.RedBaoDetailFillBean;
import com.huiche.bean.RequestBaoBean;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.LoadImageUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


/**
 * @author Administrator
 *         卡卷领取提示
 */
public class BufferRedBaoViewDialog extends RelativeLayout {
    private BufferRedBaoViewDialog view;
    private CircleImageView icon;
    private LoadImageUtil loadImageUtil = new LoadImageUtil();
    private TextView tv, introduction;

    private ImageView imageView, img, close;

    private RequestBaoBean requestBaoBean;

    private ImageView imageViewTE;

    public BufferRedBaoViewDialog(Context context) {
        super(context);
        view = (BufferRedBaoViewDialog) View.inflate(getContext(), R.layout.dialog_red_bao_custom_show_share, this);

        imageView = (ImageView) findViewById(R.id.iv_cai);


        icon = (CircleImageView) findViewById(R.id.icon);
        icon.setImageResource(R.drawable.user_comments_head);
        icon.setBorderWidth(0);
        icon.setBorderColor(Color.parseColor("#00000000"));
        tv = (TextView) findViewById(R.id.businessname);

        img = (ImageView) findViewById(R.id.img);
        introduction = (TextView) findViewById(R.id.introduction);
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(GONE);
            }
        });


        this.imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                /**
                 * 用户拆红包则请求领取接口：
                 */
                RequestParams params = new RequestParams();

                if (requestBaoBean == null) return;
                if (requestBaoBean.rows == null) return;
               final BufferCircleDialog bufferCircleDialog=new BufferCircleDialog(getContext());
                bufferCircleDialog.show("",true,null);
                params.put("showmanshipId", requestBaoBean.rows.id);  //
                if (!MyApplication.token.equals("")) {
                    params.put("token", MyApplication.token);
                }
                AsyncHttp.posts(HttpConstant.USER_REQUEST_RED_BAO, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                bufferCircleDialog.dialogcancel();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // Log.i("Helen", response.toString());
                                bufferCircleDialog.dialogcancel();
                                String status = response.optString("status");

                                imageViewTE.setImageResource(R.drawable.round_button);
                                imageViewTE.setTag("1");
                                //返回的status，为0则领取成功，跳转到红包详情页面图1.3。
                                if (status.equals("0")) {
                                    RedBaoDetailFillBean redBaoDetailFillBean = JSON.parseObject(response.toString(),
                                            RedBaoDetailFillBean.class);
                                    if (redBaoDetailFillBean == null) return;
                                    if (redBaoDetailFillBean.rows == null) return;
                                    //跳转到webview
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), WebViewActivity.class);
                                    //需要拼接  "bId=180&businessName=%E4%BD%95%E5%BA%B7%E7%9A%84%E5%BA%97&businessStoreImage=https://static.51ujf.cn/image/activities/1444881764082.jpg&image=https://static.51ujf.cn/image/ad/subject/1473040831036.png";
                                    //&id=861&integral=3.6&v=1000"
                                    intent.putExtra("url", HttpConstantChc.RED_PACK_DETAIL + "bId=" + redBaoDetailFillBean.rows.businessStoreId + "&businessName=" + redBaoDetailFillBean.rows.businessName +
                                            "&businessStoreImage=" + redBaoDetailFillBean.rows.businessStoreImage + "&image=" + redBaoDetailFillBean.rows.image + "&id=" + requestBaoBean.rows.id + "&integral=" + redBaoDetailFillBean.rows.integral + "&v=1000");
                                    intent.putExtra("title", "红包详情");
                                    getContext().startActivity(intent);
//                                    Intent intent = new Intent(getContext(), RedBaoDetailActivity.class);
//                                    intent.putExtra("redBaoDetailFillBean", redBaoDetailFillBean);
//                                    getContext().startActivity(intent);
                                    view.setVisibility(GONE);
                                    //请求完红包后
                                    MainActivity.isHasRed = false;
                                    MainActivity.isRecieve = true;
                                }
                                //判断返回的status，为1则弹出“您来晚啦，红包领完了！”。然后关闭图1.2和遮罩层，把图1.1的红色红包小图标恢复到图1.0的卡券图标。继续请求红包接口。
                                if (status.equals("1")) {
                                    //您来晚啦，红包领完了！
                                    BufferViewDialog bufferViewDialog = new BufferViewDialog(getContext());
                                    bufferViewDialog.show(view, "您来晚啦，红包领完了！", true, null);

                                }
                            }
                        }
                );


            }
        });
    }


    /**
     * 设置数据
     */
    public void setData(RequestBaoBean requestBaoBean) {
        this.requestBaoBean = requestBaoBean;
        //设置头像
        loadImageUtil.imageLoader.displayImage(requestBaoBean.rows.image, icon);
        //设置商家名称
        tv.setText(requestBaoBean.rows.businessStoreName);
        //设置对应的大图
        loadImageUtil.imageLoader.displayImage(requestBaoBean.rows.businessStoreImage, img);
        //设置对应的内容
        introduction.setText(requestBaoBean.rows.context);
    }

    public void setImageView(ImageView imageView) {
        imageViewTE = imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;

//        return super.onTouchEvent(event);
    }
}
