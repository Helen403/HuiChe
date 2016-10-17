package com.huiche.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.PostResult.OnLineAllOrderDetail;
import com.huiche.PostResult.OnLineAllOrderInfo;
import com.huiche.R;
import com.huiche.activity.mine.WebViewActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ToastUtils;
import com.huiche.view.MyListView;
import com.huiche.view.RebackMoneyDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnLineAllOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<OnLineAllOrderInfo> dataList = new ArrayList<OnLineAllOrderInfo>();
    public RebackMoneyDialog mDialog;
    private SharedPreferences share;

    public OnLineAllOrderAdapter(Context mContext, List<OnLineAllOrderInfo> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        mDialog = new RebackMoneyDialog(mContext);
        share = mContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int position) {

        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    Holder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_online_all_order, null);
            holder = new Holder();
            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_orderID);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.myListview = (MyListView) convertView.findViewById(R.id.myListview);
            holder.tv_intenal = (TextView) convertView.findViewById(R.id.tv_intenal);
            holder.ll_shop = (LinearLayout) convertView.findViewById(R.id.ll_shop);
            holder.ll_tip = (LinearLayout) convertView.findViewById(R.id.ll_tip);
            holder.line_view = (View) convertView.findViewById(R.id.line_view);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_cancel_org = (TextView) convertView.findViewById(R.id.tv_cancel_org);
            holder.tv_pay_blue = (TextView) convertView.findViewById(R.id.tv_pay_blue);
            holder.tv_confirm_red = (TextView) convertView.findViewById(R.id.tv_confirm_red);
            holder.ll_wuliu = (LinearLayout) convertView.findViewById(R.id.ll_wuliu);
            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }
        initDialog();

        holder.tv_cancel_org.setVisibility(View.GONE);
        holder.tv_pay_blue.setVisibility(View.GONE);
        holder.tv_confirm_red.setVisibility(View.GONE);
        holder.ll_tip.setVisibility(View.GONE);
        holder.line_view.setVisibility(View.GONE);
        holder.ll_wuliu.setVisibility(View.GONE);

        final OnLineAllOrderInfo lineOrderInfo = dataList.get(position);
        int length = dataList.size();
        String ss = lineOrderInfo.id;
        holder.tv_order.setText(lineOrderInfo.no);
        holder.tv_shop_name.setText(lineOrderInfo.businessName);
        holder.tv_intenal.setText(lineOrderInfo.allIntegral);

        String status = lineOrderInfo.status;
        //status订单状态（0：未付款，1：待发货， 2：已取消， 3：已关闭，4：已完成，5：待确认 ）
        if (status.equals("0")) {
            holder.tv_status.setText("未付款");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.tv_red));
            holder.ll_wuliu.setVisibility(View.GONE);
            holder.tv_confirm_red.setVisibility(View.GONE);
            holder.tv_cancel_org.setVisibility(View.GONE);
            holder.tv_pay_blue.setVisibility(View.VISIBLE);
        } else if (status.equals("1")) {
            holder.tv_status.setText("待发货");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.tv_red));
            holder.ll_wuliu.setVisibility(View.GONE);
            holder.tv_confirm_red.setVisibility(View.GONE);
            holder.tv_cancel_org.setVisibility(View.VISIBLE);
            holder.tv_pay_blue.setVisibility(View.GONE);
        } else if (status.equals("2")) {
            holder.tv_status.setText("已取消");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.tv_red));
            holder.ll_wuliu.setVisibility(View.GONE);
            holder.tv_cancel_org.setVisibility(View.GONE);
            holder.tv_pay_blue.setVisibility(View.GONE);
            holder.tv_confirm_red.setVisibility(View.GONE);
        } else if (status.equals("3")) {
            holder.tv_status.setText("已关闭");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.tv_red));
            holder.ll_wuliu.setVisibility(View.GONE);
            holder.tv_cancel_org.setVisibility(View.GONE);
            holder.tv_pay_blue.setVisibility(View.GONE);
            holder.tv_confirm_red.setVisibility(View.GONE);
        } else if (status.equals("4")) {
            holder.tv_status.setText("已完成");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.bule_title));
            holder.ll_wuliu.setVisibility(View.GONE);
            holder.tv_cancel_org.setVisibility(View.GONE);
            holder.tv_pay_blue.setVisibility(View.GONE);
            holder.tv_confirm_red.setVisibility(View.GONE);
        } else if (status.equals("5")) {
            holder.tv_status.setText("待确认");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.tv_org));
            holder.ll_wuliu.setVisibility(View.VISIBLE);
            holder.tv_confirm_red.setVisibility(View.VISIBLE);
            holder.tv_cancel_org.setVisibility(View.GONE);
            holder.tv_pay_blue.setVisibility(View.GONE);
        }


        //商品列表
        List<OnLineAllOrderDetail> list = new ArrayList<OnLineAllOrderDetail>();
        list = lineOrderInfo.onlineProducts;
        if (list.size() > 0) {
            holder.ll_shop.setVisibility(View.VISIBLE);
            holder.line_view.setVisibility(View.VISIBLE);
            OnLineAllOrderProductAdapter adapter = new OnLineAllOrderProductAdapter(mContext, list);
            holder.myListview.setAdapter(adapter);

        } else {
            holder.ll_shop.setVisibility(View.GONE);
        }

        //商品退款
        holder.tv_cancel_org.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //退款窗口
                mDialog.showDialog();

            }
        });
        //关闭窗口
        mDialog.img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismissDialog();
            }
        });

        //确认退款
        mDialog.tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = dataList.get(position).id;
                String content = mDialog.ed_content.getText().toString();
                if (content.equals("")) {
                    ToastUtils.ToastShowShort(mContext, "请填写您的退款理由");
                    return;
                } else {
                    mDialog.dismissDialog();
                    refundOrder(id, content);
                }
            }
        });
        //确认收货
        holder.tv_confirm_red.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dimissDialog();

            }
        });

        //确认收货
        tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("id", dataList.get(position).id);
                String token = share.getString("token", "");
                params.put("token", share.getString("token", token));
                AsyncHttp.posts(HttpConstantChc.SURE_RECEIVE_GOODS, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        dimissDialog();
                        Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
                        try {
                            dimissDialog();
                            String status = response.getString("status");
                            String msg = response.getString("msg");
                            if (status.equals("0")) {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            dimissDialog();
                        }
                    }
                });
            }
        });

        //查看物流
        holder.ll_wuliu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                String url = HttpConstantChc.HTTP_HEAD + "mobile/logistics.html?id=" + (dataList.get(position).getId()) + "&dingno=" + (dataList.get(position).getNo());
                intent.putExtra("url", url);
                intent.putExtra("title", "我的物流");
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    //退款
    protected void refundOrder(String id, String content) {

        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("reason", content);
        String token = share.getString("token", "");
        params.put("token", share.getString("token", token));
        AsyncHttp.posts(HttpConstantChc.ONLINE_REBACK, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                mDialog.dismissDialog();
                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    mDialog.dismissDialog();
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    mDialog.dismissDialog();
                }
            }
        });


    }

    public class Holder {
        public TextView tv_order, tv_shop_name, tv_pay_type, tv_date, tv_intenal, tv_button, tv_status;
        public TextView tv_pay_blue, tv_confirm_red, tv_cancel_org;
        public MyListView myListview;
        public LinearLayout ll_shop, ll_tip, ll_wuliu;
        public View line_view;

    }

    private Dialog payDialog;
    private TextView dialog_content;
    private TextView tv_cancel;
    private TextView tv_confirm;

    private void initDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay, null);
        dialog_content = (TextView) view.findViewById(R.id.tv_dialogNormal_content);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancelNormal);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirmNormal);
        payDialog = new Dialog(mContext, R.style.customDialog);
        payDialog.setContentView(view);
        Window diawindow = payDialog.getWindow();

        WindowManager m = payDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = payDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);
    }

    private void showDialog() {
        payDialog.show();
    }

    private void dimissDialog() {
        payDialog.dismiss();
    }

}
