package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.bean.PayOrderResult;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.TransformUtil;
import com.huiche.utils.TwoCodeUtil;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.RotateTransformer;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付成功显示的页面
 *
 * @author Administrator
 */
public class PaySuccessActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private List<PayOrderResult.RowsBean> resultList = new ArrayList<>();
    private RelativeLayout rl_returnHome;
    private TextView tv_titleText;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private LinearLayout ll_vp_container;
    private LinearLayout ll_container_circle;
    private List<ImageView> pointList = new ArrayList<>();

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_pay_success);
        rl_returnHome = (RelativeLayout) findViewById(R.id.rl_returnHome);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        viewPager = (ViewPager) findViewById(R.id.vp_paySuccess);
        ll_vp_container = (LinearLayout) findViewById(R.id.ll_vp_container);
        ll_container_circle = (LinearLayout) findViewById(R.id.ll_container_circle);
    }

    @Override
    public void initData() {
        tv_titleText.setText("支付成功");
        getResult();
    }


    @Override
    public void setListeners() {
        rl_returnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_returnHome:
                //跳转回首页
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();

                break;

            default:
                break;
        }

    }

    /**
     * 请求支付成功信息
     */
    private void getResult() {
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.PAY_SUCCESS, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtils.ToastShowShort(mContext, "网络异常");
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dialog.dialogcancel();
//                response.toString();
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    JSONArray array = response.optJSONArray("rows");
                    resultList = JSON.parseArray(array.toString(), PayOrderResult.RowsBean.class);
                    //设置数据
                    setData();
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                }
            }
        });
    }

    private void setData() {
        if (resultList.size() == 1) {
            ll_container_circle.setVisibility(View.GONE);
        }
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(new MyPagerAdapter());
//        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setPageTransformer(true, new RotateTransformer());
        //设置幕后item的缓存数目
        viewPager.setOffscreenPageLimit(adapter.getCount());
        //设置页与页之间的间距
        int margin = ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).leftMargin;
        viewPager.setPageMargin(-(TransformUtil.dip2px(this, 120)));
        //将父类的touch事件分发至viewPager，否则只能滑动中间的一个view对象
        ll_vp_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return viewPager.dispatchTouchEvent(motionEvent);
            }
        });
        //添加标示小圆点
        for (int i = 0; i < resultList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 30;
            imageView.setLayoutParams(layoutParams);
            //没选中透明白色
            imageView.setImageDrawable(mContext.getResources()
                    .getDrawable(R.drawable.point_select));
            //没选中灰色
//            imageView.setImageDrawable(mContext.getResources()
//                    .getDrawable(R.drawable.circle_point_select));
            ll_container_circle.addView(imageView);
            pointList.add(imageView);
        }
        if (pointList.size() > 0)
            pointList.get(0).setSelected(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pointList.get(position).setSelected(true);
                for (int i = 0; i < pointList.size(); i++) {
                    if (i != position) {
                        pointList.get(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_paysuccess, null);
            //findview
            TextView tv_returnIntegral = (TextView) convertView.findViewById(R.id.tv_returnIntegral_exchangeSuccess);
            TextView tv_storeName = (TextView) convertView.findViewById(R.id.tv_storeName_exchangeSuccess);
            TextView tv_payMoney = (TextView) convertView.findViewById(R.id.tv_payMoney);
            TextView tv_orderNumber = (TextView) convertView.findViewById(R.id.tv_orderNumber);
            TextView tv_checkCode = (TextView) convertView.findViewById(R.id.tv_checkCode);
            ImageView iv_twoCode = (ImageView) convertView.findViewById(R.id.iv_twoCode_exchangeSuccess);
            //setview
            PayOrderResult.RowsBean result = resultList.get(position);
            tv_checkCode.setText(result.getCheckNo());
            tv_orderNumber.setText(result.getNo() + "");
            tv_returnIntegral.setText("返回" + result.getSendIntegral() + "积分");
            tv_storeName.setText(result.getBusinessName());
            tv_payMoney.setText(result.getAllPrice() + "元");
            TwoCodeUtil.setQRImage(tv_checkCode.getText() + "", iv_twoCode);
            //必须add
            container.addView(convertView);
            return convertView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }

}
