package com.huiche.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.PostResult.OnLineUnSendDetail;
import com.huiche.PostResult.OnLineUnSendInfo;
import com.huiche.R;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.WindowUtils;
import com.huiche.view.MyListView;
import com.huiche.view.RebackMoneyDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnLineUnSendAdapter extends BaseAdapter {
    private List<OnLineUnSendInfo> dataList = new ArrayList<OnLineUnSendInfo>();
    private Context mContext;
    private SharedPreferences share;
    RebackMoneyDialog dialog;
    private WindowUtils windowUtil;
    public TextView tv_confirm;
//    public ImageView img_close;
//    public EditText ed_content;

    public OnLineUnSendAdapter(Context mContext, List<OnLineUnSendInfo> dataList) {

        this.mContext = mContext;
        this.dataList = dataList;
        share = mContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        dialog = new RebackMoneyDialog(mContext);
        windowUtil = new WindowUtils(mContext);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_online_unsend, null);
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
            OnLineUnSendProductAdapter adapter = new OnLineUnSendProductAdapter(mContext, data);
            holder.myListview.setAdapter(adapter);

        } else {
            holder.ll_shop.setVisibility(View.GONE);
        }
        //退款
        holder.tv_cancel_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退款窗口
                dialog.showDialog();
                //showNormalDialog();


            }
        });

        dialog.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退款
                String content = dialog.ed_content.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(mContext, "退款理由不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                confirmRebackMoney(position, content);
            }
        });

        //关闭窗口
        dialog.img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismissDialog();
            }
        });


        return convertView;
    }


    //申请退款
    public void confirmRebackMoney(int position, String reason) {
        RequestParams params = new RequestParams();
        params.put("id", dataList.get(position).id);
        params.put("reason", reason);
        String token = share.getString("token", "");
        params.put("token", share.getString("token", token));
        AsyncHttp.posts(HttpConstantChc.ONLINE_REBACK, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismissDialog();
                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    dialog.dismissDialog();
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
                    dialog.dismissDialog();
                }
            }
        });
    }

//    private void showNormalDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        View dialogView = View.inflate(mContext, R.layout.dialog_reback, null);
//        AlertDialog normalDialog = builder.show();
//        int width = windowUtil.getdisplayWidth();
//        int height = windowUtil.getdisplayHeight();
//        Window dialogWindow = normalDialog.getWindow();
//        dialogWindow.setContentView(dialogView);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = (int) (width * 0.8);
//        lp.height = (int) (height * 0.4);
//        dialogWindow.setAttributes(lp);
//        // find控件设置监听
//        tv_confirm = (TextView) dialogView.findViewById(R.id.tv_confirm);
//        img_close = (ImageView) dialogView.findViewById(R.id.img_close);
//        ed_content = (EditText) dialogView.findViewById(R.id.ed_content);
//    }

    public class Holder {
        public TextView tv_order, tv_shop_name, tv_pay_type, tv_date, tv_intenal, tv_button, tv_status;
        public TextView tv_pay_blue, tv_confirm_red, tv_cancel_org;
        public MyListView myListview;
        public LinearLayout ll_shop, ll_tip, ll_wuliu;
        public View line_view;

    }

}
