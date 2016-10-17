package com.huiche.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.PostResult.OnLineUnSendDetail;
import com.huiche.PostResult.OnLineUnSendInfo;
import com.huiche.R;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.view.MyListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnLineUnPayAdapter extends BaseAdapter {
    private List<OnLineUnSendInfo> dataList = new ArrayList<OnLineUnSendInfo>();
    private Context mContext;
    private SharedPreferences share;

    public OnLineUnPayAdapter(Context mContext, List<OnLineUnSendInfo> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        share = mContext.getSharedPreferences("user_data", mContext.MODE_PRIVATE);

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_online_unpay, null);
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
        OnLineUnSendInfo info = dataList.get(position);
        holder.tv_order.setText(info.no);
        holder.tv_shop_name.setText(info.businessName);
        holder.tv_intenal.setText(info.allIntegral);
        String status = info.status;
        if (status.equals("1")) {
            holder.tv_status.setText("待发货");

        } else {
            holder.tv_status.setText("");
        }
        //商品列表
        List<OnLineUnSendDetail> data = new ArrayList<OnLineUnSendDetail>();
        data = info.onlineProducts;
        if (data.size() > 0) {
            holder.ll_shop.setVisibility(View.VISIBLE);
            holder.line_view.setVisibility(View.VISIBLE);
            OnLineUnPayProductAdapter adapter = new OnLineUnPayProductAdapter(mContext, data);
            holder.myListview.setAdapter(adapter);

        } else {
            holder.ll_shop.setVisibility(View.GONE);
        }

        //付款按钮
        holder.tv_pay_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimissDialog();

            }
        });

        //确认收货
        tv_confirm.setOnClickListener(new View.OnClickListener() {
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
                            e.printStackTrace();
                            dimissDialog();
                        }
                    }
                });

            }
        });


        return convertView;
    }

    private Dialog mDialog;
    private TextView dialog_content;
    private TextView tv_cancel;
    private TextView tv_confirm;

    private void initDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay, null);
        dialog_content = (TextView) view.findViewById(R.id.tv_dialogNormal_content);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancelNormal);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirmNormal);
        mDialog = new Dialog(mContext, R.style.customDialog);
        mDialog.setContentView(view);
        Window diawindow = mDialog.getWindow();

        WindowManager m = mDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);
    }

    private void showDialog() {
        mDialog.show();
    }

    private void dimissDialog() {
        mDialog.dismiss();
    }


    public class Holder {
        public TextView tv_order, tv_shop_name, tv_pay_type, tv_date, tv_intenal, tv_button, tv_status;
        public TextView tv_pay_blue, tv_confirm_red, tv_cancel_org;
        public MyListView myListview;
        public LinearLayout ll_shop, ll_tip, ll_wuliu;
        public View line_view;

    }


}
